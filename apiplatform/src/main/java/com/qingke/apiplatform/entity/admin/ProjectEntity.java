package com.qingke.apiplatform.entity.admin;

import com.qingke.apiplatform.model.Project;
import com.qingke.apiplatform.model.Protocol;
import com.qingke.apiplatform.model.Qingke;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/13 9:40 AM
 */
@Data
public class ProjectEntity extends Project {
    String enterpriseName;
    int waitAudit;
    List<Protocol> protocolList;
    List<QingkeEntity> qingkeList;
    int uploadLicenseNum;

}
