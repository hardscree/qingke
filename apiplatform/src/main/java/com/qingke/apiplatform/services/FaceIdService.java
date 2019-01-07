package com.qingke.apiplatform.services;

import java.io.File;
import com.alibaba.fastjson.JSONObject;
import com.qingke.apiplatform.entity.FaceRandom;

public interface FaceIdService {

	FaceRandom getRandom();
	JSONObject validateVideo(File file, String tokenRandom) throws Exception;
	JSONObject verify(String idcardName, String idcardNum, String videoToken) throws Exception;
	String getErrDesc(String errKey);
	JSONObject getResult(String bizId) throws Exception;
	JSONObject getPageToken(String returnUrl, String notifyUrl
			, String bizNo, String bizExtraData
			, String idCardName, String idCardNumb
			, String sceneId, String webTitle) throws Exception;
}
