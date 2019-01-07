package com.qingke.apiplatform.entity;

import org.apache.poi.ss.formula.functions.T;

@SuppressWarnings("hiding")
public class RestResult<T> extends ResultBase {

	private T data;
	public T getData() {
		return data;
	}
	/*public void setData(T data) {
		this.data = data;
	}*/
	
	public RestResult(T dt){
		this.data = dt;
	}
}
