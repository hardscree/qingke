package com.qingke.apiplatform.services.impl;

import com.qingke.apiplatform.mapper.QingkeMapper;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.services.QingkeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 蒋世芳
 * @Date 2018/11/12 4:32 PM
 */
@Service
public class QingkeServiceImpl implements QingkeService {

	@Autowired
	private QingkeMapper qingkeMapper;
	
	@Override
	public boolean UpdateSelective(Qingke qingke) {
		return qingkeMapper.updateByPrimaryKeySelective(qingke)>0;
	}

	@Override
	public boolean Update(Qingke qingke) {
		return qingkeMapper.updateByPrimaryKey(qingke)>0;
	}

	@Override
	public Qingke getById(int id) {
		return qingkeMapper.selectByPrimaryKey(id);
	}


	@Override
	public boolean Insert(Qingke qingke) {
		return qingkeMapper.insertSelective(qingke)>0;
	}
	@Override
	public Qingke getByMobile(String mobile) {
		return qingkeMapper.selectByMobile(mobile);
	}

	@Override
	public Qingke getByOpenId(String openId) {
		return qingkeMapper.selectByOpenId(openId);
	}
}
