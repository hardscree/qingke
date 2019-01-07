package com.qingke.apiplatform.services.admin;

import com.qingke.apiplatform.entity.admin.QingkeEntity;
import com.qingke.apiplatform.entity.admin.QingkeExport;
import com.qingke.apiplatform.entity.admin.QingkeList;
import com.qingke.apiplatform.entity.admin.QingkeQueryEntity;
import com.qingke.apiplatform.model.Qingke;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/17 2:57 PM
 */
public interface QingkeAdminService {
    QingkeList query(QingkeQueryEntity entity);

    Qingke getById(int id);

    boolean update(Qingke qingke);

    List<QingkeEntity> selectByProjectId(int projectId);

    List<QingkeExport> export(QingkeQueryEntity entity);

    boolean audit(String auditType,Qingke qingke);
}
