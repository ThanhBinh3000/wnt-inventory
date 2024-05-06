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
@Table(name = "KhachHangs")
public class KhachHangs extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TenKhachHang")
    private String tenKhachHang;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "SoDienThoai")
    private String soDienThoai;
    @Column(name = "NoDauKy" , columnDefinition = "decimal default 0")
    private BigDecimal noDauKy;
    @Column(name = "DonViCongTac")
    private String donViCongTac;
    @Column(name = "Email")
    private String email;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "MaNhomKhachHang")
    private Long maNhomKhachHang;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "CustomerTypeId" , columnDefinition = "integer default 0")
    private Integer customerTypeId;
    @Column(name = "BarCode")
    private String barCode;
    @Column(name = "BirthDate")
    private Date birthDate;
    @Column(name = "Code")
    private String code;
    @Column(name = "Score", columnDefinition = "decimal default 0")
    private BigDecimal score;
    @Column(name = "InitScore", columnDefinition = "decimal default 0")
    private BigDecimal initScore;
    @Column(name = "ArchivedId", columnDefinition = "integer default 0")
    private Integer archivedId;
    @Column(name = "ReferenceId", columnDefinition = "integer default 0")
    private Integer referenceId;
    @Column(name = "StoreId", columnDefinition = "integer default 0")
    private Integer storeId;
    @Column(name = "RegionId", columnDefinition = "integer default 0" )
    private Integer regionId;
    @Column(name = "CityId",columnDefinition = "integer default 0")
    private Integer cityId;
    @Column(name = "WardId", columnDefinition = "integer default 0")
    private Integer wardId;
    @Column(name = "MasterId", columnDefinition = "integer default 0")
    private Integer masterId;
    @Column(name = "MetadataHash", columnDefinition = "integer default 0")
    private Integer metadataHash;
    @Column(name = "PreMetadataHash", columnDefinition = "integer default 0")
    private Integer preMetadataHash;
    @Column(name = "NationalFacilityCode")
    private String nationalFacilityCode;
    @Column(name = "MappingStoreId", columnDefinition = "integer default 0")
    private Integer mappingStoreId;
    @Column(name = "TotalScore" , columnDefinition = "decimal default 0")
    private BigDecimal totalScore;
    @Column(name = "SexId", columnDefinition = "integer default 0")
    private Integer sexId;
    @Column(name = "NameContacter")
    private String nameContacter;
    @Column(name = "PhoneContacter")
    private String phoneContacter;
    @Column(name = "RefCus")
    private String refCus;
    @Column(name = "CusType", columnDefinition = "boolean default 0")
    private Boolean cusType;
    @Column(name = "TaxCode")
    private String taxCode;
    @Column(name = "MedicalIdentifier")
    private String medicalIdentifier;
    @Column(name = "CitizenIdentification")
    private String citizenIdentification;
    @Column(name = "HealthInsuranceNumber")
    private String healthInsuranceNumber;
    @Column(name = "Job")
    private String job;
    @Column(name = "AbilityToPay")
    private String abilityToPay;
    @Column(name = "ZaloId")
    private String zaloId;
}

