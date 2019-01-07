package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.entity.admin.QingkeEntity;
import com.qingke.apiplatform.entity.admin.QingkeQueryEntity;
import com.qingke.apiplatform.model.Qingke;

import java.util.List;

public interface QingkeMapper {
    int deleteByPrimaryKey(Integer qkNumb);

    int insert(Qingke record);

    int insertSelective(Qingke record);

    Qingke selectByPrimaryKey(Integer qkNumb);

    int updateByPrimaryKeySelective(Qingke record);

    int updateByPrimaryKey(Qingke record);

    int queryCount(QingkeQueryEntity entity);

    List<Qingke> query(QingkeQueryEntity entity);
    Qingke selectByOpenId(String qkOpenId);
    Qingke selectByMobile(String qkPhone);
    List<QingkeEntity> selectByProjectId(int projectId);

    int updateBySignUrl(Qingke record);
}