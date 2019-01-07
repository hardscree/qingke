package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.Enterprise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseMapper {
    int deleteByPrimaryKey(Integer etNumb);

    int insert(Enterprise record);

    int insertSelective(Enterprise record);

    Enterprise selectByPrimaryKey(Integer etNumb);

    int updateByPrimaryKeySelective(Enterprise record);

    int updateByPrimaryKey(Enterprise record);

    List<Enterprise> selectByKeyword(@Param("keyword") String keyword);
}