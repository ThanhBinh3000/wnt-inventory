package vn.com.gsoft.inventory.model.system;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BaseRequest {
	private Long id;
	private Long userIdQueryData;
	private String textSearch;
	private Date created;
	private Date fromDateCreated;
	private Date toDateCreated;
	private Long createdByUserId;
	private Date modified;
	private Date fromDateModified;
	private Date toDateModified;
	private Long modifiedByUserId;
	private Long recordStatusId;
	private List<Long> recordStatusIds;
	private PaggingReq paggingReq;
	private List<Long> listIds;
}