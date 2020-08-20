package com.web.frame.util.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class RedisPubSub{
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RedisUtil redisUtil;
	
	private Logger logger = Logger.getLogger(RedisPubSub.class);

	public void receiveMessage(String messageKey) {
		
		
	}

	
	public void sendMessage(String channel,String message) {
		
		redisTemplate.convertAndSend(channel, message);
	}
}
