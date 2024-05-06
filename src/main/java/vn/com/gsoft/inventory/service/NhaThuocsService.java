package vn.com.gsoft.inventory.service;

import vn.com.gsoft.inventory.entity.NhaThuocs;
import vn.com.gsoft.inventory.model.dto.NhaThuocsReq;

public interface NhaThuocsService extends BaseService<NhaThuocs, NhaThuocsReq, Long> {
    Boolean isInTheSameStoreChain(String firstStoreCode, String secondStoreCode);

}