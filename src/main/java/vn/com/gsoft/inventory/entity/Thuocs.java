package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Thuocs")
public class Thuocs extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "MaThuoc")
    private String maThuoc;
    @Column(name = "TenThuoc")
    private String tenThuoc;
    @Column(name = "ThongTin")
    private String thongTin;
    @Column(name = "HeSo")
    private Integer heSo;
    @Column(name = "GiaNhap")
    private BigDecimal giaNhap;
    @Column(name = "GiaBanBuon")
    private BigDecimal giaBanBuon;
    @Column(name = "GiaBanLe")
    private BigDecimal giaBanLe;
    @Column(name = "SoDuDauKy")
    private BigDecimal soDuDauKy;
    @Column(name = "GiaDauKy")
    private BigDecimal giaDauKy;
    @Column(name = "GioiHan")
    private Integer gioiHan;
    @Column(name = "NhaThuoc_MaNhaThuoc")
    private String nhaThuocMaNhaThuoc;
    @Column(name = "NhomThuoc_MaNhomThuoc")
    private Long nhomThuocMaNhomThuoc;
    @Column(name = "Nuoc_MaNuoc")
    private Integer nuocMaNuoc;
    @Column(name = "DangBaoChe_MaDangBaoChe")
    private Integer dangBaoCheMaDangBaoChe;
    @Column(name = "DonViXuatLe_MaDonViTinh")
    private Long donViXuatLeMaDonViTinh;
    @Column(name = "DonViThuNguyen_MaDonViTinh")
    private Long donViThuNguyenMaDonViTinh;
    @Column(name = "BarCode")
    private String barCode;
    @Column(name = "HoatDong")
    private Boolean hoatDong;
    @Column(name = "HangTuVan")
    private Boolean hangTuVan;
    @Column(name = "HanDung")
    private Date hanDung;
    @Column(name = "DuTru")
    private Integer duTru;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "NhaThuoc_MaNhaThuocCreate")
    private String nhaThuocMaNhaThuocCreate;
    @Column(name = "ConnectivityDrugID")
    private Long connectivityDrugID;
    @Column(name = "ConnectivityDrugFactor")
    private BigDecimal connectivityDrugFactor;
    @Column(name = "MaNhaCungCap")
    private Integer maNhaCungCap;
    @Column(name = "ParentDrugId")
    private Long parentDrugId;
    @Column(name = "MetadataHash")
    private Integer metadataHash;
    @Column(name = "RpMetadataHash")
    private Integer rpMetadataHash;
    @Column(name = "ReferenceId")
    private Long referenceId;
    @Column(name = "Discount")
    private Double discount;
    @Column(name = "DiscountByRevenue")
    private Boolean discountByRevenue;
    @Column(name = "SaleTypeId")
    private Long saleTypeId;
    @Column(name = "SaleOff")
    private BigDecimal saleOff;
    @Column(name = "SaleDescription")
    private String saleDescription;
    @Column(name = "SaleStartDate")
    private Date saleStartDate;
    @Column(name = "SaleEndDate")
    private Date saleEndDate;
    @Column(name = "Scorable")
    private Boolean scorable;
    @Column(name = "ImageThumbUrl")
    private String imageThumbUrl;
    @Column(name = "ImagePreviewUrl")
    private String imagePreviewUrl;
    @Column(name = "ConnectivityTypeId")
    private Long connectivityTypeId;
    @Column(name = "ArchivedId")
    private Long archivedId;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "ProductTypeId")
    private Long productTypeId;
    @Column(name = "SerialNumber")
    private String serialNumber;
    @Column(name = "MoneyToOneScoreRate")
    private BigDecimal moneyToOneScoreRate;
    @Column(name = "Presentation")
    private Boolean presentation;
    @Column(name = "NameHash")
    private Long nameHash;
    @Column(name = "RegisteredNo")
    private String registeredNo;
    @Column(name = "ActiveSubstance")
    private String activeSubstance;
    @Column(name = "Contents")
    private String contents;
    @Column(name = "PackingWay")
    private String packingWay;
    @Column(name = "Manufacturer")
    private String manufacturer;
    @Column(name = "CountryOfManufacturer")
    private String countryOfManufacturer;
    @Column(name = "CountryId")
    private Long countryId;
    @Column(name = "ConnectivityId")
    private String connectivityId;
    @Column(name = "ConnectivityResult")
    private String connectivityResult;
    @Column(name = "ConnectivityDateTime")
    private Date connectivityDateTime;
    @Column(name = "DosageForms")
    private String dosageForms;
    @Column(name = "SmallestPackingUnit")
    private String smallestPackingUnit;
    @Column(name = "Importers")
    private String importers;
    @Column(name = "DeclaredPrice")
    private BigDecimal declaredPrice;
    @Column(name = "ConnectivityCode")
    private String connectivityCode;
    @Column(name = "CodeHash")
    private Long codeHash;
    @Column(name = "ConnectivityStatusId")
    private Long connectivityStatusId;
    @Column(name = "OrganizeDeclaration")
    private String organizeDeclaration;
    @Column(name = "CountryRegistration")
    private String countryRegistration;
    @Column(name = "AddressRegistration")
    private String addressRegistration;
    @Column(name = "AddressManufacture")
    private String addressManufacture;
    @Column(name = "Identifier")
    private String identifier;
    @Column(name = "Classification")
    private String classification;
    @Column(name = "ForWholesale")
    private Integer forWholesale;
    @Column(name = "HoatChat")
    private String hoatChat;
    @Column(name = "TypeService")
    private Integer typeService;
    @Column(name = "TypeServices")
    private Integer typeServices;
    @Column(name = "IdTypeService")
    private Long idTypeService;
    @Column(name = "IdClinic")
    private Long idClinic;
    @Column(name = "CountNumbers")
    private Integer countNumbers;
    @Column(name = "IdWarehouseLocation")
    private Long idWarehouseLocation;
    @Column(name = "HamLuong")
    private String hamLuong;
    @Column(name = "QuyCachDongGoi")
    private String quyCachDongGoi;
    @Column(name = "NhaSanXuat")
    private String nhaSanXuat;
    @Column(name = "Advantages")
    private String advantages;
    @Column(name = "UserObject")
    private String userObject;
    @Column(name = "UserManual")
    private String userManual;
    @Column(name = "Pharmacokinetics")
    private String pharmacokinetics;
    @Column(name = "IsShow_CustomerWebsite")
    private Boolean isShowCustomerWebsite;
    @Column(name = "Flag")
    private Boolean flag;
    @Column(name = "GroupIdMapping")
    private Long groupIdMapping;
    @Column(name = "GroupNameMapping")
    private String groupNameMapping;
    @Column(name = "ResultService")
    private String resultService;
    @Column(name = "TitleResultService")
    private String titleResultService;
    @Column(name = "TypeResultService")
    private Integer typeResultService;
    @Column(name = "GroupIdMappingV2")
    private Long groupIdMappingV2;
    @Column(name = "StorageConditions")
    private String storageConditions;
    @Column(name = "StorageLocation")
    private String storageLocation;
    @Column(name = "MappingDate")
    private Date mappingDate;
    @Column(name = "ChiDinh")
    private String chiDinh;
    @Column(name = "ChongChiDinh")
    private String chongChiDinh;
    @Column(name = "XuatXu")
    private String xuatXu;
    @Column(name = "LuuY")
    private String luuY;
    @Column(name = "PromotionalDiscounts")
    private BigDecimal promotionalDiscounts;
    @Column(name = "EnablePromotionalDiscounts")
    private Boolean enablePromotionalDiscounts;
    @Column(name = "DescriptionOnWebsite")
    private String descriptionOnWebsite;
    @Column(name = "ImgReferenceDrugId")
    private Long imgReferenceDrugId;
    @Column(name = "UserUploadImgDate")
    private Date userUploadImgDate;
    @Column(name = "UserUploadImgId")
    private Long userUploadImgId;
    @Column(name = "StatusConfirm")
    private Integer statusConfirm;
    @Column(name = "UserIdConfirm")
    private Long userIdConfirm;
    @Column(name = "UserIdMapping")
    private Long userIdMapping;

    // @Transient

    @Transient
    private String tenNhomThuoc;

    @Transient
    private String tenDonViTinhXuatLe;

    @Transient
    private String tenDonViTinhThuNguyen;

    @Transient
    private String tenViTri;

    @Transient
    private List<DonViTinhs> listDonViTinhs;

    @Transient
    private Inventory inventory;
}

