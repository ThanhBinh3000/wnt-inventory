package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.inventory.entity.ApplicationSetting;
import vn.com.gsoft.inventory.model.dto.ApplicationSettingReq;

import java.util.List;

@Repository
public interface ApplicationSettingRepository extends CrudRepository<ApplicationSetting, Long> {
    @Query("SELECT c FROM ApplicationSetting c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.settingKey} IS NULL OR lower(c.settingKey) LIKE lower(concat('%',CONCAT(:#{#param.settingKey},'%'))))"
            + " AND (:#{#param.settingValue} IS NULL OR lower(c.settingValue) LIKE lower(concat('%',CONCAT(:#{#param.settingValue},'%'))))"
            + " AND (:#{#param.settingDisplayName} IS NULL OR lower(c.settingDisplayName) LIKE lower(concat('%',CONCAT(:#{#param.settingDisplayName},'%'))))"
            + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
            + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
            + " AND (:#{#param.roleId} IS NULL OR c.roleId = :#{#param.roleId}) "
            + " AND (:#{#param.typeId} IS NULL OR c.typeId = :#{#param.typeId}) "
            + " ORDER BY c.id desc"
    )
    Page<ApplicationSetting> searchPage(@Param("param") ApplicationSettingReq param, Pageable pageable);


    @Query("SELECT c FROM ApplicationSetting c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.settingKey} IS NULL OR lower(c.settingKey) LIKE lower(concat('%',CONCAT(:#{#param.settingKey},'%'))))"
            + " AND (:#{#param.settingValue} IS NULL OR lower(c.settingValue) LIKE lower(concat('%',CONCAT(:#{#param.settingValue},'%'))))"
            + " AND (:#{#param.settingDisplayName} IS NULL OR lower(c.settingDisplayName) LIKE lower(concat('%',CONCAT(:#{#param.settingDisplayName},'%'))))"
            + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
            + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
            + " AND (:#{#param.roleId} IS NULL OR c.roleId = :#{#param.roleId}) "
            + " AND (:#{#param.typeId} IS NULL OR c.typeId = :#{#param.typeId}) "
            + " ORDER BY c.id desc"
    )
    List<ApplicationSetting> searchList(@Param("param") ApplicationSettingReq param);

    List<ApplicationSetting> findByIsReadOnlyAndDrugStoreIdIsNull(boolean isReadOnly);

    List<ApplicationSetting> findByIsReadOnlyAndDrugStoreId(boolean isReadOnly, String maNhaThuoc);

    List<ApplicationSetting> findByDrugStoreIdIsNull();

    List<ApplicationSetting> findByDrugStoreId(String maNhaThuoc);
}
