package vn.com.gsoft.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.PhieuNhaps;
import vn.com.gsoft.inventory.model.dto.PhieuNhapsReq;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuNhapsRepository extends BaseRepository<PhieuNhaps, PhieuNhapsReq, Long> {
    @Query("SELECT c FROM PhieuNhaps c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.soPhieuNhap} IS NULL OR c.soPhieuNhap = :#{#param.soPhieuNhap}) "
            + " AND (:#{#param.dienGiai} IS NULL OR lower(c.dienGiai) LIKE lower(concat('%',CONCAT(:#{#param.dienGiai},'%'))))"
            + " AND (:#{#param.tongTien} IS NULL OR c.tongTien = :#{#param.tongTien}) "
            + " AND (:#{#param.daTra} IS NULL OR c.daTra = :#{#param.daTra}) "
            + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR c.nhaThuocMaNhaThuoc = :#{#param.nhaThuocMaNhaThuoc})"
            + " AND (:#{#param.loaiXuatNhapMaLoaiXuatNhap} IS NULL OR c.loaiXuatNhapMaLoaiXuatNhap = :#{#param.loaiXuatNhapMaLoaiXuatNhap}) "
            + " AND (:#{#param.nhaCungCapMaNhaCungCap} IS NULL OR c.nhaCungCapMaNhaCungCap = :#{#param.nhaCungCapMaNhaCungCap}) "
            + " AND (:#{#param.khachHangMaKhachHang} IS NULL OR c.khachHangMaKhachHang = :#{#param.khachHangMaKhachHang}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
            + " AND (:#{#param.connectivityNoteID} IS NULL OR lower(c.connectivityNoteID) LIKE lower(concat('%',CONCAT(:#{#param.connectivityNoteID},'%'))))"
            + " AND (:#{#param.connectivityStatusID} IS NULL OR c.connectivityStatusID = :#{#param.connectivityStatusID}) "
            + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
            + " AND (:#{#param.orderId} IS NULL OR c.orderId = :#{#param.orderId}) "
            + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.targetId} IS NULL OR c.targetId = :#{#param.targetId}) "
            + " AND (:#{#param.sourceId} IS NULL OR c.sourceId = :#{#param.sourceId}) "
            + " AND (:#{#param.sourceStoreId} IS NULL OR c.sourceStoreId = :#{#param.sourceStoreId}) "
            + " AND (:#{#param.targetStoreId} IS NULL OR c.targetStoreId = :#{#param.targetStoreId}) "
            + " AND (:#{#param.partnerId} IS NULL OR c.partnerId = :#{#param.partnerId}) "
            + " AND (:#{#param.invoiceCode} IS NULL OR lower(c.invoiceCode) LIKE lower(concat('%',CONCAT(:#{#param.invoiceCode},'%'))))"
            + " AND (:#{#param.invoiceNo} IS NULL OR lower(c.invoiceNo) LIKE lower(concat('%',CONCAT(:#{#param.invoiceNo},'%'))))"
            + " AND (:#{#param.score} IS NULL OR c.score = :#{#param.score}) "
            + " AND (:#{#param.preScore} IS NULL OR c.preScore = :#{#param.preScore}) "
            + " AND (:#{#param.referenceKey} IS NULL OR lower(c.referenceKey) LIKE lower(concat('%',CONCAT(:#{#param.referenceKey},'%'))))"
            + " AND (:#{#param.invoiceTemplateCode} IS NULL OR lower(c.invoiceTemplateCode) LIKE lower(concat('%',CONCAT(:#{#param.invoiceTemplateCode},'%'))))"
            + " AND (:#{#param.invoiceSeries} IS NULL OR lower(c.invoiceSeries) LIKE lower(concat('%',CONCAT(:#{#param.invoiceSeries},'%'))))"
            + " AND (:#{#param.invoiceType} IS NULL OR lower(c.invoiceType) LIKE lower(concat('%',CONCAT(:#{#param.invoiceType},'%'))))"
            + " AND (:#{#param.timeTypeId} IS NULL OR c.timeTypeId = :#{#param.timeTypeId}) "
            + " AND (:#{#param.noteName} IS NULL OR lower(c.noteName) LIKE lower(concat('%',CONCAT(:#{#param.noteName},'%'))))"
            + " AND (:#{#param.notes} IS NULL OR lower(c.notes) LIKE lower(concat('%',CONCAT(:#{#param.notes},'%'))))"
            + " AND (:#{#param.reasons} IS NULL OR lower(c.reasons) LIKE lower(concat('%',CONCAT(:#{#param.reasons},'%'))))"
            + " AND (:#{#param.synStatusId} IS NULL OR c.synStatusId = :#{#param.synStatusId}) "
            + " AND (:#{#param.paymentTypeId} IS NULL OR c.paymentTypeId = :#{#param.paymentTypeId}) "
            + " AND (:#{#param.debtPaymentAmount} IS NULL OR c.debtPaymentAmount = :#{#param.debtPaymentAmount}) "
            + " AND (:#{#param.pickUpOrderId} IS NULL OR c.pickUpOrderId = :#{#param.pickUpOrderId}) "
            + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
            + " AND (:#{#param.targetManagementId} IS NULL OR c.targetManagementId = :#{#param.targetManagementId}) "
            + " AND (:#{#param.listIds} IS NULL OR c.id in (select d.phieuNhapMaPhieuNhap from PhieuNhapChiTiets d where d.id in :#{#param.listIds})) "
            + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
            + " AND (:#{#param.fromDateCreated} IS NULL OR c.created >= :#{#param.fromDateCreated}) "
            + " AND (:#{#param.toDateCreated} IS NULL OR c.created <= :#{#param.toDateCreated}) "
            + " AND (:#{#param.maLoaiXuatNhaps} IS NULL OR c.loaiXuatNhapMaLoaiXuatNhap in (:#{#param.maLoaiXuatNhaps})) "
            + " ORDER BY c.id desc"
    )
    Page<PhieuNhaps> searchPage(@Param("param") PhieuNhapsReq param, Pageable pageable);


    @Query("SELECT c FROM PhieuNhaps c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.soPhieuNhap} IS NULL OR c.soPhieuNhap = :#{#param.soPhieuNhap}) "
            + " AND (:#{#param.dienGiai} IS NULL OR lower(c.dienGiai) LIKE lower(concat('%',CONCAT(:#{#param.dienGiai},'%'))))"
            + " AND (:#{#param.tongTien} IS NULL OR c.tongTien = :#{#param.tongTien}) "
            + " AND (:#{#param.daTra} IS NULL OR c.daTra = :#{#param.daTra}) "
            + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR c.nhaThuocMaNhaThuoc = :#{#param.nhaThuocMaNhaThuoc})"
            + " AND (:#{#param.loaiXuatNhapMaLoaiXuatNhap} IS NULL OR c.loaiXuatNhapMaLoaiXuatNhap = :#{#param.loaiXuatNhapMaLoaiXuatNhap}) "
            + " AND (:#{#param.nhaCungCapMaNhaCungCap} IS NULL OR c.nhaCungCapMaNhaCungCap = :#{#param.nhaCungCapMaNhaCungCap}) "
            + " AND (:#{#param.khachHangMaKhachHang} IS NULL OR c.khachHangMaKhachHang = :#{#param.khachHangMaKhachHang}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
            + " AND (:#{#param.connectivityNoteID} IS NULL OR lower(c.connectivityNoteID) LIKE lower(concat('%',CONCAT(:#{#param.connectivityNoteID},'%'))))"
            + " AND (:#{#param.connectivityStatusID} IS NULL OR c.connectivityStatusID = :#{#param.connectivityStatusID}) "
            + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
            + " AND (:#{#param.orderId} IS NULL OR c.orderId = :#{#param.orderId}) "
            + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.targetId} IS NULL OR c.targetId = :#{#param.targetId}) "
            + " AND (:#{#param.sourceId} IS NULL OR c.sourceId = :#{#param.sourceId}) "
            + " AND (:#{#param.sourceStoreId} IS NULL OR c.sourceStoreId = :#{#param.sourceStoreId}) "
            + " AND (:#{#param.targetStoreId} IS NULL OR c.targetStoreId = :#{#param.targetStoreId}) "
            + " AND (:#{#param.partnerId} IS NULL OR c.partnerId = :#{#param.partnerId}) "
            + " AND (:#{#param.invoiceCode} IS NULL OR lower(c.invoiceCode) LIKE lower(concat('%',CONCAT(:#{#param.invoiceCode},'%'))))"
            + " AND (:#{#param.invoiceNo} IS NULL OR lower(c.invoiceNo) LIKE lower(concat('%',CONCAT(:#{#param.invoiceNo},'%'))))"
            + " AND (:#{#param.score} IS NULL OR c.score = :#{#param.score}) "
            + " AND (:#{#param.preScore} IS NULL OR c.preScore = :#{#param.preScore}) "
            + " AND (:#{#param.referenceKey} IS NULL OR lower(c.referenceKey) LIKE lower(concat('%',CONCAT(:#{#param.referenceKey},'%'))))"
            + " AND (:#{#param.invoiceTemplateCode} IS NULL OR lower(c.invoiceTemplateCode) LIKE lower(concat('%',CONCAT(:#{#param.invoiceTemplateCode},'%'))))"
            + " AND (:#{#param.invoiceSeries} IS NULL OR lower(c.invoiceSeries) LIKE lower(concat('%',CONCAT(:#{#param.invoiceSeries},'%'))))"
            + " AND (:#{#param.invoiceType} IS NULL OR lower(c.invoiceType) LIKE lower(concat('%',CONCAT(:#{#param.invoiceType},'%'))))"
            + " AND (:#{#param.timeTypeId} IS NULL OR c.timeTypeId = :#{#param.timeTypeId}) "
            + " AND (:#{#param.noteName} IS NULL OR lower(c.noteName) LIKE lower(concat('%',CONCAT(:#{#param.noteName},'%'))))"
            + " AND (:#{#param.notes} IS NULL OR lower(c.notes) LIKE lower(concat('%',CONCAT(:#{#param.notes},'%'))))"
            + " AND (:#{#param.reasons} IS NULL OR lower(c.reasons) LIKE lower(concat('%',CONCAT(:#{#param.reasons},'%'))))"
            + " AND (:#{#param.synStatusId} IS NULL OR c.synStatusId = :#{#param.synStatusId}) "
            + " AND (:#{#param.paymentTypeId} IS NULL OR c.paymentTypeId = :#{#param.paymentTypeId}) "
            + " AND (:#{#param.debtPaymentAmount} IS NULL OR c.debtPaymentAmount = :#{#param.debtPaymentAmount}) "
            + " AND (:#{#param.pickUpOrderId} IS NULL OR c.pickUpOrderId = :#{#param.pickUpOrderId}) "
            + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
            + " AND (:#{#param.targetManagementId} IS NULL OR c.targetManagementId = :#{#param.targetManagementId}) "
            + " AND (:#{#param.listIds} IS NULL OR c.id in (select d.phieuNhapMaPhieuNhap from PhieuNhapChiTiets d where d.id in :#{#param.listIds})) "
            + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
            + " AND (:#{#param.fromDateCreated} IS NULL OR c.created >= :#{#param.fromDateCreated}) "
            + " AND (:#{#param.toDateCreated} IS NULL OR c.created <= :#{#param.toDateCreated}) "
            + " ORDER BY c.id desc"
    )
    List<PhieuNhaps> searchList(@Param("param") PhieuNhapsReq param);

    Optional<PhieuNhaps> findBySoPhieuNhapAndLoaiXuatNhapMaLoaiXuatNhapAndNhaThuocMaNhaThuoc(Long soPhieuNhap,Long loaiXuatNhapMaLoaiXuatNhap,String nhaThuocMaNhaThuoc);

    @Query("SELECT MAX(px.soPhieuNhap) FROM PhieuNhaps px where px.nhaThuocMaNhaThuoc = ?1 and px.loaiXuatNhapMaLoaiXuatNhap=?2 ")
    Long findBySoPhieuNhapMax(String nhaThuocMaNhaThuoc, Long maLoaiXuatNhap);

    List<PhieuNhaps> findByNhaThuocMaNhaThuocAndKhachHangMaKhachHangAndRecordStatusId(String maNhaThuoc, Long customerId, Integer status);
}
