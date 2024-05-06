package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.QualityInventory;

@Repository
public interface QualityInventoryRepository extends CrudRepository<QualityInventory, Long> {

}
