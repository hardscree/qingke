package com.qingke.apiplatform.model;

import java.util.Date;

public class ProjectQingke {
    private Integer pNumb;

    private Integer qkNumb;

    private Integer pQkStatus;

    private Date statusUpdatetime;

    private Date focusTime;

    private String signUrl;

    private Integer signStatus;

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

    public Integer getpQkStatus() {
        return pQkStatus;
    }

    public void setpQkStatus(Integer pQkStatus) {
        this.pQkStatus = pQkStatus;
    }

    public Date getStatusUpdatetime() {
        return statusUpdatetime;
    }

    public void setStatusUpdatetime(Date statusUpdatetime) {
        this.statusUpdatetime = statusUpdatetime;
    }

    public Date getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(Date focusTime) {
        this.focusTime = focusTime;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl == null ? null : signUrl.trim();
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }
}