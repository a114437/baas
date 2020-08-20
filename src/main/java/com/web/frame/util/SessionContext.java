package com.web.frame.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.web.frame.web.interceptor.SessionServlet;

/**
 * 
 * @author wangshubin
 * @category HttpSession的代理类 封装了session的常用方法。
 */
@Scope("prototype")
@Component
public class SessionContext {
	/**
	 * session添加元素
	 */
	public static void put(String key, Object value) {
        getSession().setAttribute(key, value);
    }
	/**
	 * session获取元素
	 */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) getSession().getAttribute(key);
    }
	/**
	 * session删除
	 */
    public static void remove(String key) {
        getSession().removeAttribute(key);
    }
	/**
	 * 获取session全部内容
	 */
    public static Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> names = getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, getSession().getAttribute(name));
        }
        return map;
    }
	/**
	 * session注销
	 */
    public static void removeAll() {
        getSession().invalidate();
    }
	/**
	 * 外部获取session方法
	 */
    private static HttpSession getSession() {
        return SessionServlet.getSession();
    }
}
