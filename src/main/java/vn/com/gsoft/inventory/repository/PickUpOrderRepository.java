package vn.com.gsoft.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.PickUpOrder;
import vn.com.gsoft.inventory.model.dto.PickUpOrderReq;

import java.util.List;

@Repository
public interface PickUpOrderRepository extends BaseRepository<PickUpOrder, PickUpOrderReq, Long> {
  @Query("SELECT c FROM PickUpOrder c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.orderNumber} IS NULL OR c.orderNumber = :#{#param.orderNumber}) "
          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
          + " AND (:#{#param.totalAmount} IS NULL OR c.totalAmount = :#{#param.totalAmount}) "
          + " AND (:#{#param.paymentAmount} IS NULL OR c.paymentAmount = :#{#param.paymentAmount}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR c.drugStoreId = :#{#param.drugStoreId})"
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.cusId} IS NULL OR c.cusId = :#{#param.cusId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.orderStatusIds} IS NULL OR c.orderStatusId IN :#{#param.orderStatusIds}) "
          + " AND (:#{#param.fromDate} IS NULL OR c.orderDate >= :#{#param.fromDate}) "
          + " AND (:#{#param.toDate} IS NULL OR c.orderDate <= :#{#param.toDate}) "
          + " ORDER BY c.id desc"
  )
  Page<PickUpOrder> searchPage(@Param("param") PickUpOrderReq param, Pageable pageable);
  
  @Query("SELECT c FROM PickUpOrder c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.orderNumber} IS NULL OR c.orderNumber = :#{#param.orderNumber}) "
          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
          + " AND (:#{#param.totalAmount} IS NULL OR c.totalAmount = :#{#param.totalAmount}) "
          + " AND (:#{#param.paymentAmount} IS NULL OR c.paymentAmount = :#{#param.paymentAmount}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.cusId} IS NULL OR c.cusId = :#{#param.cusId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " ORDER BY c.id desc"
  )
  List<PickUpOrder> searchList(@Param("param") PickUpOrderReq param);

  @Query(value = "SELECT MAX(po.OrderNumber) FROM PickUpOrder po", nativeQuery = true)
  Integer findOrderNumberMax();

}
