package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BacSies")
@Entity
public class BacSies extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TenBacSy")
    private String tenBacSy;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "DienThoai")
    private String dienThoai;
    @Column(name = "Email")
    private String email;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "MasterId")
    private Integer masterId;
    @Column(name = "MetadataHash")
    private Integer metadataHash;
    @Column(name = "PreMetadataHash")
    private Integer preMetadataHash;
    @Column(name = "Code")
    private String code;
    @Column(name = "ConnectCode")
    private String connectCode;
    @Column(name = "ConnectPassword")
    private String connectPassword;
    @Column(name = "IsConnectivity")
    private Boolean isConnectivity;
    @Column(name = "ResultConnect")
    private String resultConnect;
    @Column(name = "MaNhomBacSy")
    private Long maNhomBacSy;

    // @Transient

    @Transient
    private String tenNhomBacSy;
}

