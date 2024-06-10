package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PickUpOrder")
public class PickUpOrder extends BaseEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "OrderNumber")
    private Integer orderNumber;
    @Column(name = "Description")
    private String description;
    @Column(name = "TotalAmount")
    private BigDecimal totalAmount;
    @Column(name = "PaymentAmount")
    private BigDecimal paymentAmount;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "OrderStatusId")
    private Long orderStatusId;
    @Column(name = "CusId")
    private Long cusId;
    @Column(name = "OrderDate")
    private Date orderDate;
    @Column(name = "ArchivedId")
    private Long archivedId;

    @Column(name="updated")
    private Date updated;
    @Column(name="UpdatedBy_UserId")
    private Long updatedByUserId;

    @Transient
    private int drugCount;

    @Transient
    private String cusName;

    @Transient
    private Long staffAssignId;

    @Transient
    List<UserProfile> listUserProfile;

    @Transient
    private String createUserName;

    @Transient
    private String orderStatusName;
//    @Transient
//    private List<PickUpOrderDetail> chiTiets = new ArrayList<>();


//    public String getOrderStatusName() {
//        if (orderStatusId == OrderStatusId.BUYER_NEW) {
//            return "Đơn mới tạo";
//        }else if(orderStatusId == OrderStatusId.ORDER_UPDATED){
//            return "Đơn đã cập nhật";
//        }else if(orderStatusId == OrderStatusId.COMMPLETED){
//            return "Đơn đã xử lý";
//        }
//        return orderStatusName;
//    }
}

