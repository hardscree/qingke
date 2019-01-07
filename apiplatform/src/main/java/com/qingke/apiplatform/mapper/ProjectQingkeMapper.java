package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.ProjectQingke;

public interface ProjectQingkeMapper {
    int insert(ProjectQingke record);

    int insertSelective(ProjectQingke record);

    int updateSelective(ProjectQingke record);

    ProjectQingke selectByQkNumbAndPNumb(ProjectQingke pq);

    int updateBySignUrl(ProjectQingke record);
}