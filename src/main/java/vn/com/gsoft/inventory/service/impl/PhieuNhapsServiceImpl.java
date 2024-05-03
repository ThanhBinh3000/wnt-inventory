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

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


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
    @Value("${wnt.kafka.internal.consumer.topic.inventory}")
    private String topicName;

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
        if(req.getRecordStatusId() == null){
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        return hdrRepo.searchPage(req, pageable);
    }

    @Override
    public List<PhieuNhaps> searchList(PhieuNhapsReq req) throws Exception {
        if (StringUtils.isEmpty(req.getNhaThuocMaNhaThuoc())) {
            req.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        if(req.getRecordStatusId() == null){
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
            }else{
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
        PhieuNhaps detail = detail(id);
        detail.setRecordStatusId(RecordStatusContains.ACTIVE);
        hdrRepo.save(detail);
        for(PhieuNhapChiTiets ct: detail.getChiTiets()){
            ct.setRecordStatusId(RecordStatusContains.ACTIVE);
            dtlRepo.save(ct);
        }
        updateInventory(detail);
        return detail;
    }

    @Override
    public PhieuNhaps cancel(Long id) throws Exception {
        PhieuNhaps detail = detail(id);
        detail.setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(detail);
        for(PhieuNhapChiTiets ct: detail.getChiTiets()){
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
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setIsModified(false);
        req.setVat(0);
        Optional<PhieuNhaps> phieuXuat = hdrRepo.findBySoPhieuNhapAndLoaiXuatNhapMaLoaiXuatNhapAndNhaThuocMaNhaThuoc(req.getSoPhieuNhap(), req.getLoaiXuatNhapMaLoaiXuatNhap(),req.getNhaThuocMaNhaThuoc());
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
        if(!Objects.equals(req.getSoPhieuNhap(), hdr.getSoPhieuNhap())){
            Optional<PhieuNhaps> phieuXuat = hdrRepo.findBySoPhieuNhapAndLoaiXuatNhapMaLoaiXuatNhapAndNhaThuocMaNhaThuoc(req.getSoPhieuNhap(), req.getLoaiXuatNhapMaLoaiXuatNhap(),req.getNhaThuocMaNhaThuoc());
            if (phieuXuat.isPresent()) {
                throw new Exception("Số phiếu đã tồn tại!");
            }
        }
        BeanUtils.copyProperties(req, hdr, "id", "created", "createdByUserId");
        hdr.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
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

    private List<PhieuNhapChiTiets> saveChildren(Long idHdr, PhieuNhapsReq req){
        // save chi tiết
        dtlRepo.deleteAllByPhieuNhapMaPhieuNhap(idHdr);
        for(PhieuNhapChiTiets chiTiet : req.getChiTiets()){
            chiTiet.setChietKhau(BigDecimal.valueOf(0));
            chiTiet.setPhieuNhapMaPhieuNhap(idHdr);
            chiTiet.setIsModified(false);
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
        for(PhieuXuatChiTiets chiTiet : e.getChiTiets()){
            PhieuNhapChiTiets ct = new PhieuNhapChiTiets();
            BeanUtils.copyProperties(chiTiet, ct, "id", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
            pn.getChiTiets().add(ct);
        }
        this.dtlRepo.saveAll(pn.getChiTiets());
        updateInventory(pn);
        return pn;
    }

    @Override
    public PhieuNhaps updateByPhieuXuats(PhieuXuats e) throws Exception {
        Optional<PhieuNhaps> phieuNhaps = hdrRepo.findById(e.getTargetId());
        if(phieuNhaps.isEmpty()){
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
        for(PhieuXuatChiTiets chiTiet : e.getChiTiets()){
            PhieuNhapChiTiets ct = new PhieuNhapChiTiets();
            BeanUtils.copyProperties(chiTiet, ct, "id", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
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
        }else {
            if(optional.get().getRecordStatusId() != RecordStatusContains.ACTIVE){
                throw new Exception("Không tìm thấy dữ liệu.");
            }
        }
        PhieuNhaps phieuNhaps = optional.get();
        List<PhieuNhapChiTiets> allByPhieuNhapMaPhieuNhap = dtlRepo.findAllByPhieuNhapMaPhieuNhap(phieuNhaps.getId());
        allByPhieuNhapMaPhieuNhap.forEach(item -> {
            // Get thông tin thuốc
            Optional<Thuocs> byThuocId = thuocsRepository.findById(item.getThuocThuocId());
            if(byThuocId.isPresent()){
                Thuocs thuocs = byThuocId.get();
                List<DonViTinhs> dviTinh = new ArrayList<>();
                if(thuocs.getDonViXuatLeMaDonViTinh() > 0){
                    Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViXuatLeMaDonViTinh());
                    if(byId.isPresent()){
                        byId.get().setFactor(1);
                        byId.get().setGiaBan(item.getGiaBanLe());
                        byId.get().setGiaNhap(item.getGiaNhap());
                        dviTinh.add(byId.get());
                        thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
                    }
                }
                if(thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())){
                    Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViThuNguyenMaDonViTinh());
                    if(byId.isPresent()){
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
        if(phieuNhaps.getNhaCungCapMaNhaCungCap() != null){
            Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(phieuNhaps.getNhaCungCapMaNhaCungCap());
            byId.ifPresent(nhaCungCaps -> phieuNhaps.setTenNhaCungCap(nhaCungCaps.getTenNhaCungCap()));
        }
        if(phieuNhaps.getKhachHangMaKhachHang() != null){
            Optional<KhachHangs> byId = khachHangsRepository.findById(phieuNhaps.getKhachHangMaKhachHang());
            byId.ifPresent(khachHangs -> phieuNhaps.setTenKhachHang(khachHangs.getTenKhachHang()));
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

        Optional<PhieuNhaps> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.DELETED);
        hdrRepo.save(optional.get());
        updateInventory(optional.get());
        return true;
    }

    @Override
    public boolean restore(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PhieuNhaps> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getRecordStatusId().equals(RecordStatusContains.DELETED)) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.ACTIVE);
        hdrRepo.save(optional.get());
        updateInventory(optional.get());
        return true;
    }

    @Override
    public boolean deleteForever(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PhieuNhaps> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getRecordStatusId().equals(RecordStatusContains.DELETED)) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        hdrRepo.save(optional.get());
        updateInventory(optional.get());
        return true;
    }

    @Override
    public boolean updateMultiple(PhieuNhapsReq req) throws Exception {
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
            updateInventory(e);
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

}
