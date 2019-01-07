package com.qingke.apiplatform.services.impl.admin;

import com.qingke.apiplatform.entity.admin.*;
import com.qingke.apiplatform.mapper.QingkeMapper;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.services.admin.AuditAction;
import com.qingke.apiplatform.services.admin.QingkeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/17 2:58 PM
 */
@Service
public class QingkeAdminServiceImpl implements QingkeAdminService {
    @Autowired
    QingkeMapper mapper;

    @Autowired
    SignAuditActionImpl signAuditAction;

    @Autowired
    VideoAuditActionImpl videoAuditAction;

    @Autowired
    LicenseAuditActionImpl licenseAuditAction;

    @Autowired
    IdAuditActionImpl idAuditAction;

    @Autowired
    ProjectAdminServiceImpl projectAdminAdminService;

    @Override
    public QingkeList query(QingkeQueryEntity entity) {

        QingkeList qingkeListEntity = new QingkeList();
        qingkeListEntity.setQingkeList( (mapper.query(entity)));
        qingkeListEntity.setTotal(mapper.queryCount(entity));
        qingkeListEntity.setLimit(entity.getLimit());
        qingkeListEntity.setCurrentPage(entity.getPage());

        return qingkeListEntity;
    }

    @Override
    public Qingke getById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Qingke qingke) {
        if(mapper.updateByPrimaryKey(qingke)>0) {
            return true;
        }
        return false;
    }

    @Override
    public List<QingkeEntity> selectByProjectId(int projectId) {
        return mapper.selectByProjectId(projectId);
    }

    @Override
    public List<QingkeExport> export(QingkeQueryEntity entity) {
        entity.setLimit(0);
        List<Qingke> qingkeList = mapper.query(entity);
        List<QingkeExport> exportList = new ArrayList<>();
        for(Qingke item :qingkeList)
        {
            List<ProjectEntity> projectEntityList =  projectAdminAdminService.getProjectListByQingke(item.getQkNumb());
            QingkeExport export = new QingkeExport(item);
            if(projectEntityList!=null && projectEntityList.size()>0)
            {
                export.setEnterprisenName(projectEntityList.get(0).getEnterpriseName());
                export.setProjectId(projectEntityList.get(0).getpNumb());
                export.setProjectName(projectEntityList.get(0).getpName());
            }

            exportList.add(export);
        }
        return exportList;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public boolean audit(String auditType,Qingke qingke)
    {
        AuditAction auditAction = null;
        Qingke qingkeForUpdate = new Qingke();
        qingkeForUpdate.setQkNumb(qingke.getQkNumb());
        switch (auditType)
        {
            case "license":
                auditAction = licenseAuditAction;
                qingkeForUpdate.setQkLicenseStatus(qingke.getQkLicenseStatus());
                qingkeForUpdate.setQkLicenseUrl(qingke.getQkLicenseUrl());
                qingkeForUpdate.setQkCreditCode(qingke.getQkCreditCode());
                qingkeForUpdate.setQkSelfName(qingke.getQkSelfName());
                break;
            case "id":
                auditAction = idAuditAction;
                qingkeForUpdate.setQkidStatus(qingke.getQkidStatus());
                break;
            case "sign":
                auditAction = signAuditAction;
                qingkeForUpdate.setQkSignStatus(qingke.getQkSignStatus());
                break;
            case "video":
                auditAction = videoAuditAction;
                qingkeForUpdate.setQkVideoStatus(qingke.getQkVideoStatus());
                if(qingke.getQkVideoStatus().equals(4))
                {
                    qingkeForUpdate.setQkVideoUrl("");
                }
                break;
            default:
                break;
        }
        if(mapper.updateByPrimaryKeySelective(qingkeForUpdate)<0)
        {
            return false;
        }
        else{
            if(auditAction!=null) {
                auditAction.doAction(qingke);
            }
            return true;
        }
    }
}
