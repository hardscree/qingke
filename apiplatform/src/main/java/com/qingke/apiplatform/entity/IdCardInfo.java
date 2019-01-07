package com.qingke.apiplatform.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IdCardInfo {	

    public int getQkSex() {
		return qkSex;
	}

	public void setQkSex(int qkSex) {
		this.qkSex = qkSex;
	}

	//@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getQkBirth() {
		return qkBirth;
	}

	public void setQkBirth(Date qkBirth) {
		this.qkBirth = qkBirth;
	}

	public String getQkId() {
		return qkId;
	}

	public void setQkId(String qkId) {
		this.qkId = qkId;
	}

	public String getQkIdFrontphoto() {
		return qkIdFrontphoto;
	}

	public void setQkIdFrontphoto(String qkIdFrontphoto) {
		this.qkIdFrontphoto = qkIdFrontphoto;
	}

	public String getQkIdBackphoto() {
		return qkIdBackphoto;
	}

	public void setQkIdBackphoto(String qkIdBackphoto) {
		this.qkIdBackphoto = qkIdBackphoto;
	}

	public String getQkIdAddress() {
		return qkIdAddress;
	}

	public void setQkIdAddress(String qkIdAddress) {
		this.qkIdAddress = qkIdAddress;
	}

	public String getQkIdValidity() {
		return qkIdValidity;
	}

	public void setQkIdValidity(String qkIdValidity) {
		this.qkIdValidity = qkIdValidity;
	}

	public String getQkName() {
		return qkName;
	}

	public void setQkName(String qkName) {
		this.qkName = qkName;
	}

	public String getQkOpenid() {
		return qkOpenid;
	}

	public void setQkOpenid(String qkOpenid) {
		this.qkOpenid = qkOpenid;
	}

	public String getQkNation() {
		return qkNation;
	}

	public void setQkNation(String qkNation) {
		this.qkNation = qkNation;
	}

	public String getQkNumb() {
		return qkNumb;
	}

	public void setQkNumb(String qkNumb) {
		this.qkNumb = qkNumb;
	}

	private int qkSex;	
	private Date qkBirth;
    private String qkId;
    private String qkIdFrontphoto;
    private String qkIdBackphoto;
    private String qkIdAddress;
    private String qkIdValidity;
    private String qkName;
    private String qkOpenid;
    private String qkNumb;
    private String qkNation;
}
