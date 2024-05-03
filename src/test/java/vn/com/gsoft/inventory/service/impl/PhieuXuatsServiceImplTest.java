package vn.com.gsoft.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.service.PhieuXuatsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class PhieuXuatsServiceImplTest {
    @Autowired
    private PhieuXuatsService phieuXuatsService;

    @BeforeAll
    static void beforeAll() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Profile p = new Profile();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(p, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void detail() throws Exception {
        PhieuXuats detail = phieuXuatsService.detail(35543753l);
        assert detail!= null;
    }
}