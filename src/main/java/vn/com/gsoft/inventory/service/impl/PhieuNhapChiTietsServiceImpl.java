package vn.com.gsoft.inventory.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.InventoryReq;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;
import vn.com.gsoft.inventory.repository.DonViTinhsRepository;
import vn.com.gsoft.inventory.repository.InventoryRepository;
import vn.com.gsoft.inventory.repository.PhieuNhapChiTietsRepository;
import vn.com.gsoft.inventory.repository.ThuocsRepository;
import vn.com.gsoft.inventory.service.PhieuNhapChiTietsService;
import vn.com.gsoft.inventory.util.system.DataUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PhieuNhapChiTietsServiceImpl extends BaseServiceImpl<PhieuNhapChiTiets, PhieuNhapChiTietsReq,Long> implements PhieuNhapChiTietsService {

	private PhieuNhapChiTietsRepository hdrRepo;

	@Autowired
	private ThuocsRepository thuocsRepository;

	@Autowired
	private DonViTinhsRepository donViTinhsRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

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
		if(req.getRecordStatusId() ==null){
			req.setRecordStatusId(RecordStatusContains.ACTIVE);
		}
		Page<PhieuNhapChiTiets> xuatChiTiets = DataUtils.convertPage(hdrRepo.searchPageCustom(req, pageable), PhieuNhapChiTiets.class) ;
		for(PhieuNhapChiTiets ct: xuatChiTiets.getContent()){
			if (ct.getThuocThuocId() != null && ct.getThuocThuocId() > 0) {
				Optional<Thuocs> thuocsOpt = thuocsRepository.findById(ct.getThuocThuocId());
				if (thuocsOpt.isPresent()) {
					Thuocs thuocs = thuocsOpt.get();
					ct.setMaThuocText(thuocs.getMaThuoc());
					ct.setTenThuocText(thuocs.getTenThuoc());
					List<DonViTinhs> dviTinh = new ArrayList<>();
					if (thuocs.getDonViXuatLeMaDonViTinh()!= null && thuocs.getDonViXuatLeMaDonViTinh() > 0) {
						Optional<DonViTinhs> byId = donViTinhsRepository.findById(thuocs.getDonViXuatLeMaDonViTinh());
						if (byId.isPresent()) {
							byId.get().setFactor(1);
							dviTinh.add(byId.get());
							thuocs.setTenDonViTinhXuatLe(byId.get().getTenDonViTinh());
						}
					}
					if (thuocs.getDonViThuNguyenMaDonViTinh() !=null && thuocs.getDonViThuNguyenMaDonViTinh() > 0 && !thuocs.getDonViThuNguyenMaDonViTinh().equals(thuocs.getDonViXuatLeMaDonViTinh())) {
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
			if (ct.getDonViTinhMaDonViTinh() != null && ct.getDonViTinhMaDonViTinh() > 0) {
				ct.setDonViTinhMaDonViTinhText(donViTinhsRepository.findById(ct.getDonViTinhMaDonViTinh()).get().getTenDonViTinh());
			}
		}
		return xuatChiTiets;
	}

}
