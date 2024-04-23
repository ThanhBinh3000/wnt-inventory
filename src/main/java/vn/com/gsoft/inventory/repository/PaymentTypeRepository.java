package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.PaymentType;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {

}
