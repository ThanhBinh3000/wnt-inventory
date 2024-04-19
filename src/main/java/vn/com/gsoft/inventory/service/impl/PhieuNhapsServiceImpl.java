package vn.com.gsoft.inventory.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.ENoteType;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.*;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.repository.NhaCungCapsRepository;
import vn.com.gsoft.inventory.repository.PhieuNhapChiTietsRepository;
import vn.com.gsoft.inventory.repository.PhieuNhapsRepository;
import vn.com.gsoft.inventory.service.ApplicationSettingService;
import vn.com.gsoft.inventory.service.PhieuNhapsService;

import java.math.BigDecimal;
import java.util.*;


@Service
@Log4j2
public class PhieuNhapsServiceImpl extends BaseServiceImpl<PhieuNhaps, PhieuNhapsReq, Long> implements PhieuNhapsService {

    @Autowired
    private PhieuNhapsRepository hdrRepo;
    private ApplicationSettingService applicationSettingService;
    @Autowired
    private PhieuNhapChiTietsRepository dtlRepo;
    @Autowired
    private NhaCungCapsRepository nhaCungCapsRepository;

    @Autowired
    public PhieuNhapsServiceImpl(PhieuNhapsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public PhieuNhaps init(Long maLoaiXuatNhap, Long id) throws Exception {
        Profile currUser = getLoggedUser();
        String maNhaThuoc = currUser.getNhaThuoc().getMaNhaThuoc();
        PhieuNhaps data = null;
        if (id == null) {
            data = new PhieuNhaps();
            Long soPhieuXuat = hdrRepo.findBySoPhieuNhapMax(maNhaThuoc, maLoaiXuatNhap);
            if (soPhieuXuat == null) {
                soPhieuXuat = 1L;
            }else{
                soPhieuXuat += 1;
            }
            data.setSoPhieuNhap(soPhieuXuat);
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
            Optional<PhieuNhaps> phieuXuats = hdrRepo.findById(id);
            if (phieuXuats.isPresent()) {
//                data = phieuXuats.get();
//                data.setId(null);
//                Long soPhieuXuat = hdrRepo.findBySoPhieuXuatMax(storeCode, maLoaiXuatNhap);
//                if (soPhieuXuat == null) {
//                    soPhieuXuat = 1L;
//                }
//                data.setUId(UUID.randomUUID());
//                data.setSoPhieuXuat(soPhieuXuat);
//                data.setNgayXuat(new Date());
//                data.setCreatedByUserId(null);
//                data.setModifiedByUserId(null);
//                data.setCreated(null);
//                data.setModified(null);
            } else {
                throw new Exception("Không tìm thấy phiếu copy!");
            }
        }
        return data;
    }

    @Override
    public PhieuNhaps create(PhieuNhapsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        req.setLoaiXuatNhapMaLoaiXuatNhap(ENoteType.Receipt);
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
        save.setChildren(phieuNhapChiTiets);
//        updateInventory(e);
        return save;
    }

    private List<PhieuNhapChiTiets> saveChildren(Long idHdr, PhieuNhapsReq req){
        // save chi tiết
        for(PhieuNhapChiTiets chiTiet : req.getChildren()){
            chiTiet.setChietKhau(BigDecimal.valueOf(0));
            chiTiet.setPhieuNhapMaPhieuNhap(idHdr);
            chiTiet.setIsModified(false);
        }
        this.dtlRepo.saveAll(req.getChildren());
        return req.getChildren();
    }

    @Override
    public PhieuNhaps createByPhieuXuats(PhieuXuats e) {
        return null;
    }

    @Override
    public PhieuNhaps updateByPhieuXuats(PhieuXuats e) {
        return null;
    }

}
