package vn.com.gsoft.inventory.service;


import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.inventory.entity.PhieuXuats;
import vn.com.gsoft.inventory.entity.ReportTemplateResponse;
import vn.com.gsoft.inventory.entity.Process;
import vn.com.gsoft.inventory.model.dto.PhieuXuatsReq;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface PhieuXuatsService extends BaseService<PhieuXuats, PhieuXuatsReq, Long> {


    PhieuXuats init(Long maLoaiXuatNhap, Long id) throws Exception;
    @Transactional
    PhieuXuats lock(Long id) throws Exception;
    @Transactional
    PhieuXuats unlock(Long id) throws Exception;
    @Transactional
    Boolean sync(String nhaThuocMaNhaThuoc, List<Long> listIds) throws Exception;
    @Transactional
    Boolean resetSync(List<Long> ids) throws Exception;
    @Transactional
    PhieuXuats medicineSync(Long id);

    @Transactional
    PhieuXuats approve(Long id) throws Exception;
    @Transactional
    PhieuXuats cancel(Long id) throws Exception;
    Double getTotalDebtAmountCustomer(String maNhaThuoc, Long customerId, Date ngayTinhNo);
    ReportTemplateResponse preview(HashMap<String, Object> hashMap) throws Exception;
    PhieuXuats convertSampleNoteToDeliveryNote(Long sampleNoteId) throws Exception;

    Process importExcel(MultipartFile file) throws Exception;
}