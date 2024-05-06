package vn.com.gsoft.inventory.model.system;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class PaggingReq {
    @NotNull(message = "Không được để trống")

    Integer limit;
    @NotNull(message = "Không được để trống")
    @PositiveOrZero(message = "Trang tìm kiếm phải >= 1")

    Integer page;
}