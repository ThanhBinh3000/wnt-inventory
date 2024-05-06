package vn.com.gsoft.inventory.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.KhachHangs;
import vn.com.gsoft.inventory.model.dto.KhachHangsReq;

import java.util.Optional;

@Repository
public interface KhachHangsRepository extends CrudRepository<KhachHangs, Long> {
    @Query("SELECT kh FROM KhachHangs kh where kh.customerTypeId=1 and kh.maNhaThuoc= ?1 ")
    Optional<KhachHangs> findKhachHangLe(String storeCode);
}
