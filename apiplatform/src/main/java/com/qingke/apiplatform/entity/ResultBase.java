package com.qingke.apiplatform.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author 蒋世芳
 * @Date 2018/11/8 2:29 PM
 * @Description API返回结果基类
 */
public class ResultBase {

    @ApiModelProperty(name="code",value="返回状态",example = "200")
    protected int code;
    @ApiModelProperty(name="message",value="返回信息",example = "请求成功")
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
