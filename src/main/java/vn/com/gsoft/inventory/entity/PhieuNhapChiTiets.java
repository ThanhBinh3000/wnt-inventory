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
public class PhieuNhapChiTiets extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Double remainRefQuantity;
    @Column(name = "RetailQuantity")
    private Double retailQuantity;
    @Column(name = "PreRetailQuantity")
    private Double preRetailQuantity;
    @Column(name = "HandledStatusId")
    private Long handledStatusId;
    @Column(name = "RetailPrice")
    private Double retailPrice;
    @Column(name = "RequestUpdateFromBkgService")
    private Boolean requestUpdateFromBkgService;
    @Column(name = "ReduceNoteItemIds")
    private String reduceNoteItemIds;
    @Column(name = "ReduceQuantity")
    private Double reduceQuantity;
    @Column(name = "IsModified")
    private Boolean isModified;
    @Column(name = "GiaBanLe")
    private BigDecimal giaBanLe;
    @Column(name = "RetailOutPrice")
    private Double retailOutPrice;
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
    private Double vat;
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
    //    priview
    @Transient
    private BigDecimal thanhTien;

    @Transient
    private String donViTinhMaDonViTinhText;
    @Transient
    private String maThuocText;
    @Transient
    private String tenThuocText;
    @Transient
    private Long soPhieuNhap;
    @Transient
    private Date ngayNhap;
    @Transient
    private Double vatPhieuNhap;
    @Transient
    private Double debtPaymentAmount;
    @Transient
    private Long loaiXuatNhapMaLoaiXuatNhap;
    @Transient
    private String loaiXuatNhapMaLoaiXuatNhapText;
    @Transient
    private Long khachHangMaKhachHang;
    @Transient
    private String khachHangMaKhachHangText;
    @Transient
    private String createdByUserText;
    @Transient
    private Long nhaCungCapMaNhaCungCap;
    @Transient
    private String nhaCungCapMaNhaCungCapText;
    @Transient
    private Long maLoaiXuatNhap;
    @Transient
    private String maLoaiXuatNhapText;
    @Transient
    private Long targetStoreId;
    @Transient
    private String targetStoreText;
}

