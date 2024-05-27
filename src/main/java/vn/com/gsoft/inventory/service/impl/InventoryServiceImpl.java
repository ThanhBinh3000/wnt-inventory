package vn.com.gsoft.inventory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.Inventory;
import vn.com.gsoft.inventory.model.dto.InventoryReq;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.repository.InventoryRepository;
import vn.com.gsoft.inventory.service.InventoryService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {
    private InventoryRepository hdrRepo;

    @Autowired
    public InventoryServiceImpl(InventoryRepository repository) {
        this.hdrRepo = repository;
    }

    public Profile getLoggedUser() throws Exception {
        try {
            return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            throw new Exception("Token invalid!");
        }
    }

    @Override
    public Page<Inventory> searchPage(InventoryReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return hdrRepo.searchPage(req, pageable);
    }

    @Override
    public List<Inventory> searchList(InventoryReq req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return hdrRepo.searchList(req);
    }

    @Override
    public Inventory searchDetail(InventoryReq req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        Optional<Inventory> inventory = hdrRepo.searchDetail(req);
        return inventory.orElse(null);
    }

    @Override
    public HashMap<Integer, Double> totalInventory(InventoryReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setDrugUnitID(Integer.getInteger(userInfo.getNhaThuoc().getMaNhaThuocCha()));
        List<Inventory> inventory = hdrRepo.searchList(req);
        Map<Integer, Double> result = inventory.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDrugID,
                        Collectors.summingDouble(Inventory::getLastValue)
                ));
        HashMap<Integer, Double> hashMapResult = new HashMap<>(result);
        return hashMapResult;
    }
}
