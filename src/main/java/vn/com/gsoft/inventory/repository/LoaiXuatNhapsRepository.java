package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.LoaiXuatNhaps;

@Repository
public interface LoaiXuatNhapsRepository extends CrudRepository<LoaiXuatNhaps, Long> {

}
