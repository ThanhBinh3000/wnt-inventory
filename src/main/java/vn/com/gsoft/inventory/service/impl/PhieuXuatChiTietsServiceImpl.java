package vn.com.gsoft.inventory.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.InventoryReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;
import vn.com.gsoft.inventory.repository.*;
import vn.com.gsoft.inventory.service.PhieuXuatChiTietsService;
import vn.com.gsoft.inventory.util.system.DataUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PhieuXuatChiTietsServiceImpl extends BaseServiceImpl<PhieuXuatChiTiets, PhieuXuatChiTietsReq, Long> implements PhieuXuatChiTietsService {

    private PhieuXuatChiTietsRepository hdrRepo;
    private ThuocsRepository thuocsRepository;
    private DonViTinhsRepository donViTinhsRepository;
    private InventoryRepository inventoryRepository;
    private LoaiXuatNhapsRepository loaiXuatNhapsRepository;
    private KhachHangsRepository khachHangsRepository;
    private UserProfileRepository userProfileRepository;
    private NhaThuocsRepository nhaThuocsRepository;
    private NhaCungCapsRepository nhaCungCapsRepository;

    @Autowired
    public PhieuXuatChiTietsServiceImpl(PhieuXuatChiTietsRepository hdrRepo,
                                        ThuocsRepository thuocsRepository,
                                        DonViTinhsRepository donViTinhsRepository,
                                        LoaiXuatNhapsRepository loaiXuatNhapsRepository,
                                        KhachHangsRepository khachHangsRepository,
                                        UserProfileRepository userProfileRepository,
                                        NhaThuocsRepository nhaThuocsRepository,
                                        NhaCungCapsRepository nhaCungCapsRepository,
                                        InventoryRepository inventoryRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.thuocsRepository = thuocsRepository;
        this.donViTinhsRepository = donViTinhsRepository;
        this.inventoryRepository = inventoryRepository;
        this.loaiXuatNhapsRepository =loaiXuatNhapsRepository;
        this.khachHangsRepository = khachHangsRepository;
        this.userProfileRepository = userProfileRepository;
        this.nhaThuocsRepository = nhaThuocsRepository;
        this.nhaCungCapsRepository = nhaCungCapsRepository;
    }

    @Override
    public Page<PhieuXuatChiTiets> searchPage(PhieuXuatChiTietsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        Page<PhieuXuatChiTiets> xuatChiTiets = hdrRepo.searchPage(req, pageable);
        for (PhieuXuatChiTiets ct : xuatChiTiets.getContent()) {
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
        }
        return xuatChiTiets;
    }

    @Override
    public Page<PhieuXuatChiTiets> searchPageCustom(PhieuXuatChiTietsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        Page<PhieuXuatChiTiets> xuatChiTiets = DataUtils.convertPage(hdrRepo.searchPageCustom(req, pageable), PhieuXuatChiTiets.class);
        for (PhieuXuatChiTiets ct : xuatChiTiets.getContent()) {
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
            if (ct.getMaLoaiXuatNhap() != null && ct.getMaLoaiXuatNhap() > 0) {
                ct.setMaLoaiXuatNhapText(this.loaiXuatNhapsRepository.findById(ct.getMaLoaiXuatNhap()).get().getTenLoaiXuatNhap());
            }
            if (ct.getKhachHangMaKhachHang() != null && ct.getKhachHangMaKhachHang() > 0) {
                ct.setKhachHangMaKhachHangText(this.khachHangsRepository.findById(ct.getKhachHangMaKhachHang()).get().getTenKhachHang());
            }
            if (ct.getCreatedByUserId() != null && ct.getCreatedByUserId() > 0) {
                ct.setCreatedByUserText(this.userProfileRepository.findById(ct.getCreatedByUserId()).get().getTenDayDu());
            }
            if (ct.getTargetStoreId() != null && ct.getTargetStoreId() > 0) {
                Optional<NhaThuocs> byId = nhaThuocsRepository.findById(ct.getTargetStoreId());
                byId.ifPresent(nhaThuocs -> ct.setTargetStoreText(nhaThuocs.getTenNhaThuoc()));
            }
            if (ct.getNhaCungCapMaNhaCungCap() != null && ct.getNhaCungCapMaNhaCungCap() > 0) {
                ct.setNhaCungCapMaNhaCungCapText(this.nhaCungCapsRepository.findById(ct.getNhaCungCapMaNhaCungCap()).get().getTenNhaCungCap());
            }
            if (ct.getDonViTinhMaDonViTinh() != null && ct.getDonViTinhMaDonViTinh() > 0) {
                ct.setDonViTinhMaDonViTinhText(donViTinhsRepository.findById(ct.getDonViTinhMaDonViTinh()).get().getTenDonViTinh());
            }
        }
        return xuatChiTiets;
    }
}
