package vn.com.gsoft.inventory.model.dto;

import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InventoryReq extends BaseRequest {
    private String drugStoreID;
    private Long drugID;
    private Float lastValue;
    private Integer drugUnitID;
    private Long recordStatusID;
    private Boolean needUpdate;
    private Float lastInPrice;
    private Float lastOutPrice;
    private Float retailOutPrice;
    private Float retailBatchOutPrice;
    private Date lastUpdated;
    private Date lastIncurredData;
    private String serialNumber;
    private Boolean regenRevenue;
    private Integer archiveDrugId;
    private Integer archiveUnitId;
    private Boolean hasTransactions;
    private Integer receiptItemCount;
    private Integer deliveryItemCount;
    private Date expiredDate;
    private BigDecimal initValue;
    private BigDecimal initOutPrice;
    private BigDecimal initInPrice;
    private Integer storeId;
    private Date archivedDate;
    private BigDecimal outPrice;
}
