package vn.com.gsoft.inventory.model.system;

import lombok.Data;

import java.util.List;

/**
 * @author duongvd5
 * @since Aug, 2022
 * Định nghĩa kiểu trả về mặc định
 * @param <T>
 */

@Data
public class DataTableResults<T> {
    /**
     * Số bản ghi mỗi trang
     */
    private String draw;

    /**
     * Số thứ tự của bản ghi đầu tiên
     */
    private String first;

    private String recordsFiltered;

    /**
     * Tổng số bản ghi
     */
    private String recordsTotal;

    /**
     * Danh sách dữ liệu
     */
    List<T> data;

    List<T> headerConfig;
}
