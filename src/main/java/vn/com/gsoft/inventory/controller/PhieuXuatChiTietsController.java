package vn.com.gsoft.inventory.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.inventory.constant.PathContains;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.BaseResponse;
import vn.com.gsoft.inventory.service.PhieuXuatChiTietsService;
import vn.com.gsoft.inventory.service.PhieuXuatsService;
import vn.com.gsoft.inventory.util.system.ResponseUtils;


@Slf4j
@RestController
@RequestMapping("/phieu-xuat-chi-tiets")
public class PhieuXuatChiTietsController {

    @Autowired
    PhieuXuatChiTietsService service;


    @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> colection(@RequestBody PhieuXuatChiTietsReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.searchPageCustom(objReq)));
    }

}
