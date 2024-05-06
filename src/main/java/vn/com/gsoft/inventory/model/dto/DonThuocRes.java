package vn.com.gsoft.inventory.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class DonThuocRes {
    @SerializedName("ngay_gio_ke_don")
    private String ngayGioKeDon;
    @SerializedName("thong_tin_don_thuoc")
    private List<ThongTinDonThuocRes> thongTinDonThuoc;
    @SerializedName("drug_ids")
    private List<Long> drugIds;
    @SerializedName("ma_don_thuoc")
    private String maDonThuoc;
    @SerializedName("ho_ten_benh_nhan")
    private String hoTenBenhNhan;
    @SerializedName("ngay_sinh_benh_nhan")
    private String ngaySinhBenhNhan;
    @SerializedName("ma_dinh_danh_y_te")
    private String maDinhDanhYTe;
    @SerializedName("loai_don_thuoc")
    private String loaiDonThuoc;
    @SerializedName("hinh_thuc_dieu_tri")
    private String hinhThucDieuTri;
    @SerializedName("dia_chi")
    private String diaChi;
    @SerializedName("gioi_tinh")
    private String gioiTinh;
    @SerializedName("can_nang")
    private String canNang;
    @SerializedName("ma_so_the_bao_hiem_y_te")
    private String maSoTheBaoHiemYTe;
    @SerializedName("dot_dung_thuoc")
    public List<DrugUsePriodRes> dotDungThuoc;
    @SerializedName("chan_doan")
    public List<DiagnoseRes> chanDoan;
    @SerializedName("luu_y")
    private String luuY;
    @SerializedName("loi_dan")
    private String loiDan;
    @SerializedName("ten_bac_si")
    private String tenBacSi;
    @SerializedName("ten_co_so_kham_chua_benh")
    private String tenCoSoKhamChuaBenh;
    @SerializedName("so_dien_thoai_co_so_kham_chua_benh")
    private String soDienThoaiCoSoKhamChuaBenh;
    @SerializedName("ngay_tai_kham")
    private String ngayTaiKham;
    @SerializedName("id")
    public Long id;
}
