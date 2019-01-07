package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.CodeMessage;

public interface CodeMessageMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(CodeMessage record);

    int insertSelective(CodeMessage record);

    CodeMessage selectByPrimaryKey(Integer msgId);
    CodeMessage selectLatestByOpenId(String openId);

    int updateByPrimaryKeySelective(CodeMessage record);

    int updateByPrimaryKey(CodeMessage record);
}