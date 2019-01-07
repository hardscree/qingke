package com.qingke.apiplatform.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qingke.apiplatform.mapper.ProjectMapper;
import com.qingke.apiplatform.mapper.ProtocolMapper;
import com.qingke.apiplatform.model.Project;
import com.qingke.apiplatform.model.Protocol;
import com.qingke.apiplatform.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
    private ProjectMapper mapper;
	
	@Autowired
	private ProtocolMapper protocolMapper;
	
	@Value("${qingke.image.host}")
	private String imgHost;
	
	@Override
	public boolean UpdateBySelective(Project project) {
		return mapper.updateByPrimaryKeySelective(project)>0;
	}

	@Override
	public List<Protocol> getProtocolsByProjectId(int projectId) {
		List<Protocol> dest = new ArrayList<>();
		List<Protocol> source = protocolMapper.selectByProjectId(projectId);
		source.forEach(s->{
			if(s.getProType()==1 || s.getProType() == 2) {
				s.setProPhotoUrl(imgHost + s.getProPhotoUrl());
				dest.add(s);
			}
		});
		return dest;
	}

	@Override
	public Project getById(int projectId) {
		return mapper.selectByPrimaryKey(projectId);
	}

	
}
