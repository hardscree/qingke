package com.qingke.apiplatform.services;

public interface AuthCodeService {
	public String getCode(String key, int expires);
	public boolean checkCode(String key, String code);
}
