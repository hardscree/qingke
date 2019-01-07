package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.QingkeStatus;

public interface QingkeStatusMapper {
    int insert(QingkeStatus record);

    int insertSelective(QingkeStatus record);
}