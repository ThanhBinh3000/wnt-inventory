package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PhieuNhaps")
public class PhieuNhaps extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "SoPhieuNhap")
    private Long soPhieuNhap;
    @Column(name = "NgayNhap")
    private Date ngayNhap;
    @Column(name = "VAT")
    private Integer vat;
    @Column(name = "DienGiai")
    private String dienGiai;
    @Column(name = "TongTien")
    private Double tongTien;
    @Column(name = "DaTra")
    private Double daTra;
    @Column(name = "NhaThuoc_MaNhaThuoc")
    private String nhaThuocMaNhaThuoc;
    @Column(name = "LoaiXuatNhap_MaLoaiXuatNhap")
    private Long loaiXuatNhapMaLoaiXuatNhap;
    @Column(name = "NhaCungCap_MaNhaCungCap")
    private Long nhaCungCapMaNhaCungCap;
    @Column(name = "KhachHang_MaKhachHang")
    private Long khachHangMaKhachHang;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "IsModified")
    private Boolean isModified;
    @Column(name = "Locked")
    private Boolean locked;
    @Column(name = "IsDebt")
    private Boolean isDebt;
    @Column(name = "PreNoteDate")
    private Date preNoteDate;
    @Column(name = "ConnectivityNoteID")
    private String connectivityNoteID;
    @Column(name = "ConnectivityStatusID")
    private Long connectivityStatusID;
    @Column(name = "ConnectivityResult")
    private String connectivityResult;
    @Column(name = "ConnectivityDateTime")
    private Date connectivityDateTime;
    @Column(name = "OrderId")
    private Long orderId;
    @Column(name = "ArchivedId")
    private Long archivedId;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "TargetId")
    private Long targetId;
    @Column(name = "SourceId")
    private Long sourceId;
    @Column(name = "SourceStoreId")
    private Long sourceStoreId;
    @Column(name = "TargetStoreId")
    private Long targetStoreId;
    @Column(name = "PartnerId")
    private Long partnerId;
    @Column(name = "UId")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uId;
    @Column(name = "InvoiceCode")
    private String invoiceCode;
    @Column(name = "InvoiceNo")
    private String invoiceNo;
    @Column(name = "Score")
    private BigDecimal score;
    @Column(name = "PreScore")
    private BigDecimal preScore;
    @Column(name = "ReferenceKey")
    private String referenceKey;
    @Column(name = "InvoiceDate")
    private Date invoiceDate;
    @Column(name = "InvoiceTemplateCode")
    private String invoiceTemplateCode;
    @Column(name = "InvoiceSeries")
    private String invoiceSeries;
    @Column(name = "InvoiceType")
    private String invoiceType;
    @Column(name = "ArchivedDate")
    private Date archivedDate;
    @Column(name = "TimeTypeId")
    private Long timeTypeId;
    @Column(name = "NoteName")
    private String noteName;
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Reasons")
    private String reasons;
    @Column(name = "SynStatusId")
    private Long synStatusId;
    @Column(name = "PaymentTypeId")
    private Long paymentTypeId;
    @Column(name = "DebtPaymentAmount")
    private BigDecimal debtPaymentAmount;
    @Column(name = "PickUpOrderId")
    private Long pickUpOrderId;
    @Lob
    @Column(name = "LinkFile")
    private String linkFile;
    @Column(name = "Discount")
    private Double discount;
    @Column(name = "TargetManagementId")
    private Long targetManagementId;

    @Transient
    private List<PhieuNhapChiTiets> chiTiets;

    @Transient
    private String tenNhaCungCap;

    @Transient
    private String tenKhachHang;

    @Transient
    private String tenPaymentType;

    @Transient
    private String tenNguoiTao;

    @Transient
    private String targetStoreText;

    @Transient
    private String loaiXuatNhapMaLoaiXuatNhapText;

    //    priview
    @Transient
    private String diaChiNhaThuoc;
    @Transient
    private String sdtNhaThuoc;
    @Transient
    private String diaChiKhachHang;
    @Transient
    private String sdtKhachHang;
    @Transient
    private String diaChiNhaCungCap;
    @Transient
    private Double conNo;
}