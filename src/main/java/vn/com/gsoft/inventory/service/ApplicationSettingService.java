package vn.com.gsoft.inventory.service;

import vn.com.gsoft.inventory.entity.ApplicationSetting;

import java.util.List;
import java.util.Map;

public interface ApplicationSettingService {
    Map<String, Object> getDrugStoreSetting(String maNhaThuoc);

    List<ApplicationSetting> getAppSettings(String maNhaThuoc);

    List<ApplicationSetting> getAppSettings(String maNhaThuoc, boolean excludeReadOnlyKey);
}
