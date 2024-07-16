package vn.com.gsoft.inventory.repository;

import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.inventory.entity.ConfigTemplate;

import java.util.Optional;

@Repository
public interface ConfigTemplateRepository extends CrudRepository<ConfigTemplate, Long> {

    @Query("SELECT ct FROM ConfigTemplate ct WHERE ct.maNhaThuoc = :maNhaThuoc AND ct.printType = :printType AND ct.maLoai = :maLoai AND ct.type = :type ")
    Optional<ConfigTemplate> findByMaNhaThuocAndPrintTypeAndMaLoaiAndType(@Param("maNhaThuoc") String maNhaThuoc, @Param("printType") String printType, @Param("maLoai") Long maLoai, @Param("type") Integer type);

    @Query("SELECT ct FROM ConfigTemplate ct WHERE ct.maNhaThuoc IS NULL AND ct.printType = :printType AND ct.maLoai = :maLoai AND ct.type = :type")
    Optional<ConfigTemplate> findByPrintTypeAndMaLoaiAndType(@Param("printType") String printType, @Param("maLoai") Long maLoai, @Param("type") Integer type);
}
