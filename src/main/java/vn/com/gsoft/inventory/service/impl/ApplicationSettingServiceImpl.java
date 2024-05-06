package vn.com.gsoft.inventory.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.AppConstants;
import vn.com.gsoft.inventory.entity.ApplicationSetting;
import vn.com.gsoft.inventory.repository.ApplicationSettingRepository;
import vn.com.gsoft.inventory.service.ApplicationSettingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ApplicationSettingServiceImpl implements ApplicationSettingService {
    @Autowired
    private ApplicationSettingRepository applicationSettingRepository;

    @Override
    public Map<String, Object> getDrugStoreSetting(String maNhaThuoc) {
        Map<String, Object> retVal = new HashMap<>();
        if (AppConstants.GlobalStoreID.equals(maNhaThuoc)) return retVal;
        List<ApplicationSetting> appSettings = getAppSettings(maNhaThuoc);
        for (ApplicationSetting as : appSettings) {
            retVal.put(as.getSettingKey(), as.getSettingValue());
        }

        return retVal;
    }

    @Override
    public List<ApplicationSetting> getAppSettings(String maNhaThuoc) {
        return getAppSettings(maNhaThuoc, true);
    }

    @Override
    public List<ApplicationSetting> getAppSettings(String maNhaThuoc, boolean excludeReadOnlyKey) {
        if (excludeReadOnlyKey) {
            if (StringUtils.isEmpty(maNhaThuoc)) {
                return applicationSettingRepository.findByIsReadOnlyAndDrugStoreIdIsNull(false);
            } else {
                return applicationSettingRepository.findByIsReadOnlyAndDrugStoreId(false, maNhaThuoc);
            }
        } else {
            if (StringUtils.isEmpty(maNhaThuoc)) {
                return applicationSettingRepository.findByDrugStoreIdIsNull();
            } else {
                return applicationSettingRepository.findByDrugStoreId(maNhaThuoc);
            }
        }
    }

}
