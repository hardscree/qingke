package com.qingke.apiplatform.entity.enums;

/**
 * @Author 蒋世芳
 * @Date 2018/11/30 3:22 PM
 */
public enum  SmsTypeEnum {
    /**
     * 验证码
     */
    AuthCode("SMS_152211630"),
    /**
     * 认证成功
     */
    LicenseSuccess("SMS_152213520"),
    /**
     * 认证失败
     */
    AuditFailed("SMS_151090366"),
    /**
     * 签名审核不通过
     */
    AuditSignFailed("SMS_152208658"),
    /**
     * 视频审核不通过
     */
    AuditVideoFailed("SMS_152208657"),
    /**
     * 身份证审核不通过
     */
    AuditIdFailed("SMS_152213523");

    private String templateCode="";
    private SmsTypeEnum(String value)
    {
        templateCode = value;
    }
    public String getTemplateCode()
    {
        return templateCode;
    }
}
