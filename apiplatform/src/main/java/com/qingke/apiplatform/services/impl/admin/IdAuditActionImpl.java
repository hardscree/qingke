package com.qingke.apiplatform.services.impl.admin;

import com.qingke.apiplatform.entity.SortMsg;
import com.qingke.apiplatform.entity.enums.SmsTypeEnum;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.services.admin.AuditAction;
import com.qingke.apiplatform.services.impl.AliSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 蒋世芳
 * @Date 2018/11/30 5:39 PM
 */
@Service
public class IdAuditActionImpl implements AuditAction {
    @Autowired
    AliSmsServiceImpl smsService;
    @Override
    public void doAction(Object obj) {
        if(obj.getClass().equals(Qingke.class))
        {
            Qingke qingke = (Qingke)obj;
            if(qingke.getQkidStatus().equals(4))
            {
                SortMsg msg = new SortMsg();
                msg.setMobile(qingke.getQkPhone());
                msg.setType(SmsTypeEnum.AuditIdFailed);
                smsService.send(msg);
            }
        }
    }
}
