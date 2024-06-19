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
@Table(name = "ConfigTemplate")
public class ConfigTemplate {
    @Id
    @Column(name = "Id")
    private Integer id;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "PrintType")
    private String printType;
    @Column(name = "TemplateFileName")
    private String templateFileName;
    @Column(name = "MaLoai")
    private Long maLoai;
    @Column(name = "Type")
    private Integer type;
}
