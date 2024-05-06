package vn.com.gsoft.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.BacSies;
import vn.com.gsoft.inventory.model.dto.BacSiesReq;

import java.util.List;

@Repository
public interface BacSiesRepository extends BaseRepository<BacSies, BacSiesReq, Long> {
  @Query("SELECT c FROM BacSies c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.tenBacSy} IS NULL OR lower(c.tenBacSy) LIKE lower(concat('%',CONCAT(:#{#param.tenBacSy},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.dienThoai} IS NULL OR lower(c.dienThoai) LIKE lower(concat('%',CONCAT(:#{#param.dienThoai},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
//          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
//          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified >= :#{#param.modifiedFrom}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified <= :#{#param.modifiedTo}) "
//          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
//          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
//          + " AND (:#{#param.recordStatusID} IS NULL OR c.recordStatusID = :#{#param.recordStatusID}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.masterId} IS NULL OR c.masterId = :#{#param.masterId}) "
          + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
          + " AND (:#{#param.preMetadataHash} IS NULL OR c.preMetadataHash = :#{#param.preMetadataHash}) "
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.connectCode} IS NULL OR lower(c.connectCode) LIKE lower(concat('%',CONCAT(:#{#param.connectCode},'%'))))"
          + " AND (:#{#param.connectPassword} IS NULL OR lower(c.connectPassword) LIKE lower(concat('%',CONCAT(:#{#param.connectPassword},'%'))))"
          + " AND (:#{#param.resultConnect} IS NULL OR lower(c.resultConnect) LIKE lower(concat('%',CONCAT(:#{#param.resultConnect},'%'))))"
          + " AND (:#{#param.maNhomBacSy} IS NULL OR c.maNhomBacSy = :#{#param.maNhomBacSy}) "
          + " ORDER BY c.id desc"
  )
  Page<BacSies> searchPage(@Param("param") BacSiesReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM BacSies c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.tenBacSy} IS NULL OR lower(c.tenBacSy) LIKE lower(concat('%',CONCAT(:#{#param.tenBacSy},'%'))))"
          + " AND (:#{#param.diaChi} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.diaChi},'%'))))"
          + " AND (:#{#param.dienThoai} IS NULL OR lower(c.dienThoai) LIKE lower(concat('%',CONCAT(:#{#param.dienThoai},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
//          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
//          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified >= :#{#param.modifiedFrom}) "
//          + " AND (:#{#param.modified} IS NULL OR c.modified <= :#{#param.modifiedTo}) "
//          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
//          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
//          + " AND (:#{#param.recordStatusID} IS NULL OR c.recordStatusID = :#{#param.recordStatusID}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.masterId} IS NULL OR c.masterId = :#{#param.masterId}) "
          + " AND (:#{#param.metadataHash} IS NULL OR c.metadataHash = :#{#param.metadataHash}) "
          + " AND (:#{#param.preMetadataHash} IS NULL OR c.preMetadataHash = :#{#param.preMetadataHash}) "
          + " AND (:#{#param.code} IS NULL OR lower(c.code) LIKE lower(concat('%',CONCAT(:#{#param.code},'%'))))"
          + " AND (:#{#param.connectCode} IS NULL OR lower(c.connectCode) LIKE lower(concat('%',CONCAT(:#{#param.connectCode},'%'))))"
          + " AND (:#{#param.connectPassword} IS NULL OR lower(c.connectPassword) LIKE lower(concat('%',CONCAT(:#{#param.connectPassword},'%'))))"
          + " AND (:#{#param.resultConnect} IS NULL OR lower(c.resultConnect) LIKE lower(concat('%',CONCAT(:#{#param.resultConnect},'%'))))"
          + " AND (:#{#param.maNhomBacSy} IS NULL OR c.maNhomBacSy = :#{#param.maNhomBacSy}) "
          + " ORDER BY c.id desc"
  )
  List<BacSies> searchList(@Param("param") BacSiesReq param);

}
