package com.web.frame.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.web.frame.entity.table.web.App;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.redis.RedisUtil;

@Component
public class AccessTokenUtil {

	@Autowired
	private RedisUtil redisUtil;
	
	public Map<String, Object> createToken(String appId,String secret) {
		
		long timeout = 2 * 60 * 60 * 1000;
		Date start = new Date();
		long currentTimeMillis = System.currentTimeMillis();
        long currentTime = currentTimeMillis + timeout;//2小时有效时间
        Date end = new Date(currentTime);
        String token = JWT.create().withAudience(appId).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(secret));
        
        App app = (App) redisUtil.get(appId);
        app.setAccessToken(token);
        redisUtil.set(appId, app);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accessToken", token);
        map.put("currentTime", currentTimeMillis);
        map.put("validTime", currentTime);
        return map;
	}
	
	public String getAppId(String token) throws BussinessException {
		
		String appId = "";
		try {
			appId = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException e) {
			e.printStackTrace();
			throw new BussinessException("token错误，请获取新的token");
		}
		if(JWT.decode(token).getExpiresAt().getTime()<new Date().getTime()) {
//			throw new RuntimeException("已过期");
			throw new BussinessException("token已过期，请获取新的token");
		}
		App app = (App) redisUtil.get(appId);
		if(!token.equals(app.getAccessToken())) {
			throw new BussinessException("token已失效，请使用最近获取的token");
		}
		return appId;
	}
}
