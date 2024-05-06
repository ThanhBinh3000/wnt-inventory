package vn.com.gsoft.inventory.model.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;
    private String roleName;
    private Boolean isDeleted;
    private String maNhaThuoc;
    private String description;
    private Integer type;  // 0 mặc định, 1 của nhà thuốc
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date created;
    private Long createdByUserId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date modified;
    private Long modifiedByUserId;
    private Long recordStatusId;
    private Boolean isDefault;
}

