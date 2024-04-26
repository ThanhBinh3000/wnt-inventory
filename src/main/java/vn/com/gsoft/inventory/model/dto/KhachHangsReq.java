package vn.com.gsoft.inventory.model.dto;

import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class KhachHangsReq extends BaseRequest {

    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;
    private BigDecimal noDauKy;
    private String donViCongTac;
    private String email;
    private String ghiChu;
    private String maNhaThuoc;
    private Long maNhomKhachHang;
    private Date created;
    private Date modified;
    private Boolean active;
    private Integer customerTypeId;
    private String barCode;
    private Date birthDate;
    private String code;
    private BigDecimal score;
    private BigDecimal initScore;
    private Integer archivedId;
    private Integer referenceId;
    private Integer storeId;
    private Integer regionId;
    private Integer cityId;
    private Integer wardId;
    private Integer masterId;
    private Integer metadataHash;
    private Integer preMetadataHash;
    private String nationalFacilityCode;
    private Integer mappingStoreId;
    private BigDecimal totalScore;
    private Integer sexId;
    private String nameContacter;
    private String phoneContacter;
    private String refCus;
    private Boolean cusType;
    private String taxCode;
    private String medicalIdentifier;
    private String citizenIdentification;
    private String healthInsuranceNumber;
    private String job;
    private String abilityToPay;
    private String zaloId;
}

