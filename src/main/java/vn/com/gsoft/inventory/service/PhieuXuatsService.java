package vn.com.gsoft.inventory.service;


import jakarta.transaction.Transactional;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;

import java.util.Date;

public interface PhieuXuatsService extends BaseService<PhieuXuats, PhieuXuatsReq, Long> {


    PhieuXuats init(Long maLoaiXuatNhap, Long id) throws Exception;
    @Transactional
    PhieuXuats lock(Long id) throws Exception;
    @Transactional
    PhieuXuats unlock(Long id) throws Exception;
    @Transactional
    PhieuXuats sync(Long id);
    @Transactional
    PhieuXuats resetSync(Long id) throws Exception;
    @Transactional
    PhieuXuats medicineSync(Long id);

    @Transactional
    PhieuXuats approve(Long id) throws Exception;
    @Transactional
    PhieuXuats cancel(Long id) throws Exception;
    Double getTotalDebtAmountCustomer(String maNhaThuoc, Long customerId, Date ngayTinhNo);
}