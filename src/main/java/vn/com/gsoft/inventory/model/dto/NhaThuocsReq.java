package vn.com.gsoft.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class NhaThuocsReq extends BaseRequest {
    private String maNhaThuoc;
    private String tenNhaThuoc;
    private String diaChi;
    private String soKinhDoanh;
    private String dienThoai;
    private String nguoiDaiDien;
    private String email;
    private String mobile;
    private String duocSy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date modified;
    private Long createdByUserId;
    private Long modifiedByUserId;
    private Boolean hoatDong;
    private Boolean active;
    private Boolean isReportDataGenerating;
    private Long tinhThanhId;
    private String maNhaThuocCha;
    private Boolean isConnectivity;
    private String connectivityCode;
    private String connectivityUserName;
    private String connectivityPassword;
    private String generalPharmacyId;
    private Boolean isGeneralPharmacy;
    private BigDecimal paidAmount;
    private String description;
    private Long drugStoreTypeId;
    private Boolean isPaid;
    private Long chainLinkId;
    private Long regionId;
    private Long cityId;
    private Long wardId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastTransDate;
    private String supportPhones;
    private String deliveryPolicy;
    private String contentThankYou;
    private Long idTypeBasic;
    private String ghiChu;
    private Boolean isNationalDBConnected;
    private String imagePreviewUrl;
    private String imageThumbUrl;
    private Long totalNumberInvoices;
    private String connEInvoiceUserName;
    private String connEInvoicePassword;
    private String symbolCodeInvocie;
    private String formNumberInvoice;
    private Integer typeInvoice;
    private Boolean paymentStatus;
    private BigDecimal paidMoney;
    private Boolean isNationalSampleConnected;
    private String connectivityCodeMeidcal;
    private String connectivityPasswordMedical;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expiredDate;
    private String businessDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date paidDate;
    private Boolean isUploading;
    private String connEInvoiceSerialCert;
    private String linkConnectEInvoice;
    private String nameServiceEInvoice;
    private String passServiceEInvoice;
    private String signedString;
    private Integer typeSendEinvocie;
    private String slugCustomerWebsite;
    private String googleLocationCustomerWebsite;
    private Long themeIdCustomerWebsite;
    private String tokenZalo;
    private String zaloKey;
    private String appId;
    private String refreshTokenZalo;
    private String imageOrderThumbUrl;
    private String imageOrderPreviewUrl;
    private Integer typeMessage;
    private String bannerCustomerWebsite;
    private String mainSloganCustomerWebsite;
    private String subSloganCustomerWebsite;
    private String qRDeviceName;
    private String qRDeviceToken;
    private String simData;
    private Boolean mapped;
    private Boolean zNSStatusSendCreateAccount;
    private String zNSTrackingIdCreateAccount;
    private Boolean zNSStatusSendPayment;
    private String zNSTrackingIdPayment;
    private Long businessId;
    private Integer codeErrorConfirmPaymentZNS;
    private Integer codeErrorCreateAccountZNS;
    private String footerPrint;
    private Integer classify;
    private Integer evaluate;
    private Long supporterId;
    private Boolean upgradeToPlus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date toDate;
    private Long storeTypeId;
    private Long storePaymentTypeId;
    private Integer numDaysNoTrans;
    private Integer typeDate;
    private Long storeDeployTypeId;
    private Integer expiredType;
    private Boolean outOfInvoice;
    private Integer typeZNS;
}
