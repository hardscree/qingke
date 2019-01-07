package com.qingke.apiplatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingke.apiplatform.mapper.ProjectQingkeMapper;
import com.qingke.apiplatform.model.ProjectQingke;
import com.qingke.apiplatform.services.ProjectQingkeService;

@Service
public class ProjectQingkeServiceImpl implements ProjectQingkeService{

	@Autowired
	private ProjectQingkeMapper mapper;

	@Override
	public boolean updateSelective(ProjectQingke projectQingke) {
		return mapper.updateSelective(projectQingke)>0;
	}

	@Override
	public int insert(ProjectQingke pq) {
		return mapper.insert(pq);
	}

	@Override
	public ProjectQingke selectByQkNumbAndPNumb(int qkNumb, int pNumb) {
		ProjectQingke pq = new ProjectQingke();
		pq.setQkNumb(qkNumb);
		pq.setpNumb(pNumb);
		return mapper.selectByQkNumbAndPNumb(pq);
	}
	
}
