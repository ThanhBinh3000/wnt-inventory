package vn.com.gsoft.inventory.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DiagnoseRes {
    @SerializedName("id")
    public Long id ;
    @SerializedName("ma_chan_doan")
    public String maChanDoan;
    @SerializedName("ten_chan_doan")
    public String tenChanDoan;
    @SerializedName("ket_luan")
    public String ketLuan;
}
