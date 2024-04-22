package vn.com.gsoft.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Inventory")
public class Inventory{
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "DrugStoreID")
    private String drugStoreID;
    @Column(name = "DrugID")
    private Integer drugID;
    @Column(name = "LastValue")
    private Float lastValue;
    @Column(name = "DrugUnitID")
    private Integer drugUnitID;
    @Column(name = "RecordStatusID")
    private Integer recordStatusID;
    @Column(name = "NeedUpdate")
    private Boolean needUpdate;
    @Column(name = "LastInPrice")
    private Float lastInPrice;
    @Column(name = "LastOutPrice")
    private Float lastOutPrice;
    @Column(name = "RetailOutPrice")
    private Float retailOutPrice;
    @Column(name = "RetailBatchOutPrice")
    private Float retailBatchOutPrice;
    @Column(name = "LastUpdated")
    private Date lastUpdated;
    @Column(name = "LastIncurredData")
    private Date lastIncurredData;
    @Column(name = "SerialNumber")
    private String serialNumber;
    @Column(name = "RegenRevenue")
    private Boolean regenRevenue;
    @Column(name = "ArchiveDrugId")
    private Integer archiveDrugId;
    @Column(name = "ArchiveUnitId")
    private Integer archiveUnitId;
    @Column(name = "HasTransactions")
    private Boolean hasTransactions;
    @Column(name = "ReceiptItemCount")
    private Integer receiptItemCount;
    @Column(name = "DeliveryItemCount")
    private Integer deliveryItemCount;
    @Column(name = "ExpiredDate")
    private Date expiredDate;
    @Column(name = "InitValue")
    private BigDecimal initValue;
    @Column(name = "InitOutPrice")
    private BigDecimal initOutPrice;
    @Column(name = "InitInPrice")
    private BigDecimal initInPrice;
    @Column(name = "StoreId")
    private Integer storeId;
    @Column(name = "ArchivedDate")
    private Date archivedDate;
    @Column(name = "OutPrice")
    private BigDecimal outPrice;
}

