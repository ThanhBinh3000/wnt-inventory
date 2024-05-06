package vn.com.gsoft.inventory.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.inventory.entity.Inventory;
import vn.com.gsoft.inventory.model.dto.InventoryReq;

import java.util.List;

public interface InventoryService {
    Page<Inventory> searchPage(InventoryReq req) throws Exception;

    List<Inventory> searchList(InventoryReq req) throws Exception;

    Inventory searchDetail(InventoryReq req) throws Exception;
}
