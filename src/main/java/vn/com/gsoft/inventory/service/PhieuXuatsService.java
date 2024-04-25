package vn.com.gsoft.inventory.service;


import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;

import java.util.Date;

public interface PhieuXuatsService extends BaseService<PhieuXuats, PhieuXuatsReq, Long> {


    PhieuXuats init(Integer maLoaiXuatNhap, Long id) throws Exception;

    PhieuXuats lock(Long id) throws Exception;

    PhieuXuats unlock(Long id) throws Exception;
}