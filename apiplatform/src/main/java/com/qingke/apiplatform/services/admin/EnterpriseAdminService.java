package com.qingke.apiplatform.services.admin;

import com.qingke.apiplatform.model.Enterprise;
import com.qingke.apiplatform.model.Project;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/15 4:00 PM
 */
public interface EnterpriseAdminService {
    List<Enterprise> query(String keyword);
}
