package com.qingke.apiplatform.core.interceptors;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qingke.apiplatform.core.Annotations.PassToken;
import com.qingke.apiplatform.core.Annotations.QingkeLoginToken;
import com.qingke.apiplatform.core.Annotations.UserLoginToken;
import com.qingke.apiplatform.entity.ResultBase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author 蒋世芳
 * @Date 2018/11/8 4:59 PM
 */
public class TokenInterceptor implements HandlerInterceptor {
    @Value("${token.key}")
    private String tokenKey;

    @Value("${token.expire}")
    private int tokeyExpire;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token

        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Class bean = handlerMethod.getBean().getClass();
        Method method = handlerMethod.getMethod();

//        检查是否有passtoken注释，有则跳过认证
//        if (method.isAnnotationPresent(PassToken.class) ||
//                (bean.isAnnotationPresent(PassToken.class) && !method.isAnnotationPresent(UserLoginToken.class))) {
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class) || bean.isAnnotationPresent(UserLoginToken.class)) {
            if(method.isAnnotationPresent(PassToken.class))
            {
                return true;
            }
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken==null) {
                userLoginToken = (UserLoginToken) bean.getAnnotation(UserLoginToken.class);
            }
            ResultBase tokenResult = new ResultBase();
            tokenResult.setCode(401);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    tokenResult.setMessage("无token，请重新登录");
                    httpServletResponse.getWriter().write(JSON.toJSONString(tokenResult));
                    return  false;
                    //throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    tokenResult.setMessage(j.getMessage());
                    httpServletResponse.getWriter().write(JSON.toJSONString(tokenResult));
                    return  false;
                    //throw new RuntimeException("401");
                }

                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenKey)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    tokenResult.setMessage(e.getMessage());
                    httpServletResponse.getWriter().write(JSON.toJSONString(tokenResult));
                    return  false;
                    //throw new RuntimeException("401");
                }
                return true;
            }
        }
        
        
        //检查qingke login
        if (method.isAnnotationPresent(QingkeLoginToken.class)) {
        	QingkeLoginToken qkLoginToken = method.getAnnotation(QingkeLoginToken.class);
            if (qkLoginToken.required()) {
            	
            	ResultBase result = new ResultBase();
            	result.setCode(401);
            	result.setMessage("Token has expired!");
            	String json401 = JSON.toJSONString(result);
                // 执行认证
                if (token == null) {
                	System.out.println("token is null");
                	httpServletResponse.getWriter().write(json401);
                	return false;
                    //throw new RuntimeException("无token，请重新登录");
                }                
                
                String mobile;
                try {
                	// 获取 token 中的 qingke mobile
                	mobile = JWT.decode(token).getAudience().get(0); 
                	System.out.println("=======mobile is "+mobile);
                    //TODO:validate mobile
                } catch (JWTDecodeException j) {
                	httpServletResponse.getWriter().write(json401);
                	return false;
                }
            
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenKey))
                		.withAudience(mobile)
                		.build();
                try {
                    jwtVerifier.verify(token);                    
                } catch (JWTVerificationException e) {
                	System.out.println("=======JWTVerificationException");
                	httpServletResponse.getWriter().write(json401);
                	return false;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
