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
@Table(name = "PhieuThuChis")
public class PhieuThuChis extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "SoPhieu")
    private Integer soPhieu;
    @Column(name = "DienGiai")
    private String dienGiai;
    @Column(name = "NgayTao")
    private Date ngayTao;
    @Column(name = "LoaiPhieu")
    private Integer loaiPhieu;
    @Column(name = "NhaThuoc_MaNhaThuoc")
    private String nhaThuocMaNhaThuoc;
    @Column(name = "KhachHang_MaKhachHang")
    private Long khachHangMaKhachHang;
    @Column(name = "NhaCungCap_MaNhaCungCap")
    private Long nhaCungCapMaNhaCungCap;
    @Column(name = "UserProfile_UserId")
    private Integer userProfileUserId;
    @Column(name = "Amount")
    private Double amount;
    @Column(name = "NguoiNhan")
    private String nguoiNhan;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "LoaiThuChi_MaLoaiPhieu")
    private Integer loaiThuChiMaLoaiPhieu;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "CustomerId")
    private Integer customerId;
    @Column(name = "SupplierId")
    private Integer supplierId;
    @Column(name = "ArchivedId")
    private Integer archivedId;
    @Column(name = "StoreId")
    private Integer storeId;
    @Column(name = "PaymentTypeId")
    private Integer paymentTypeId;
    @Column(name = "MaCoSo")
    private String maCoSo;
    @Column(name = "NhanVienId")
    private Integer nhanVienId;
    @Column(name = "RewardProgramId")
    private Integer rewardProgramId;
    @Column(name = "FromDate")
    private Date fromDate;
    @Column(name = "ToDate")
    private Date toDate;
}

