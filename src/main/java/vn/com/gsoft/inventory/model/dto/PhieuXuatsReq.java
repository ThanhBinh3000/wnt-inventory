package vn.com.gsoft.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.gsoft.inventory.entity.PhieuXuatChiTiets;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class PhieuXuatsReq extends BaseRequest {
    private Long soPhieuXuat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date ngayXuat;
    private Integer vat;
    private String dienGiai;
    private Double tongTien;
    private Double daTra;
    private String nhaThuocMaNhaThuoc;
    private Long maLoaiXuatNhap;
    private Long khachHangMaKhachHang;
    private Long nhaCungCapMaNhaCungCap;
    private Long bacSyMaBacSy;
    private Boolean active;
    private Boolean isModified;
    private Boolean locked;
    private Boolean isDebt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date preNoteDate;
    private String connectivityNoteID;
    private Long connectivityStatusID;
    private String connectivityResult;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date connectivityDateTime;
    private Long orderId;
    private Double discount;
    private BigDecimal score;
    private BigDecimal preScore;
    private Long archivedId;
    private Long storeId;
    private Long targetId;
    private Long sourceId;
    private Long sourceStoreId;
    private Long targetStoreId;
    private Long partnerId;
    private Long prescriptionId;
    private UUID uId;
    private String invoiceCode;
    private String invoiceNo;
    private String referenceKey;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date invoiceDate;
    private BigDecimal paymentScore;
    private Double paymentScoreAmount;
    private Long bonusPaymentId;
    private String invoiceTemplateCode;
    private String invoiceSeries;
    private String invoiceType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date archivedDate;
    private BigDecimal prePaymentScore;
    private Long synStatusId;
    private BigDecimal transPaymentAmount;
    private Long paymentTypeId;
    private BigDecimal debtPaymentAmount;
    private BigDecimal backPaymentAmount;
    private String linkFile;
    private String doctorComments;
    private String keyNewEInvoice;
    private String keyOldEInvoice;
    private Long einvoiceStatusID;
    private Long signEInvoiceStatusID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date connEInvoiceDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date signEInvoiceDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date destroyEInvoiceDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date replacedEInvoiceDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date editedEInvoiceDateTime;
    private String khhDon;
    private String khmshDon;
    private String shDon;
    private Long pickUpOrderId;
    private String esampleNoteCode;
    private String linkConfirm;
    private String taxAuthorityCode;
    private String einvoiveResult;
    private Boolean isRefSampleNote;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date nextPurchaseDate;
    private String trackingIdZNS;
    private String resultZNS;
    private Long idPaymentQR;
    private Long targetManagementId;

    private Boolean isConnectivity;

    private List<PhieuXuatChiTiets> chiTiets;

    private List<Long> thuocIds;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date ngayTinhNo;
}
