package com.qingke.apiplatform.services;

import com.qingke.apiplatform.model.CodeMessage;

public interface CodeMessageService {
	boolean insert(CodeMessage msg);
	int getLatestPnumb(String openId);
}
