package vn.com.gsoft.inventory.service.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.inventory.constant.*;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.entity.Process;
import vn.com.gsoft.inventory.model.dto.*;
import vn.com.gsoft.inventory.model.dto.InventoryReq;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.dto.ReportImage;
import vn.com.gsoft.inventory.model.system.ApplicationSetting;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.model.system.WrapData;
import vn.com.gsoft.inventory.repository.*;
import vn.com.gsoft.inventory.service.ApplicationSettingService;
import vn.com.gsoft.inventory.service.KafkaProducer;
import vn.com.gsoft.inventory.service.PhieuNhapsService;
import vn.com.gsoft.inventory.service.PhieuXuatsService;
import vn.com.gsoft.inventory.util.system.FileUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
@Log4j2
public class PhieuXuatsServiceImpl extends BaseServiceImpl<PhieuXuats, PhieuXuatsReq, Long> implements PhieuXuatsService {
    private final SampleNoteRepository sampleNoteRepository;
    private final SampleNoteDetailRepository sampleNoteDetailRepository;
    private PhieuXuatsRepository hdrRepo;
    private PhieuXuatChiTietsRepository phieuXuatChiTietsRepository;
    private ApplicationSettingService applicationSettingService;
    private KhachHangsRepository khachHangsRepository;
    private BacSiesRepository bacSiesRepository;
    private NhaCungCapsRepository nhaCungCapsRepository;
    private PhieuNhapsService phieuNhapsService;
    private PhieuNhapsRepository phieuNhapsRepository;
    private PhieuThuChisRepository phieuThuChisRepository;
    private PaymentTypeRepository paymentTypeRepository;
    private UserProfileRepository userProfileRepository;
    private ThuocsRepository thuocsRepository;
    private DonViTinhsRepository donViTinhsRepository;
    private InventoryRepository inventoryRepository;
    private NhaThuocsRepository nhaThuocsRepository;
    private PickUpOrderRepository pickUpOrderRepository;
    private ConfigTemplateRepository configTemplateRepository;
    private KafkaProducer kafkaProducer;
    private LoaiXuatNhapsRepository loaiXuatNhapsRepository;
    @Value("${wnt.kafka.internal.consumer.topic.inventory}")
    private String topicName;
    @Value("${wnt.kafka.internal.consumer.topic.import-trans}")
    private String topicNameImport;

    @Autowired
    public PhieuXuatsServiceImpl(PhieuXuatsRepository hdrRepo, ApplicationSettingService applicationSettingService,
                                 KhachHangsRepository khachHangsRepository, NhaCungCapsRepository nhaCungCapsRepository,
                                 PhieuXuatChiTietsRepository phieuXuatChiTietsRepository,
                                 PaymentTypeRepository paymentTypeRepository,
                                 UserProfileRepository userProfileRepository,
                                 ThuocsRepository thuocsRepository,
                                 DonViTinhsRepository donViTinhsRepository,
                                 InventoryRepository inventoryRepository,
                                 NhaThuocsRepository nhaThuocsRepository,
                                 PickUpOrderRepository pickUpOrderRepository,
                                 PhieuNhapsRepository phieuNhapsRepository,
                                 PhieuThuChisRepository phieuThuChisRepository,
                                 BacSiesRepository bacSiesRepository,
                                 PhieuNhapsService phieuNhapsService,
                                 LoaiXuatNhapsRepository loaiXuatNhapsRepository,
                                 KafkaProducer kafkaProducer,
                                 ConfigTemplateRepository configTemplateRepository, SampleNoteRepository sampleNoteRepository, SampleNoteDetailRepository sampleNoteDetailRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.applicationSettingService = applicationSettingService;
        this.khachHangsRepository = khachHangsRepository;
        this.nhaCungCapsRepository = nhaCungCapsRepository;
        this.phieuNhapsService = phieuNhapsService;
        this.pickUpOrderRepository = pickUpOrderRepository;
        this.kafkaProducer = kafkaProducer;
        this.phieuXuatChiTietsRepository = phieuXuatChiTietsRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.userProfileRepository = userProfileRepository;
        this.thuocsRepository = thuocsRepository;
        this.donViTinhsRepository = donViTinhsRepository;
        this.nhaThuocsRepository = nhaThuocsRepository;
        this.inventoryRepository = inventoryRepository;
        this.phieuNhapsRepository = phieuNhapsRepository;
        this.phieuThuChisRepository = phieuThuChisRepository;
        this.loaiXuatNhapsRepository = loaiXuatNhapsRepository;
        this.bacSiesRepository = bacSiesRepository;
        this.configTemplateRepository = configTemplateRepository;
        this.sampleNoteRepository = sampleNoteRepository;
        this.sampleNoteDetailRepository = sampleNoteDetailRepository;
    }

    @Override
    public Page<PhieuXuats> searchPage(PhieuXuatsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        if (StringUtils.isEmpty(req.getNhaThuocMaNhaThuoc())) {
            req.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        Page<PhieuXuats> phieuXuats = hdrRepo.searchPage(req, pageable);
        for (PhieuXuats px : phieuXuats.getContent()) {
            if (px.getMaLoaiXuatNhap() != null && px.getMaLoaiXuatNhap() > 0) {
                px.setMaLoaiXuatNhapText(this.loaiXuatNhapsRepository.findById(px.getMaLoaiXuatNhap()).get().getTenLoaiXuatNhap());
            }
            if (px.getKhachHangMaKhachHang() != null && px.getKhachHangMaKhachHang() > 0) {
                px.setKhachHangMaKhachHangText(this.khachHangsRepository.findById(px.getKhachHangMaKhachHang()).get().getTenKhachHang());
            }
            if (px.getCreatedByUserId() != null && px.getCreatedByUserId() > 0) {
                px.setCreatedByUserText(this.userProfileRepository.findById(px.getCreatedByUserId()).get().getTenDayDu());
            }
            if (px.getTargetStoreId() != null && px.getTargetStoreId() > 0) {
                Optional<NhaThuocs> byId = nhaThuocsRepository.findById(px.getTargetStoreId());
                byId.ifPresent(nhaThuocs -> px.setTargetStoreText(nhaThuocs.getTenNhaThuoc()));
            }
            if (px.getNhaCungCapMaNhaCungCap() != null && px.getNhaCungCapMaNhaCungCap() > 0) {
                px.setNhaCungCapMaNhaCungCapText(this.nhaCungCapsRepository.findById(px.getNhaCungCapMaNhaCungCap()).get().getTenNhaCungCap());
            }
        }

        return phieuXuats;
    }

    @Override
    public List<PhieuXuats> searchList(PhieuXuatsReq req) throws Exception {
        if (StringUtils.isEmpty(req.getNhaThuocMaNhaThuoc())) {
            req.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        return hdrRepo.searchList(req);
    }

    @Override
    public PhieuXuats init(Long maLoaiXuatNhap, Long id) throws Exception {
        Profile currUser = getLoggedUser();
        String storeCode = currUser.getNhaThuoc().getMaNhaThuoc();
        List<ApplicationSetting> applicationSetting = currUser.getApplicationSettings();
        PhieuXuats data = null;
        if (id == null) {
            data = new PhieuXuats();
            Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax(storeCode, maLoaiXuatNhap);
            if (soPhieuXuat == null) {
                soPhieuXuat = 0L;
            }
            data.setSoPhieuXuat(soPhieuXuat + 1);
            data.setUId(UUID.randomUUID());
            data.setNgayXuat(new Date());

            if (Objects.equals(maLoaiXuatNhap, ENoteType.ReturnToSupplier)) {
                // tìm nhà cung cấp nhập lẻ
                Optional<NhaCungCaps> ncc = this.nhaCungCapsRepository.findKhachHangLe(storeCode);
                if (ncc.isPresent()) {
                    data.setNhaCungCapMaNhaCungCap(ncc.get().getId());
                } else {
                    throw new Exception("Không tìm thấy khách hàng lẻ!");
                }
            } else if (Objects.equals(maLoaiXuatNhap, ENoteType.Delivery)) {
                // tìm khách hàng lẻ
                Optional<KhachHangs> kh = this.khachHangsRepository.findKhachHangLe(storeCode);
                if (kh.isPresent()) {
                    data.setKhachHangMaKhachHang(kh.get().getId());
                } else {
                    throw new Exception("Không tìm thấy khách hàng lẻ!");
                }
            }

        } else {
            Optional<PhieuXuats> phieuXuats = hdrRepo.findById(id);
            if (phieuXuats.isPresent()) {
                data = phieuXuats.get();
                data.setId(null);
                Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax(storeCode, maLoaiXuatNhap);
                if (soPhieuXuat == null) {
                    soPhieuXuat = 0L;
                }
                data.setUId(UUID.randomUUID());
                data.setSoPhieuXuat(soPhieuXuat + 1);
                data.setNgayXuat(new Date());
                data.setCreatedByUserId(null);
                data.setModifiedByUserId(null);
                data.setCreated(null);
                data.setModified(null);
            } else {
                throw new Exception("Không tìm thấy phiếu copy!");
            }
        }
        return data;
    }

    @Override
    public PhieuXuats lock(Long id) throws Exception {
        PhieuXuats detail = detail(id);
        detail.setLocked(true);
        hdrRepo.save(detail);
        return detail;
    }

    @Override
    public PhieuXuats unlock(Long id) throws Exception {
        PhieuXuats detail = detail(id);
        detail.setLocked(false);
        hdrRepo.save(detail);
        return detail;
    }

    @Override
    public Boolean sync(String nhaThuocMaNhaThuoc, List<Long> listIds) throws Exception {
        List<PhieuXuats> pxs;
        List<Long> synStatusIds = List.of(ESynStatus.NotSyn, ESynStatus.Failed);
        if (listIds.isEmpty()) {
            pxs = hdrRepo.findByNhaThuocMaNhaThuocAndMaLoaiXuatNhapAndSynStatusIdIn(nhaThuocMaNhaThuoc, ENoteType.Delivery, synStatusIds);
        } else {
            pxs = hdrRepo.findByNhaThuocMaNhaThuocAndIdInAndMaLoaiXuatNhapAndSynStatusIdIn(nhaThuocMaNhaThuoc, listIds, ENoteType.Delivery, synStatusIds);
        }
        Set<Long> idNhaThuocs = pxs.stream().map(PhieuXuats::getTargetStoreId).collect(Collectors.toSet());
        List<NhaThuocs> nhaThuocs = nhaThuocsRepository.findAllByIdIn(idNhaThuocs.stream().toList());
        if (nhaThuocs.stream().anyMatch(x -> x.getIsUploading() != null && x.getIsUploading())) {
            throw new Exception("Hệ thống đang xử lý việc upload , đồng bộ bạn vui lòng chờ hệ thống xử lý xong mới có thể upload , đồng bộ tiếp.");
        }
        List<PhieuNhaps> pns = new ArrayList<>();
        for (PhieuXuats px : pxs) {
            PhieuNhaps pn = this.phieuNhapsService.createByPhieuXuats(px);
            if (pn != null) {
                pns.add(pn);
            }
        }

        return true;
    }

    @Override
    public Boolean resetSync(List<Long> ids) throws Exception {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                PhieuXuats detail = detail(id);
                detail.setSynStatusId(ESynStatus.NotSyn);
                hdrRepo.save(detail);
            }
        }

        return true;
    }

    @Override
    public PhieuXuats medicineSync(Long id) {
        return null;
    }

    @Override
    public PhieuXuats approve(Long id) throws Exception {
        PhieuXuats detail = getDetail(id);
        detail.setRecordStatusId(RecordStatusContains.ACTIVE);
        hdrRepo.save(detail);
        for (PhieuXuatChiTiets ct : detail.getChiTiets()) {
            ct.setRecordStatusId(RecordStatusContains.ACTIVE);
            phieuXuatChiTietsRepository.save(ct);
        }
        updateInventory(detail);
        return detail;
    }

    @Override
    public PhieuXuats cancel(Long id) throws Exception {
        PhieuXuats detail = getDetail(id);
        detail.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(detail);
        for (PhieuXuatChiTiets ct : detail.getChiTiets()) {
            ct.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
            phieuXuatChiTietsRepository.save(ct);
        }
        updateInventory(detail);
        return detail;
    }

    @Override
    public Double getTotalDebtAmountCustomer(String maNhaThuoc, Long customerId, Date ngayTinhNo) {
        if (ngayTinhNo == null) {
            ngayTinhNo = new Date();
        }
        double result = 0;
        List<Integer> typePx = List.of(ENoteType.Delivery, ENoteType.InitialSupplierDebt);
        List<Long> statusPx = List.of(RecordStatusContains.ACTIVE, RecordStatusContains.ARCHIVED);
        Date finalNgayTinhNo = ngayTinhNo;
        List<PhieuXuats> deliveryNoteService = hdrRepo.findByNhaThuocMaNhaThuocAndKhachHangMaKhachHangAndMaLoaiXuatNhapInAndRecordStatusIdIn(maNhaThuoc, customerId, typePx, statusPx)
                .stream()
                .filter(x -> (x.getTongTien() - x.getDaTra() - x.getPaymentScoreAmount() - x.getDiscount()) > 0)
                .filter(x -> (x.getNgayXuat() != null && x.getNgayXuat().before(finalNgayTinhNo)))
                .toList();

        List<PhieuNhaps> returnNoteCus = phieuNhapsRepository.findByNhaThuocMaNhaThuocAndKhachHangMaKhachHangAndRecordStatusId(maNhaThuoc, customerId, ENoteType.ReturnFromCustomer)
                .stream()
                .filter(x -> (x.getTongTien() - x.getDaTra()) > 0)
                .filter(x -> (x.getNgayNhap() != null && x.getNgayNhap().before(finalNgayTinhNo)))
                .toList();

        List<Integer> statusPtc = List.of(InOutCommingType.Incomming, InOutCommingType.OutReturnCustomer);
        List<PhieuThuChis> inOutNotes = phieuThuChisRepository.findByNhaThuocMaNhaThuocAndKhachHangMaKhachHangAndLoaiPhieuIn(maNhaThuoc, customerId, statusPtc)
                .stream()
                .filter(x -> (x.getNgayTao() != null && x.getNgayTao().before(finalNgayTinhNo)))
                .toList();


        if (!deliveryNoteService.isEmpty()) {
            result = deliveryNoteService.stream()
                    .mapToDouble(i -> i.getTongTien() - i.getDaTra() - i.getPaymentScoreAmount() - i.getDiscount())
                    .sum();
        }
        if (!returnNoteCus.isEmpty()) {
            result -= returnNoteCus.stream()
                    .mapToDouble(x -> x.getTongTien() - x.getDaTra())
                    .sum();
        }

        if (!inOutNotes.isEmpty()) {
            if (inOutNotes.stream().anyMatch(x -> Objects.equals(x.getLoaiPhieu(), InOutCommingType.Incomming))) {
                result -= inOutNotes.stream()
                        .filter(x -> Objects.equals(x.getLoaiPhieu(), InOutCommingType.Incomming))
                        .mapToDouble(PhieuThuChis::getAmount)
                        .sum();
            }

            if (inOutNotes.stream().anyMatch(x -> Objects.equals(x.getLoaiPhieu(), InOutCommingType.OutReturnCustomer))) {
                result += inOutNotes.stream()
                        .filter(x -> Objects.equals(x.getLoaiPhieu(), InOutCommingType.OutReturnCustomer))
                        .mapToDouble(PhieuThuChis::getAmount)
                        .sum();
            }

        }
        return result;
    }

    @Override
    @Transactional
    public PhieuXuats create(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setStoreId(userInfo.getNhaThuoc().getId());
        req.setSourceStoreId(userInfo.getNhaThuoc().getId());
        req.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        req.setLocked(false);
        if (req.getConnectivityStatusID() == null) {
            req.setConnectivityStatusID(0l);
        }

        List<PhieuXuats> phieuXuat = hdrRepo.findByNhaThuocMaNhaThuocAndSoPhieuXuatAndMaLoaiXuatNhap(req.getNhaThuocMaNhaThuoc(), req.getSoPhieuXuat(), req.getMaLoaiXuatNhap());
        if (!phieuXuat.isEmpty()) {
            throw new Exception("Số phiếu đã tồn tại!");
        }
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && req.getTargetStoreId() == null) {
            throw new Exception("TargetStoreId không được để trống");
        }
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        PhieuXuats e = new PhieuXuats();
        BeanUtils.copyProperties(req, e, "id");
        e.setCreated(new Date());
        e.setCreatedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        // save chi tiết
        for (PhieuXuatChiTiets chiTiet : e.getChiTiets()) {
            chiTiet.setNhaThuocMaNhaThuoc(e.getNhaThuocMaNhaThuoc());
            chiTiet.setStoreId(e.getStoreId());
            chiTiet.setPhieuXuatMaPhieuXuat(e.getId());
            chiTiet.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        this.phieuXuatChiTietsRepository.saveAll(e.getChiTiets());
        // xử lý phiếu chuyển kho
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer)) {
            PhieuNhaps phieuNhap = this.phieuNhapsService.createByPhieuXuats(e);
            e.setTargetId(phieuNhap.getId());
        }
        // xử lý xuất kho
        updateInventory(e);
        if (e.getPickUpOrderId() != null) {
            updateHandleOrder(e);
        }
        return e;
    }

    private void updateHandleOrder(PhieuXuats phieuXuats) {
        Optional<PickUpOrder> pickUpOrder = pickUpOrderRepository.findById(phieuXuats.getPickUpOrderId());
        if (pickUpOrder.isPresent()) {
            PickUpOrder data = pickUpOrder.get();
            data.setOrderStatusId(40L);
        }
    }


    @Override
    @Transactional
    public PhieuXuats update(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setStoreId(userInfo.getNhaThuoc().getId());
        req.setSourceStoreId(userInfo.getNhaThuoc().getId());
        req.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        if (req.getConnectivityStatusID() == null) {
            req.setConnectivityStatusID(0l);
        }
        Optional<PhieuXuats> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getSoPhieuXuat().equals(req.getSoPhieuXuat())) {
            List<PhieuXuats> phieuXuat = hdrRepo.findByNhaThuocMaNhaThuocAndSoPhieuXuatAndMaLoaiXuatNhap(req.getNhaThuocMaNhaThuoc(), req.getSoPhieuXuat(), req.getMaLoaiXuatNhap());
            if (!phieuXuat.isEmpty()) {
                throw new Exception("Số phiếu đã tồn tại!");
            }
        }

        boolean normalUser = "User".equals(userInfo.getNhaThuoc().getRole());

        if (optional.get().getLocked() != null && optional.get().getLocked() && !normalUser) {
            throw new Exception("Phiếu đã được khóa!");
        }
        if (optional.get().getRecordStatusId() == RecordStatusContains.ARCHIVED) {
            throw new Exception("Không thể chỉnh sửa phiếu đã sao lưu.");
        }
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && req.getTargetStoreId() == null) {
            throw new Exception("TargetStoreId không được để trống");
        }

        PhieuXuats e = new PhieuXuats();
        BeanUtils.copyProperties(req, e);
        e.setCreated(optional.get().getCreated());
        e.setCreatedByUserId(optional.get().getCreatedByUserId());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        e.setModified(new Date());
        e.setModifiedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        // save chi tiết
        this.phieuXuatChiTietsRepository.deleteByPhieuXuatMaPhieuXuat(e.getId());
        e.setChiTiets(req.getChiTiets());
        for (PhieuXuatChiTiets chiTiet : e.getChiTiets()) {
            chiTiet.setNhaThuocMaNhaThuoc(e.getNhaThuocMaNhaThuoc());
            chiTiet.setStoreId(e.getStoreId());
            chiTiet.setPhieuXuatMaPhieuXuat(e.getId());
            chiTiet.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        this.phieuXuatChiTietsRepository.saveAll(e.getChiTiets());
        // xử lý phiếu chuyển kho
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer)) {
            PhieuNhaps phieuNhap = this.phieuNhapsService.updateByPhieuXuats(e);
            e.setTargetId(phieuNhap.getId());
        }
        // xử lý xuất kho
        updateInventory(e);
        return e;
    }

    @Override
    public PhieuXuats detail(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PhieuXuats> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        } else {
            if (optional.get().getRecordStatusId() != RecordStatusContains.ACTIVE) {
                throw new Exception("Không tìm thấy dữ liệu.");
            }
            List<PhieuXuatChiTiets> phieuXuatMaPhieuXuat = phieuXuatChiTietsRepository.findByPhieuXuatMaPhieuXuatAndRecordStatusId(optional.get().getId(), RecordStatusContains.ACTIVE);
            phieuXuatMaPhieuXuat = phieuXuatMaPhieuXuat.stream().filter(item -> RecordStatusContains.ACTIVE == item.getRecordStatusId()).collect(Collectors.toList());
            optional.get().setChiTiets(phieuXuatMaPhieuXuat);
            if (optional.get().getKhachHangMaKhachHang() != null && optional.get().getKhachHangMaKhachHang() > 0) {
                Optional<KhachHangs> byId = khachHangsRepository.findById(optional.get().getKhachHangMaKhachHang());
                if (byId.isPresent()) {
                    optional.get().setKhachHangMaKhachHangText(byId.get().getTenKhachHang());
                    optional.get().setDiaChiKhachHang(byId.get().getDiaChi());
                    optional.get().setSdtKhachHang(byId.get().getSoDienThoai());
                    optional.get().setTaxCode(byId.get().getTaxCode());
                    optional.get().setScores(byId.get().getScore());
                }
            }
            if (optional.get().getBacSyMaBacSy() != null && optional.get().getBacSyMaBacSy() > 0) {
                optional.get().setBacSyMaBacSyText(this.bacSiesRepository.findById(optional.get().getBacSyMaBacSy()).get().getTenBacSy());
            }
            if (optional.get().getTargetStoreId() != null && optional.get().getTargetStoreId() > 0) {
                Optional<NhaThuocs> byId = nhaThuocsRepository.findById(optional.get().getTargetStoreId());
                if (byId.isPresent()) {
                    optional.get().setTargetStoreText(byId.get().getTenNhaThuoc());
                    optional.get().setDiaChiNhaThuoc(byId.get().getDiaChi());
                    optional.get().setSdtNhaThuoc(byId.get().getDienThoai());
                }
            }
            if (optional.get().getNhaCungCapMaNhaCungCap() != null && optional.get().getNhaCungCapMaNhaCungCap() > 0) {
                Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(optional.get().getNhaCungCapMaNhaCungCap());
                if (byId.isPresent()) {
                    optional.get().setNhaCungCapMaNhaCungCapText(byId.get().getTenNhaCungCap());
                    optional.get().setDiaChiNhaCungCap(byId.get().getDiaChi());
                }
            }
            if (optional.get().getPaymentTypeId() != null && optional.get().getPaymentTypeId() > 0) {
                optional.get().setPaymentTypeText(this.paymentTypeRepository.findById(optional.get().getPaymentTypeId()).get().getDisplayName());
            }
            if (optional.get().getCreatedByUserId() != null && optional.get().getCreatedByUserId() > 0) {
                optional.get().setCreatedByUserText(this.userProfileRepository.findById(optional.get().getCreatedByUserId()).get().getTenDayDu());
            }
            for (PhieuXuatChiTiets ct : optional.get().getChiTiets()) {
                if (ct.getThuocThuocId() != null && ct.getThuocThuocId() > 0) {
                    Optional<Thuocs> thuocsOpt = thuocsRepository.findById(ct.getThuocThuocId());
                    if (thuocsOpt.isPresent()) {
                        Thuocs thuocs = thuocsOpt.get();
                        ct.setMaThuocText(thuocs.getMaThuoc());
                        ct.setTenThuocText(thuocs.getTenThuoc());
                        List<DonViTinhs> dviTinh = new ArrayList<>();
                        if (thuocs.getDonViXuatLeMaDonViTinh() != null && thuocs.getDonViXuatLeMaDonViTinh() > 0) {
                            Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViXuatLeMaDonViTinh());
                            if (byId.isPresent()) {
                                byId.get().setFactor(1);
                                byId.get().setGiaBan(BigDecimal.valueOf(ct.getGiaXuat()));
                                dviTinh.add(byId.get());
                                thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
                            }
                        }
                        if (thuocs.getDonViThuNguyenMaDonViTinh() != null && thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())) {
                            Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViThuNguyenMaDonViTinh());
                            if (byId.isPresent()) {
                                byId.get().setFactor(thuocs.getHeSo());
                                byId.get().setGiaBan(BigDecimal.valueOf(ct.getGiaXuat()).multiply(BigDecimal.valueOf(thuocs.getHeSo())));
                                dviTinh.add(byId.get());
                                thuocs.setTenDonViTinhThuNguyen(byId.get().getTenDonViTinh());
                            }
                        }
                        thuocs.setListDonViTinhs(dviTinh);
                        InventoryReq inventoryReq = new InventoryReq();
                        inventoryReq.setDrugID(thuocs.getId());
                        inventoryReq.setDrugStoreID(thuocs.getNhaThuocMaNhaThuoc());
                        inventoryReq.setRecordStatusId(RecordStatusContains.ACTIVE);
                        Optional<Inventory> inventory = inventoryRepository.searchDetail(inventoryReq);
                        inventory.ifPresent(thuocs::setInventory);
                        ct.setThuocs(thuocs);
                    }
                }
                if (ct.getDonViTinhMaDonViTinh() != null && ct.getDonViTinhMaDonViTinh() > 0) {
                    ct.setDonViTinhMaDonViTinhText(donViTinhsRepository.findById(ct.getDonViTinhMaDonViTinh()).get().getTenDonViTinh());
                }
                // todo test
                ct.setImageThumbData("data:image/jpeg;base64,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");
                ct.setImagePreviewData(ct.getImageThumbData());
            }
        }
        return optional.get();
    }

    private PhieuXuats getDetail(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PhieuXuats> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        } else {
            List<PhieuXuatChiTiets> phieuXuatMaPhieuXuat = phieuXuatChiTietsRepository.findByPhieuXuatMaPhieuXuatAndRecordStatusId(optional.get().getId(), RecordStatusContains.ACTIVE);
            phieuXuatMaPhieuXuat = phieuXuatMaPhieuXuat.stream().filter(item -> RecordStatusContains.ACTIVE == item.getRecordStatusId()).collect(Collectors.toList());
            optional.get().setChiTiets(phieuXuatMaPhieuXuat);
            if (optional.get().getKhachHangMaKhachHang() != null && optional.get().getKhachHangMaKhachHang() > 0) {
                Optional<KhachHangs> byId = khachHangsRepository.findById(optional.get().getKhachHangMaKhachHang());
                if (byId.isPresent()) {
                    optional.get().setKhachHangMaKhachHangText(byId.get().getTenKhachHang());
                    optional.get().setDiaChiKhachHang(byId.get().getDiaChi());
                    optional.get().setSdtKhachHang(byId.get().getSoDienThoai());
                    optional.get().setTaxCode(byId.get().getTaxCode());
                    optional.get().setScores(byId.get().getScore());
                    optional.get().setBarCode(byId.get().getBarCode());
                }
            }
            if (optional.get().getTargetStoreId() != null && optional.get().getTargetStoreId() > 0) {
                Optional<NhaThuocs> byId = nhaThuocsRepository.findById(optional.get().getTargetStoreId());
                if (byId.isPresent()) {
                    optional.get().setTargetStoreText(byId.get().getTenNhaThuoc());
                    optional.get().setDiaChiNhaThuoc(byId.get().getDiaChi());
                    optional.get().setSdtNhaThuoc(byId.get().getDienThoai());
                }
            }
            if (optional.get().getNhaCungCapMaNhaCungCap() != null && optional.get().getNhaCungCapMaNhaCungCap() > 0) {
                Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(optional.get().getNhaCungCapMaNhaCungCap());
                if (byId.isPresent()) {
                    optional.get().setNhaCungCapMaNhaCungCapText(byId.get().getTenNhaCungCap());
                    optional.get().setDiaChiNhaCungCap(byId.get().getDiaChi());
                }
            }
            if (optional.get().getPaymentTypeId() != null && optional.get().getPaymentTypeId() > 0) {
                optional.get().setPaymentTypeText(this.paymentTypeRepository.findById(optional.get().getPaymentTypeId()).get().getDisplayName());
            }
            if (optional.get().getCreatedByUserId() != null && optional.get().getCreatedByUserId() > 0) {
                optional.get().setCreatedByUserText(this.userProfileRepository.findById(optional.get().getCreatedByUserId()).get().getTenDayDu());
            }
            for (PhieuXuatChiTiets ct : optional.get().getChiTiets()) {
                if (ct.getThuocThuocId() != null && ct.getThuocThuocId() > 0) {
                    Optional<Thuocs> thuocsOpt = thuocsRepository.findById(ct.getThuocThuocId());
                    if (thuocsOpt.isPresent()) {
                        Thuocs thuocs = thuocsOpt.get();
                        ct.setMaThuocText(thuocs.getMaThuoc());
                        ct.setTenThuocText(thuocs.getTenThuoc());
                        List<DonViTinhs> dviTinh = new ArrayList<>();
                        if (thuocs.getDonViXuatLeMaDonViTinh() != null && thuocs.getDonViXuatLeMaDonViTinh() > 0) {
                            Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViXuatLeMaDonViTinh());
                            if (byId.isPresent()) {
                                byId.get().setFactor(1);
                                byId.get().setGiaBan(BigDecimal.valueOf(ct.getGiaXuat()));
                                dviTinh.add(byId.get());
                                thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
                            }
                        }
                        if (thuocs.getDonViThuNguyenMaDonViTinh() != null && thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())) {
                            Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViThuNguyenMaDonViTinh());
                            if (byId.isPresent()) {
                                byId.get().setFactor(thuocs.getHeSo());
                                byId.get().setGiaBan(BigDecimal.valueOf(ct.getGiaXuat()).multiply(BigDecimal.valueOf(thuocs.getHeSo())));
                                dviTinh.add(byId.get());
                                thuocs.setTenDonViTinhThuNguyen(byId.get().getTenDonViTinh());
                            }
                        }
                        thuocs.setListDonViTinhs(dviTinh);
                        InventoryReq inventoryReq = new InventoryReq();
                        inventoryReq.setDrugID(thuocs.getId());
                        inventoryReq.setDrugStoreID(thuocs.getNhaThuocMaNhaThuoc());
                        inventoryReq.setRecordStatusId(RecordStatusContains.ACTIVE);
                        Optional<Inventory> inventory = inventoryRepository.searchDetail(inventoryReq);
                        inventory.ifPresent(thuocs::setInventory);
                        ct.setThuocs(thuocs);
                    }
                }
                if (ct.getDonViTinhMaDonViTinh() != null && ct.getDonViTinhMaDonViTinh() > 0) {
                    ct.setDonViTinhMaDonViTinhText(donViTinhsRepository.findById(ct.getDonViTinhMaDonViTinh()).get().getTenDonViTinh());
                }
                // todo test
                ct.setImageThumbData("data:image/jpeg;base64,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");
                ct.setImagePreviewData(ct.getImageThumbData());
            }
        }
        return optional.get();
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        PhieuXuats phieuXuats = detail(id);
        phieuXuats.setRecordStatusId(RecordStatusContains.DELETED);
        hdrRepo.save(phieuXuats);
        updateInventory(phieuXuats);
        return true;
    }

    @Override
    public boolean restore(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        PhieuXuats phieuXuats = getDetail(id);
        phieuXuats.setRecordStatusId(RecordStatusContains.ACTIVE);
        hdrRepo.save(phieuXuats);
        updateInventory(phieuXuats);
        return true;
    }

    @Override
    public boolean deleteForever(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        PhieuXuats phieuXuats = getDetail(id);
        if (!phieuXuats.getRecordStatusId().equals(RecordStatusContains.DELETED)) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        phieuXuats.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(phieuXuats);
        updateInventory(phieuXuats);
        return true;
    }

    @Override
    public boolean updateStatusMulti(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }
        if (req == null || req.getListIds().isEmpty()) {
            throw new Exception("Bad request.");
        }
        List<PhieuXuats> allByIdIn = hdrRepo.findAllByIdIn(req.getListIds());
        allByIdIn.forEach(item -> {
            item.setRecordStatusId(req.getRecordStatusId());
        });
        hdrRepo.saveAll(allByIdIn);
        for (PhieuXuats e : allByIdIn) {
            PhieuXuats detail = getDetail(e.getId());
            updateInventory(detail);
        }
        return true;
    }

    private Process updateInventory(PhieuXuats e) throws Exception {
        int size = e.getChiTiets().size();
        int index = 1;
        UUID uuid = UUID.randomUUID();
        String batchKey = uuid.toString();
        Profile userInfo = this.getLoggedUser();
        Process process = kafkaProducer.createProcess(batchKey, userInfo.getNhaThuoc().getMaNhaThuoc(), new Gson().toJson(e), new Date(), size, userInfo.getId());
        for (PhieuXuatChiTiets chiTiet : e.getChiTiets()) {
            String key = e.getNhaThuocMaNhaThuoc() + "-" + chiTiet.getThuocThuocId();
            WrapData<PhieuXuats> data = new WrapData<>();
            data.setBatchKey(batchKey);
            PhieuXuats px = new PhieuXuats();
            BeanUtils.copyProperties(e, px);
            px.setChiTiets(List.copyOf(Collections.singleton(chiTiet)));
            data.setCode(InventoryConstant.XUAT);
            data.setSendDate(new Date());
            data.setData(px);
            data.setTotal(size);
            data.setIndex(index++);
            kafkaProducer.createProcessDtl(process, data);
            this.kafkaProducer.sendInternal(topicName, key, new Gson().toJson(data));
        }
        return process;
    }

    public ReportTemplateResponse preview(HashMap<String, Object> hashMap) throws Exception {
        Profile userInfo = getLoggedUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }
        try {
            String loai = FileUtils.safeToString(hashMap.get("loai"));
            PhieuXuats phieuXuats = detail(FileUtils.safeToLong(hashMap.get("id")));
            String templatePath = getTemplatePath(userInfo, phieuXuats, loai);
            exampleClass(userInfo, phieuXuats, loai);
            List<PhieuXuatChiTiets> phieuXuatChiTiets = phieuXuatChiTietsRepository
                    .findByPhieuXuatMaPhieuXuatAndRecordStatusId(phieuXuats.getId(), RecordStatusContains.ACTIVE);
            phieuXuatChiTiets.forEach(item -> item.setThanhTien(calendarTien(item)));
            List<ReportImage> reportImage = new ArrayList<>();
            if ("10322".equals(phieuXuats.getNhaThuocMaNhaThuoc())) {
                reportImage.add(new ReportImage("imageLogo_10322", "src/main/resources/template/imageLogo_10322.png"));
            }
            if ("11259".equals(phieuXuats.getNhaThuocMaNhaThuoc())) {
                reportImage.add(new ReportImage("imageLogo_11259", "src/main/resources/template/imageLogo_11259.png"));
                reportImage.add(new ReportImage("imageChuKy_11259", "src/main/resources/template/imageChuKy_11259.png"));
            }
            if ("13021".equals(phieuXuats.getNhaThuocMaNhaThuoc())) {
                reportImage.add(new ReportImage("imageLogo_13021", "src/main/resources/template/imageLogo_13021.png"));
                reportImage.add(new ReportImage("imageQR_13021", "src/main/resources/template/imageQR_13021.png"));
            }
            if ("11625".equals(phieuXuats.getNhaThuocMaNhaThuoc())) {
                reportImage.add(new ReportImage("imageQR_11952", "src/main/resources/template/imageQR_11952.png"));
            }
            InputStream templateInputStream = FileUtils.getInputStreamByFileName(templatePath);
            return FileUtils.convertDocxToPdf(templateInputStream, phieuXuats, phieuXuats.getBarCode(), reportImage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Lỗi trong quá trình tải file.", e);
        }
    }

    @Override
    public PhieuXuats convertSampleNoteToDeliveryNote(Long sampleNoteId) throws Exception {
        Profile userInfo = getLoggedUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }
        var phieuXuat = init(ENoteType.Delivery.longValue(), null);
        Optional<SampleNote> optional = sampleNoteRepository.findById(sampleNoteId);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        } else {
            if (optional.get().getRecordStatusId() != RecordStatusContains.ACTIVE) {
                throw new Exception("Không tìm thấy dữ liệu.");
            }
        }
        SampleNote sampleNote = optional.get();
        phieuXuat.setIsRefSampleNote(true);
        phieuXuat.setBacSyMaBacSy(sampleNote.getDoctorId());
        phieuXuat.setKhachHangMaKhachHang(sampleNote.getPatientId());
        var khachHangsOptional = khachHangsRepository.findById(sampleNote.getPatientId());
        if(khachHangsOptional.isPresent()) phieuXuat.setKhachHangMaKhachHangText(khachHangsOptional.get().getTenKhachHang());
        sampleNote.setChiTiets(sampleNoteDetailRepository.findByNoteID(sampleNote.getId()));
        List<PhieuXuatChiTiets> chiTiets = new ArrayList<>();
//        List<Long> thuocIdList = sampleNote.getChiTiets().stream()
//                .map(SampleNoteDetail::getDrugID)
//                .collect(Collectors.toList());
//        List<Thuocs> thuocList = thuocsRepository.findAllByIdIn(thuocIdList);
//        Map<Long, Thuocs> thuocMap = thuocList.stream()
//                .collect(Collectors.toMap(
//                        Thuocs::getId,
//                        thuoc -> thuoc,
//                        (existing, replacement) -> replacement
//                ));
        sampleNote.getChiTiets().forEach(item -> {
//            long drugID = item.getDrugID();
//            Thuocs thuoc = thuocMap.get(drugID);
//            if (thuoc != null) {
//                var chiTiet = new PhieuXuatChiTiets();
//                chiTiet.setThuocThuocId(thuoc.getId());
//                chiTiet.setThuocs(thuoc);
//                chiTiet.setSoLuong(item.getQuantity().doubleValue());
//                chiTiet.setDonViTinhMaDonViTinh(item.getDrugUnitID());
//                chiTiet.setGiaXuat(item.getDrugUnitID().equals(thuoc.getDonViThuNguyenMaDonViTinh())
//                        ? thuoc.getGiaBanLe().doubleValue() * thuoc.getHeSo() : thuoc.getGiaBanLe().doubleValue());
//                chiTiets.add(chiTiet);
//            }
            var chiTiet = new PhieuXuatChiTiets();
                chiTiet.setThuocThuocId(item.getDrugID());
                chiTiet.setSoLuong(item.getQuantity().doubleValue());
                chiTiet.setDonViTinhMaDonViTinh(item.getDrugUnitID());
                chiTiets.add(chiTiet);
        });
        phieuXuat.setChiTiets(chiTiets);
        return phieuXuat;
    }

    @Override
    public Process importExcel(MultipartFile file) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        InputStream inputStream = file.getInputStream();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            List<String> propertyNames = new ArrayList<>();
            int index = 0;
            List<String> row = getRow(workbook, 9);
            int size = row.size();
            switch (size) {
                case 1:
                    Supplier<PhieuXuatsInvoiceImport> phieuXuatsInvoiceImportSupplier = PhieuXuatsInvoiceImport::new;
                    index = 11;
                    propertyNames = Arrays.asList("soPhieuXuat", "stt", "ngayXuat", "shDon", "ngayHdon"
                            , "tenKhachHang", "daTra", "dienGiai", "maThuoc", "tenThuoc", "donViTinh", "soLuong", "donGia", "chietKhau", "vat", "result", "soLo", "hanDung", "bacSi", "chuanDoan");
                    List<PhieuXuatsInvoiceImport> phieuXuatsInvoiceImport = new ArrayList<>(handleImportExcel(workbook, propertyNames, phieuXuatsInvoiceImportSupplier, index));
                    return pushToKafka(phieuXuatsInvoiceImport);
                case 2:
                    Supplier<PhieuXuatsInvoiceImport> phieuXuatsInvoiceImportSupplier2 = PhieuXuatsInvoiceImport::new;
                    index = 2;
                    propertyNames = Arrays.asList("soPhieuXuat", "stt", "ngayXuat", "shDon", "ngayHdon"
                            , "tenKhachHang", "daTra", "dienGiai", "maThuoc", "tenThuoc", "donViTinh", "soLuong", "donGia", "chietKhau", "vat", "result", "soLo", "hanDung", "bacSi", "chuanDoan");
                    List<PhieuXuatsInvoiceImport> phieuXuat2s = new ArrayList<>(handleImportExcel(workbook, propertyNames, phieuXuatsInvoiceImportSupplier2, index));
                    break;
                case 11:
                    Supplier<PhieuXuatsInvoiceImport> phieuXuatsInvoiceImportSupplier3 = PhieuXuatsInvoiceImport::new;
                    index = 2;
                    propertyNames = Arrays.asList("soPhieuXuat", "stt", "ngayXuat", "shDon", "ngayHdon"
                            , "tenKhachHang", "daTra", "dienGiai", "maThuoc", "tenThuoc", "donViTinh", "soLuong", "donGia", "chietKhau", "vat", "result", "soLo", "hanDung", "bacSi", "chuanDoan");
                    List<PhieuXuatsInvoiceImport> phieuXuat3s = new ArrayList<>(handleImportExcel(workbook, propertyNames, phieuXuatsInvoiceImportSupplier3, index));
                    break;
                case 17:
                    Supplier<PhieuXuatsInvoiceImport> phieuXuatsInvoiceImportSupplier4 = PhieuXuatsInvoiceImport::new;
                    index = 2;
                    propertyNames = Arrays.asList("soPhieuXuat", "stt", "ngayXuat", "shDon", "ngayHdon"
                            , "tenKhachHang", "daTra", "dienGiai", "maThuoc", "tenThuoc", "donViTinh", "soLuong", "donGia", "chietKhau", "vat", "result", "soLo", "hanDung", "bacSi", "chuanDoan");
                    List<PhieuXuatsInvoiceImport> phieuXuat4s = new ArrayList<>(handleImportExcel(workbook, propertyNames, phieuXuatsInvoiceImportSupplier4, index));
                    break;
                case 20:
                    Supplier<PhieuXuatsDeliveryNotesImport> phieuXuatsDeliveryNotesImportSupplier = PhieuXuatsDeliveryNotesImport::new;
                    index = 2;
                    propertyNames = Arrays.asList("soPhieuXuat", "stt", "ngayXuat", "shDon", "ngayHdon"
                            , "tenKhachHang", "daTra", "dienGiai", "maThuoc", "tenThuoc", "donViTinh", "soLuong", "donGia", "chietKhau", "vat", "result", "soLo", "hanDung", "bacSi", "chuanDoan");
                    List<PhieuXuatsDeliveryNotesImport> phieuXuatsDeliveryNotesImport = new ArrayList<>(handleImportExcel(workbook, propertyNames, phieuXuatsDeliveryNotesImportSupplier, index));
                    return pushToKafka(phieuXuatsDeliveryNotesImport);
                default:
                    throw new Exception("Template không đúng!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private <T> Process pushToKafka(List<T> phieuXuats) throws Exception {
        int size = phieuXuats.size();
        int index = 1;
        UUID uuid = UUID.randomUUID();
        String batchKey = uuid.toString();
        Profile userInfo = this.getLoggedUser();
        Process process = kafkaProducer.createProcess(batchKey, userInfo.getNhaThuoc().getMaNhaThuoc(), new Gson().toJson(phieuXuats), new Date(), size, userInfo.getId());
        for (T chiTiet : phieuXuats) {
            String key = userInfo.getNhaThuoc().getMaNhaThuoc();
            WrapData<T> data = new WrapData<>();
            data.setBatchKey(batchKey);
            data.setCode(ImportConstant.PHIEU_XUAT);
            data.setSendDate(new Date());
            data.setData(chiTiet);
            data.setTotal(size);
            data.setIndex(index++);
            kafkaProducer.createProcessDtl(process, data);
            this.kafkaProducer.sendInternal(topicNameImport, key, new Gson().toJson(data));
        }
        return process;
    }

    private String getTemplatePath(Profile userInfo, PhieuXuats phieuXuats, String loai) {
        String templatePath = "/xuat/";
        Integer checkType = 0;
        boolean isConnectivity = userInfo.getNhaThuoc().getIsConnectivity();
        boolean isGeneralPharmacy = userInfo.getNhaThuoc().getIsGeneralPharmacy();
        boolean isDuocSy = "X".equals(userInfo.getNhaThuoc().getDuocSy());
        if (Long.valueOf(ENoteType.Delivery).equals(phieuXuats.getMaLoaiXuatNhap())) {
            if (loai.equals(FileUtils.InPhieuA5)) {
                checkType = userInfo.getApplicationSettings().stream()
                        .anyMatch(setting -> "ENABLE_DELIVERY_PICK_UP".equals(setting.getSettingKey())) ? 1 : 2;
            } else if (loai.equals(FileUtils.InPhieuA4)) {
                checkType = isConnectivity && isGeneralPharmacy ? 1 : (isDuocSy ? 2 : 3);
            }
        }
        Optional<ConfigTemplate> configTemplates = null;
        configTemplates = configTemplateRepository.findByMaNhaThuocAndPrintTypeAndMaLoaiAndType(phieuXuats.getNhaThuocMaNhaThuoc(), loai, phieuXuats.getMaLoaiXuatNhap(), checkType);
        if (!configTemplates.isPresent()) {
            configTemplates = configTemplateRepository.findByPrintTypeAndMaLoaiAndType(loai, phieuXuats.getMaLoaiXuatNhap(), checkType);
        }
        if (configTemplates.isPresent()) {
            templatePath += configTemplates.get().getTemplateFileName();
        }
        return templatePath;
    }

    private void exampleClass(Profile userInfo, PhieuXuats phieuXuats, String loai) {
        if (Long.valueOf(ENoteType.Delivery).equals(phieuXuats.getMaLoaiXuatNhap())) {
            if (loai.equals(FileUtils.InCatLieu80mm) || loai.equals(FileUtils.InKhachLe80mm)) {
                phieuXuats.setSoTaiKhoan("ICB - 0974825446 - NGUYEN THI THOA");
                phieuXuats.setTitle("HOÁ ĐƠN BÁN LẺ");
            } else if (loai.equals(FileUtils.InBuonA4) || loai.equals(FileUtils.InBuon80mm) || loai.equals(FileUtils.InBuonA5)) {
                phieuXuats.setSoTaiKhoan("BIDV - 3950023944 - NGUYEN THI THOA");
                phieuXuats.setTitle("PHIẾU BÁN HÀNG");
            } else if (loai.equals(FileUtils.InPhieuA5)) {
                phieuXuats.setSizeDetail(phieuXuats.getChiTiets().size() + "Khoản");
                phieuXuats.setGioBan(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
                phieuXuats.setTitle(phieuXuats.getNhaThuocMaNhaThuoc().equals("9371") ? "PHIẾU BÁN HÀNG" : "PHIẾU XUẤT KHO");
                phieuXuats.setSoTaiKhoan("ICB - 0974825446 - NGUYEN THI THOA");
            } else if (loai.equals(FileUtils.InPhieuA4)) {
                phieuXuats.setTitle(phieuXuats.getNhaThuocMaNhaThuoc().equals("9371") ? "PHIẾU BÁN HÀNG" : "PHIẾU XUẤT KHO");
                phieuXuats.setSoTaiKhoan("ICB - 0974825446 - NGUYEN THI THOA");
            }
        }
        if (Long.valueOf(ENoteType.InventoryAdjustment).equals(phieuXuats.getMaLoaiXuatNhap())) {
            phieuXuats.setKhachHangMaKhachHangText("Điều chỉnh kiểm kê");
        }
        if (Long.valueOf(ENoteType.WarehouseTransfer).equals(phieuXuats.getMaLoaiXuatNhap())) {
            Optional<NhaThuocs> byIdNhaThuoc = nhaThuocsRepository.findById(phieuXuats.getTargetStoreId());
            if (byIdNhaThuoc.isPresent()) {
                phieuXuats.setKhachHangMaKhachHangText(byIdNhaThuoc.get().getTenNhaThuoc());
                phieuXuats.setDiaChiKhachHang(byIdNhaThuoc.get().getDiaChi());
            }
        }
        if (Long.valueOf(ENoteType.Delivery).equals(phieuXuats.getMaLoaiXuatNhap())) {
            this.thuaThieu(phieuXuats);
        }
        this.getInComingCustomerDebt(phieuXuats);
        phieuXuats.setBangChu(FileUtils.convertToWords(phieuXuats.getTongTien()));
        phieuXuats.setTargetStoreText(userInfo.getNhaThuoc().getTenNhaThuoc());
        phieuXuats.setDiaChiNhaThuoc(userInfo.getNhaThuoc().getDiaChi());
        phieuXuats.setSdtNhaThuoc(userInfo.getNhaThuoc().getDienThoai());
    }

    public List<PhieuXuats> getInComingCustomerDebt(PhieuXuats phieuXuats) {
        List<PhieuXuats> phieuXuatsList = getValidDeliveryNotes(phieuXuats);
        List<PhieuNhaps> phieuNhapsList = getValidReceiptNotes(phieuXuats);
        double debtAmount = 0;
        double returnAmount = 0;
        if (!phieuXuatsList.isEmpty()) {
            debtAmount = phieuXuatsList.stream()
                    .mapToDouble(x -> x.getTongTien() - x.getDaTra() - x.getDiscount() - x.getPaymentScoreAmount() - Optional.ofNullable(x.getDebtPaymentAmount()).map(BigDecimal::doubleValue).orElse(0.0)).sum();
        }
        if (!phieuNhapsList.isEmpty()) {
            returnAmount = phieuNhapsList.stream()
                    .mapToDouble(x -> x.getTongTien() - x.getDaTra() - Optional.ofNullable(x.getDebtPaymentAmount()).map(BigDecimal::doubleValue).orElse(0.0)).sum();
        }
        phieuXuats.setNoCu(debtAmount);
        phieuXuats.setConNo(returnAmount);
        return phieuXuatsList;
    }

    private List<PhieuXuats> getValidDeliveryNotes(PhieuXuats phieuXuats) {
        PhieuXuatsReq phieuXuatsReq = new PhieuXuatsReq();
        phieuXuatsReq.setNhaThuocMaNhaThuoc(phieuXuats.getNhaThuocMaNhaThuoc());
        phieuXuatsReq.setRecordStatusId(RecordStatusContains.ACTIVE);
        phieuXuatsReq.setKhachHangMaKhachHang(phieuXuats.getKhachHangMaKhachHang());
        List<PhieuXuats> hdrXuat = hdrRepo.searchList(phieuXuatsReq);
        return hdrXuat.stream().filter(item -> Objects.equals(item.getMaLoaiXuatNhap(), ENoteType.Delivery)
                || Objects.equals(item.getMaLoaiXuatNhap(), ENoteType.InitialSupplierDebt)).collect(Collectors.toList());
    }

    private List<PhieuNhaps> getValidReceiptNotes(PhieuXuats phieuXuats) {
        PhieuNhapsReq phieuNhapReq = new PhieuNhapsReq();
        phieuNhapReq.setNhaThuocMaNhaThuoc(phieuXuats.getNhaThuocMaNhaThuoc());
        phieuNhapReq.setRecordStatusId(RecordStatusContains.ACTIVE);
        phieuNhapReq.setKhachHangMaKhachHang(phieuXuats.getKhachHangMaKhachHang());
        List<PhieuNhaps> hdrNhap = phieuNhapsRepository.searchList(phieuNhapReq);
        return hdrNhap.stream().filter(item -> Objects.equals(item.getLoaiXuatNhapMaLoaiXuatNhap(), ENoteType.ReturnFromCustomer)).collect(Collectors.toList());
    }

    public BigDecimal calendarTien(PhieuXuatChiTiets rowTable) {
        if (rowTable != null) {
            BigDecimal discount = BigDecimal.ZERO;
            if (new BigDecimal(rowTable.getGiaXuat()).compareTo(new BigDecimal("0.05")) > 0) {
                discount = new BigDecimal(rowTable.getChietKhau()).divide(new BigDecimal(rowTable.getGiaXuat()), 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
            }
            if (discount.compareTo(new BigDecimal("0.5")) < 0) {
                discount = BigDecimal.ZERO;
            }
            BigDecimal vatAmount = new BigDecimal(rowTable.getVat()).compareTo(new BigDecimal("0.5")) < 0 ? BigDecimal.ZERO : new BigDecimal(rowTable.getVat());
            BigDecimal price = new BigDecimal(rowTable.getGiaXuat()).multiply(BigDecimal.ONE.subtract(discount.divide(new BigDecimal("100"))))
                    .multiply(BigDecimal.ONE.add(vatAmount.divide(new BigDecimal("100"))));
            BigDecimal thanhTien = price.multiply(new BigDecimal(rowTable.getSoLuong()));
            return thanhTien;
        } else {
            return null;
        }
    }

    private void thuaThieu(PhieuXuats phieuXuats) {
        if (phieuXuats.getTongTien() > phieuXuats.getDaTra() + phieuXuats.getBackPaymentAmount().doubleValue() - phieuXuats.getDiscount() - phieuXuats.getPaymentScoreAmount()) {
            phieuXuats.setThuaThieu(phieuXuats.getTongTien() - phieuXuats.getDaTra() - phieuXuats.getDiscount() - phieuXuats.getPaymentScoreAmount());
            phieuXuats.setThuaThieuText("Tiền nợ:");
        } else {
            phieuXuats.setThuaThieu(phieuXuats.getBackPaymentAmount().doubleValue());
            phieuXuats.setThuaThieuText("Tiền thừa:");
        }
    }
}
