package vn.com.gsoft.inventory.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.inventory.constant.PathContains;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;
import vn.com.gsoft.inventory.model.system.BaseResponse;
import vn.com.gsoft.inventory.service.PhieuNhapChiTietsService;
import vn.com.gsoft.inventory.util.system.ResponseUtils;


@Slf4j
@RestController
@RequestMapping("/phieu-nhap-chi-tiets")
public class PhieuNhapChiTietsController {
	
  @Autowired
  PhieuNhapChiTietsService service;


  @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody PhieuNhapChiTietsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPage(objReq)));
  }

  @PostMapping(value = PathContains.URL_SEARCH_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colectionList(@RequestBody PhieuNhapChiTietsReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchList(objReq)));
  }


}
