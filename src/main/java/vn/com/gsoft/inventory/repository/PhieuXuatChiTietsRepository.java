package vn.com.gsoft.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.PhieuXuatChiTiets;
import vn.com.gsoft.inventory.model.dto.PhieuXuatChiTietsReq;

import java.util.List;

@Repository
public interface PhieuXuatChiTietsRepository extends BaseRepository<PhieuXuatChiTiets, PhieuXuatChiTietsReq, Long> {
  @Query("SELECT c FROM PhieuXuatChiTiets c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.phieuXuatMaPhieuXuat} IS NULL OR c.phieuXuatMaPhieuXuat = :#{#param.phieuXuatMaPhieuXuat}) "
          + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
          + " AND (:#{#param.thuocThuocId} IS NULL OR c.thuocThuocId = :#{#param.thuocThuocId}) "
          + " AND (:#{#param.donViTinhMaDonViTinh} IS NULL OR c.donViTinhMaDonViTinh = :#{#param.donViTinhMaDonViTinh}) "
          + " AND (:#{#param.chietKhau} IS NULL OR c.chietKhau = :#{#param.chietKhau}) "
          + " AND (:#{#param.giaXuat} IS NULL OR c.giaXuat = :#{#param.giaXuat}) "
          + " AND (:#{#param.soLuong} IS NULL OR c.soLuong = :#{#param.soLuong}) "
          + " AND (:#{#param.option1} IS NULL OR lower(c.option1) LIKE lower(concat('%',CONCAT(:#{#param.option1},'%'))))"
          + " AND (:#{#param.option2} IS NULL OR lower(c.option2) LIKE lower(concat('%',CONCAT(:#{#param.option2},'%'))))"
          + " AND (:#{#param.option3} IS NULL OR lower(c.option3) LIKE lower(concat('%',CONCAT(:#{#param.option3},'%'))))"
          + " AND (:#{#param.refConnectivityCode} IS NULL OR lower(c.refConnectivityCode) LIKE lower(concat('%',CONCAT(:#{#param.refConnectivityCode},'%'))))"
          + " AND (:#{#param.preQuantity} IS NULL OR lower(c.preQuantity) LIKE lower(concat('%',CONCAT(:#{#param.preQuantity},'%'))))"
          + " AND (:#{#param.retailQuantity} IS NULL OR c.retailQuantity = :#{#param.retailQuantity}) "
          + " AND (:#{#param.handledStatusId} IS NULL OR c.handledStatusId = :#{#param.handledStatusId}) "
          + " AND (:#{#param.retailPrice} IS NULL OR c.retailPrice = :#{#param.retailPrice}) "
          + " AND (:#{#param.reduceNoteItemIds} IS NULL OR lower(c.reduceNoteItemIds) LIKE lower(concat('%',CONCAT(:#{#param.reduceNoteItemIds},'%'))))"
          + " AND (:#{#param.reduceQuantity} IS NULL OR c.reduceQuantity = :#{#param.reduceQuantity}) "
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
          + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.preRetailQuantity} IS NULL OR c.preRetailQuantity = :#{#param.preRetailQuantity}) "
          + " AND (:#{#param.batchNumber} IS NULL OR lower(c.batchNumber) LIKE lower(concat('%',CONCAT(:#{#param.batchNumber},'%'))))"
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.connectivityStatusId} IS NULL OR c.connectivityStatusId = :#{#param.connectivityStatusId}) "
          + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
          + " AND (:#{#param.reason} IS NULL OR lower(c.reason) LIKE lower(concat('%',CONCAT(:#{#param.reason},'%'))))"
          + " AND (:#{#param.solution} IS NULL OR lower(c.solution) LIKE lower(concat('%',CONCAT(:#{#param.solution},'%'))))"
          + " AND (:#{#param.notes} IS NULL OR lower(c.notes) LIKE lower(concat('%',CONCAT(:#{#param.notes},'%'))))"
          + " AND (:#{#param.revenue} IS NULL OR c.revenue = :#{#param.revenue}) "
          + " AND (:#{#param.refPrice} IS NULL OR c.refPrice = :#{#param.refPrice}) "
          + " AND (:#{#param.usage} IS NULL OR lower(c.usage) LIKE lower(concat('%',CONCAT(:#{#param.usage},'%'))))"
          + " AND (:#{#param.outOwnerPriceChild} IS NULL OR c.outOwnerPriceChild = :#{#param.outOwnerPriceChild}) "
          + " ORDER BY c.id desc"
  )
  Page<PhieuXuatChiTiets> searchPage(@Param("param") PhieuXuatChiTietsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM PhieuXuatChiTiets c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.phieuXuatMaPhieuXuat} IS NULL OR c.phieuXuatMaPhieuXuat = :#{#param.phieuXuatMaPhieuXuat}) "
          + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
          + " AND (:#{#param.thuocThuocId} IS NULL OR c.thuocThuocId = :#{#param.thuocThuocId}) "
          + " AND (:#{#param.donViTinhMaDonViTinh} IS NULL OR c.donViTinhMaDonViTinh = :#{#param.donViTinhMaDonViTinh}) "
          + " AND (:#{#param.chietKhau} IS NULL OR c.chietKhau = :#{#param.chietKhau}) "
          + " AND (:#{#param.giaXuat} IS NULL OR c.giaXuat = :#{#param.giaXuat}) "
          + " AND (:#{#param.soLuong} IS NULL OR c.soLuong = :#{#param.soLuong}) "
          + " AND (:#{#param.option1} IS NULL OR lower(c.option1) LIKE lower(concat('%',CONCAT(:#{#param.option1},'%'))))"
          + " AND (:#{#param.option2} IS NULL OR lower(c.option2) LIKE lower(concat('%',CONCAT(:#{#param.option2},'%'))))"
          + " AND (:#{#param.option3} IS NULL OR lower(c.option3) LIKE lower(concat('%',CONCAT(:#{#param.option3},'%'))))"
          + " AND (:#{#param.refConnectivityCode} IS NULL OR lower(c.refConnectivityCode) LIKE lower(concat('%',CONCAT(:#{#param.refConnectivityCode},'%'))))"
          + " AND (:#{#param.preQuantity} IS NULL OR lower(c.preQuantity) LIKE lower(concat('%',CONCAT(:#{#param.preQuantity},'%'))))"
          + " AND (:#{#param.retailQuantity} IS NULL OR c.retailQuantity = :#{#param.retailQuantity}) "
          + " AND (:#{#param.handledStatusId} IS NULL OR c.handledStatusId = :#{#param.handledStatusId}) "
          + " AND (:#{#param.retailPrice} IS NULL OR c.retailPrice = :#{#param.retailPrice}) "
          + " AND (:#{#param.reduceNoteItemIds} IS NULL OR lower(c.reduceNoteItemIds) LIKE lower(concat('%',CONCAT(:#{#param.reduceNoteItemIds},'%'))))"
          + " AND (:#{#param.reduceQuantity} IS NULL OR c.reduceQuantity = :#{#param.reduceQuantity}) "
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
          + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.preRetailQuantity} IS NULL OR c.preRetailQuantity = :#{#param.preRetailQuantity}) "
          + " AND (:#{#param.batchNumber} IS NULL OR lower(c.batchNumber) LIKE lower(concat('%',CONCAT(:#{#param.batchNumber},'%'))))"
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.connectivityStatusId} IS NULL OR c.connectivityStatusId = :#{#param.connectivityStatusId}) "
          + " AND (:#{#param.connectivityResult} IS NULL OR lower(c.connectivityResult) LIKE lower(concat('%',CONCAT(:#{#param.connectivityResult},'%'))))"
          + " AND (:#{#param.reason} IS NULL OR lower(c.reason) LIKE lower(concat('%',CONCAT(:#{#param.reason},'%'))))"
          + " AND (:#{#param.solution} IS NULL OR lower(c.solution) LIKE lower(concat('%',CONCAT(:#{#param.solution},'%'))))"
          + " AND (:#{#param.notes} IS NULL OR lower(c.notes) LIKE lower(concat('%',CONCAT(:#{#param.notes},'%'))))"
          + " AND (:#{#param.revenue} IS NULL OR c.revenue = :#{#param.revenue}) "
          + " AND (:#{#param.refPrice} IS NULL OR c.refPrice = :#{#param.refPrice}) "
          + " AND (:#{#param.usage} IS NULL OR lower(c.usage) LIKE lower(concat('%',CONCAT(:#{#param.usage},'%'))))"
          + " AND (:#{#param.outOwnerPriceChild} IS NULL OR c.outOwnerPriceChild = :#{#param.outOwnerPriceChild}) "
          + " ORDER BY c.id desc"
  )
  List<PhieuXuatChiTiets> searchList(@Param("param") PhieuXuatChiTietsReq param);

  void deleteByPhieuXuatMaPhieuXuat(Long id);

    List<PhieuXuatChiTiets> findByPhieuXuatMaPhieuXuat(Long phieuXuatMaPhieuXuat);
}
