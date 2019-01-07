package com.qingke.apiplatform.model;

public class QingkeStatus {
    private Integer qkStatusNumber;

    private String qkStatusName;

    public Integer getQkStatusNumber() {
        return qkStatusNumber;
    }

    public void setQkStatusNumber(Integer qkStatusNumber) {
        this.qkStatusNumber = qkStatusNumber;
    }

    public String getQkStatusName() {
        return qkStatusName;
    }

    public void setQkStatusName(String qkStatusName) {
        this.qkStatusName = qkStatusName == null ? null : qkStatusName.trim();
    }
}