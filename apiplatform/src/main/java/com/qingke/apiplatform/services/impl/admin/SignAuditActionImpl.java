package com.qingke.apiplatform.services.impl.admin;

import com.qingke.apiplatform.entity.SortMsg;
import com.qingke.apiplatform.entity.enums.SmsTypeEnum;
import com.qingke.apiplatform.mapper.ProjectQingkeMapper;
import com.qingke.apiplatform.mapper.QingkeMapper;
import com.qingke.apiplatform.model.ProjectQingke;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.services.impl.AliSmsServiceImpl;
import com.qingke.apiplatform.services.admin.AuditAction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 蒋世芳
 * @Date 2018/11/29 7:24 PM
 */
@Service
public class SignAuditActionImpl implements AuditAction {
    @Autowired
    ProjectQingkeMapper projectQingkeMapper;

    @Autowired
    QingkeMapper qingkeMapper;

    @Autowired
    AliSmsServiceImpl smsService;

    @Override
    public void doAction(Object obj) {
        SortMsg msg = new SortMsg();
        Qingke qingKe = null;
        ProjectQingke projectQingke = null;
        if(obj.getClass().equals(Qingke.class))
        {
            qingKe = (Qingke)obj;
            projectQingke = new ProjectQingke();
            projectQingke.setSignStatus(qingKe.getQkSignStatus());
            projectQingke.setSignUrl(qingKe.getQkSignUrl());
            projectQingkeMapper.updateBySignUrl(projectQingke);
        }
        if(obj.getClass().equals(ProjectQingke.class))
        {
            projectQingke = (ProjectQingke)obj;
            qingKe = new Qingke();
            qingKe.setQkSignStatus(projectQingke.getSignStatus());
            qingKe.setQkNumb(projectQingke.getQkNumb());
            projectQingke = projectQingkeMapper.selectByQkNumbAndPNumb(projectQingke);
            qingKe.setQkSignUrl(projectQingke.getSignUrl());
            qingkeMapper.updateBySignUrl(qingKe);
        }
        if(qingKe.getQkSignStatus().equals(4))
        {
            msg.setType(SmsTypeEnum.AuditSignFailed);
            if(StringUtils.isBlank(qingKe.getQkPhone())) {
            	qingKe = qingkeMapper.selectByPrimaryKey(qingKe.getQkNumb());
            }
            msg.setMobile(qingKe.getQkPhone());
            smsService.send(msg);
        }
    }
}
