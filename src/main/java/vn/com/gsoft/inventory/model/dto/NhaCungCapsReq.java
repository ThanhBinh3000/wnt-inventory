package vn.com.gsoft.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.gsoft.inventory.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class NhaCungCapsReq extends BaseRequest {

    private Integer maNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private String soDienThoai;
    private String soFax;
    private String maSoThue;
    private String nguoiDaiDien;
    private String nguoiLienHe;
    private String email;
    private BigDecimal noDauKy;
    private String maNhaThuoc;
    private Integer maNhomNhaCungCap;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date modified;
    private Boolean active;
    private Integer supplierTypeId;
    private String barCode;
    private String diaBanHoatDong;
    private String website;
    private Integer archivedId;
    private Integer referenceId;
    private Integer storeId;
    private Integer masterId;
    private Integer metadataHash;
    private Integer preMetadataHash;
    private String code;
    private Integer mappingStoreId;
    private Integer isOrganization;
}

