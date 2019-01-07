package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.ProtocolType;

public interface ProtocolTypeMapper {
    int insert(ProtocolType record);

    int insertSelective(ProtocolType record);
}