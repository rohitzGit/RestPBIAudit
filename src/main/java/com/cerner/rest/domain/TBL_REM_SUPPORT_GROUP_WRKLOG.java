package com.cerner.rest.domain;

import com.cerner.rest.persistence.PersistenceObject;

public class TBL_REM_SUPPORT_GROUP_WRKLOG implements PersistenceObject{

	private String problemId;

	/*public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	*/
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

}

