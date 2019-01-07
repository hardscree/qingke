package com.qingke.apiplatform.services.impl;

import com.qingke.apiplatform.entity.AliSortMessage;
import com.qingke.apiplatform.entity.SortMsg;
import com.qingke.apiplatform.services.SmsService;
import com.qingke.apiplatform.util.SortMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author 蒋世芳
 * @Date 2018/11/30 4:24 PM
 */
@Service
public class AliSmsServiceImpl  implements SmsService {
    @Override
    public boolean send(SortMsg msg) {
        return send(msg,null);
    }

    @Override
    public boolean send(SortMsg msg, Map<String, String> templateData) {
        AliSortMessage message = new AliSortMessage();
        message.setMobile(msg.getMobile());
        message.setTemplateCode(msg.getType().getTemplateCode());
        if(templateData!=null) {
            message.setTemplateData(templateData);
        }
        boolean rst = new SortMessageHelper().send(message);

        return rst;
    }
}
