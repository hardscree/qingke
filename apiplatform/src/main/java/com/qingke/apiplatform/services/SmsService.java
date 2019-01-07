package com.qingke.apiplatform.services;

import com.qingke.apiplatform.entity.SortMsg;

import java.util.Map;

/**
 * @Author 蒋世芳
 * @Date 2018/11/30 4:23 PM
 */
public interface SmsService {
    boolean send(SortMsg msg);
    boolean send(SortMsg msg, Map<String, String> templateData);
}
