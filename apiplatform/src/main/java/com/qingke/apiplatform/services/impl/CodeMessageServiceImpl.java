package com.qingke.apiplatform.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingke.apiplatform.mapper.CodeMessageMapper;
import com.qingke.apiplatform.model.CodeMessage;
import com.qingke.apiplatform.services.CodeMessageService;

@Service
public class CodeMessageServiceImpl implements CodeMessageService {

	@Autowired
	private CodeMessageMapper codeMessageMapper;
	@Override
	public boolean insert(CodeMessage msg) {
		return codeMessageMapper.insert(msg)>0;
	}
	@Override
	public int getLatestPnumb(String openId) {
		CodeMessage codeMessage = codeMessageMapper.selectLatestByOpenId(openId);
		if(codeMessage == null || StringUtils.isBlank(codeMessage.getEventType()))
			return 0;
		return Integer.parseInt(codeMessage.getEvnetKey());
	}

}
