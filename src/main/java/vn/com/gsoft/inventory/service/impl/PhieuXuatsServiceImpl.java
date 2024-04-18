package vn.com.gsoft.inventory.service.impl;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.ENoteType;
import vn.com.gsoft.inventory.constant.InventoryConstant;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.model.system.WrapData;
import vn.com.gsoft.inventory.repository.KhachHangsRepository;
import vn.com.gsoft.inventory.repository.NhaCungCapsRepository;
import vn.com.gsoft.inventory.repository.PhieuXuatChiTietsRepository;
import vn.com.gsoft.inventory.repository.PhieuXuatsRepository;
import vn.com.gsoft.inventory.service.ApplicationSettingService;
import vn.com.gsoft.inventory.service.KafkaProducer;
import vn.com.gsoft.inventory.service.PhieuNhapsService;
import vn.com.gsoft.inventory.service.PhieuXuatsService;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@Service
@Log4j2
public class PhieuXuatsServiceImpl extends BaseServiceImpl<PhieuXuats, PhieuXuatsReq, Long> implements PhieuXuatsService {
    private PhieuXuatsRepository hdrRepo;
    private PhieuXuatChiTietsRepository phieuXuatChiTietsRepository;
    private ApplicationSettingService applicationSettingService;
    private KhachHangsRepository khachHangsRepository;
    private NhaCungCapsRepository nhaCungCapsRepository;
    private PhieuNhapsService phieuNhapsService;
    private KafkaProducer kafkaProducer;

    @Autowired
    public PhieuXuatsServiceImpl(PhieuXuatsRepository hdrRepo, ApplicationSettingService applicationSettingService,
                                 KhachHangsRepository khachHangsRepository, NhaCungCapsRepository nhaCungCapsRepository,
                                 PhieuXuatChiTietsRepository phieuXuatChiTietsRepository,
                                 PhieuNhapsService phieuNhapsService, KafkaProducer kafkaProducer) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.applicationSettingService = applicationSettingService;
        this.khachHangsRepository = khachHangsRepository;
        this.nhaCungCapsRepository = nhaCungCapsRepository;
        this.phieuNhapsService = phieuNhapsService;
        this.kafkaProducer = kafkaProducer;
        this.phieuXuatChiTietsRepository = phieuXuatChiTietsRepository;
    }

    @Override
    public PhieuXuats init(Integer maLoaiXuatNhap, Long id) throws Exception {
        Profile currUser = getLoggedUser();
        String storeCode = currUser.getNhaThuoc().getMaNhaThuoc();
        Map<String, Object> applicationSetting = applicationSettingService.getDrugStoreSetting(storeCode);
        PhieuXuats data = null;
        if (id == null) {
            data = new PhieuXuats();
            Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax(storeCode, maLoaiXuatNhap);
            if (soPhieuXuat == null) {
                soPhieuXuat = 1L;
            }
            data.setSoPhieuXuat(soPhieuXuat);
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
                    soPhieuXuat = 1L;
                }
                data.setUId(UUID.randomUUID());
                data.setSoPhieuXuat(soPhieuXuat);
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
    public PhieuXuats create(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<PhieuXuats> phieuXuat = hdrRepo.findBySoPhieuXuatAndMaLoaiXuatNhap(req.getSoPhieuXuat(), req.getMaLoaiXuatNhap());
        if (phieuXuat.isPresent()) {
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
        for(PhieuXuatChiTiets chiTiet : e.getChiTiets()){
            chiTiet.setPhieuXuatMaPhieuXuat(e.getId());
        }
        this.phieuXuatChiTietsRepository.saveAll(e.getChiTiets());
        // xử lý phiếu chuyển kho
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer)) {
            PhieuNhaps phieuNhap = this.phieuNhapsService.createByPhieuXuats(e);
            e.setTargetId(phieuNhap.getId());
        }
        // xử lý xuất kho
        updateInventory(e);
        return e;
    }


    @Override
    public PhieuXuats update(PhieuXuatsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<PhieuXuats> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getSoPhieuXuat().equals(req.getSoPhieuXuat())) {
            Optional<PhieuXuats> phieuXuat = hdrRepo.findBySoPhieuXuatAndMaLoaiXuatNhap(req.getSoPhieuXuat(), req.getMaLoaiXuatNhap());
            if (phieuXuat.isPresent()) {
                throw new Exception("Số phiếu đã tồn tại!");
            }
        }

        boolean normalUser = "User".equals(userInfo.getNhaThuoc().getRole());

        if (optional.get().getLocked() && !normalUser) {
            throw new Exception("Phiếu đã được khóa!");
        }
        if (optional.get().getRecordStatusId() == RecordStatusContains.ARCHIVED) {
            throw new Exception("Không thể chỉnh sửa phiếu đã sao lưu.");
        }
        if (Objects.equals(req.getMaLoaiXuatNhap(), ENoteType.WarehouseTransfer) && req.getTargetStoreId() == null) {
            throw new Exception("TargetStoreId không được để trống");
        }

        PhieuXuats e = new PhieuXuats();
        BeanUtils.copyProperties(req, e, "id", "created", "createdByUserId");
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        e.setModified(new Date());
        e.setModifiedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        // save chi tiết
        this.phieuXuatChiTietsRepository.deleteByPhieuXuatMaPhieuXuat(e.getId());
        for(PhieuXuatChiTiets chiTiet : e.getChiTiets()){
            chiTiet.setPhieuXuatMaPhieuXuat(e.getId());
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

    private void updateInventory(PhieuXuats e) throws ExecutionException, InterruptedException, TimeoutException {
        String topicName = "inventory-topic";
        Gson gson = new Gson();
        for (PhieuXuatChiTiets chiTiet : e.getChiTiets()) {
            String key = e.getNhaThuocMaNhaThuoc() + "-" + chiTiet.getThuocThuocId();
            WrapData data = new WrapData();
            PhieuXuats px = new PhieuXuats();
            BeanUtils.copyProperties(e, px);
            px.setChiTiets(List.copyOf(Collections.singleton(chiTiet)));
            data.setCode(InventoryConstant.XUAT);
            data.setSendDate(new Date());
            data.setData(px);
            this.kafkaProducer.sendInternal(topicName, key, gson.toJson(data));
        }
    }
}
