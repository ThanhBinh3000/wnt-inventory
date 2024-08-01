package vn.com.gsoft.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PhieuNhapChiTietsReq  extends BaseRequest {
    private Long phieuNhapMaPhieuNhap;
    private String nhaThuocMaNhaThuoc;
    private Long thuocThuocId;
    private Long donViTinhMaDonViTinh;
    private BigDecimal chietKhau;
    private BigDecimal giaNhap;
    private BigDecimal soLuong;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String soLo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date hanDung;
    private Float remainRefQuantity;
    private Float retailQuantity;
    private Float preRetailQuantity;
    private Long handledStatusId;
    private Float retailPrice;
    private Boolean requestUpdateFromBkgService;
    private String reduceNoteItemIds;
    private Float reduceQuantity;
    private Boolean isModified;
    private BigDecimal giaBanLe;
    private Float retailOutPrice;
    private Integer itemOrder;
    private Integer rpMetadataHash;
    private Long archiveDrugId;
    private Long archiveUnitId;
    private Boolean expirySetAuto;
    private Long referenceId;
    private Long archivedId;
    private Long storeId;
    private Long connectivityStatusId;
    private String connectivityResult;
    private Integer vat;
    private Boolean lockReGenReportData;
    private String reason;
    private String solution;
    private String notes;
    private Boolean isProdRef;
    private BigDecimal refPrice;
    private String decscription;
    private String storageConditions;

    private Long khachHangMaKhachHang;

    private Long nhaCungCapMaNhaCungCap;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fromDateNgayNhap;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date toDateNgayNhap;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fromDateTu;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fromDateDen;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fromDate;
    private BigDecimal totalAmount;
    private String soPhieuNhap;
    private String nhaCungCapMaNhaCungCapText;
    private String tenThuocText;
    private String donViTinhMaDonViTinhText;
    private BigDecimal totalThanhTien;
    private String maLoaiXuatNhapText;

}
