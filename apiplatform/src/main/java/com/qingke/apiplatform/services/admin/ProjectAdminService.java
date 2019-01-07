package com.qingke.apiplatform.services.admin;

import com.qingke.apiplatform.entity.admin.ProjectEntity;
import com.qingke.apiplatform.entity.admin.ProjectQueryEntity;
import com.qingke.apiplatform.model.ProjectStatus;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/13 1:54 PM
 */
public interface ProjectAdminService {
    ProjectEntity getProjectById(int id);
    List<ProjectEntity> getProjectList(ProjectQueryEntity queryEntity);
    List<ProjectStatus> getProjectStatus();
    int createProject(ProjectEntity entity);
    ProjectEntity getProjectQingkeList(int projectId);
    boolean auditSign(int projectId,int qingkeId,int state);
    List<ProjectEntity> getProjectListByQingke(int qingkeId);
    boolean discard(int projectId);
}
