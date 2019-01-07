package com.qingke.apiplatform.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.qingke.apiplatform.core.Annotations.UserLoginToken;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.admin.ProjectEntity;
import com.qingke.apiplatform.entity.admin.ProjectQueryEntity;
import com.qingke.apiplatform.model.ProjectStatus;
import com.qingke.apiplatform.services.impl.admin.ProjectAdminServiceImpl;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/13 9:32 AM
 */
@RestController
@RequestMapping("/admin/project")
@Api("后台项目管理接口集")
@UserLoginToken
public class ProjectControllerAdmin {
    @Autowired
    ProjectAdminServiceImpl projectAdminAdminService;

    @PostMapping("/publish")
    @ApiOperation("项目发布接口")
    public RestResult<Integer> publishProject(@RequestBody ProjectEntity entity)
    {
        return ResultUtil.success(projectAdminAdminService.createProject(entity));
    }

    @PostMapping("/query")
    @ApiOperation("项目列表查询接口")
    @UserLoginToken
    public RestResult<List<ProjectEntity>> query(@RequestBody ProjectQueryEntity queryEntity)
    {
        return ResultUtil.success(projectAdminAdminService.getProjectList(queryEntity));
    }

    @GetMapping("/query/{projectId}")
    @ApiOperation("项目详情接口")
    public RestResult<ProjectEntity> queryById(@PathVariable("projectId") Integer projectId)
    {
        return ResultUtil.success(projectAdminAdminService.getProjectById(projectId));
    }

    @GetMapping("/status")
    @ApiOperation("项目状态列表接口")
    public RestResult<List<ProjectStatus>> getStatusList()
    {
        return ResultUtil.success(projectAdminAdminService.getProjectStatus());
    }

    @GetMapping("/project_qingke/{projectId}")
    @ApiOperation("项目轻客列表接口")
    public RestResult<ProjectEntity> getPorjectQingkeList(@PathVariable("projectId") Integer projectId)
    {
        return ResultUtil.success(projectAdminAdminService.getProjectQingkeList(projectId));
    }

    @PostMapping("/project_qingke/audit")
    @ApiOperation("项目轻客签名审核接口")
    public RestResult<Boolean> auditSign(@RequestBody JSONObject obj)
    {
        int projectId = obj.getInteger("projectId");
        int qingkeId = obj.getInteger("qingkeId");
        int auditState = obj.getInteger("state");
        return ResultUtil.success(projectAdminAdminService.auditSign(projectId,qingkeId,auditState));
    }

    @PostMapping("/discard/{projectId}")
    @ApiOperation("项目作废接口")
    public RestResult<Boolean> discard(@PathVariable("projectId") Integer projectId)
    {

        return ResultUtil.success(projectAdminAdminService.discard(projectId));
    }

}
