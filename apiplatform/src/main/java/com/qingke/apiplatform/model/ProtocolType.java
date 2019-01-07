package com.qingke.apiplatform.model;

public class ProtocolType {
    private Integer protypeNumb;

    private String protypeName;

    public Integer getProtypeNumb() {
        return protypeNumb;
    }

    public void setProtypeNumb(Integer protypeNumb) {
        this.protypeNumb = protypeNumb;
    }

    public String getProtypeName() {
        return protypeName;
    }

    public void setProtypeName(String protypeName) {
        this.protypeName = protypeName == null ? null : protypeName.trim();
    }
}