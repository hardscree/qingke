package com.qingke.apiplatform.entity;

import com.qingke.apiplatform.model.ProjectQingke;
import com.qingke.apiplatform.model.Qingke;

/**
 * @Author 蒋世芳
 * @Date 2018/11/8 2:33 PM
 * @Description 登录返回信息实体
 */
public class LoginResult extends ResultBase {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokeyExpire() {
		return tokeyExpire;
	}

	public void setTokeyExpire(int tokeyExpire) {
		this.tokeyExpire = tokeyExpire;
	}

	public Qingke getQingke() {
		return qingke;
	}

	public void setQingke(Qingke qingke) {
		this.qingke = qingke;
	}

	public ProjectQingke getProjectQingke() {
		return projectQingke;
	}

	public void setProjectQingke(ProjectQingke projectQingke) {
		this.projectQingke = projectQingke;
	}

	/**
     * 用户登录成功后返回登录令牌
     */
    private  String token;
    private Integer tokeyExpire;
    private Qingke qingke;
    private ProjectQingke projectQingke;
    
}
