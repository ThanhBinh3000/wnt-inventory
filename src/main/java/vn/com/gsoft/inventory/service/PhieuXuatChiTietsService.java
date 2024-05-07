package vn.com.gsoft.inventory.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.inventory.entity.PhieuXuatChiTiets;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;

public interface PhieuXuatChiTietsService extends BaseService<PhieuXuatChiTiets, PhieuXuatChiTietsReq, Long> {
    public Page<PhieuXuatChiTiets> searchPageCustom(PhieuXuatChiTietsReq req) throws Exception;
}