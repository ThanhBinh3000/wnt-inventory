package vn.com.gsoft.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ApplicationSetting")
public class ApplicationSetting {
    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "SettingKey")
    private String settingKey;
    @Column(name = "SettingValue")
    private String settingValue;
    @Column(name = "SettingDisplayName")
    private String settingDisplayName;
    @Column(name = "IsReadOnly")
    private Boolean isReadOnly;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "Description")
    private String description;
    @Column(name = "Activated")
    private Boolean activated;
    @Column(name = "RoleId")
    private Long roleId;
    @Column(name = "TypeId")
    private Long typeId;
}

