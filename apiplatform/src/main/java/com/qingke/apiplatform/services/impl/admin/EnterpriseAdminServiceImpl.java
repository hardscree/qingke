package com.qingke.apiplatform.services.impl.admin;

import com.qingke.apiplatform.mapper.EnterpriseMapper;
import com.qingke.apiplatform.model.Enterprise;
import com.qingke.apiplatform.model.Project;
import com.qingke.apiplatform.services.admin.EnterpriseAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/15 4:07 PM
 */
@Service
public class EnterpriseAdminServiceImpl implements EnterpriseAdminService {
    @Autowired
    EnterpriseMapper mapper;

    @Override
    public List<Enterprise> query(String keyword) {
        return mapper.selectByKeyword(keyword);
    }
}
