package vn.com.gsoft.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SampleNote")
public class SampleNote extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NoteName")
    private String noteName;
    @Column(name = "Barcode")
    private String barcode;
    @Column(name = "Description")
    private String description;
    @Column(name = "CreatedDateTime")
    private Date createdDateTime;
    @Column(name = "CreatedByUserID")
    private Long createdByUserID;
    @Column(name = "ModifiedDateTime")
    private Date modifiedDateTime;
    @Column(name = "ModifiedByUserID")
    private Long modifiedByUserID;
    @Column(name = "DrugStoreID")
    private String drugStoreID;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "Amount")
    private BigDecimal amount;
    @Column(name = "PatientId")
    private Long patientId;
    @Column(name = "DoctorId")
    private Long doctorId;
    @Column(name = "DoctorComments")
    private String doctorComments;
    @Column(name = "NoteDate")
    private Date noteDate;
    @Column(name = "IdExamination")
    private Long idExamination;
    @Column(name = "TypeId")
    private String typeId;
    @Column(name = "IsConnect")
    private Boolean isConnect;
    @Column(name = "ResultConnect")
    private String resultConnect;
    @Column(name = "CodeConnect")
    private String codeConnect;
    @Column(name = "StatusConnect")
    private Integer statusConnect;
    @Column(name = "NoteNumber")
    private Integer noteNumber;
    @Column(name = "ConnectDate")
    private Date connectDate;
    @Column(name = "NoteCheckSum")
    private String noteCheckSum;
    @Column(name = "FormOfTreatment")
    private Integer formOfTreatment;
    @Column(name = "TypeSampleNote")
    private Integer typeSampleNote;
    @Column(name = "ReferenceId")
    private Long referenceId;
    @Column(name = "DiagnosticIds")
    private String diagnosticIds;
    @Transient
    private String patientName;
    @Transient
    private String patientPhoneNumber;
    @Transient
    private String patientAddress;
    @Transient
    private Integer patientAge;
    @Transient
    private String patientGender;
    @Transient
    private String doctorName;
    @Transient
    private String doctorPhoneNumber;
    @Transient
    private String doctorAddress;
    @Transient
    private Long typeDrugTotal;
    @Transient
    private List<SampleNoteDetail> chiTiets;
    @Transient
    private String pharmacyName;
    @Transient
    private String pharmacyAddress;
    @Transient
    private String pharmacyPhoneNumber;
    @Transient
    private String note;
    @Transient
    private String title;
    @Transient
    private Date patientBirthDate;
    @Transient
    private String weight;
    @Transient
    private String HealthInsuranceNumber;
    @Transient
    private String citizenIdentification;
    @Transient
    private String heartbeat;
    @Transient
    private String temperature;
    @Transient
    private String bloodPressure;
    @Transient
    private String breathing;
    @Transient
    private String conclude;
    @Transient
    private Integer sizeDetail;
    @Transient
    private String titleTatle;
    @Transient
    private String createdByUserText;
}

