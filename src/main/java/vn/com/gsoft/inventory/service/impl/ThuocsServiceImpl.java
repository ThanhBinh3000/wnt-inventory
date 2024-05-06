package vn.com.gsoft.inventory.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.entity.Thuocs;
import vn.com.gsoft.inventory.model.dto.ThuocsReq;
import vn.com.gsoft.inventory.repository.ThuocsRepository;
import vn.com.gsoft.inventory.service.ThuocsService;

@Service
@Log4j2
public class ThuocsServiceImpl extends BaseServiceImpl<Thuocs, ThuocsReq, Long> implements ThuocsService {

    private ThuocsRepository hdrRepo;

    @Autowired
    public ThuocsServiceImpl(ThuocsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }


}
