package com.qingke.apiplatform.util;

import org.apache.poi.ss.formula.functions.T;

import com.qingke.apiplatform.entity.RestResult;

public class ResultUtil {

	@SuppressWarnings("hiding")
	public  static <T> RestResult<T> success(T data){
		RestResult<T>  restResult = new RestResult<T>(data);
		restResult.setCode(200);
		restResult.setMessage("success");
		return restResult;
	}
	
	@SuppressWarnings("hiding")
	public static <T> RestResult<T> failed(int code, String message, T data){
		RestResult<T>  restResult = new RestResult<T>(data);
		restResult.setCode(code);
		restResult.setMessage(message);
		return restResult;
	}
}
