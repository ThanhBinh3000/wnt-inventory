package vn.com.gsoft.inventory.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

public interface BaseService<E,R, PK extends Serializable> {

    Page<E> searchPage (R req) throws Exception;

    List<E> searchList (R req) throws Exception;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    E create(R req) throws Exception;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    E update(R req) throws Exception;

    E detail (PK id) throws Exception;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    boolean delete(PK id) throws Exception;
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    boolean restore(PK id) throws Exception;
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    boolean deleteForever(PK id) throws Exception;
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    boolean updateStatusMulti(R req) throws Exception;
    List<E> handleImportExcel(Workbook workbook, List<String> propertyNames, Supplier<E> supplier) throws Exception;
}
