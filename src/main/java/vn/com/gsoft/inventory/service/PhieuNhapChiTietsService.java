package vn.com.gsoft.inventory.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.inventory.entity.PhieuNhapChiTiets;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;

public interface PhieuNhapChiTietsService extends BaseService<PhieuNhapChiTiets, PhieuNhapChiTietsReq, Long> {

    public Page<PhieuNhapChiTiets> searchPageCustom(PhieuNhapChiTietsReq req) throws Exception;
}