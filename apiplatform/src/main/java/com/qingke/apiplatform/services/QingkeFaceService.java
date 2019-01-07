package com.qingke.apiplatform.services;

import com.qingke.apiplatform.model.QingkeFace;

public interface QingkeFaceService {

	boolean insert(QingkeFace qf);
	QingkeFace getLatestByBizNo(String bizNo);
	QingkeFace getLatestOkResult(String qkNumb);
}
