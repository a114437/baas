package com.web.frame.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Sign  {  
	
	
	public static void main(String[] args) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("chain", "0");
		map.put("symbol", "AUT");
		map.put("tokenAddress", "0x37c8b2de2d3fff6eb4ca6ce1aa06987333954dbd");
		map.put("tokenType", "0");
		map.put("appSecret", "76bc33f62023462e8156a63a40c3f305");
		String sign = sign(map);
		System.out.println(sign);
	}
	
	
	public static Map<String,Object> addParam(String key,String value,Map<String,Object> map ) {
		if(StringUtils.isNotEmpty(value)) {
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 签名
	 * @param map
	 * @return
	 */
	public static String sign(Map<String,Object> map) {
		Collection<String> keyset = map.keySet(); 
		List<String> list = new ArrayList<String>(keyset);
		Collections.sort(list);
		String temp = "";
		for (int i = 0; i < list.size(); i++) {
			temp+=list.get(i)+"="+map.get(list.get(i))+"&";
		}
		if(StringUtils.isNotEmpty(temp)) {
			temp = temp.substring(0, temp.length()-1);
		}
		String md5 = getMD5(temp);
		return md5;
	}
	
	//生成MD5  
    public static String getMD5(String message) {  
        String md5 = "";  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象  
            byte[] messageByte = message.getBytes("UTF-8");  
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位  
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return md5;  
    }  
   
     // 二进制转十六进制  
    public static String bytesToHex(byte[] bytes) {  
        StringBuffer hexStr = new StringBuffer();  
        int num;  
        for (int i = 0; i < bytes.length; i++) {  
            num = bytes[i];  
             if(num < 0) {  
                 num += 256;  
            }  
            if(num < 16){  
                hexStr.append("0");  
            }  
            hexStr.append(Integer.toHexString(num));  
        }  
        return hexStr.toString().toUpperCase();  
    }  
}