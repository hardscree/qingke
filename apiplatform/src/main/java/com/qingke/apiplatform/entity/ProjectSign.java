package com.qingke.apiplatform.entity;

import lombok.Data;


public class ProjectSign {

	private Integer pNumb;
	private Integer qkNumb;
	private String data;
	public Integer getpNumb() {
		return pNumb;
	}
	public void setpNumb(Integer pNumb) {
		this.pNumb = pNumb;
	}
	public Integer getQkNumb() {
		return qkNumb;
	}
	public void setQkNumb(Integer qkNumb) {
		this.qkNumb = qkNumb;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
