package com.qingke.apiplatform.services;

import com.qingke.apiplatform.entity.ApiToken;
import com.qingke.apiplatform.model.User;

/**
 * @Author 蒋世芳【jiangshifang@reignwood.com】
 * @Date 2018/11/8 5:31 PM
 */
public interface TokenService {
    String getToken(User user);
    ApiToken getQingkeToken(String mobile);
}
