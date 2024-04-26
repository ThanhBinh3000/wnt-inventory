package vn.com.gsoft.inventory.repository;

import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import vn.com.gsoft.inventory.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<E, R, PK extends Serializable> extends CrudRepository<E, PK> {

    Page<E> searchPage(@Param("param") R param, Pageable pageable);

    List<E> searchList(@Param("param") R param);

    <E extends BaseEntity> List<E> findAllByIdIn(List<Long> listIds);
}
