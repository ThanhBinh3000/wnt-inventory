package vn.com.gsoft.inventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.SampleNoteDetail;

import java.util.List;

@Repository
public interface SampleNoteDetailRepository extends CrudRepository<SampleNoteDetail,Long> {
    List<SampleNoteDetail> findByNoteID(Long noteId);
}
