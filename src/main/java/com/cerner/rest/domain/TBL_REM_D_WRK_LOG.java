package com.cerner.rest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cerner.rest.persistence.PersistenceObject;

@Entity
public class TBL_REM_D_WRK_LOG implements PersistenceObject {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
		private String workLogId;
		private String integrationID;
		private String locked;
		private String notes;
		private String submitter;
		private String summary;
		private String statusCode;
		private String status;
		private String viewAccess;
		private String workLogDate;
		private String workLogType;
		private String w_type;
		private float w_balance;
		private int w_encounters;
		private String w_cr_pbi;
		private String problemId;
		
		public String getIntegrationID() {
			return integrationID;
		}
		public void setIntegrationID(String integrationID) {
			this.integrationID = integrationID;
		}
		public String getLocked() {
			return locked;
		}
		public void setLocked(String locked) {
			this.locked = locked;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public String getSubmitter() {
			return submitter;
		}
		public void setSubmitter(String submitter) {
			this.submitter = submitter;
		}
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getViewAccess() {
			return viewAccess;
		}
		public void setViewAccess(String viewAccess) {
			this.viewAccess = viewAccess;
		}
		public String getWorkLogDate() {
			return workLogDate;
		}
		public void setWorkLogDate(String workLogDate) {
			this.workLogDate = workLogDate;
		}
		public String getWorkLogId() {
			return workLogId;
		}
		public void setWorkLogId(String workLogId) {
			this.workLogId = workLogId;
		}
		public String getWorkLogType() {
			return workLogType;
		}
		public void setWorkLogType(String workLogType) {
			this.workLogType = workLogType;
		}
		public String getW_type() {
			return w_type;
		}
		public void setW_type(String w_type) {
			this.w_type = w_type;
		}
		public float getW_balance() {
			return w_balance;
		}
		public void setW_balance(float w_balance) {
			this.w_balance = w_balance;
		}
		public int getW_encounters() {
			return w_encounters;
		}
		public void setW_encounters(int w_encounters) {
			this.w_encounters = w_encounters;
		}
		public String getid() {
			return id;
		}
		public void setid(String id) 
			{
				this.id = id;
			}
		public String getProblemId() {
			return problemId;
		}
		public void setProblemId(String problemId) {
			this.problemId = problemId;
		}
		public String getW_cr_pbi() {
			return w_cr_pbi;
		}
		public void setW_cr_pbi(String w_cr_pbi) {
			this.w_cr_pbi = w_cr_pbi;
		}

	}


