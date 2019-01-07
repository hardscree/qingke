package com.qingke.apiplatform.controller.admin;

import com.qingke.apiplatform.core.Annotations.PassToken;
import com.qingke.apiplatform.core.Annotations.UserLoginToken;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.ResultBase;
import com.qingke.apiplatform.model.User;
import com.qingke.apiplatform.services.impl.UserServiceImpl;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@Api("后台用户管理接口集")
@UserLoginToken
public class UserAdminController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    @ApiOperation("后台用户登录接口")
    @PassToken
    public RestResult<String> login(@RequestBody User user)
    {
        String token;
        try {
            token = userService.login(user.getUserName(), user.getUserPwd());
            return ResultUtil.success(token);
        }
        catch (Exception ex)
        {
            return ResultUtil.failed(-1,ex.getMessage(),null);
        }

    }
}
