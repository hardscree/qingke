package com.qingke.apiplatform.services.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingke.apiplatform.entity.FaceRandom;
import com.qingke.apiplatform.services.FaceIdService;
import com.qingke.apiplatform.util.HttpHelper;

@Service
public class FaceIdServiceImpl implements FaceIdService {

//	@Value("${facepp.url.ocridcard}")
//	private String url = "https://api-cn.faceplusplus.com/cardpp/v1/ocridcard";

	private String apiKey = "3WNXbVYxi8rqFs16oplweub5TKt8LyGQ";

	private String apiSecret = "LaamMSCotj2Lgs2TZLtg2JCOjAUkB2-Q";
	
	private String randomUrl = "https://api.megvii.com/faceid/lite/raw/get_random_number";
	
	private String videoUrl = "https://api.megvii.com/faceid/lite/raw/validate_video";
	
	private String verifyUrl = "https://api.megvii.com/faceid/lite/raw/verify";
	
	private String pageTokenUrl = "https://api.megvii.com/faceid/lite/get_token";
	
	private String getResultUrl = "https://api.megvii.com/faceid/lite/get_result";

	private String randomKey = "token_random_number";
	
	private static Map<String, String> errMessage;
	
	public FaceIdServiceImpl() {
		errMessage = new HashMap<>();
//		INVALID_TOKEN	参数所对应调用Raw-GetRandomNumber API的token_random_number不存在、或格式错误、或已过期、或并非通过一个API Key调用的Raw-GetRandomNumber API返回的token_random_number。
//		400	VIDEO_FACE_NOT_FOUND	上传的视频中没有检测到人脸
//		400	VIDEO_LOW_FACE_QUALITY	上传的视频中人脸质量太差
//		400	VIDEO_INVALID_DURATION	上传的视频时长不对
//		400	VIDEO_MULTIPLE_FACES	上传的视频中有多张人脸
//		400	VIDEO_NO_AUDIO	上传视频中没有音轨，通常是由于手机的录音权限没有打开或者受到阻止
//		400	VIDEO_UNSUPPORTED_FORMAT	视频无法解析，有可能是ffmpeg不支持的格式或视频有破损
		errMessage.put("INVALID_TOKEN", "视频验证码不正确");
		errMessage.put("VIDEO_FACE_NOT_FOUND", "未检测到人脸");
		errMessage.put("VIDEO_LOW_FACE_QUALITY", "视频中人脸质量太差");
		
		errMessage.put("VIDEO_INVALID_DURATION", "视频时长不对");
		errMessage.put("VIDEO_MULTIPLE_FACES", "上传的视频中有多张人脸");
		errMessage.put("VIDEO_NO_AUDIO", "上传视频中没有音轨");
		errMessage.put("VIDEO_UNSUPPORTED_FORMAT", "视频格式错误");
		
		/*verify 接口*/
//		NO_SUCH_ID_NUMBER	有源比对时，数据源中没有此身份证号码的记录。
//		ID_NUMBER_NAME_NOT_MATCH 有源比对时，数据源中存在此身份证号码，但与提供的姓名不匹配。
//		//INVALID_TOKEN	token不存在、过期、或格式错误、或不是同一个API Key调用的所返回的token
//		INVALID_IDCARD_NUMBER idcard_number参数不是正确的身份证号码格式。身份证号码必定为18位数字，且最后一位可以是X（大小写均可）。
		
		errMessage.put("NO_SUCH_ID_NUMBER", "没有此身份证号码的记录");
		errMessage.put("ID_NUMBER_NAME_NOT_MATCH", "身份证号码与提供的姓名不匹配");
		errMessage.put("INVALID_IDCARD_NUMBER", "不是正确的身份证号码格式");
		
//		"VIDEO_SR_ERROR"：语音识别结果与要求不符。（仅当视频验证流程时）
//		"VIDEO_NOT_SYNCHRONIZED"：视频中唇语识别错误。（仅当视频验证流程时）
//		"VIDEO_FACE_INCONSISTENT": 视频过程中的人脸不一致
//		"SELFIE_INCONSISTENT"：正脸侧脸照不一致 。（仅双角度活体验证时）
//		"SELFIE_METADATA_INCONSISTENT"：正侧脸照的照片不都为JPG编码、或者元信息不一致。

		errMessage.put("VIDEO_SR_ERROR", "语音识别结果与要求不符");
		errMessage.put("VIDEO_NOT_SYNCHRONIZED", "视频中唇语识别错误");
		errMessage.put("VIDEO_FACE_INCONSISTENT", "视频过程中的人脸不一致");
		errMessage.put("SELFIE_INCONSISTENT", "正脸侧脸照不一致");
		errMessage.put("SELFIE_METADATA_INCONSISTENT", "正侧脸照的照片不都为JPG编码、或者元信息不一致");
		
//		“MASK”：面具攻击。
//		“SCREEN_REPLAY”：屏幕翻拍。
//		“FACEGEN”：软件合成脸
		
		errMessage.put("MASK", "面具攻击");
		errMessage.put("SCREEN_REPLAY", "屏幕翻拍");
		errMessage.put("FACEGEN", "软件合成脸");
	}
	
	@Override
	public FaceRandom getRandom() {

		Map<String, String> params = new HashMap<>();
		params.put("api_key", apiKey);
		params.put("api_secret", apiSecret);


		FaceRandom random = new FaceRandom();
		try {
			String rep = HttpHelper.post(params, randomUrl);
			System.out.println(rep);
			JSONObject jsonObject = JSON.parseObject(rep);
			
			
			if(jsonObject.containsKey(randomKey)){
				random.setTokenRandomNumber(jsonObject.getString(randomKey));
				random.setRandomNumber(jsonObject.getString("random_number"));				
			}					
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return random;
	}
	
	@Override
	public JSONObject getResult(String bizId) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("api_key", apiKey);
		params.put("api_secret", apiSecret);		
		params.put("biz_id", bizId);
		params.put("return_image", "1");
		
		String rep = HttpHelper.get(null, params, getResultUrl);
		//System.out.println(rep);
		return JSON.parseObject(rep);
	}
	
	@Override
	public JSONObject validateVideo(File file, String tokenRandom) throws Exception{
	
		Map<String, String> params = new HashMap<>();
		params.put("api_key", apiKey);
		params.put("api_secret", apiSecret);		
		params.put(randomKey, tokenRandom);
		
		String rep = HttpHelper.upload(videoUrl, file, "video", params, null);
		return JSONObject.parseObject(rep);
	}

	@Override
	public JSONObject verify(String idcardName, String idcardNum, String videoToken) throws Exception{
		
		Map<String, String> params = new HashMap<>();
		params.put("api_key", apiKey);
		params.put("api_secret", apiSecret);
		
		params.put("idcard_name", idcardName);
		params.put("idcard_number", idcardNum);
		params.put("token", videoToken);
		
		params.put("comparison_type", "1");
		
		String rep = HttpHelper.post(params, verifyUrl);
		return JSONObject.parseObject(rep);
	}
	
	@Override
	public JSONObject getPageToken(String returnUrl, String notifyUrl
			, String bizNo, String bizExtraData
			, String idCardName, String idCardNumb
			, String sceneId, String webTitle) throws Exception{
		Map<String, String> params = new HashMap<>();
		params.put("api_key", apiKey);
		params.put("api_secret", apiSecret);
		
		params.put("comparison_type", "1");
		params.put("idcard_mode", "0");
		
		params.put("return_url", returnUrl);
		params.put("notify_url", notifyUrl);
		params.put("biz_no", bizNo);
		
		if(!StringUtils.isBlank(bizExtraData)){
			params.put("biz_extra_data", bizExtraData);
		}
		
		params.put("idcard_name", idCardName);
		params.put("idcard_number", idCardNumb);
		
		if(!StringUtils.isBlank(sceneId)){
			params.put("scene_id", sceneId);
		}
		if(!StringUtils.isBlank(webTitle)){
			params.put("web_title", webTitle);
		}
		
		String rep = HttpHelper.post(params, pageTokenUrl);
		return JSONObject.parseObject(rep);
	}

	@Override
	public String getErrDesc(String errKey) {
		String desc = "视频验证中出现错误";
		if(errMessage.containsKey(errKey)) {
			desc = errMessage.get(errKey);
		}
		
		return desc;
	}
}
