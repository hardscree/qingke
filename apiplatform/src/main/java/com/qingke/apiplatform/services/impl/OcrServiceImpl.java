package com.qingke.apiplatform.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.qingke.apiplatform.services.OcrService;
import com.qingke.apiplatform.util.HttpHelper;
import com.qingke.apiplatform.util.HttpUtils;
import com.qingke.apiplatform.util.UnicodeUtil;

import springfox.documentation.swagger.web.SwaggerApiListingReader;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

@Service
public class OcrServiceImpl implements OcrService {

	/*
	 * @Value("${facepp.url.ocridcard}") private String url;
	 * 
	 * @Value("${facepp.api.key}") private String apiKey;
	 * 
	 * @Value("${facepp.api.secret}") private String apiSecret;
	 * 
	 * @Override public Map<String, String> getIdCardInfo(byte[] bytes) {
	 * 
	 * String base64Img = Base64.byteArrayToBase64(bytes); Map<String, String>
	 * params = new HashMap<>(); params.put("api_key", apiKey);
	 * params.put("api_secret", apiSecret); params.put("image_base64", base64Img);
	 * String idcardinfo; try { idcardinfo = HttpHelper.post(params, url); } catch
	 * (Exception e) { e.printStackTrace(); return null; } String cn =
	 * UnicodeUtil.unicodeToCn(idcardinfo); System.out.println(idcardinfo);
	 * System.out.println(cn);
	 * 
	 * JSONObject obj = JSON.parseObject(cn); if (!obj.containsKey("cards")) return
	 * null;
	 * 
	 * // 对数组的解析 JSONArray cards = obj.getJSONArray("cards"); if (cards.size() == 0)
	 * return null;
	 * 
	 * JSONObject card = cards.getJSONObject(0);
	 * 
	 * Map<String, String> result = new HashMap<>();
	 * 
	 * if (card.containsKey("id_card_number")) {// 正面 result.put("qkSex",
	 * card.getString("gender")); result.put("qkBirth", card.getString("birthday"));
	 * result.put("qkId", card.getString("id_card_number"));
	 * result.put("qkIdAddress", card.getString("address")); result.put("qkName",
	 * card.getString("name")); result.put("qkNation", card.getString("race")); }
	 * else {// 背面 result.put("qkIdValidity", card.getString("valid_date")); }
	 * result.put("side", card.getString("side"));
	 * 
	 * return result; }
	 */

	/*
	 * 获取参数的json对象
	 */
	private JSONObject getParam(int type, String dataValue) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("dataType", type);
			obj.put("dataValue", dataValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Map<String, String> getIdCardInfo(File file, String side) {
		String host = "http://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		String appcode = "6b8c01e127244aa8b91beaf94886adc7";

		Boolean is_old_format = false;// 如果文档的输入中含有inputs字段，设置为True， 否则设置为False
		// 请根据线上文档修改configure字段
		JSONObject configObj = new JSONObject();
		configObj.put("side", side);
		String config_str = configObj.toString();
		// configObj.put("min_size", 5);
		// String config_str = "";

		String method = "POST";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);

		Map<String, String> querys = new HashMap<String, String>();

		// 对图像进行base64编码
		String imgBase64 = "";
		try {
			// File file = new File(imgFile);
			byte[] content = new byte[(int) file.length()];
			FileInputStream finputstream = new FileInputStream(file);
			finputstream.read(content);
			finputstream.close();
			imgBase64 = new String(encodeBase64(content));
		} catch (IOException e) {
			e.printStackTrace();
			// return;
		}
		// 拼装请求body的json字符串
		JSONObject requestObj = new JSONObject();
		try {

			requestObj.put("image", imgBase64);
			if (config_str.length() > 0) {
				requestObj.put("configure", config_str);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		String bodys = requestObj.toString();

		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			int stat = response.getStatusLine().getStatusCode();
			if (stat != 200) {
				System.out.println("Http code: " + stat);
				System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
				System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
				return null;
			}

			String res = EntityUtils.toString(response.getEntity());
			JSONObject card = JSON.parseObject(res);
			Map<String, String> result = new HashMap<>();
			if (side.equals("face")) {// 正面
				result.put("qkSex", card.getString("sex"));
				result.put("qkBirth", card.getString("birth"));
				result.put("qkId", card.getString("num"));
				result.put("qkIdAddress", card.getString("address"));
				result.put("qkName", card.getString("name"));
				result.put("qkNation", card.getString("nationality"));
			} else {// 背面
				String start = card.getString("start_date");
				String end = card.getString("end_date");
				result.put("qkIdValidity", start + "-" + end);
				result.put("issue", card.getString("issue"));
			}
			result.put("side", side);
			//System.out.println(card.toJSONString());

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
