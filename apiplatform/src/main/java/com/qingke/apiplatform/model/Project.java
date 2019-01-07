package com.qingke.apiplatform.model;

import java.util.Date;

public class Project {
    private Integer pNumb;

    private Integer etNumb;

    private String pName;

    private Integer pType;

    private Long serviceCharge;

    private Long openAccount;

    private Integer paymentType;

    private String pPrincipal;

    private String etPrincipal;

    private String etPhone;

    private Date createTime;

    private Date beginTime;

    private Date endTime;

    private Date updateTime;

    private Integer pStatus;

    private Integer qkQuantity;

    private Integer qkLink;

    private String pContent;

    private String pOperator;
    
    private String pCodeUrl;
    private String wxCodeUrl;
    private String wxTicket;
    
    public String getWxCodeUrl() {
		return wxCodeUrl;
	}

	public void setWxCodeUrl(String wxCodeUrl) {
		this.wxCodeUrl = wxCodeUrl;
	}

	public String getWxTicket() {
		return wxTicket;
	}

	public void setWxTicket(String wxTicket) {
		this.wxTicket = wxTicket;
	}

	

    public Integer getpNumb() {
        return pNumb;
    }

    public void setpNumb(Integer pNumb) {
        this.pNumb = pNumb;
    }

    public Integer getEtNumb() {
        return etNumb;
    }

    public void setEtNumb(Integer etNumb) {
        this.etNumb = etNumb;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Long getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Long serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Long getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Long openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getpPrincipal() {
        return pPrincipal;
    }

    public void setpPrincipal(String pPrincipal) {
        this.pPrincipal = pPrincipal == null ? null : pPrincipal.trim();
    }

    public String getEtPrincipal() {
        return etPrincipal;
    }

    public void setEtPrincipal(String etPrincipal) {
        this.etPrincipal = etPrincipal == null ? null : etPrincipal.trim();
    }

    public String getEtPhone() {
        return etPhone;
    }

    public void setEtPhone(String etPhone) {
        this.etPhone = etPhone == null ? null : etPhone.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getpStatus() {
        return pStatus;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    public Integer getQkQuantity() {
        return qkQuantity;
    }

    public void setQkQuantity(Integer qkQuantity) {
        this.qkQuantity = qkQuantity;
    }

    public Integer getQkLink() {
        return qkLink;
    }

    public void setQkLink(Integer qkLink) {
        this.qkLink = qkLink;
    }

    public String getpContent() {
        return pContent;
    }

    public void setpContent(String pContent) {
        this.pContent = pContent == null ? null : pContent.trim();
    }

    public String getpOperator() {
        return pOperator;
    }

    public void setpOperator(String pOperator) {
        this.pOperator = pOperator == null ? null : pOperator.trim();
    }

	public String getpCodeUrl() {
		return pCodeUrl;
	}

	public void setpCodeUrl(String pCodeUrl) {
		this.pCodeUrl = pCodeUrl;
	}
}