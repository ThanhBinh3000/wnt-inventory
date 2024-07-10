package vn.com.gsoft.inventory.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.inventory.entity.Inventory;
import vn.com.gsoft.inventory.model.dto.InventoryReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends BaseRepository<Inventory, InventoryReq, Long> {
    @Query("SELECT c FROM Inventory c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.drugStoreID} IS NULL OR lower(c.drugStoreID) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreID},'%'))))"
            + " AND (:#{#param.drugID} IS NULL OR c.drugID = :#{#param.drugID}) "
            + " AND (:#{#param.lastValue} IS NULL OR c.lastValue = :#{#param.lastValue}) "
            + " AND (:#{#param.drugUnitID} IS NULL OR c.drugUnitID = :#{#param.drugUnitID}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
            + " AND (:#{#param.lastInPrice} IS NULL OR c.lastInPrice = :#{#param.lastInPrice}) "
            + " AND (:#{#param.lastOutPrice} IS NULL OR c.lastOutPrice = :#{#param.lastOutPrice}) "
            + " AND (:#{#param.retailOutPrice} IS NULL OR c.retailOutPrice = :#{#param.retailOutPrice}) "
            + " AND (:#{#param.retailBatchOutPrice} IS NULL OR c.retailBatchOutPrice = :#{#param.retailBatchOutPrice}) "
            + " AND (:#{#param.serialNumber} IS NULL OR lower(c.serialNumber) LIKE lower(concat('%',CONCAT(:#{#param.serialNumber},'%'))))"
            + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
            + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
            + " AND (:#{#param.receiptItemCount} IS NULL OR c.receiptItemCount = :#{#param.receiptItemCount}) "
            + " AND (:#{#param.deliveryItemCount} IS NULL OR c.deliveryItemCount = :#{#param.deliveryItemCount}) "
            + " AND (:#{#param.initValue} IS NULL OR c.initValue = :#{#param.initValue}) "
            + " AND (:#{#param.initOutPrice} IS NULL OR c.initOutPrice = :#{#param.initOutPrice}) "
            + " AND (:#{#param.initInPrice} IS NULL OR c.initInPrice = :#{#param.initInPrice}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.outPrice} IS NULL OR c.outPrice = :#{#param.outPrice}) "
            + " ORDER BY c.id desc"
    )
    Page<Inventory> searchPage(@Param("param") InventoryReq param, Pageable pageable);


    @Query("SELECT c FROM Inventory c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.drugStoreID} IS NULL OR lower(c.drugStoreID) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreID},'%'))))"
            + " AND (:#{#param.drugID} IS NULL OR c.drugID = :#{#param.drugID}) "
            + " AND (:#{#param.lastValue} IS NULL OR c.lastValue = :#{#param.lastValue}) "
            + " AND (:#{#param.drugUnitID} IS NULL OR c.drugUnitID = :#{#param.drugUnitID}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
            + " AND (:#{#param.lastInPrice} IS NULL OR c.lastInPrice = :#{#param.lastInPrice}) "
            + " AND (:#{#param.lastOutPrice} IS NULL OR c.lastOutPrice = :#{#param.lastOutPrice}) "
            + " AND (:#{#param.retailOutPrice} IS NULL OR c.retailOutPrice = :#{#param.retailOutPrice}) "
            + " AND (:#{#param.retailBatchOutPrice} IS NULL OR c.retailBatchOutPrice = :#{#param.retailBatchOutPrice}) "
            + " AND (:#{#param.serialNumber} IS NULL OR lower(c.serialNumber) LIKE lower(concat('%',CONCAT(:#{#param.serialNumber},'%'))))"
            + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
            + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
            + " AND (:#{#param.receiptItemCount} IS NULL OR c.receiptItemCount = :#{#param.receiptItemCount}) "
            + " AND (:#{#param.deliveryItemCount} IS NULL OR c.deliveryItemCount = :#{#param.deliveryItemCount}) "
            + " AND (:#{#param.initValue} IS NULL OR c.initValue = :#{#param.initValue}) "
            + " AND (:#{#param.initOutPrice} IS NULL OR c.initOutPrice = :#{#param.initOutPrice}) "
            + " AND (:#{#param.initInPrice} IS NULL OR c.initInPrice = :#{#param.initInPrice}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.outPrice} IS NULL OR c.outPrice = :#{#param.outPrice}) "
            + " ORDER BY c.id desc"
    )
    List<Inventory> searchList(@Param("param") InventoryReq param);

    @Query("SELECT c FROM Inventory c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND ((:#{#param.drugStoreID} IS NULL OR c.drugStoreID = :#{#param.drugStoreID}) "
            + " OR (:#{#param.parentDrugStoreID} IS NULL OR c.drugStoreID = :#{#param.parentDrugStoreID})) "
            + " AND (:#{#param.drugID} IS NULL OR c.drugID = :#{#param.drugID}) "
            + " AND (:#{#param.lastValue} IS NULL OR c.lastValue = :#{#param.lastValue}) "
            + " AND (:#{#param.drugUnitID} IS NULL OR c.drugUnitID = :#{#param.drugUnitID}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
            + " AND (:#{#param.lastInPrice} IS NULL OR c.lastInPrice = :#{#param.lastInPrice}) "
            + " AND (:#{#param.lastOutPrice} IS NULL OR c.lastOutPrice = :#{#param.lastOutPrice}) "
            + " AND (:#{#param.retailOutPrice} IS NULL OR c.retailOutPrice = :#{#param.retailOutPrice}) "
            + " AND (:#{#param.retailBatchOutPrice} IS NULL OR c.retailBatchOutPrice = :#{#param.retailBatchOutPrice}) "
            + " AND (:#{#param.serialNumber} IS NULL OR lower(c.serialNumber) LIKE lower(concat('%',CONCAT(:#{#param.serialNumber},'%'))))"
            + " AND (:#{#param.archiveDrugId} IS NULL OR c.archiveDrugId = :#{#param.archiveDrugId}) "
            + " AND (:#{#param.archiveUnitId} IS NULL OR c.archiveUnitId = :#{#param.archiveUnitId}) "
            + " AND (:#{#param.receiptItemCount} IS NULL OR c.receiptItemCount = :#{#param.receiptItemCount}) "
            + " AND (:#{#param.deliveryItemCount} IS NULL OR c.deliveryItemCount = :#{#param.deliveryItemCount}) "
            + " AND (:#{#param.initValue} IS NULL OR c.initValue = :#{#param.initValue}) "
            + " AND (:#{#param.initOutPrice} IS NULL OR c.initOutPrice = :#{#param.initOutPrice}) "
            + " AND (:#{#param.initInPrice} IS NULL OR c.initInPrice = :#{#param.initInPrice}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " AND (:#{#param.outPrice} IS NULL OR c.outPrice = :#{#param.outPrice}) "
            + " ORDER BY c.id desc"
    )
    Optional<Inventory> searchDetail(@Param("param") InventoryReq param);

}
