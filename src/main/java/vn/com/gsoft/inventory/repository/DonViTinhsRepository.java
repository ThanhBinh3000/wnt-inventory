package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.DonViTinhs;


import java.util.List;

@Repository
public interface DonViTinhsRepository extends CrudRepository<DonViTinhs, Long> {


}
