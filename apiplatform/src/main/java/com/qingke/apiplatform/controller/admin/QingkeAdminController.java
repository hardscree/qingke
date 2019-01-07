package com.qingke.apiplatform.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.qingke.apiplatform.core.Annotations.UserLoginToken;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.admin.QingkeExport;
import com.qingke.apiplatform.entity.admin.QingkeList;
import com.qingke.apiplatform.entity.admin.QingkeQueryEntity;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.services.impl.admin.ProjectAdminServiceImpl;
import com.qingke.apiplatform.services.impl.admin.QingkeAdminServiceImpl;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/17 11:05 AM
 */
@RestController
@RequestMapping("/admin/qingke")
@Api("后台轻客管理接口集")
@UserLoginToken
public class QingkeAdminController {
    @Value("${export.qingkeTemplate}")
    private String templateFile;
    @Autowired
    QingkeAdminServiceImpl service;

    @Autowired
    ProjectAdminServiceImpl projectService;
    @PostMapping("/query")
    @ApiOperation("轻客列表查询接口")
    public RestResult<QingkeList> query(@RequestBody JSONObject queryEntity)
    {

        return ResultUtil.success(service.query(makeQuery(queryEntity)));
    }

    private  QingkeQueryEntity makeQuery(JSONObject queryEntity)
    {
        QingkeQueryEntity entity = new QingkeQueryEntity();
        entity.setName(queryEntity.getString("name"));
        int qkidStatus = queryEntity.getString("qkidStatus").equals("")?-1:Integer.parseInt(queryEntity.getString("qkidStatus"));
        entity.setQkidStatus(qkidStatus);
        int qkVideoStatus = queryEntity.getString("qkVideoStatus").equals("")?-1:Integer.parseInt(queryEntity.getString("qkVideoStatus"));
        entity.setQkVideoStatus(qkVideoStatus);
        int qkLicenseStatus = queryEntity.getString("qkLicenseStatus").equals("")?-1:Integer.parseInt(queryEntity.getString("qkLicenseStatus"));
        entity.setQkLicenseStatus(qkLicenseStatus);
        entity.setPage(queryEntity.getInteger("page"));
        entity.setLimit(queryEntity.getInteger("limit"));
        entity.setOffset(entity.getPage()>0?(entity.getPage()-1)*entity.getLimit():0);
        entity.setQkPhone(queryEntity.getString("qkPhone"));
        entity.setStartDate(queryEntity.getString("startDate"));
        entity.setEndDate(queryEntity.getString("endDate"));
        entity.setIdCode(queryEntity.getString("idCode"));
        return entity;
    }

    @PostMapping("/query/export")
    @ApiOperation("轻客列表查询接口")
    public RestResult<List<QingkeExport>> export(@RequestBody JSONObject queryEntity)
    {
        QingkeQueryEntity entity = makeQuery(queryEntity);
        return ResultUtil.success(service.export(entity));
    }

    @GetMapping("/query/{qingkeId}")
    @ApiOperation("轻客详情接口")
    public RestResult<Qingke> getById(@PathVariable("qingkeId") Integer qingkeId)
    {
        return ResultUtil.success(service.getById(qingkeId));
    }

    @PostMapping("/update")
    @ApiOperation("轻客更新接口")
    public RestResult<Boolean> update(@RequestBody Qingke qingke)
    {
        return ResultUtil.success(service.update(qingke));
    }

    @PostMapping("/audit/{auditType}")
    @ApiOperation("轻客审核接口")
    public RestResult<Boolean> audit(@PathVariable("auditType")String auditType,@RequestBody Qingke qingke)
    {
        return ResultUtil.success(service.audit(auditType,qingke));
    }


}
