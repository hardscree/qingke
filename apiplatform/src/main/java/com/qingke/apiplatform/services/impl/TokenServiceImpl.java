package com.qingke.apiplatform.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.qingke.apiplatform.entity.ApiToken;
import com.qingke.apiplatform.model.User;
import com.qingke.apiplatform.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author 蒋世芳【jiangshifang@reignwood.com】
 * @Date 2018/11/8 5:32 PM
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Value("${token.key}")
    private  String tokenKey;

    @Value("${token.expire}")
    private int tokeyExpire;
    
    @Value("${token.qingke.expire}")
    private int qingkeTokenExpire;

    @Override
    public String getToken(User user) {
        String token="";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,tokeyExpire);
        Date exprieDate = calendar.getTime();
        token= JWT.create().withAudience(user.getUserName()).withExpiresAt(exprieDate)
                .sign(Algorithm.HMAC256(tokenKey));
        return token;
    }

	@Override
	public ApiToken getQingkeToken(String mobile) {
		String token="";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,qingkeTokenExpire);
        Date exprieDate = calendar.getTime();
        token= JWT.create().withAudience(mobile).withExpiresAt(exprieDate)
                .sign(Algorithm.HMAC256(tokenKey));
        
        ApiToken apiToken = new ApiToken();
        apiToken.setToken(token);
        apiToken.setExpires(qingkeTokenExpire);
        return apiToken;
	}
}
