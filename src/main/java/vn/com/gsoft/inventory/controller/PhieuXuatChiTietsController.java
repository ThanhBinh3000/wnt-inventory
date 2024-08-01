package vn.com.gsoft.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.inventory.constant.PathContains;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;
import vn.com.gsoft.inventory.model.system.BaseResponse;
import vn.com.gsoft.inventory.service.PhieuXuatChiTietsService;
import vn.com.gsoft.inventory.util.system.ResponseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @PostMapping(value =  PathContains.URL_EXPORT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void exportList(@RequestBody PhieuXuatChiTietsReq objReq, HttpServletResponse response) throws Exception {
        try {
            service.export(objReq, response);
        } catch (Exception e) {
            log.error("Kết xuất danh sách dánh  : {}", e);
            final Map<String, Object> body = new HashMap<>();
            body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            body.put("msg", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);

        }
    }

    @PostMapping(value = PathContains.URL_PREVIEW, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> preview(@RequestBody List<PhieuXuatChiTietsReq> body) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.preview(body)));
    }
}
