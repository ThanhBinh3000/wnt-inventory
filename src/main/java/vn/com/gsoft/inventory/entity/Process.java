package vn.com.gsoft.inventory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Process")
public class Process {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String batchKey;
    private String maNhaThuoc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date endDate;
    private Integer total;
    private Integer status; // 0: khởi tạo, 1: running , 2:done
    private Integer returnCode; // 0: Thành công, 1:Chạy thành công có lỗi 1 phần, 2: Chạy không thành công
    private String message;
    private Long createBy;
}

