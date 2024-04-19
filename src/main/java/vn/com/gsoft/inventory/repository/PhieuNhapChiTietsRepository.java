package vn.com.gsoft.inventory.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.inventory.entity.PhieuNhapChiTiets;
import vn.com.gsoft.inventory.model.dto.PhieuNhapChiTietsReq;

import java.util.List;

@Repository
public interface PhieuNhapChiTietsRepository extends BaseRepository<PhieuNhapChiTiets, PhieuNhapChiTietsReq, Long> {
  @Query("SELECT c FROM PhieuNhapChiTiets c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.phieuNhapMaPhieuNhap} IS NULL OR c.phieuNhapMaPhieuNhap = :#{#param.phieuNhapMaPhieuNhap}) "
          + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
          + " AND (:#{#param.thuocThuocId} IS NULL OR c.thuocThuocId = :#{#param.thuocThuocId}) "
          + " AND (:#{#param.donViTinhMaDonViTinh} IS NULL OR c.donViTinhMaDonViTinh = :#{#param.donViTinhMaDonViTinh}) "
          + " AND (:#{#param.chietKhau} IS NULL OR c.chietKhau = :#{#param.chietKhau}) "
          + " AND (:#{#param.giaNhap} IS NULL OR c.giaNhap = :#{#param.giaNhap}) "
          + " AND (:#{#param.soLuong} IS NULL OR c.soLuong = :#{#param.soLuong}) "
          + " AND (:#{#param.option1} IS NULL OR lower(c.option1) LIKE lower(concat('%',CONCAT(:#{#param.option1},'%'))))"
          + " AND (:#{#param.option2} IS NULL OR lower(c.option2) LIKE lower(concat('%',CONCAT(:#{#param.option2},'%'))))"
          + " AND (:#{#param.option3} IS NULL OR lower(c.option3) LIKE lower(concat('%',CONCAT(:#{#param.option3},'%'))))"
          + " AND (:#{#param.option4} IS NULL OR lower(c.option4) LIKE lower(concat('%',CONCAT(:#{#param.option4},'%'))))"
          + " AND (:#{#param.option5} IS NULL OR lower(c.option5) LIKE lower(concat('%',CONCAT(:#{#param.option5},'%'))))"
          + " AND (:#{#param.soLo} IS NULL OR lower(c.soLo) LIKE lower(concat('%',CONCAT(:#{#param.soLo},'%'))))"
          + " AND (:#{#param.remainRefQuantity} IS NULL OR c.remainRefQuantity = :#{#param.remainRefQuantity}) "
          + " AND (:#{#param.retailQuantity} IS NULL OR c.retailQuantity = :#{#param.retailQuantity}) "
          + " AND (:#{#param.preRetailQuantity} IS NULL OR c.preRetailQuantity = :#{#param.preRetailQuantity}) "
          + " AND (:#{#param.handledStatusId} IS NULL OR c.handledStatusId = :#{#param.handledStatusId}) "
          + " AND (:#{#param.retailPrice} IS NULL OR c.retailPrice = :#{#param.retailPrice}) "
          + " AND (:#{#param.reduceNoteItemIds} IS NULL OR lower(c.reduceNoteItemIds) LIKE lower(concat('%',CONCAT(:#{#param.reduceNoteItemIds},'%'))))"
          + " AND (:#{#param.reduceQuantity} IS NULL OR c.reduceQuantity = :#{#param.reduceQuantity}) "
          + " AND (:#{#param.giaBanLe} IS NULL OR c.giaBanLe = :#{#param.giaBanLe}) "
          + " AND (:#{#param.retailOutPrice} IS NULL OR c.retailOutPrice = :#{#param.retailOutPrice}) "
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.rpMetadataHash} IS NULL OR c.rpMetadataHash = :#{#param.rpMetadataHash}) "
          + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
          + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.connectivityStatusId} IS NULL OR c.connectivityStatusId = :#{#param.connectivityStatusId}) "
          + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
          + " AND (:#{#param.reason} IS NULL OR lower(c.reason) LIKE lower(concat('%',CONCAT(:#{#param.reason},'%'))))"
          + " AND (:#{#param.solution} IS NULL OR lower(c.solution) LIKE lower(concat('%',CONCAT(:#{#param.solution},'%'))))"
          + " AND (:#{#param.notes} IS NULL OR lower(c.notes) LIKE lower(concat('%',CONCAT(:#{#param.notes},'%'))))"
          + " AND (:#{#param.refPrice} IS NULL OR c.refPrice = :#{#param.refPrice}) "
          + " AND (:#{#param.decscription} IS NULL OR lower(c.decscription) LIKE lower(concat('%',CONCAT(:#{#param.decscription},'%'))))"
          + " AND (:#{#param.storageConditions} IS NULL OR lower(c.storageConditions) LIKE lower(concat('%',CONCAT(:#{#param.storageConditions},'%'))))"
          + " ORDER BY c.id desc"
  )
  Page<PhieuNhapChiTiets> searchPage(@Param("param") PhieuNhapChiTietsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM PhieuNhapChiTiets c " +
          "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.phieuNhapMaPhieuNhap} IS NULL OR c.phieuNhapMaPhieuNhap = :#{#param.phieuNhapMaPhieuNhap}) "
          + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
          + " AND (:#{#param.thuocThuocId} IS NULL OR c.thuocThuocId = :#{#param.thuocThuocId}) "
          + " AND (:#{#param.donViTinhMaDonViTinh} IS NULL OR c.donViTinhMaDonViTinh = :#{#param.donViTinhMaDonViTinh}) "
          + " AND (:#{#param.chietKhau} IS NULL OR c.chietKhau = :#{#param.chietKhau}) "
          + " AND (:#{#param.giaNhap} IS NULL OR c.giaNhap = :#{#param.giaNhap}) "
          + " AND (:#{#param.soLuong} IS NULL OR c.soLuong = :#{#param.soLuong}) "
          + " AND (:#{#param.option1} IS NULL OR lower(c.option1) LIKE lower(concat('%',CONCAT(:#{#param.option1},'%'))))"
          + " AND (:#{#param.option2} IS NULL OR lower(c.option2) LIKE lower(concat('%',CONCAT(:#{#param.option2},'%'))))"
          + " AND (:#{#param.option3} IS NULL OR lower(c.option3) LIKE lower(concat('%',CONCAT(:#{#param.option3},'%'))))"
          + " AND (:#{#param.option4} IS NULL OR lower(c.option4) LIKE lower(concat('%',CONCAT(:#{#param.option4},'%'))))"
          + " AND (:#{#param.option5} IS NULL OR lower(c.option5) LIKE lower(concat('%',CONCAT(:#{#param.option5},'%'))))"
          + " AND (:#{#param.soLo} IS NULL OR lower(c.soLo) LIKE lower(concat('%',CONCAT(:#{#param.soLo},'%'))))"
          + " AND (:#{#param.remainRefQuantity} IS NULL OR c.remainRefQuantity = :#{#param.remainRefQuantity}) "
          + " AND (:#{#param.retailQuantity} IS NULL OR c.retailQuantity = :#{#param.retailQuantity}) "
          + " AND (:#{#param.preRetailQuantity} IS NULL OR c.preRetailQuantity = :#{#param.preRetailQuantity}) "
          + " AND (:#{#param.handledStatusId} IS NULL OR c.handledStatusId = :#{#param.handledStatusId}) "
          + " AND (:#{#param.retailPrice} IS NULL OR c.retailPrice = :#{#param.retailPrice}) "
          + " AND (:#{#param.reduceNoteItemIds} IS NULL OR lower(c.reduceNoteItemIds) LIKE lower(concat('%',CONCAT(:#{#param.reduceNoteItemIds},'%'))))"
          + " AND (:#{#param.reduceQuantity} IS NULL OR c.reduceQuantity = :#{#param.reduceQuantity}) "
          + " AND (:#{#param.giaBanLe} IS NULL OR c.giaBanLe = :#{#param.giaBanLe}) "
          + " AND (:#{#param.retailOutPrice} IS NULL OR c.retailOutPrice = :#{#param.retailOutPrice}) "
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.rpMetadataHash} IS NULL OR c.rpMetadataHash = :#{#param.rpMetadataHash}) "
          + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
          + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.connectivityStatusId} IS NULL OR c.connectivityStatusId = :#{#param.connectivityStatusId}) "
          + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
          + " AND (:#{#param.reason} IS NULL OR lower(c.reason) LIKE lower(concat('%',CONCAT(:#{#param.reason},'%'))))"
          + " AND (:#{#param.solution} IS NULL OR lower(c.solution) LIKE lower(concat('%',CONCAT(:#{#param.solution},'%'))))"
          + " AND (:#{#param.notes} IS NULL OR lower(c.notes) LIKE lower(concat('%',CONCAT(:#{#param.notes},'%'))))"
          + " AND (:#{#param.refPrice} IS NULL OR c.refPrice = :#{#param.refPrice}) "
          + " AND (:#{#param.decscription} IS NULL OR lower(c.decscription) LIKE lower(concat('%',CONCAT(:#{#param.decscription},'%'))))"
          + " AND (:#{#param.storageConditions} IS NULL OR lower(c.storageConditions) LIKE lower(concat('%',CONCAT(:#{#param.storageConditions},'%'))))"
          + " ORDER BY c.id desc"
  )
  List<PhieuNhapChiTiets> searchList(@Param("param") PhieuNhapChiTietsReq param);

}
