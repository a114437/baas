package com.web.frame.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;


/**
 * 进行json格式的数据封装
 * @author ywp
 * @date 2013/12/17
 */
@Scope("prototype")
@Component
public class JsonFormat{

	/**
	 * 字符串转json格式字符串
	 * @param str
	 * @return
	 */
	public static String jFormatString(String str){

        JSONObject result = JSON.parseObject(str);  
        return result.toString();
	}
	
	/**
	 * Object对象转json
	 * @param obj
	 * @return
	 */
	public static String beanToJson(Object obj){
		
		 String result = JSON.toJSONString(obj);  
		 return result;
	}
	
	/**
	 * json转map
	 * @param json
	 * @return
	 */
	public static Map<String,Object> jsonToMap(String json){
		
		return JSON.parseObject(json, Map.class);
	}
	
	/**
	 * list集合转json格式字符串
	 * @param list
	 * @return
	 */
	public static String jFormatString(List list){

		JSONArray json = new JSONArray(list);
		return json.toString();
	}
	
	/**
	 * 将集合转换成json格式字符串
	 */
	public <T> String listToJson(List<T> t){
		
		String json = JSON.toJSONString(t);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> parseJSON2List(String jsonStr){
		
		List<T> list = new ArrayList<T>();
		JSONArray jsonArray = JSON.parseArray(jsonStr);
		Iterator<Object> iterator = jsonArray.iterator();
		while(iterator.hasNext()){
			T t = (T) iterator.next();
//			T t = JSON.parseObject(json.toString(),new TypeReference<T>(){});
			list.add(t);
		}
		return list;
	}

	
	public static void main(String[] args) {
		
		String str=  "[{\"a\":\"b\"}]";
		List<Map<String, List<String>>> list = parseJSON2List(str);
		System.out.println(list);
	}
	
}







