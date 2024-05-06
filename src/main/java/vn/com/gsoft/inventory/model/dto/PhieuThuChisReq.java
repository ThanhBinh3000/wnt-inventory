package vn.com.gsoft.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PhieuThuChisReq extends BaseRequest {
    private Integer soPhieu;
    private String dienGiai;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date ngayTao;
    private Integer loaiPhieu;
    private String nhaThuocMaNhaThuoc;
    private Long khachHangMaKhachHang;
    private Long nhaCungCapMaNhaCungCap;
    private Integer userProfileUserId;
    private Double amount;
    private String nguoiNhan;
    private String diaChi;
    private Integer loaiThuChiMaLoaiPhieu;
    private Boolean active;
    private Integer customerId;
    private Integer supplierId;
    private Integer archivedId;
    private Integer storeId;
    private Integer paymentTypeId;
    private String maCoSo;
    private Integer nhanVienId;
    private Integer rewardProgramId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date toDate;
}
