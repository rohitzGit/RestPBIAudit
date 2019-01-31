package com.cerner.rest.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportGroupWrklogResponse {
	private String status;
	private List<SupportGroupWrklog> data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<SupportGroupWrklog> getData() {
		return data;
	}
	public void setData(List<SupportGroupWrklog> data) {
		this.data = data;
	}
	
}
