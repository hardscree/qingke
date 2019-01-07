package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.QingkeFace;

public interface QingkeFaceMapper {
    int deleteByPrimaryKey(Integer fId);

    int insert(QingkeFace record);

    int insertSelective(QingkeFace record);

    QingkeFace selectByPrimaryKey(Integer fId);

    int updateByPrimaryKeySelective(QingkeFace record);

    int updateByPrimaryKeyWithBLOBs(QingkeFace record);

    int updateByPrimaryKey(QingkeFace record);
    
    QingkeFace selectLatestByBizNo(String bizNo);
    QingkeFace selectLatestOkResult(String qkNumb);
}