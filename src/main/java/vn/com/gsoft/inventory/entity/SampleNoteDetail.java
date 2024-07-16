package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SampleNoteDetail")
public class SampleNoteDetail extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NoteID")
    private Long noteID;
    @Column(name = "DrugID")
    private Long drugID;
    @Column(name = "DrugUnitID")
    private Long drugUnitID;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "DrugStoreID")
    private String drugStoreID;
    @Column(name = "Quantity")
    private BigDecimal quantity;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "Batch")
    private Integer batch;
    @Column(name = "FromDate")
    private Date fromDate;
    @Column(name = "ToDate")
    private Date toDate;
    @Column(name = "NumberOfPotionBars")
    private String numberOfPotionBars;
    @Transient
    private String drugCodeText;
    @Transient
    private String drugNameText;
    @Transient
    private String drugUnitText;
    @Transient
    private BigDecimal retailPrice;
    @Transient
    private Thuocs thuocs;
}

