package vn.com.gsoft.inventory.service;

import vn.com.gsoft.inventory.model.dto.DonThuocReq;
import vn.com.gsoft.inventory.model.dto.DonThuocRes;

public interface DonThuocService {
    DonThuocRes searchList(DonThuocReq objReq) throws Exception;
}
