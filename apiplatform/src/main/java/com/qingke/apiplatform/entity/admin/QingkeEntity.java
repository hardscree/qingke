package com.qingke.apiplatform.entity.admin;

import com.qingke.apiplatform.model.Qingke;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/21 9:41 AM
 */
@Data
public class QingkeEntity extends Qingke {
    Date statusUpdatetime;
    Date focusTime;

}
