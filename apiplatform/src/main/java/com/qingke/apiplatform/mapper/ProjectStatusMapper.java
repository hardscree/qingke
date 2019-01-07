package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.model.ProjectStatus;

import java.util.List;

public interface ProjectStatusMapper {
    int insert(ProjectStatus record);

    int insertSelective(ProjectStatus record);

    List<ProjectStatus> selectAllStatus();
}