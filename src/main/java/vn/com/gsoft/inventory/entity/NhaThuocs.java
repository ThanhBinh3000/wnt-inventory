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
@Table(name = "NhaThuocs")
public class NhaThuocs extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "TenNhaThuoc")
    private String tenNhaThuoc;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "SoKinhDoanh")
    private String soKinhDoanh;
    @Column(name = "DienThoai")
    private String dienThoai;
    @Column(name = "NguoiDaiDien")
    private String nguoiDaiDien;
    @Column(name = "Email")
    private String email;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "DuocSy")
    private String duocSy;
    @Column(name = "HoatDong")
    private Boolean hoatDong;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "IsReportDataGenerating")
    private Boolean isReportDataGenerating;
    @Column(name = "TinhThanhId")
    private Long tinhThanhId;
    @Column(name = "MaNhaThuocCha")
    private String maNhaThuocCha;
    @Column(name = "IsConnectivity")
    private Boolean isConnectivity;
    @Column(name = "ConnectivityCode")
    private String connectivityCode;
    @Column(name = "ConnectivityUserName")
    private String connectivityUserName;
    @Column(name = "ConnectivityPassword")
    private String connectivityPassword;
    @Column(name = "GeneralPharmacyId")
    private String generalPharmacyId;
    @Column(name = "IsGeneralPharmacy")
    private Boolean isGeneralPharmacy;
    @Column(name = "PaidAmount")
    private BigDecimal paidAmount;
    @Column(name = "Description")
    private String description;
    @Column(name = "DrugStoreTypeId")
    private Long drugStoreTypeId;
    @Column(name = "IsPaid")
    private Boolean isPaid;
    @Column(name = "ChainLinkId")
    private Long chainLinkId;
    @Column(name = "RegionId")
    private Long regionId;
    @Column(name = "CityId")
    private Long cityId;
    @Column(name = "WardId")
    private Long wardId;
    @Column(name = "LastTransDate")
    private Date lastTransDate;
    @Column(name = "SupportPhones")
    private String supportPhones;
    @Column(name = "DeliveryPolicy")
    private String deliveryPolicy;
    @Column(name = "ContentThankYou")
    private String contentThankYou;
    @Column(name = "IdTypeBasic")
    private Long idTypeBasic;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "IsNationalDBConnected")
    private Boolean isNationalDBConnected;
    @Column(name = "ImagePreviewUrl")
    private String imagePreviewUrl;
    @Column(name = "ImageThumbUrl")
    private String imageThumbUrl;
    @Column(name = "TotalNumberInvoices")
    private Long totalNumberInvoices;
    @Column(name = "ConnEInvoiceUserName")
    private String connEInvoiceUserName;
    @Column(name = "ConnEInvoicePassword")
    private String connEInvoicePassword;
    @Column(name = "SymbolCodeInvocie")
    private String symbolCodeInvocie;
    @Column(name = "FormNumberInvoice")
    private String formNumberInvoice;
    @Column(name = "TypeInvoice")
    private Integer typeInvoice;
    @Column(name = "PaymentStatus")
    private Boolean paymentStatus;
    @Column(name = "PaidMoney")
    private BigDecimal paidMoney;
    @Column(name = "IsNationalSampleConnected")
    private Boolean isNationalSampleConnected;
    @Column(name = "ConnectivityCodeMeidcal")
    private String connectivityCodeMeidcal;
    @Column(name = "ConnectivityPasswordMedical")
    private String connectivityPasswordMedical;
    @Column(name = "ExpiredDate")
    private Date expiredDate;
    @Column(name = "BusinessDescription")
    private String businessDescription;
    @Column(name = "PaidDate")
    private Date paidDate;
    @Column(name = "IsUploading")
    private Boolean isUploading;
    @Column(name = "ConnEInvoiceSerialCert")
    private String connEInvoiceSerialCert;
    @Column(name = "LinkConnectEInvoice")
    private String linkConnectEInvoice;
    @Column(name = "NameServiceEInvoice")
    private String nameServiceEInvoice;
    @Column(name = "PassServiceEInvoice")
    private String passServiceEInvoice;
    @Column(name = "SignedString")
    private String signedString;
    @Column(name = "TypeSendEinvocie")
    private Integer typeSendEinvocie;
    @Column(name = "Slug_CustomerWebsite")
    private String slugCustomerWebsite;
    @Lob
    @Column(name = "GoogleLocation_CustomerWebsite")
    private String googleLocationCustomerWebsite;
    @Column(name = "ThemeId_CustomerWebsite")
    private Long themeIdCustomerWebsite;
    @Column(name = "TokenZalo")
    private String tokenZalo;
    @Column(name = "ZaloKey")
    private String zaloKey;
    @Column(name = "AppId")
    private String appId;
    @Column(name = "RefreshTokenZalo")
    private String refreshTokenZalo;
    @Column(name = "ImageOrderThumbUrl")
    private String imageOrderThumbUrl;
    @Column(name = "ImageOrderPreviewUrl")
    private String imageOrderPreviewUrl;
    @Column(name = "TypeMessage")
    private Integer typeMessage;
    @Lob
    @Column(name = "Banner_CustomerWebsite")
    private String bannerCustomerWebsite;
    @Lob
    @Column(name = "MainSlogan_CustomerWebsite")
    private String mainSloganCustomerWebsite;
    @Lob
    @Column(name = "SubSlogan_CustomerWebsite")
    private String subSloganCustomerWebsite;
    @Column(name = "QRDeviceName")
    private String qRDeviceName;
    @Column(name = "QRDeviceToken")
    private String qRDeviceToken;
    @Column(name = "SimData")
    private String simData;
    @Column(name = "Mapped")
    private Boolean mapped;
    @Column(name = "ZNS_StatusSendCreateAccount")
    private Boolean zNSStatusSendCreateAccount;
    @Column(name = "ZNS_TrackingIdCreateAccount")
    private String zNSTrackingIdCreateAccount;
    @Column(name = "ZNS_StatusSendPayment")
    private Boolean zNSStatusSendPayment;
    @Column(name = "ZNS_TrackingIdPayment")
    private String zNSTrackingIdPayment;
    @Column(name = "BusinessId")
    private Long businessId;
    @Column(name = "CodeErrorConfirmPayment_ZNS")
    private Integer codeErrorConfirmPaymentZNS;
    @Column(name = "CodeErrorCreateAccount_ZNS")
    private Integer codeErrorCreateAccountZNS;
    @Column(name = "FooterPrint")
    private String footerPrint;
    @Column(name = "Classify")
    private Integer classify;
    @Column(name = "Evaluate")
    private Integer evaluate;
    @Column(name = "SupporterId")
    private Long supporterId;
    @Column(name = "UpgradeToPlus")
    private Boolean upgradeToPlus;

    // @Transient
    @Transient
    private String nguoiPhuTrach;

    @Transient
    private String nhaThuocQuanLy;

    @Transient
    private String tenTinhThanh;

    @Transient
    private String createdByUserName;

    @Transient
    private String nameTypeBasis;

    @Transient
    private Long resultBusinessId;
}

