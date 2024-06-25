package vn.com.gsoft.inventory.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PhieuXuatsImport {
    private String soPhieuXuat;
    private Date ngayXuat;
    private String shDon;
    private Date ngayHDon;
    private String khachHangMaKhachHangText;
    private Double daTra;
    private String dienGiai;
    private String maThuoc;
    private String tenThuoc;
    private String donViTinh;
    private Long soLuong;
    private Double donGia;
    private Double chietKhau;
    private Double vat;
    private Long result;
    private Long soLo;
    private Date hanDung;
    private String bacSi;
    private String chuanDoan;

    @Override
    public String toString() {
        return "PhieuXuatsImport{" +
                "soPhieuXuat='" + soPhieuXuat + '\'' +
                ", ngayXuat=" + ngayXuat +
                ", shDon='" + shDon + '\'' +
                ", ngayHDon=" + ngayHDon +
                ", khachHangMaKhachHangText='" + khachHangMaKhachHangText + '\'' +
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
