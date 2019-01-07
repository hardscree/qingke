package com.qingke.apiplatform.mapper;

import com.qingke.apiplatform.entity.admin.ProjectEntity;
import com.qingke.apiplatform.entity.admin.ProjectQueryEntity;
import com.qingke.apiplatform.model.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer pNumb);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer pNumb);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<ProjectEntity> getProjectList(@Param("projectName") String projectName,
                                       @Param("projectState") int projectState,
                                       @Param("enterpriseName") String enterpriseName,
                                       @Param("createDateStart") String createDateStart,
                                       @Param("createDateEnd") String createDateEnd);

    List<ProjectEntity> getProjectList(@Param("query")ProjectQueryEntity entity);
    ProjectEntity getProjectById(@Param("projectId") int projectId);

    List<ProjectEntity> getProjectByQingkeId(@Param("qingkeId") int qingkeId);
}