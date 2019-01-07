package com.qingke.apiplatform.controller.admin;

import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.model.Enterprise;
import com.qingke.apiplatform.services.admin.EnterpriseAdminService;
import com.qingke.apiplatform.services.impl.admin.EnterpriseAdminServiceImpl;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/15 3:55 PM
 */

@RestController
@RequestMapping("/admin/enterprise")
@Api("后台项目管理接口集")
public class EnterpriseAdminController {
    @Autowired
    EnterpriseAdminServiceImpl enterpriseAdminService;
    @GetMapping("/query")
    @ApiOperation("企业查询接口")
    public RestResult<List<Enterprise>> query(String keyword)
    {
        return ResultUtil.success(enterpriseAdminService.query(keyword));
    }
}
