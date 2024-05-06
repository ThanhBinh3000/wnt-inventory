package vn.com.gsoft.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.NhaCungCaps;
import vn.com.gsoft.inventory.model.dto.NhaCungCapsReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhaCungCapsRepository extends BaseRepository<NhaCungCaps, NhaCungCapsReq, Long> {
  @Query("SELECT c FROM NhaCungCaps c " +
         "WHERE 1=1 "
          + " AND (:#{#param.tenNhaCungCap} IS NULL OR lower(c.tenNhaCungCap) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaCungCap},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.soFax} IS NULL OR lower(c.soFax) LIKE lower(concat('%',CONCAT(:#{#param.soFax},'%'))))"
          + " AND (:#{#param.maSoThue} IS NULL OR lower(c.maSoThue) LIKE lower(concat('%',CONCAT(:#{#param.maSoThue},'%'))))"
          + " AND (:#{#param.nguoiDaiDien} IS NULL OR lower(c.nguoiDaiDien) LIKE lower(concat('%',CONCAT(:#{#param.nguoiDaiDien},'%'))))"
          + " AND (:#{#param.nguoiLienHe} IS NULL OR lower(c.nguoiLienHe) LIKE lower(concat('%',CONCAT(:#{#param.nguoiLienHe},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.noDauKy} IS NULL OR c.noDauKy = :#{#param.noDauKy}) "
          + " AND (:#{#param.maNhaThuoc} IS NULL OR c.maNhaThuoc = :#{#param.maNhaThuoc}) "
          + " AND (:#{#param.maNhomNhaCungCap} IS NULL OR c.maNhomNhaCungCap = :#{#param.maNhomNhaCungCap}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.supplierTypeId} IS NULL OR c.supplierTypeId = :#{#param.supplierTypeId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
          + " AND (:#{#param.diaBanHoatDong} IS NULL OR lower(c.diaBanHoatDong) LIKE lower(concat('%',CONCAT(:#{#param.diaBanHoatDong},'%'))))"
          + " AND (:#{#param.website} IS NULL OR lower(c.website) LIKE lower(concat('%',CONCAT(:#{#param.website},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.masterId} IS NULL OR c.masterId = :#{#param.masterId}) "
          + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
          + " AND (:#{#param.preMetadataHash} IS NULL OR c.preMetadataHash = :#{#param.preMetadataHash}) "
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.mappingStoreId} IS NULL OR c.mappingStoreId = :#{#param.mappingStoreId}) "
          + " AND (:#{#param.isOrganization} IS NULL OR c.isOrganization = :#{#param.isOrganization}) "
          + " ORDER BY c.id desc"
  )
  Page<NhaCungCaps> searchPage(@Param("param") NhaCungCapsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM NhaCungCaps c " +
         "WHERE 1=1 "
          + " AND (:#{#param.tenNhaCungCap} IS NULL OR lower(c.tenNhaCungCap) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaCungCap},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.soFax} IS NULL OR lower(c.soFax) LIKE lower(concat('%',CONCAT(:#{#param.soFax},'%'))))"
          + " AND (:#{#param.maSoThue} IS NULL OR lower(c.maSoThue) LIKE lower(concat('%',CONCAT(:#{#param.maSoThue},'%'))))"
          + " AND (:#{#param.nguoiDaiDien} IS NULL OR lower(c.nguoiDaiDien) LIKE lower(concat('%',CONCAT(:#{#param.nguoiDaiDien},'%'))))"
          + " AND (:#{#param.nguoiLienHe} IS NULL OR lower(c.nguoiLienHe) LIKE lower(concat('%',CONCAT(:#{#param.nguoiLienHe},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.noDauKy} IS NULL OR c.noDauKy = :#{#param.noDauKy}) "
          + " AND (:#{#param.maNhaThuoc} IS NULL OR c.maNhaThuoc = :#{#param.maNhaThuoc}) "
          + " AND (:#{#param.maNhomNhaCungCap} IS NULL OR c.maNhomNhaCungCap = :#{#param.maNhomNhaCungCap}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.supplierTypeId} IS NULL OR c.supplierTypeId = :#{#param.supplierTypeId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.barCode} IS NULL OR lower(c.barCode) LIKE lower(concat('%',CONCAT(:#{#param.barCode},'%'))))"
          + " AND (:#{#param.diaBanHoatDong} IS NULL OR lower(c.diaBanHoatDong) LIKE lower(concat('%',CONCAT(:#{#param.diaBanHoatDong},'%'))))"
          + " AND (:#{#param.website} IS NULL OR lower(c.website) LIKE lower(concat('%',CONCAT(:#{#param.website},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.masterId} IS NULL OR c.masterId = :#{#param.masterId}) "
          + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
          + " AND (:#{#param.preMetadataHash} IS NULL OR c.preMetadataHash = :#{#param.preMetadataHash}) "
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.mappingStoreId} IS NULL OR c.mappingStoreId = :#{#param.mappingStoreId}) "
          + " AND (:#{#param.isOrganization} IS NULL OR c.isOrganization = :#{#param.isOrganization}) "
          + " ORDER BY c.id desc"
  )
  List<NhaCungCaps> searchList(@Param("param") NhaCungCapsReq param);
  @Query("SELECT ncc FROM NhaCungCaps ncc where ncc.supplierTypeId=1 and ncc.maNhaThuoc= ?1 ")
  Optional<NhaCungCaps> findKhachHangLe(String storeCode);
}
