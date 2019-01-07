package com.qingke.apiplatform.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import javax.servlet.http.HttpServletRequest;

public class HttpHelper {

	private static String UTF8 = "UTF-8";
	private static RequestConfig requestConfig;

	public static String post(Map<String, String> header, Map<String, String> params, String url) throws Exception {
		HttpPost post = null;
		post = new HttpPost(url);
		if (header != null) {
			for (String key : header.keySet()) {
				post.addHeader(key, header.get(key));
			}
		}
		if (params != null) {
			List<BasicNameValuePair> list = new LinkedList<BasicNameValuePair>();
			post.setConfig(getRequestConfig());
			for (String key : params.keySet()) {
				list.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, UTF8);
			post.setEntity(entity);
		}
		return HttpClientHelper.getHttpClient().execute(post);
	}
	
	public static String get(Map<String, String> header, Map<String, String> params, String url) throws Exception {
		
		if (params != null) {
			if(!url.contains("?")) {
				url += "?";
			}
			for(String key : params.keySet()) {
				url += key + "=" + params.get(key) + "&";
			}
		}
		
		HttpGet get = new HttpGet(url);
		if (header != null) {
			for (String key : header.keySet()) {
				get.addHeader(key, header.get(key));
			}
		}
		return HttpClientHelper.getHttpClient().execute(get);
	}

	public static String post(Map<String, String> header, String jsonObject, String url) throws Exception {
		HttpPost post = null;
		post = new HttpPost(url);
		if (header != null) {
			for (String key : header.keySet()) {
				post.addHeader(key, header.get(key));
			}
		}
		if (jsonObject.isEmpty()) {
			throw new Exception("jsonObject不能为空！");
		}
		//HttpEntity entity = new StringEntity(jsonObject, "UTF-8");
		return HttpClientHelper.getHttpClient().execute(post);
	}

	public static String post(Map<String, String> params, String url) throws Exception {
		HttpPost post = null;
		post = new HttpPost(url);
		List<BasicNameValuePair> list = new LinkedList<BasicNameValuePair>();
		post.setConfig(getRequestConfig());
		for (String key : params.keySet()) {
			list.add(new BasicNameValuePair(key, params.get(key)));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, UTF8);
		post.setEntity(entity);
		return HttpClientHelper.getHttpClient().execute(post);
	}

	public static String upload(String url, File file, String fileInputName, Map<String, String> params, Map<String, String> headers) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(getRequestConfig());
		if (headers != null) {
			for (String key : headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
		}
				
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		// multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
		multipartEntityBuilder.addBinaryBody(fileInputName, file);
		
		
		if(params!=null){
			for (String key : params.keySet()) {
				multipartEntityBuilder.addTextBody(key, params.get(key));
			}
		}
		
		HttpEntity httpEntity = multipartEntityBuilder.build();
		httpPost.setEntity(httpEntity);

		return HttpClientHelper.getHttpClient().execute(httpPost);
	}

	public static RequestConfig getRequestConfig() {
		if (requestConfig == null) {
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000)
					.setSocketTimeout(60000).build();
		}
		return requestConfig;
	}

	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client_IP");
		}
		if (ip == null || ip.length() == 0 || "unkonwn".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.length() < 5) {
			ip = "0.0.0.0";
		}
		return ip;
	}
}