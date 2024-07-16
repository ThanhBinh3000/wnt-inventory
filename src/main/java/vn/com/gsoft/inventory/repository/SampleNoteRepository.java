package vn.com.gsoft.inventory.repository;

import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.SampleNote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface SampleNoteRepository extends CrudRepository<SampleNote,Long> {

}
