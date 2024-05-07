package vn.com.gsoft.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.util.Date;

@Data
public class PhieuXuatChiTietsReq extends BaseRequest {
    private Long phieuXuatMaPhieuXuat;
    private String nhaThuocMaNhaThuoc;
    private Long thuocThuocId;
    private Long donViTinhMaDonViTinh;
    private Double chietKhau;
    private Double giaXuat;
    private Double soLuong;
    private String option1;
    private String option2;
    private String option3;
    private String refConnectivityCode;
    private String preQuantity;
    private Boolean isReceiptDrugPriceRefGenerated;
    private Double retailQuantity;
    private Long handledStatusId;
    private Double retailPrice;
    private Boolean requestUpdateFromBkgService;
    private String reduceNoteItemIds;
    private Double reduceQuantity;
    private Boolean isModified;
    private Integer itemOrder;
    private Long archiveDrugId;
    private Long archiveUnitId;
    private Double preRetailQuantity;
    private String batchNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expiredDate;
    private Boolean expirySetAuto;
    private Long referenceId;
    private Long archivedId;
    private Long storeId;
    private Long connectivityStatusId;
    private String connectivityResult;
    private Double vat;
    private String reason;
    private String solution;
    private String notes;
    private Boolean lockReGenReportData;
    private Boolean isProdRef;
    private Boolean negativeRevenue;
    private Double revenue;
    private Double refPrice;
    private String usage;
    private Double outOwnerPriceChild;

    private Long khachHangMaKhachHang;
}
