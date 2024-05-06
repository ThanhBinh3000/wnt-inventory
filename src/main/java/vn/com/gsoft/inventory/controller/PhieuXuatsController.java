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
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;
import vn.com.gsoft.inventory.model.system.BaseResponse;
import vn.com.gsoft.inventory.service.PhieuXuatsService;
import vn.com.gsoft.inventory.util.system.ResponseUtils;


@Slf4j
@RestController
@RequestMapping("/phieu-xuats")
public class PhieuXuatsController {

    @Autowired
    PhieuXuatsService service;


    @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> colection(@RequestBody PhieuXuatsReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.searchPage(objReq)));
    }


    @PostMapping(value = PathContains.URL_SEARCH_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> colectionList(@RequestBody PhieuXuatsReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.searchList(objReq)));
    }

    @PostMapping(value = PathContains.URL_INIT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> init(@RequestBody PhieuXuatsReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.init(objReq.getMaLoaiXuatNhap(), objReq.getId())));
    }

    @PostMapping(value = PathContains.URL_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> insert(@Valid @RequestBody PhieuXuatsReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.create(objReq)));
    }


    @PostMapping(value = PathContains.URL_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> update(@Valid @RequestBody PhieuXuatsReq objReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.update(objReq)));
    }


    @GetMapping(value = PathContains.URL_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> detail(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.detail(id)));
    }


    @PostMapping(value = PathContains.URL_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> delete(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.delete(idSearchReq.getId())));
    }

    @PostMapping(value = PathContains.URL_DELETE_DATABASE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> deleteDatabase(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.deleteForever(idSearchReq.getId())));
    }

    @PostMapping(value = PathContains.URL_UPDATE_STATUS_MULTI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> updStatusMulti(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.updateStatusMulti(idSearchReq)));
    }

    @PostMapping(value = PathContains.URL_LOCK, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> lock(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.lock(idSearchReq.getId())));
    }

    @PostMapping(value = PathContains.URL_UNLOCK, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> unlock(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.unlock(idSearchReq.getId())));
    }

    @PostMapping(value = PathContains.URL_RESTORE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> restore(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.restore(idSearchReq.getId())));
    }

    @PostMapping(value = PathContains.URL_SYNC, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> sync(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.sync(idSearchReq.getNhaThuocMaNhaThuoc(), idSearchReq.getListIds())));
    }

    @PostMapping(value = PathContains.URL_RESET_SYNC, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> resetSync(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.resetSync(idSearchReq.getListIds())));
    }
    @PostMapping(value = PathContains.URL_APPROVE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> approve(@Valid @RequestBody PhieuNhapsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.approve(idSearchReq.getId())));
    }

    @PostMapping(value = PathContains.URL_CANCEL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> cancel(@Valid @RequestBody PhieuNhapsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.cancel(idSearchReq.getId())));
    }
    @PostMapping(value = PathContains.URL_SYNC_MEDICINE_PORT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> medicineSync(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.medicineSync(idSearchReq.getId())));
    }
    @PostMapping(value = "/get-total-debt-amount-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> getTotalDebtAmountCustomer(@Valid @RequestBody PhieuXuatsReq idSearchReq) throws Exception {
        return ResponseEntity.ok(ResponseUtils.ok(service.getTotalDebtAmountCustomer(idSearchReq.getNhaThuocMaNhaThuoc(), idSearchReq.getKhachHangMaKhachHang(), idSearchReq.getNgayTinhNo())));
    }

}
