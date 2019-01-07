package com.qingke.apiplatform.model;

public class ProjectStatus {
    private Integer pstatusNumb;

    private String pstatusName;

    public Integer getPstatusNumb() {
        return pstatusNumb;
    }

    public void setPstatusNumb(Integer pstatusNumb) {
        this.pstatusNumb = pstatusNumb;
    }

    public String getPstatusName() {
        return pstatusName;
    }

    public void setPstatusName(String pstatusName) {
        this.pstatusName = pstatusName == null ? null : pstatusName.trim();
    }
}