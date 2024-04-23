package vn.com.gsoft.inventory.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DonThuocRes {
    private String ngayGioKeDon;
    private List<ThongTinDonThuocRes> thongTinDonThuoc;
    private List<Long> drugIds;
}
