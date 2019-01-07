package com.qingke.apiplatform.entity.admin;

import com.qingke.apiplatform.model.Qingke;
import lombok.Data;

import java.util.List;

/**
 * @Author 蒋世芳
 * @Date 2018/11/17 1:50 PM
 */
@Data
public class QingkeList {
    int total;
    int currentPage;
    int limit;
    List<Qingke> qingkeList;
}
