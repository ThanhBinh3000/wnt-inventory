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
import vn.com.gsoft.inventory.entity.PhieuNhaps;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.PaggingReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.service.PhieuNhapsService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class PhieuNhapsServiceImplTest {
    @Autowired
    private PhieuNhapsService phieuNhapsService;

    @BeforeAll
    static void beforeAll() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Profile p = new Profile();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(p, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void searchPage() throws Exception {
        PhieuNhapsReq noteMedicalsReq = new PhieuNhapsReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        noteMedicalsReq.setNhaThuocMaNhaThuoc("0010");
        Page<PhieuNhaps> sampleNotes = phieuNhapsService.searchPage(noteMedicalsReq);
        assert sampleNotes != null;
    }

    @Test
    void detail() throws Exception {
        PhieuNhaps detail = phieuNhapsService.detail(35543753l);
        assert detail != null;
    }
}