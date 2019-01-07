package com.qingke.apiplatform.entity.admin;

import lombok.Data;

import java.util.Date;

/**
 * @Author 蒋世芳
 * @Date 2018/11/13 9:58 AM
 */
@Data
public class ProjectQueryEntity {
    int projectId;
    String projectName;
    Integer projectState;
    String createDateStart;
    String createDateEnd;
    String enterpriseName;
}
