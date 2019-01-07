package com.qingke.apiplatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingke.apiplatform.mapper.QingkeFaceMapper;
import com.qingke.apiplatform.model.QingkeFace;
import com.qingke.apiplatform.services.QingkeFaceService;

@Service
public class QingkeFaceServiceImpl implements QingkeFaceService {

	@Autowired
	private QingkeFaceMapper mapper;
	
	@Override
	public boolean insert(QingkeFace qf) {
		return mapper.insertSelective(qf)>0;
	}

	@Override
	public QingkeFace getLatestByBizNo(String bizNo) {
		return mapper.selectLatestByBizNo(bizNo);
	}

	@Override
	public QingkeFace getLatestOkResult(String qkNumb) {
		return mapper.selectLatestOkResult(qkNumb);
	}

}
