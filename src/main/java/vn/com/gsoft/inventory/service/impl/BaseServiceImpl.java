package vn.com.gsoft.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.gsoft.inventory.constant.RecordStatusContains;
import vn.com.gsoft.inventory.entity.BaseEntity;
import vn.com.gsoft.inventory.model.system.BaseRequest;
import vn.com.gsoft.inventory.model.system.Profile;
import vn.com.gsoft.inventory.repository.BaseRepository;
import vn.com.gsoft.inventory.service.BaseService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
public class BaseServiceImpl<E extends BaseEntity, R extends BaseRequest, PK extends Serializable> implements BaseService<E, R, PK> {
    private BaseRepository repository;

    public BaseServiceImpl(BaseRepository repository) {
        this.repository = repository;
    }


    public Profile getLoggedUser() throws Exception {
        try {
            return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            throw new Exception("Token invalid!");
        }
    }

    @Override
    public Page<E> searchPage(R req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return repository.searchPage(req, pageable);
    }

    @Override
    public List<E> searchList(R req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return repository.searchList(req);
    }

    @Override
    public E create(R req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        E e = (E) ((Class) ((ParameterizedType) this.getClass().
                getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setCreated(new Date());
        e.setCreatedByUserId(getLoggedUser().getId());
        repository.save(e);
        return e;
    }

    @Override
    public E update(R req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<E> optional = repository.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }

        E e = optional.get();
        BeanUtils.copyProperties(req, e, "id", "created", "createdByUserId");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setModified(new Date());
        e.setModifiedByUserId(getLoggedUser().getId());
        repository.save(e);
        return e;
    }

    @Override
    public E detail(PK id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<E> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        } else {
            if (optional.get().getRecordStatusId() != RecordStatusContains.ACTIVE) {
                throw new Exception("Không tìm thấy dữ liệu.");
            }
        }
        return optional.get();
    }

    @Override
    public boolean delete(PK id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<E> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.DELETED);
        repository.save(optional.get());
        return true;
    }

    @Override
    public boolean restore(PK id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<E> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getRecordStatusId().equals(RecordStatusContains.DELETED)) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.ACTIVE);
        repository.save(optional.get());
        return true;
    }

    @Override
    public boolean deleteForever(PK id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<E> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getRecordStatusId().equals(RecordStatusContains.DELETED)) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.DELETED_FOREVER);
        repository.save(optional.get());
        return true;
    }

    @Override
    public boolean updateStatusMulti(R req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null) {
            throw new Exception("Bad request.");
        }
        if (req == null || req.getListIds().isEmpty()) {
            throw new Exception("Bad request.");
        }
        List<E> allByIdIn = repository.findAllByIdIn(req.getListIds());
        allByIdIn.forEach(item -> {
            item.setRecordStatusId(req.getRecordStatusId());
        });
        repository.saveAll(allByIdIn);
        return true;
    }

    public void setPropertyValue(Object obj, String propertyName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();

            // Convert value based on field type
            if (fieldType == int.class || fieldType == Integer.class) {
                field.set(obj, Integer.parseInt(value.toString()));
            } else if (fieldType == float.class || fieldType == Float.class) {
                if(!StringUtils.isEmpty(value.toString())){
                    field.set(obj, Float.parseFloat(value.toString().replaceAll(",","")));
                }
            } else if (fieldType == double.class || fieldType == Double.class) {
                if(!StringUtils.isEmpty(value.toString())) {
                    field.set(obj, Double.parseDouble(value.toString().replaceAll(",", "")));
                }
            } else if (fieldType == long.class || fieldType == Long.class) {
                if(!StringUtils.isEmpty(value.toString())) {
                    field.set(obj, Long.parseLong(value.toString().replaceAll(",", "")));
                }
            } else if (fieldType == BigDecimal.class) {
                if(!StringUtils.isEmpty(value.toString())) {
                    field.set(obj, new BigDecimal(value.toString().replaceAll(",", "")));
                }
            } else {
                if(!StringUtils.isEmpty(value.toString())) {
                    field.set(obj, value); // Default case, for other types
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    public boolean isRowEmpty(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T> List<T> handleImportExcel(Workbook workbook, List<String> propertyNames, Supplier<T> supplier) throws Exception {
        return handleImportExcel(workbook, propertyNames, supplier, 1);
    }

    @Override
    public <T> List<T> handleImportExcel(Workbook workbook, List<String> propertyNames, Supplier<T> supplier, int index) throws Exception {
        List<T> list = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        for (int i = 0; i < index && iterator.hasNext(); i++) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            // Kiểm tra xem dòng hiện tại có dữ liệu không
            if (isRowEmpty(currentRow)) {
                continue;
            }
            T data = supplier.get();
            for (int i = 0; i < propertyNames.size(); i++) {
                Cell dataCell = currentRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = getCellValueAsString(dataCell);
                setPropertyValue(data, propertyNames.get(i), cellValue);
            }
            list.add(data);
        }
        workbook.close();

        return list;
    }
    @Override
    public List<String> getRow(Workbook workbook, int rowIndex) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(rowIndex);

        if (row == null) {
            throw new IllegalArgumentException("Row index " + rowIndex + " is invalid.");
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                result.add(cell.getStringCellValue());
            }
        }

        return result;
    }
}
