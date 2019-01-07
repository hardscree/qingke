package com.qingke.apiplatform.entity.admin;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/17 1:53 PM
 */
@Data
public class QingkeQueryEntity {
    String name;
    int qkidStatus;
    int qkVideoStatus;
    int qkLicenseStatus;
    String qkPhone;
    String startDate;
    String endDate;
    String idCode;
    int limit;
    int page;
    int offset;

}
