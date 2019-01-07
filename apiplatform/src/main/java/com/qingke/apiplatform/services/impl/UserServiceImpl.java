package com.qingke.apiplatform.services.impl;

import com.qingke.apiplatform.mapper.UserMapper;
import com.qingke.apiplatform.model.User;
import com.qingke.apiplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 蒋世芳
 * @Date 2018/11/12 4:32 PM
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenServiceImpl tokenService;

    @Override
    public String login(String userName, String password) throws Exception {

        User user =  userMapper.selectByUsername(userName);
        if(user==null)
        {
            throw new Exception("用户名不存在。");
        }
        if(!user.getUserPwd().equals(password))
        {
            throw new Exception("密码错误");
        }
        return tokenService.getToken(user);
    }
}
