package com.qingke.apiplatform.services;

import com.qingke.apiplatform.model.Qingke;

/**
 * @Author 蒋世芳
 * @Date 2018/11/12 4:26 PM
 */
public interface QingkeService {
    boolean Update(Qingke qingke);
    boolean Insert(Qingke qingke);
    boolean UpdateSelective(Qingke qingke);
    Qingke getById(int id);
    Qingke getByMobile(String mobile);
    Qingke getByOpenId(String openId);

}
