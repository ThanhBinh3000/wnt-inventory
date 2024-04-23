package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DonViTinhs")
public class DonViTinhs extends BaseEntity{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TenDonViTinh")
    private String tenDonViTinh;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "ReferenceId")
    private Long referenceId;
    @Column(name = "ArchivedId")
    private Long archivedId;
    @Column(name = "StoreId")
    private Long storeId;

    @Transient
    private Integer factor;
    @Transient
    private BigDecimal giaBan;
    @Transient
    private BigDecimal giaNhap;
}

