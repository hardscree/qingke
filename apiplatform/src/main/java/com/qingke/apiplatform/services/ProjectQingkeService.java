package com.qingke.apiplatform.services;

import com.qingke.apiplatform.model.ProjectQingke;

public interface ProjectQingkeService {
	boolean updateSelective(ProjectQingke projectQingke);
	int insert(ProjectQingke pq);
	ProjectQingke selectByQkNumbAndPNumb(int qkNumb, int pNumb);
}
