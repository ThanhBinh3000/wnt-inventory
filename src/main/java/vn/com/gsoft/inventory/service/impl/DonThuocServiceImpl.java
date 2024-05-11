package vn.com.gsoft.inventory.service.impl;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.Thuocs;
import vn.com.gsoft.inventory.model.dto.DonThuocReq;
import vn.com.gsoft.inventory.model.dto.DonThuocRes;
import vn.com.gsoft.inventory.model.dto.ThongTinDonThuocRes;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.repository.ThuocsRepository;
import vn.com.gsoft.inventory.service.DonThuocService;
import vn.com.gsoft.inventory.constant.AppConstants;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonThuocServiceImpl implements DonThuocService {
    @Value("${wnt.app-name}")
    private String appName;
    @Value("${wnt.app-key}")
    private String appKey;
    @Autowired
    private ThuocsRepository thuocsRepository;

    public Profile getLoggedUser() throws Exception {
        try {
            return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            throw new Exception("Token invalid!");
        }
    }

    @Override
    public DonThuocRes searchList(DonThuocReq objReq) throws Exception {
        DonThuocRes note = new DonThuocRes();
        if (StringUtils.isEmpty(objReq.getCode())) return note;
        String appName = AppConstants.DefaultStoreCodeDemo.equals(objReq.getStoreCode()) || "NTPK".equals(objReq.getStoreCode()) ? "webnhathuoc" : this.appName;
        String appKey = AppConstants.DefaultStoreCodeDemo.equals(objReq.getStoreCode()) || "NTPK".equals(objReq.getStoreCode()) ? "844G9yb0lYWYfs59MFv6QiqTF11JYfKl9KMXKVsslbjkyt2WGGjas2t3lplk" :
                this.appKey;
        String apiUrl = AppConstants.DefaultStoreCodeDemo.equals(objReq.getStoreCode()) || "NTPK".equals(objReq.getStoreCode()) ?
                String.format("http://beta.donthuocquocgia.vn/api/v1/thong-tin-don-thuoc/%s", URLEncoder.encode(objReq.getCode(), "UTF-8")) :
                String.format("https://donthuocquocgia.vn/api/v1/thong-tin-don-thuoc/%s", URLEncoder.encode(objReq.getCode(), "UTF-8"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("app-name", appName);
        headers.add("app-key", appKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class);
            System.out.println(response.getBody());
            note = new Gson().fromJson(response.getBody(), DonThuocRes.class);
        } catch (HttpClientErrorException.NotFound n) {
            throw new Exception("Không tìm thấy đơn thuốc!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        List<String> codes = note.getThongTinDonThuoc().stream()
                .filter(x -> x.getMaThuoc() != null && x.getMaThuoc().contains("DQG"))
                .map(ThongTinDonThuocRes::getMaThuoc)
                .toList();

        if (!codes.isEmpty()) {
            List<Long> drugIds = thuocsRepository.findByNhaThuocMaNhaThuocAndConnectivityCodeInAndRecordStatusId(getLoggedUser().getNhaThuoc().getMaNhaThuoc(), codes, RecordStatusContains.ACTIVE).stream()
                    .map(Thuocs::getId)
                    .collect(Collectors.toList());
            note.setDrugIds(drugIds);
        } else {
            note.setDrugIds(new ArrayList<>());
        }

        return note;
    }
}
