package vn.com.gsoft.inventory.model.dto;

import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PhieuXuatChiTietsReq extends BaseRequest {
    private Long phieuXuatMaPhieuXuat;
    private String nhaThuocMaNhaThuoc;
    private Long thuocThuocId;
    private Long donViTinhMaDonViTinh;
    private BigDecimal chietKhau;
    private BigDecimal giaXuat;
    private BigDecimal soLuong;
    private String option1;
    private String option2;
    private String option3;
    private String refConnectivityCode;
    private String preQuantity;
    private Boolean isReceiptDrugPriceRefGenerated;
    private Float retailQuantity;
    private Long handledStatusId;
    private Float retailPrice;
    private Boolean requestUpdateFromBkgService;
    private String reduceNoteItemIds;
    private Float reduceQuantity;
    private Boolean isModified;
    private Integer itemOrder;
    private Long archiveDrugId;
    private Long archiveUnitId;
    private Float preRetailQuantity;
    private String batchNumber;
    private Date expiredDate;
    private Boolean expirySetAuto;
    private Long referenceId;
    private Long archivedId;
    private Long storeId;
    private Long connectivityStatusId;
    private String connectivityResult;
    private Integer vat;
    private String reason;
    private String solution;
    private String notes;
    private Boolean lockReGenReportData;
    private Boolean isProdRef;
    private Boolean negativeRevenue;
    private BigDecimal revenue;
    private BigDecimal refPrice;
    private String usage;
    private BigDecimal outOwnerPriceChild;
}
