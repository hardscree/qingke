package com.qingke.apiplatform.model;

import java.util.Date;

public class Enterprise {
    private Integer etNumb;

    private String etSname;

    private String etFname;

    private Integer etType;

    private String etAddress;

    private String creditCode;

    private String regCapita;

    private String legalPerson;

    private String etLinkman;

    private String etPhone;

    private String etEmail;

    private Date etDate;

    private String etLicenseUrl;

    private Boolean etStatus;

    private String etComment;

    public Integer getEtNumb() {
        return etNumb;
    }

    public void setEtNumb(Integer etNumb) {
        this.etNumb = etNumb;
    }

    public String getEtSname() {
        return etSname;
    }

    public void setEtSname(String etSname) {
        this.etSname = etSname == null ? null : etSname.trim();
    }

    public String getEtFname() {
        return etFname;
    }

    public void setEtFname(String etFname) {
        this.etFname = etFname == null ? null : etFname.trim();
    }

    public Integer getEtType() {
        return etType;
    }

    public void setEtType(Integer etType) {
        this.etType = etType;
    }

    public String getEtAddress() {
        return etAddress;
    }

    public void setEtAddress(String etAddress) {
        this.etAddress = etAddress == null ? null : etAddress.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getRegCapita() {
        return regCapita;
    }

    public void setRegCapita(String regCapita) {
        this.regCapita = regCapita == null ? null : regCapita.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getEtLinkman() {
        return etLinkman;
    }

    public void setEtLinkman(String etLinkman) {
        this.etLinkman = etLinkman == null ? null : etLinkman.trim();
    }

    public String getEtPhone() {
        return etPhone;
    }

    public void setEtPhone(String etPhone) {
        this.etPhone = etPhone == null ? null : etPhone.trim();
    }

    public String getEtEmail() {
        return etEmail;
    }

    public void setEtEmail(String etEmail) {
        this.etEmail = etEmail == null ? null : etEmail.trim();
    }

    public Date getEtDate() {
        return etDate;
    }

    public void setEtDate(Date etDate) {
        this.etDate = etDate;
    }

    public String getEtLicenseUrl() {
        return etLicenseUrl;
    }

    public void setEtLicenseUrl(String etLicenseUrl) {
        this.etLicenseUrl = etLicenseUrl == null ? null : etLicenseUrl.trim();
    }

    public Boolean getEtStatus() {
        return etStatus;
    }

    public void setEtStatus(Boolean etStatus) {
        this.etStatus = etStatus;
    }

    public String getEtComment() {
        return etComment;
    }

    public void setEtComment(String etComment) {
        this.etComment = etComment == null ? null : etComment.trim();
    }
}