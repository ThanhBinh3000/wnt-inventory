package vn.com.gsoft.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import vn.com.gsoft.inventory.entity.PhieuNhapChiTiets;
import vn.com.gsoft.inventory.entity.PhieuNhaps;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;
import vn.com.gsoft.inventory.model.system.PaggingReq;
import vn.com.gsoft.inventory.service.PhieuNhapChiTietsService;
import vn.com.gsoft.inventory.service.PhieuNhapsService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class PhieuNhapChiTietsServiceImplTest {
    @Autowired
    private PhieuNhapChiTietsService phieuNhapChiTietsService;
    @Test
    void searchPageCustom() throws Exception {
        PhieuNhapChiTietsReq noteMedicalsReq = new PhieuNhapChiTietsReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        noteMedicalsReq.setNhaThuocMaNhaThuoc("0010");
        Page<PhieuNhapChiTiets> sampleNotes = phieuNhapChiTietsService.searchPageCustom(noteMedicalsReq);
        assert sampleNotes != null;
    }
}