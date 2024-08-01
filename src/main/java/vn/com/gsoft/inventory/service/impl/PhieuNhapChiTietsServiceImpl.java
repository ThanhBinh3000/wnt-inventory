package vn.com.gsoft.inventory.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.ENoteType;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.InventoryReq;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;
import vn.com.gsoft.inventory.model.system.PaggingReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.repository.*;
import vn.com.gsoft.inventory.service.PhieuNhapChiTietsService;
import vn.com.gsoft.inventory.util.system.DataUtils;
import vn.com.gsoft.inventory.util.system.ExportExcel;
import vn.com.gsoft.inventory.util.system.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PhieuNhapChiTietsServiceImpl extends BaseServiceImpl<PhieuNhapChiTiets, PhieuNhapChiTietsReq, Long> implements PhieuNhapChiTietsService {

    private PhieuNhapChiTietsRepository hdrRepo;

    @Autowired
    private ThuocsRepository thuocsRepository;

    @Autowired
    private DonViTinhsRepository donViTinhsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private LoaiXuatNhapsRepository loaiXuatNhapsRepository;
    @Autowired
    private KhachHangsRepository khachHangsRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private NhaThuocsRepository nhaThuocsRepository;
    @Autowired
    private NhaCungCapsRepository nhaCungCapsRepository;
    @Autowired
    private ConfigTemplateRepository configTemplateRepository;

    @Autowired
    public PhieuNhapChiTietsServiceImpl(PhieuNhapChiTietsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

//	@Override
//	public Page<PhieuNhapChiTiets> searchPage(PhieuNhapChiTietsReq req) throws Exception {
//		Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
//		if (StringUtils.isEmpty(req.getNhaThuocMaNhaThuoc())) {
//			req.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
//		}
//		if(req.getRecordStatusId() == null){
//			req.setRecordStatusId(RecordStatusContains.ACTIVE);
//		}
//		Page<PhieuNhapChiTiets> phieuNhaps = hdrRepo.searchPage(req, pageable);
//		phieuNhaps.getContent().forEach(item -> {
//			if(item.getNhaCungCapMaNhaCungCap() != null && item.getNhaCungCapMaNhaCungCap() > 0){
//				Optional<NhaCungCaps> byId = nhaCungCapsRepository.findById(item.getNhaCungCapMaNhaCungCap());
//				byId.ifPresent(nhaCungCaps -> item.setTenNhaCungCap(nhaCungCaps.getTenNhaCungCap()));
//			}
//			if(item.getKhachHangMaKhachHang() != null && item.getKhachHangMaKhachHang() >0){
//				Optional<KhachHangs> byId = khachHangsRepository.findById(item.getKhachHangMaKhachHang());
//				byId.ifPresent(khachHangs -> item.setTenKhachHang(khachHangs.getTenKhachHang()));
//			}
//			if(item.getPaymentTypeId() != null && item.getPaymentTypeId() >0){
//				Optional<PaymentType> byId = paymentTypeRepository.findById(item.getPaymentTypeId());
//				byId.ifPresent(paymentType -> item.setTenPaymentType(paymentType.getDisplayName()));
//			}
//			if(item.getCreatedByUserId() != null && item.getCreatedByUserId() >0){
//				Optional<UserProfile> byId1 = userProfileRepository.findById(item.getCreatedByUserId());
//				byId1.ifPresent(userProfile -> item.setTenNguoiTao(userProfile.getTenDayDu()));
//			}
//			if(item.getTargetStoreId() != null && item.getTargetStoreId()>0){
//				Optional<NhaThuocs> byId = nhaThuocsRepository.findById(item.getTargetStoreId());
//				byId.ifPresent(nhaThuocs -> item.setTargetStoreText(nhaThuocs.getTenNhaThuoc()));
//			}
//		});
//		return phieuNhaps;
//	}

    @Override
    public Page<PhieuNhapChiTiets> searchPageCustom(PhieuNhapChiTietsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        if (req.getRecordStatusId() == null) {
            req.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        Page<PhieuNhapChiTiets> xuatChiTiets = DataUtils.convertPage(hdrRepo.searchPageCustom(req, pageable), PhieuNhapChiTiets.class);
        for (PhieuNhapChiTiets ct : xuatChiTiets.getContent()) {
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
                            dviTinh.add(byId.get());
                            thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
                        }
                    }
                    if (thuocs.getDonViThuNguyenMaDonViTinh() != null && thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())) {
                        Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViThuNguyenMaDonViTinh());
                        if (byId.isPresent()) {
                            byId.get().setFactor(thuocs.getHeSo());
                            byId.get().setGiaBan(ct.getGiaBanLe().multiply(BigDecimal.valueOf(thuocs.getHeSo())));
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
            if (ct.getLoaiXuatNhapMaLoaiXuatNhap() != null && ct.getLoaiXuatNhapMaLoaiXuatNhap() > 0) {
                ct.setLoaiXuatNhapMaLoaiXuatNhapText(this.loaiXuatNhapsRepository.findById(ct.getLoaiXuatNhapMaLoaiXuatNhap()).get().getTenLoaiXuatNhap());
            }
            if (ct.getKhachHangMaKhachHang() != null && ct.getKhachHangMaKhachHang() > 0) {
                ct.setKhachHangMaKhachHangText(this.khachHangsRepository.findById(ct.getKhachHangMaKhachHang()).get().getTenKhachHang());
            }
            if (ct.getCreatedByUserId() != null && ct.getCreatedByUserId() > 0) {
                ct.setCreatedByUserText(this.userProfileRepository.findById(ct.getCreatedByUserId()).get().getTenDayDu());
            }
            if (ct.getNhaCungCapMaNhaCungCap() != null && ct.getNhaCungCapMaNhaCungCap() > 0) {
                ct.setNhaCungCapMaNhaCungCapText(this.nhaCungCapsRepository.findById(ct.getNhaCungCapMaNhaCungCap()).get().getTenNhaCungCap());
            }
            if (ct.getDonViTinhMaDonViTinh() != null && ct.getDonViTinhMaDonViTinh() > 0) {
                ct.setDonViTinhMaDonViTinhText(donViTinhsRepository.findById(ct.getDonViTinhMaDonViTinh()).get().getTenDonViTinh());
            }
            if (ct.getMaLoaiXuatNhap() != null && ct.getMaLoaiXuatNhap() > 0) {
                ct.setMaLoaiXuatNhapText(this.loaiXuatNhapsRepository.findById(ct.getMaLoaiXuatNhap()).get().getTenLoaiXuatNhap());
            }
        }
        return xuatChiTiets;
    }

    @Override
    public void export(PhieuNhapChiTietsReq req, HttpServletResponse response) throws Exception {
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(Integer.MAX_VALUE);
        req.setPaggingReq(paggingReq);
        Page<PhieuNhapChiTiets> page = this.searchPage(req);
        List<PhieuNhapChiTiets> dataPage = page.getContent();
        String title = "Lịch sử giao dịch";
        String[] rowsName = new String[]{"STT", "Ngày", "Đối tượng", "Loại phiếu", "Tên thuốc", "Đơn vị", "Số lượng", "Đơn giá",
                "CK", "VAT", "Lô/Hạn", "Sổ đăng ký", "Thành tiền"};
        String fileName = "DsLichSuGiaoDich.xlsx";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < dataPage.size(); i++) {
            PhieuNhapChiTiets data = dataPage.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = data.getNgayNhap();
            objs[2] = data.getSoPhieuNhap();
            objs[3] = "i.CustomerName : i.SupplyerName";
            objs[4] = data.getTenThuocText();
            objs[5] = data.getDonViTinhMaDonViTinhText();
            objs[6] = data.getSoLuong();
            objs[7] = data.getGiaNhap();
            objs[8] = data.getChietKhau();
            objs[9] = data.getChietKhau();
            objs[10] = " i.ExpiredDate != null ? i.BatchNumber + \"-\" + i.ExpiredDate.Value.ToString(\"dd/MM/yyyy\") : i.BatchNumber,";
            objs[11] = "RegisteredNo";
            objs[12] = "Amount";
            dataList.add(objs);
        }
        ExportExcel ex = new ExportExcel(title, fileName, rowsName, dataList, response);
        ex.export();
    }

    @Override
    public ReportTemplateResponse preview(List<PhieuNhapChiTietsReq> res) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }
        String templatePath = "/lsDaoDich/";
        Integer checkType = 0;
        String type = "1";
        for (PhieuNhapChiTietsReq data : res) {
            Optional<ConfigTemplate> configTemplates = null;
            configTemplates = configTemplateRepository.findByMaNhaThuocAndPrintTypeAndMaLoaiAndType(userInfo.getNhaThuoc().getMaNhaThuoc(), type, Long.valueOf(ENoteType.ransactionHistory), checkType);
            if (!configTemplates.isPresent()) {
                configTemplates = configTemplateRepository.findByPrintTypeAndMaLoaiAndType(type, Long.valueOf(ENoteType.ransactionHistory), checkType);
            }
            if (configTemplates.isPresent()) {
                templatePath += configTemplates.get().getTemplateFileName();
            }
            try (InputStream templateInputStream = FileUtils.getInputStreamByFileName(templatePath)) {
                return FileUtils.convertDocxToPdf(templateInputStream, data, null, null, res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}