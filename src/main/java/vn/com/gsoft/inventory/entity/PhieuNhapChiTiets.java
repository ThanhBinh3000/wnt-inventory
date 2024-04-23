package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
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
@Table(name = "PhieuNhapChiTiets")
public class PhieuNhapChiTiets {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PhieuNhap_MaPhieuNhap")
    private Long phieuNhapMaPhieuNhap;
    @Column(name = "NhaThuoc_MaNhaThuoc")
    private String nhaThuocMaNhaThuoc;
    @Column(name = "Thuoc_ThuocId")
    private Long thuocThuocId;
    @Column(name = "DonViTinh_MaDonViTinh")
    private Long donViTinhMaDonViTinh;
    @Column(name = "ChietKhau")
    private BigDecimal chietKhau;
    @Column(name = "GiaNhap")
    private BigDecimal giaNhap;
    @Column(name = "SoLuong")
    private BigDecimal soLuong;
    @Column(name = "Option1")
    private String option1;
    @Column(name = "Option2")
    private String option2;
    @Column(name = "Option3")
    private String option3;
    @Column(name = "Option4")
    private String option4;
    @Column(name = "Option5")
    private String option5;
    @Column(name = "SoLo")
    private String soLo;
    @Column(name = "HanDung")
    private Date hanDung;
    @Column(name = "RemainRefQuantity")
    private Float remainRefQuantity;
    @Column(name = "RetailQuantity")
    private Float retailQuantity;
    @Column(name = "PreRetailQuantity")
    private Float preRetailQuantity;
    @Column(name = "HandledStatusId")
    private Long handledStatusId;
    @Column(name = "RetailPrice")
    private Float retailPrice;
    @Column(name = "RequestUpdateFromBkgService")
    private Boolean requestUpdateFromBkgService;
    @Column(name = "ReduceNoteItemIds")
    private String reduceNoteItemIds;
    @Column(name = "ReduceQuantity")
    private Float reduceQuantity;
    @Column(name = "IsModified")
    private Boolean isModified;
    @Column(name = "GiaBanLe")
    private BigDecimal giaBanLe;
    @Column(name = "RetailOutPrice")
    private Float retailOutPrice;
    @Column(name = "ItemOrder")
    private Integer itemOrder;
    @Column(name = "RpMetadataHash")
    private Integer rpMetadataHash;
    @Column(name = "ArchiveDrugId")
    private Long archiveDrugId;
    @Column(name = "ArchiveUnitId")
    private Long archiveUnitId;
    @Column(name = "ExpirySetAuto")
    private Boolean expirySetAuto;
    @Column(name = "ReferenceId")
    private Long referenceId;
    @Column(name = "ArchivedId")
    private Long archivedId;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "ConnectivityStatusId")
    private Long connectivityStatusId;
    @Column(name = "ConnectivityResult")
    private String connectivityResult;
    @Column(name = "VAT")
    private Integer vat;
    @Column(name = "LockReGenReportData")
    private Boolean lockReGenReportData;
    @Column(name = "Reason")
    private String reason;
    @Column(name = "Solution")
    private String solution;
    @Column(name = "Notes")
    private String notes;
    @Column(name = "IsProdRef")
    private Boolean isProdRef;
    @Column(name = "RefPrice")
    private BigDecimal refPrice;
    @Column(name = "Decscription")
    private String decscription;
    @Column(name = "StorageConditions")
    private String storageConditions;

    @Transient
    private Thuocs thuocs;

    @Transient
    private String tenDonViTinh;

    }

