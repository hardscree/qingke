package com.qingke.apiplatform.services.impl;

import org.springframework.stereotype.Service;
import com.qingke.apiplatform.services.AuthCodeService;
import com.qingke.apiplatform.util.LocalCache;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

	@Override
	public String getCode(String key, int expires) {

		//随机产生四位数[1000,9999] 
		int num=(int)(Math.random()*9000)+1000; 
		String code = String.valueOf(num);
		
		LocalCache.put(key, code, expires);
		
		return code;
	}

	@Override
	public boolean checkCode(String key, String code) {
		if(code == null || code.equals(""))
			return false;
		
		Object object = LocalCache.get(key);
		if(object == null)
			return false;
		
		String oldCode = object.toString();
		if(oldCode.equals(code)){
			LocalCache.remove(key);
			return true;
		}
		return false;
	}

}
