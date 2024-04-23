package vn.com.gsoft.inventory.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ThongTinDonThuocRes {
    @SerializedName("id")
    public Long id;
    @SerializedName("ma_thuoc")
    private String maThuoc;
    @SerializedName("biet_duoc")
    public String bietDuoc;
    @SerializedName("ten_thuoc")
    public String tenThuoc;
    @SerializedName("don_vi_tinh")
    public String donViTinh;
    @SerializedName("so_luong")
    public Long soLuong;
    @SerializedName("cach_dung")
    public String cachDung;
    @SerializedName("so_luong_ban")
    public Long soLuongBan;
}
