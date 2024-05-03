package vn.com.gsoft.inventory.service;


import jakarta.transaction.Transactional;
import vn.com.gsoft.inventory.entity.PhieuNhaps;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;

import java.util.Collection;

public interface PhieuNhapsService extends BaseService<PhieuNhaps, PhieuNhapsReq, Long> {

    @Transactional
    PhieuNhaps createByPhieuXuats(PhieuXuats e) throws Exception;
    @Transactional
    PhieuNhaps updateByPhieuXuats(PhieuXuats e) throws Exception;

    PhieuNhaps init(Long maLoaiXuatNhap, Long id) throws Exception;
    @Transactional
    PhieuNhaps lock(Long id) throws Exception;
    @Transactional
    PhieuNhaps unlock(Long id) throws Exception;
    @Transactional
    PhieuNhaps approve(Long id) throws Exception;
    @Transactional
    PhieuNhaps cancel(Long id) throws Exception;
    @Transactional
    PhieuNhaps medicineSync(Long id);
    @Transactional
    PhieuNhaps resetSync(Long id) throws Exception;
}