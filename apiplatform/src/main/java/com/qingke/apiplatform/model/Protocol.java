package com.qingke.apiplatform.model;

import java.util.Date;

public class Protocol {
    private Integer proNumb;

    private Integer pNumb;

    private Integer proType;

    private String proPhotoUrl;

    private Integer proPhotoSort;

    private Date uploadDate;

    private String proContent;

    public Integer getProNumb() {
        return proNumb;
    }

    public void setProNumb(Integer proNumb) {
        this.proNumb = proNumb;
    }

    public Integer getpNumb() {
        return pNumb;
    }

    public void setpNumb(Integer pNumb) {
        this.pNumb = pNumb;
    }

    public Integer getProType() {
        return proType;
    }

    public void setProType(Integer proType) {
        this.proType = proType;
    }

    public String getProPhotoUrl() {
        return proPhotoUrl;
    }

    public void setProPhotoUrl(String proPhotoUrl) {
        this.proPhotoUrl = proPhotoUrl == null ? null : proPhotoUrl.trim();
    }

    public Integer getProPhotoSort() {
        return proPhotoSort;
    }

    public void setProPhotoSort(Integer proPhotoSort) {
        this.proPhotoSort = proPhotoSort;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getProContent() {
        return proContent;
    }

    public void setProContent(String proContent) {
        this.proContent = proContent == null ? null : proContent.trim();
    }
}