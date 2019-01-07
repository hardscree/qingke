package com.qingke.apiplatform.entity;

public class QingkeSignOn {
	public String getQkPhone() {
		return qkPhone;
	}
	public void setQkPhone(String qkPhone) {
		this.qkPhone = qkPhone;
	}
	public Integer getpNumb() {
		return pNumb;
	}
	public void setpNumb(Integer pNumb) {
		this.pNumb = pNumb;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public String getQkOpenId() {
		return qkOpenId;
	}
	public void setQkOpenId(String qkOpenId) {
		this.qkOpenId = qkOpenId;
	}



	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	private String qkPhone;
    private Integer pNumb;
    private String authCode;
    private String code;//微信获取openId需要
    private String qkOpenId;
}
