package vn.com.gsoft.inventory.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.entity.NhaThuocs;
import vn.com.gsoft.inventory.model.dto.NhaThuocsReq;
import vn.com.gsoft.inventory.repository.NhaThuocsRepository;
import vn.com.gsoft.inventory.service.NhaThuocsService;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class NhaThuocsServiceImpl extends BaseServiceImpl<NhaThuocs, NhaThuocsReq, Long> implements NhaThuocsService {

    private NhaThuocsRepository hdrRepo;


    @Autowired
    public NhaThuocsServiceImpl(NhaThuocsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }
    @Override
    public Boolean isInTheSameStoreChain(String firstStoreCode, String secondStoreCode) {
        if (firstStoreCode == null || firstStoreCode.isEmpty() || secondStoreCode == null || secondStoreCode.isEmpty()) {
            return false;
        }

        // Giả sử mã cha được lưu trữ trực tiếp trong bảng NhaThuoc, bạn cần điều chỉnh truy vấn phù hợp với cấu trúc DB của mình
        List<String> storeCodes = Arrays.asList(firstStoreCode, secondStoreCode);
        long count = hdrRepo.countDistinctByMaNhaThuocChaIn(storeCodes);

        return count == 1;
    }
}
