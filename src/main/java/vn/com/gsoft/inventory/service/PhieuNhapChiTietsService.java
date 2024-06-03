package vn.com.gsoft.inventory.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import vn.com.gsoft.inventory.entity.PhieuNhapChiTiets;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;

public interface PhieuNhapChiTietsService extends BaseService<PhieuNhapChiTiets, PhieuNhapChiTietsReq, Long> {

    public Page<PhieuNhapChiTiets> searchPageCustom(PhieuNhapChiTietsReq req) throws Exception;

    void export(PhieuNhapChiTietsReq req, HttpServletResponse response) throws Exception;

}