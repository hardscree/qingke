package com.qingke.apiplatform.model;

public class ProjectQingkeStatus {
    private Integer qkstatusNumb;

    private String qkstatusName;

    public Integer getQkstatusNumb() {
        return qkstatusNumb;
    }

    public void setQkstatusNumb(Integer qkstatusNumb) {
        this.qkstatusNumb = qkstatusNumb;
    }

    public String getQkstatusName() {
        return qkstatusName;
    }

    public void setQkstatusName(String qkstatusName) {
        this.qkstatusName = qkstatusName == null ? null : qkstatusName.trim();
    }
}