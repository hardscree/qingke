package com.qingke.apiplatform.services;

import java.util.List;

import com.qingke.apiplatform.model.Project;
import com.qingke.apiplatform.model.Protocol;

public interface ProjectService {
	boolean UpdateBySelective(Project project);
	List<Protocol> getProtocolsByProjectId(int projectId);
	Project getById(int projectId);
}
