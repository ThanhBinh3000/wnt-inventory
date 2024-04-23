package vn.com.gsoft.inventory.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DrugUsePriodRes {
    @SerializedName("id")
    public Long id;
    @SerializedName("dot")
    public Long dot;
    @SerializedName("tu_ngay")
    public String tuNgay;
    @SerializedName("den_ngay")
    public String denNgay;
    @SerializedName("so_thang_thuoc")
    public String soThangThuoc;
}
