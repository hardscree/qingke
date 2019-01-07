package com.qingke.apiplatform.services.admin;


/**
 * @Author 蒋世芳
 * @Date 2018/11/29 7:07 PM
 */
public interface AuditAction<T> {
    void doAction(T obj);
}
