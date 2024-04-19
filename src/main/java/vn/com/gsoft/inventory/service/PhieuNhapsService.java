package vn.com.gsoft.inventory.service;


import vn.com.gsoft.inventory.entity.PhieuNhaps;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;

public interface PhieuNhapsService extends BaseService<PhieuNhaps, PhieuNhapsReq, Long> {


    PhieuNhaps createByPhieuXuats(PhieuXuats e);

    PhieuNhaps updateByPhieuXuats(PhieuXuats e);

    PhieuNhaps init(Long maLoaiXuatNhap, Long id) throws Exception;
}