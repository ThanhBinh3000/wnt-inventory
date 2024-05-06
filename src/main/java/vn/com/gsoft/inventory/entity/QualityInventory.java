package vn.com.gsoft.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QualityInventory")
public class QualityInventory {
    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "DrugId")
    private Integer drugId;
    @Column(name = "StoreId")
    private Integer storeId;
    @Column(name = "ReceiptItemId")
    private Integer receiptItemId;
    @Column(name = "RemainQuantity")
    private Float remainQuantity;
    @Column(name = "RealityQuantity")
    private Float realityQuantity;
    @Column(name = "Description")
    private String description;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "RecordStatusId")
    private Integer recordStatusId;
}

