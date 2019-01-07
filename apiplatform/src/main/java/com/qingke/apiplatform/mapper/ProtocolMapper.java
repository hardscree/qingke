package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.Protocol;

import java.util.List;

public interface ProtocolMapper {
    int deleteByPrimaryKey(Integer proNumb);

    int insert(Protocol record);

    int insertSelective(Protocol record);

    Protocol selectByPrimaryKey(Integer proNumb);

    int updateByPrimaryKeySelective(Protocol record);

    int updateByPrimaryKeyWithBLOBs(Protocol record);

    int updateByPrimaryKey(Protocol record);

    List<Protocol> selectByProjectId(int projectId);

}