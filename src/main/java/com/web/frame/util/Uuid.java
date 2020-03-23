package com.web.frame.util;

import java.util.Random;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Uuid {
	
	public static  String getUuid(){
		
		String uuid = UUID.randomUUID().toString();
		
		return uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
	//	return uuid.substring(0,8);
		
	}
	
}

