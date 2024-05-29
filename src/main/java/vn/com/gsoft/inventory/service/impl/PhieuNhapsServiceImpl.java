package vn.com.gsoft.inventory.service.impl;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.ENoteType;
import vn.com.gsoft.inventory.constant.ESynStatus;
import vn.com.gsoft.inventory.constant.InventoryConstant;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.InventoryReq;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.model.system.WrapData;
import vn.com.gsoft.inventory.repository.*;
import vn.com.gsoft.inventory.service.ApplicationSettingService;
import vn.com.gsoft.inventory.service.KafkaProducer;
import vn.com.gsoft.inventory.service.PhieuNhapsService;
import vn.com.gsoft.inventory.util.system.DataUtils;
import vn.com.gsoft.inventory.util.system.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;


@Service
@Log4j2
public class PhieuNhapsServiceImpl extends BaseServiceImpl<PhieuNhaps, PhieuNhapsReq, Long> implements PhieuNhapsService {
    private final PaymentTypeRepository paymentTypeRepository;
    private final KhachHangsRepository khachHangsRepository;

    @Autowired
    private PhieuNhapsRepository hdrRepo;
    private ApplicationSettingService applicationSettingService;
    @Autowired
    private PhieuNhapChiTietsRepository dtlRepo;
    @Autowired
    private NhaCungCapsRepository nhaCungCapsRepository;
    @Autowired
    private ThuocsRepository thuocsRepository;
    @Autowired
    private DonViTinhsRepository donViTinhsRepository;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private NhaThuocsRepository nhaThuocsRepository;
    @Autowired
    private LoaiXuatNhapsRepository loaiXuatNhapsRepository;
    @Value("${wnt.kafka.internal.consumer.topic.inventory}")
    private String topicName;
    @Autowired
    private PhieuXuatChiTietsRepository phieuXuatChiTietsRepository;

    @Autowired
    public PhieuNhapsServiceImpl(PhieuNhapsRepository hdrRepo,
                                 KhachHangsRepository khachHangsRepository,
                                 PaymentTypeRepository paymentTypeRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.khachHangsRepository = khachHangsRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public Page<PhieuNhaps> searchPage(PhieuNhapsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        if (StringUtils.isEmpty(req.getNhaThuocMaNhaThuoc())) {
            req.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        Page<PhieuNhaps> phieuNhaps = hdrRepo.searchPage(req, pageable);
        phieuNhaps.getContent().forEach(item -> {
            if (item.getLoaiXuatNhapMaLoaiXuatNhap() != null && item.getLoaiXuatNhapMaLoaiXuatNhap() > 0) {
                Optional<LoaiXuatNhaps> byId = loaiXuatNhapsRepository.findById(item.getLoaiXuatNhapMaLoaiXuatNhap());
                byId.ifPresent(loaiXuatNhaps -> item.setLoaiXuatNhapMaLoaiXuatNhapText(loaiXuatNhaps.getTenLoaiXuatNhap()));
            }
            if (item.getNhaCungCapMaNhaCungCap() != null && item.getNhaCungCapMaNhaCungCap() > 0) {
                Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(item.getNhaCungCapMaNhaCungCap());
                byId.ifPresent(nhaCungCaps -> item.setTenNhaCungCap(nhaCungCaps.getTenNhaCungCap()));
                byId.ifPresent(nhaCungCaps -> item.setDiaChiNhaCungCap(nhaCungCaps.getDiaChi()));
            }
            if (item.getKhachHangMaKhachHang() != null && item.getKhachHangMaKhachHang() > 0) {
                Optional<KhachHangs> byId = khachHangsRepository.findById(item.getKhachHangMaKhachHang());
                byId.ifPresent(khachHangs -> item.setTenKhachHang(khachHangs.getTenKhachHang()));
            }
            if (item.getPaymentTypeId() != null && item.getPaymentTypeId() > 0) {
                Optional<PaymentType> byId = paymentTypeRepository.findById(item.getPaymentTypeId());
                byId.ifPresent(paymentType -> item.setTenPaymentType(paymentType.getDisplayName()));
            }
            if (item.getCreatedByUserId() != null && item.getCreatedByUserId() > 0) {
                Optional<UserProfile> byId1 = userProfileRepository.findById(item.getCreatedByUserId());
                byId1.ifPresent(userProfile -> item.setTenNguoiTao(userProfile.getTenDayDu()));
            }
            if (item.getTargetStoreId() != null && item.getTargetStoreId() > 0) {
                Optional<NhaThuocs> byId = nhaThuocsRepository.findById(item.getTargetStoreId());
                byId.ifPresent(nhaThuocs -> item.setTargetStoreText(nhaThuocs.getTenNhaThuoc()));
            }
        });
        return phieuNhaps;
    }

    @Override
    public List<PhieuNhaps> searchList(PhieuNhapsReq req) throws Exception {
        if (StringUtils.isEmpty(req.getNhaThuocMaNhaThuoc())) {
            req.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        return hdrRepo.searchList(req);
    }

    @Override
    public PhieuNhaps init(Long maLoaiXuatNhap, Long id) throws Exception {
        Profile currUser = getLoggedUser();
        String maNhaThuoc = currUser.getNhaThuoc().getMaNhaThuoc();
        PhieuNhaps data = null;
        if (id == null) {
            data = new PhieuNhaps();
            Long soPhieuNhap = hdrRepo.findBySoPhieuNhapMax(maNhaThuoc, maLoaiXuatNhap);
            if (soPhieuNhap == null) {
                soPhieuNhap = 1L;
            } else {
                soPhieuNhap += 1;
            }
            data.setSoPhieuNhap(soPhieuNhap);
            data.setUId(UUID.randomUUID());
            data.setNgayNhap(new Date());

//            if (Objects.equals(maLoaiXuatNhap, ENoteType.Receipt)) {
//                // tìm nhà cung cấp nhập lẻ
//                Optional<NhaCungCaps> ncc = this.nhaCungCapsRepository.findKhachHangLe(storeCode);
//                if (ncc.isPresent()) {
//                    data.setNhaCungCapMaNhaCungCap(ncc.get().getId());
//                } else {
//                    throw new Exception("Không tìm thấy khách hàng lẻ!");
//                }
//            } else if (Objects.equals(maLoaiXuatNhap, ENoteType.Delivery)) {
//                // tìm khách hàng lẻ
//                Optional<KhachHangs> kh = this.khachHangsRepository.findKhachHangLe(storeCode);
//                if (kh.isPresent()) {
//                    data.setKhachHangMaKhachHang(kh.get().getId());
//                } else {
//                    throw new Exception("Không tìm thấy khách hàng lẻ!");
//                }
//            }
        } else {
            Optional<PhieuNhaps> phieuNhaps = hdrRepo.findById(id);
            if (phieuNhaps.isPresent()) {
                data = phieuNhaps.get();
                data.setId(null);
                Long soPhieuNhap = hdrRepo.findBySoPhieuNhapMax(maNhaThuoc, maLoaiXuatNhap);
                if (soPhieuNhap == null) {
                    soPhieuNhap = 1L;
                }
                data.setUId(UUID.randomUUID());
                data.setSoPhieuNhap(soPhieuNhap);
                data.setNgayNhap(new Date());
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
    public PhieuNhaps lock(Long id) throws Exception {
        PhieuNhaps detail = detail(id);
        detail.setLocked(true);
        hdrRepo.save(detail);
        return detail;
    }

    @Override
    public PhieuNhaps unlock(Long id) throws Exception {
        PhieuNhaps detail = detail(id);
        detail.setLocked(false);
        hdrRepo.save(detail);
        return detail;
    }

    @Override
    public PhieuNhaps approve(Long id) throws Exception {
        PhieuNhaps detail = getDetail(id);
        detail.setRecordStatusId(RecordStatusContains.ACTIVE);
        hdrRepo.save(detail);
        for (PhieuNhapChiTiets ct : detail.getChiTiets()) {
            ct.setRecordStatusId(RecordStatusContains.ACTIVE);
            dtlRepo.save(ct);
        }
        updateInventory(detail);
        return detail;
    }

    @Override
    public PhieuNhaps cancel(Long id) throws Exception {
        PhieuNhaps detail = getDetail(id);
        detail.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(detail);
        for (PhieuNhapChiTiets ct : detail.getChiTiets()) {
            ct.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
            dtlRepo.save(ct);
        }
        updateInventory(detail);
        return detail;
    }

    @Override
    public PhieuNhaps medicineSync(Long id) {
        return null;
    }

    @Override
    public PhieuNhaps resetSync(Long id) throws Exception {
        PhieuNhaps detail = detail(id);
        detail.setSynStatusId(ESynStatus.NotSyn);
        hdrRepo.save(detail);
        return detail;
    }

    @Override
    public PhieuNhaps create(PhieuNhapsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        req.setStoreId(userInfo.getNhaThuoc().getId());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setIsModified(false);
        req.setVat(0);
        Optional<PhieuNhaps> phieuXuat = hdrRepo.findBySoPhieuNhapAndLoaiXuatNhapMaLoaiXuatNhapAndNhaThuocMaNhaThuoc(req.getSoPhieuNhap(), req.getLoaiXuatNhapMaLoaiXuatNhap(), req.getNhaThuocMaNhaThuoc());
        if (phieuXuat.isPresent()) {
            throw new Exception("Số phiếu đã tồn tại!");
        }
        PhieuNhaps hdr = new PhieuNhaps();
        BeanUtils.copyProperties(req, hdr, "id");
        hdr.setCreated(new Date());
        hdr.setCreatedByUserId(getLoggedUser().getId());
        PhieuNhaps save = hdrRepo.save(hdr);
        List<PhieuNhapChiTiets> phieuNhapChiTiets = saveChildren(save.getId(), req);
        save.setChiTiets(phieuNhapChiTiets);
        updateInventory(hdr);
        return save;
    }

    @Override
    public PhieuNhaps update(PhieuNhapsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<PhieuNhaps> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        PhieuNhaps hdr = optional.get();
        if (!Objects.equals(req.getSoPhieuNhap(), hdr.getSoPhieuNhap())) {
            Optional<PhieuNhaps> phieuXuat = hdrRepo.findBySoPhieuNhapAndLoaiXuatNhapMaLoaiXuatNhapAndNhaThuocMaNhaThuoc(req.getSoPhieuNhap(), req.getLoaiXuatNhapMaLoaiXuatNhap(), req.getNhaThuocMaNhaThuoc());
            if (phieuXuat.isPresent()) {
                throw new Exception("Số phiếu đã tồn tại!");
            }
        }
        BeanUtils.copyProperties(req, hdr, "id", "created", "createdByUserId");
        hdr.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        hdr.setStoreId(userInfo.getNhaThuoc().getId());
        hdr.setModified(new Date());
        hdr.setModifiedByUserId(getLoggedUser().getId());
        hdr.setRecordStatusId(RecordStatusContains.ACTIVE);
        hdr.setIsModified(true);
        hdr.setVat(0);
        PhieuNhaps save = hdrRepo.save(hdr);

        List<PhieuNhapChiTiets> phieuNhapChiTiets = saveChildren(save.getId(), req);
        save.setChiTiets(phieuNhapChiTiets);
        updateInventory(hdr);
        return save;
    }

    private List<PhieuNhapChiTiets> saveChildren(Long idHdr, PhieuNhapsReq req) {
        // save chi tiết
        dtlRepo.deleteAllByPhieuNhapMaPhieuNhap(idHdr);
        for (PhieuNhapChiTiets chiTiet : req.getChiTiets()) {
            chiTiet.setNhaThuocMaNhaThuoc(req.getNhaThuocMaNhaThuoc());
            chiTiet.setStoreId(req.getStoreId());
            chiTiet.setChietKhau(BigDecimal.valueOf(0));
            chiTiet.setPhieuNhapMaPhieuNhap(idHdr);
            chiTiet.setIsModified(false);
            chiTiet.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        this.dtlRepo.saveAll(req.getChiTiets());
        return req.getChiTiets();
    }

    @Override
    public PhieuNhaps createByPhieuXuats(PhieuXuats e) throws Exception {
        PhieuNhaps pn = new PhieuNhaps();
        BeanUtils.copyProperties(e, pn, "id", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
        PhieuNhaps init = init(e.getMaLoaiXuatNhap(), null);
        pn.setSoPhieuNhap(init.getSoPhieuNhap());
        pn.setNhaThuocMaNhaThuoc(nhaThuocsRepository.findById(e.getTargetStoreId()).get().getMaNhaThuoc());
        pn.setStoreId(e.getTargetStoreId());
        pn.setTargetId(null);
        pn.setTargetStoreId(null);
        pn.setTargetManagementId(null);
        pn.setRecordStatusId(RecordStatusContains.ACTIVE);
        pn.setIsModified(false);
        e.setCreated(new Date());
        e.setCreatedByUserId(getLoggedUser().getId());
        pn = hdrRepo.save(pn);
        // save chi tiết
        pn.setChiTiets(new ArrayList<>());
        for (PhieuXuatChiTiets chiTiet : e.getChiTiets()) {
            PhieuNhapChiTiets ct = new PhieuNhapChiTiets();
            BeanUtils.copyProperties(chiTiet, ct, "id", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
            ct.setPhieuNhapMaPhieuNhap(pn.getId());
            pn.getChiTiets().add(ct);
        }
        this.dtlRepo.saveAll(pn.getChiTiets());
        updateInventory(pn);
        return pn;
    }

    @Override
    public PhieuNhaps updateByPhieuXuats(PhieuXuats e) throws Exception {
        Optional<PhieuNhaps> phieuNhaps = hdrRepo.findById(e.getTargetId());
        if (phieuNhaps.isEmpty()) {
            throw new Exception("Không tìm thấy phiếu nhập cũ!");
        }
        phieuNhaps.get().setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(phieuNhaps.get());
        PhieuNhaps pn = new PhieuNhaps();
        BeanUtils.copyProperties(e, pn, "id", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
        PhieuNhaps init = init(e.getMaLoaiXuatNhap(), null);
        pn.setSoPhieuNhap(init.getSoPhieuNhap());
        pn.setNhaThuocMaNhaThuoc(nhaThuocsRepository.findById(e.getTargetStoreId()).get().getMaNhaThuoc());
        pn.setStoreId(e.getTargetStoreId());
        pn.setTargetId(null);
        pn.setTargetStoreId(null);
        pn.setTargetManagementId(null);
        pn.setRecordStatusId(RecordStatusContains.ACTIVE);
        pn.setIsModified(false);
        e.setCreated(new Date());
        e.setCreatedByUserId(getLoggedUser().getId());
        pn = hdrRepo.save(pn);
        // save chi tiết
        pn.setChiTiets(new ArrayList<>());
        for (PhieuXuatChiTiets chiTiet : e.getChiTiets()) {
            PhieuNhapChiTiets ct = new PhieuNhapChiTiets();
            BeanUtils.copyProperties(chiTiet, ct, "id", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
            ct.setPhieuNhapMaPhieuNhap(pn.getId());
            pn.getChiTiets().add(ct);
        }
        this.dtlRepo.saveAll(pn.getChiTiets());
        updateInventory(pn);
        return pn;
    }


    @Override
    public PhieuNhaps detail(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PhieuNhaps> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        } else {
            if (optional.get().getRecordStatusId() != RecordStatusContains.ACTIVE) {
                throw new Exception("Không tìm thấy dữ liệu.");
            }
        }
        PhieuNhaps phieuNhaps = optional.get();
        List<PhieuNhapChiTiets> allByPhieuNhapMaPhieuNhap = dtlRepo.findAllByPhieuNhapMaPhieuNhap(phieuNhaps.getId());
        allByPhieuNhapMaPhieuNhap.forEach(item -> {
            // Get thông tin thuốc
            Optional<Thuocs> byThuocId = thuocsRepository.findById(item.getThuocThuocId());
            if (byThuocId.isPresent()) {
                Thuocs thuocs = byThuocId.get();
                List<DonViTinhs> dviTinh = new ArrayList<>();
                if (thuocs.getDonViXuatLeMaDonViTinh() != null && thuocs.getDonViXuatLeMaDonViTinh() > 0) {
                    Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViXuatLeMaDonViTinh());
                    if (byId.isPresent()) {
                        byId.get().setFactor(1);
                        byId.get().setGiaBan(item.getGiaBanLe());
                        byId.get().setGiaNhap(item.getGiaNhap());
                        dviTinh.add(byId.get());
                        thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
                    }
                }
                if (thuocs.getDonViThuNguyenMaDonViTinh() != null && thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())) {
                    Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViThuNguyenMaDonViTinh());
                    if (byId.isPresent()) {
                        byId.get().setFactor(thuocs.getHeSo());
                        byId.get().setGiaBan(item.getGiaBanLe().multiply(BigDecimal.valueOf(thuocs.getHeSo())));
                        byId.get().setGiaNhap(item.getGiaNhap().multiply(BigDecimal.valueOf(thuocs.getHeSo())));
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
                item.setThuocs(thuocs);
            }
            // Get thông tin đơn vị tính
            Optional<DonViTinhs> byId1 = donViTinhsRepository.findById(item.getDonViTinhMaDonViTinh());
            byId1.ifPresent(donViTinhs -> item.setTenDonViTinh(donViTinhs.getTenDonViTinh()));
        });
        if (phieuNhaps.getNhaCungCapMaNhaCungCap() != null && phieuNhaps.getNhaCungCapMaNhaCungCap() > 0) {
            Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(phieuNhaps.getNhaCungCapMaNhaCungCap());
            byId.ifPresent(nhaCungCaps -> phieuNhaps.setTenNhaCungCap(nhaCungCaps.getTenNhaCungCap()));
        }
        if (phieuNhaps.getKhachHangMaKhachHang() != null && phieuNhaps.getKhachHangMaKhachHang() > 0) {
            Optional<KhachHangs> byId = khachHangsRepository.findById(phieuNhaps.getKhachHangMaKhachHang());
            byId.ifPresent(khachHangs -> phieuNhaps.setTenKhachHang(khachHangs.getTenKhachHang()));
        }
        if (phieuNhaps.getPaymentTypeId() != null && phieuNhaps.getPaymentTypeId() > 0) {
            Optional<PaymentType> byId = paymentTypeRepository.findById(phieuNhaps.getPaymentTypeId());
            byId.ifPresent(paymentType -> phieuNhaps.setTenPaymentType(paymentType.getDisplayName()));
        }
        if (phieuNhaps.getCreatedByUserId() != null && phieuNhaps.getCreatedByUserId() > 0) {
            Optional<UserProfile> byId1 = userProfileRepository.findById(phieuNhaps.getCreatedByUserId());
            byId1.ifPresent(userProfile -> phieuNhaps.setTenNguoiTao(userProfile.getTenDayDu()));
        }
        if (phieuNhaps.getTargetStoreId() != null && phieuNhaps.getTargetStoreId() > 0) {
            Optional<NhaThuocs> byId = nhaThuocsRepository.findById(phieuNhaps.getTargetStoreId());
            byId.ifPresent(nhaThuocs -> phieuNhaps.setTargetStoreText(nhaThuocs.getTenNhaThuoc()));
            byId.ifPresent(nhaThuocs -> phieuNhaps.setDiaChiNhaThuoc(nhaThuocs.getDiaChi()));
            byId.ifPresent(nhaThuocs -> phieuNhaps.setSdtNhaThuoc(nhaThuocs.getDienThoai()));
        }

        phieuNhaps.setChiTiets(allByPhieuNhapMaPhieuNhap);

        return phieuNhaps;
    }

    private PhieuNhaps getDetail(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PhieuNhaps> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        PhieuNhaps phieuNhaps = optional.get();
        List<PhieuNhapChiTiets> allByPhieuNhapMaPhieuNhap = dtlRepo.findAllByPhieuNhapMaPhieuNhap(phieuNhaps.getId());
        allByPhieuNhapMaPhieuNhap.forEach(item -> {
            // Get thông tin thuốc
            Optional<Thuocs> byThuocId = thuocsRepository.findById(item.getThuocThuocId());
            if (byThuocId.isPresent()) {
                Thuocs thuocs = byThuocId.get();
                List<DonViTinhs> dviTinh = new ArrayList<>();
                if (thuocs.getDonViXuatLeMaDonViTinh() != null && thuocs.getDonViXuatLeMaDonViTinh() > 0) {
                    Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViXuatLeMaDonViTinh());
                    if (byId.isPresent()) {
                        byId.get().setFactor(1);
                        byId.get().setGiaBan(item.getGiaBanLe());
                        byId.get().setGiaNhap(item.getGiaNhap());
                        dviTinh.add(byId.get());
                        thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
                    }
                }
                if (thuocs.getDonViThuNguyenMaDonViTinh() != null && thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())) {
                    Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViThuNguyenMaDonViTinh());
                    if (byId.isPresent()) {
                        byId.get().setFactor(thuocs.getHeSo());
                        byId.get().setGiaBan(item.getGiaBanLe().multiply(BigDecimal.valueOf(thuocs.getHeSo())));
                        byId.get().setGiaNhap(item.getGiaNhap().multiply(BigDecimal.valueOf(thuocs.getHeSo())));
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
                item.setThuocs(thuocs);
            }
            // Get thông tin đơn vị tính
            Optional<DonViTinhs> byId1 = donViTinhsRepository.findById(item.getDonViTinhMaDonViTinh());
            byId1.ifPresent(donViTinhs -> item.setTenDonViTinh(donViTinhs.getTenDonViTinh()));
        });
        if (phieuNhaps.getNhaCungCapMaNhaCungCap() != null) {
            Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(phieuNhaps.getNhaCungCapMaNhaCungCap());
            if (byId.isPresent()) {
                phieuNhaps.setTenNhaCungCap(byId.get().getTenNhaCungCap());
                phieuNhaps.setDiaChiNhaCungCap(byId.get().getDiaChi());
            }
        }
        if (phieuNhaps.getKhachHangMaKhachHang() != null) {
            Optional<KhachHangs> byId = khachHangsRepository.findById(phieuNhaps.getKhachHangMaKhachHang());
            if (byId.isPresent()) {
                phieuNhaps.setTenKhachHang(byId.get().getTenKhachHang());
                phieuNhaps.setDiaChiKhachHang(byId.get().getDiaChi());
                phieuNhaps.setSdtKhachHang(byId.get().getSoDienThoai());
            }
        }
        Optional<PaymentType> byId = paymentTypeRepository.findById(phieuNhaps.getPaymentTypeId());
        byId.ifPresent(paymentType -> phieuNhaps.setTenPaymentType(paymentType.getDisplayName()));
        phieuNhaps.setChiTiets(allByPhieuNhapMaPhieuNhap);
        Optional<UserProfile> byId1 = userProfileRepository.findById(phieuNhaps.getCreatedByUserId());
        byId1.ifPresent(userProfile -> phieuNhaps.setTenNguoiTao(userProfile.getTenDayDu()));
        return phieuNhaps;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        PhieuNhaps phieuNhaps = detail(id);
        phieuNhaps.setRecordStatusId(RecordStatusContains.DELETED);
        hdrRepo.save(phieuNhaps);
        updateInventory(phieuNhaps);
        return true;
    }

    @Override
    public boolean restore(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        PhieuNhaps phieuNhaps = getDetail(id);
        phieuNhaps.setRecordStatusId(RecordStatusContains.ACTIVE);
        hdrRepo.save(phieuNhaps);
        updateInventory(phieuNhaps);
        return true;
    }

    @Override
    public boolean deleteForever(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        PhieuNhaps phieuNhaps = getDetail(id);
        if (!phieuNhaps.getRecordStatusId().equals(RecordStatusContains.DELETED)) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        phieuNhaps.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(phieuNhaps);
        updateInventory(phieuNhaps);
        return true;
    }

    @Override
    public boolean updateStatusMulti(PhieuNhapsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }
        if (req == null || req.getListIds().isEmpty()) {
            throw new Exception("Bad request.");
        }
        List<PhieuNhaps> allByIdIn = hdrRepo.findAllByIdIn(req.getListIds());
        allByIdIn.forEach(item -> {
            item.setRecordStatusId(req.getRecordStatusId());
        });
        hdrRepo.saveAll(allByIdIn);
        for (PhieuNhaps e : allByIdIn) {
            PhieuNhaps detail = getDetail(e.getId());
            updateInventory(detail);
        }
        return true;
    }


    private void updateInventory(PhieuNhaps e) throws ExecutionException, InterruptedException, TimeoutException {
        Gson gson = new Gson();
        for (PhieuNhapChiTiets chiTiet : e.getChiTiets()) {
            String key = e.getNhaThuocMaNhaThuoc() + "-" + chiTiet.getThuocThuocId();
            WrapData data = new WrapData();
            PhieuNhaps px = new PhieuNhaps();
            BeanUtils.copyProperties(e, px);
            px.setChiTiets(List.copyOf(Collections.singleton(chiTiet)));
            data.setCode(InventoryConstant.NHAP);
            data.setSendDate(new Date());
            data.setData(px);
            this.kafkaProducer.sendInternal(topicName, key, gson.toJson(data));
        }
    }

    @Override
    public ReportTemplateResponse preview(HashMap<String, Object> hashMap) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        try {
            PhieuNhaps phieuNhaps = this.detail(FileUtils.safeToLong(hashMap.get("id")));
            String loai = FileUtils.safeToString(hashMap.get("loai"));
            String templatePath = null;
            if (phieuNhaps.getLoaiXuatNhapMaLoaiXuatNhap().equals(ENoteType.Receipt)) {
                templatePath = "/template/nhapKho/";
                if (loai.equals("nhapHang")) {
                    templatePath += "phieu_nhap_hang.docx";
                }
            }
            if (phieuNhaps.getLoaiXuatNhapMaLoaiXuatNhap().equals(Long.valueOf(ENoteType.ReturnFromCustomer))) {
                templatePath = "/template/khachHangTraLai/";
                if (loai.equals("80mm")) {
                    templatePath += "khach_hang_tra_lai_80mm.docx";
                }
                if (loai.equals("A4")) {
                    templatePath += "phieu_khach_quen_A4.docx";
                }
                if (loai.equals("A5")) {
                    templatePath += "phieu_khach_le_A5.docx";
                }
            }
            InputStream templateInputStream = FileUtils.templateInputStream(templatePath);
            if (phieuNhaps.getTongTien() != null && phieuNhaps.getDaTra() != null) {
                phieuNhaps.setConNo(phieuNhaps.getTongTien() - phieuNhaps.getDaTra());
            }
            List<PhieuNhapChiTiets> allByPhieuNhapMaPhieuNhap = dtlRepo.findAllByPhieuNhapMaPhieuNhap(phieuNhaps.getId());
            allByPhieuNhapMaPhieuNhap.forEach(item -> {
                item.setThanhTien(this.calendarTien(item));
            });
            return FileUtils.convertDocxToPdf(templateInputStream, phieuNhaps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal calendarTien(PhieuNhapChiTiets rowTable) {
        if (rowTable != null) {
            BigDecimal discount = BigDecimal.ZERO;
            if (rowTable.getGiaNhap().compareTo(new BigDecimal("0.05")) > 0) {
                discount = rowTable.getChietKhau().divide(rowTable.getGiaNhap(), 10, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
            }
            if (discount.compareTo(new BigDecimal("0.5")) < 0) {
                discount = BigDecimal.ZERO;
            }
            BigDecimal vatAmount = new BigDecimal(rowTable.getVat()).compareTo(new BigDecimal("0.5")) < 0 ? BigDecimal.ZERO : new BigDecimal(rowTable.getVat());
            BigDecimal price = rowTable.getGiaNhap().multiply(BigDecimal.ONE.subtract(discount.divide(new BigDecimal("100"))))
                    .multiply(BigDecimal.ONE.add(vatAmount.divide(new BigDecimal("100"))));
            BigDecimal thanhTien = price.multiply(rowTable.getSoLuong());
            return thanhTien;
        } else {
            return null;
        }
    }
}
