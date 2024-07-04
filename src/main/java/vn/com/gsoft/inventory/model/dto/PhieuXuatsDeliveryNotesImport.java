package vn.com.gsoft.inventory.model.dto;

import lombok.Data;

@Data
public class PhieuXuatsDeliveryNotesImport {
    private String soPhieuXuat;
    private Integer stt;
    private String ngayXuat;
    private String shDon;
    private String ngayHdon;
    private String tenKhachHang;
    private Double daTra;
    private String dienGiai;
    private String maThuoc;
    private String tenThuoc;
    private String donViTinh;
    private Double soLuong;
    private Double donGia;
    private Double chietKhau;
    private Double vat;
    private Long result;
    private String soLo;
    private String hanDung;
    private String bacSi;
    private String chuanDoan;

    @Override
    public String toString() {
        return "PhieuXuatsImport{" +
                "soPhieuXuat='" + soPhieuXuat + '\'' +
                ", ngayXuat=" + ngayXuat +
                ", shDon='" + shDon + '\'' +
                ", ngayHdon=" + ngayHdon +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", daTra=" + daTra +
                ", dienGiai='" + dienGiai + '\'' +
                ", maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", chietKhau=" + chietKhau +
                ", vat=" + vat +
                ", result=" + result +
                ", soLo=" + soLo +
                ", hanDung=" + hanDung +
                ", bacSi='" + bacSi + '\'' +
                ", chuanDoan='" + chuanDoan + '\'' +
                '}';
    }
}
