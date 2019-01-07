package com.qingke.apiplatform.services.impl.admin;

import com.qingke.apiplatform.entity.admin.ProjectEntity;
import com.qingke.apiplatform.entity.admin.ProjectQueryEntity;
import com.qingke.apiplatform.mapper.*;
import com.qingke.apiplatform.model.*;
import com.qingke.apiplatform.services.admin.ProjectAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/13 1:55 PM
 */
@Service
public class ProjectAdminServiceImpl implements ProjectAdminService {
    @Autowired
    ProjectMapper mapper;

    @Autowired
    ProjectStatusMapper statusMapper;

    @Autowired
    ProtocolMapper protocolMapper;

    @Autowired
    QingkeMapper qingkeMapper;

    @Autowired
    ProjectQingkeMapper projectQingkeMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Autowired
    SignAuditActionImpl signAuditAction;

    @Override
    public ProjectEntity getProjectById(int id) {
        ProjectEntity entity = mapper.getProjectById(id);
        entity.setProtocolList(protocolMapper.selectByProjectId(entity.getpNumb()));
        return entity;
    }

    @Override
    public List<ProjectEntity> getProjectList(ProjectQueryEntity queryEntity) {
        return mapper.getProjectList(queryEntity);
//        return mapper.getProjectList(queryEntity.getProjectName(),queryEntity.getProjectState(),queryEntity.getEnterpriseName(),queryEntity.getCreateDateStart(),queryEntity.getCreateDateEnd());
    }

    @Override
    public List<ProjectStatus> getProjectStatus() {
        return statusMapper.selectAllStatus();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createProject(ProjectEntity entity) {
        Date today = new Date();
        entity.setCreateTime(today);
        entity.setUpdateTime(today);
        if(entity.getEtNumb()<=0)
        {
            Enterprise enterprise = new Enterprise();
            enterprise.setEtSname(entity.getEnterpriseName());
            enterprise.setEtFname(entity.getEnterpriseName());
            enterpriseMapper.insertSelective(enterprise);
            entity.setEtNumb(enterprise.getEtNumb());
        }
        mapper.insert(entity);
        int index=0;
        for (Protocol protocol: entity.getProtocolList())
        {
            protocol.setpNumb(entity.getpNumb());
            protocol.setProPhotoSort(index);
            protocol.setUploadDate(today);
            protocolMapper.insert(protocol);
            index++;
        }
        return entity.getpNumb();
    }

    @Override
    public ProjectEntity getProjectQingkeList(int projectId) {
        ProjectEntity entity = mapper.getProjectById(projectId);
        entity.setQingkeList(qingkeMapper.selectByProjectId(projectId));
        return entity;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public boolean auditSign(int projectId, int qingkeId, int state) {

        ProjectQingke qingke = new ProjectQingke();
        qingke.setpNumb(projectId);
        qingke.setQkNumb(qingkeId);
        qingke.setSignStatus(state);
        signAuditAction.doAction(qingke);
        if (projectQingkeMapper.updateSelective(qingke)>0)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<ProjectEntity> getProjectListByQingke(int qingkeId) {
        return mapper.getProjectByQingkeId(qingkeId);
    }

    @Override
    public boolean discard(int projectId) {
        Project project = new Project();
        project.setpNumb(projectId);
        project.setpStatus(5);
        if(mapper.updateByPrimaryKeySelective(project)>0)
            return true;
        return false;
    }
}
