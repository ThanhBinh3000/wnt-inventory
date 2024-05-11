package vn.com.gsoft.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LoaiXuatNhaps")
public class LoaiXuatNhaps {
    @Id
    @Column(name = "MaLoaiXuatNhap")
    private Integer maLoaiXuatNhap;
    @Column(name = "TenLoaiXuatNhap")
    private String tenLoaiXuatNhap;
    @Column(name = "IsHidden")
    private Boolean isHidden;
}

