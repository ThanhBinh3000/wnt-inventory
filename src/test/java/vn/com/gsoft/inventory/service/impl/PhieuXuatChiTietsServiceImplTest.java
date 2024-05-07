package vn.com.gsoft.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.gsoft.inventory.entity.PhieuXuatChiTiets;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.PaggingReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.service.PhieuXuatChiTietsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PhieuXuatChiTietsServiceImplTest {
    @Autowired
    private PhieuXuatChiTietsService phieuXuatChiTietsService;

    @BeforeAll
    static void beforeAll() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Profile p = new Profile();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(p, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void searchPage() throws Exception {
        PhieuXuatChiTietsReq noteMedicalsReq = new PhieuXuatChiTietsReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        noteMedicalsReq.setNhaThuocMaNhaThuoc("0010");
        Page<PhieuXuatChiTiets> sampleNotes = phieuXuatChiTietsService.searchPage(noteMedicalsReq);
        assert sampleNotes != null;
    }

    @Test
    void searchPageCustom() throws Exception {
        PhieuXuatChiTietsReq noteMedicalsReq = new PhieuXuatChiTietsReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        noteMedicalsReq.setNhaThuocMaNhaThuoc("0010");
        Page<PhieuXuatChiTiets> sampleNotes = phieuXuatChiTietsService.searchPageCustom(noteMedicalsReq);
        assert sampleNotes != null;
    }
}