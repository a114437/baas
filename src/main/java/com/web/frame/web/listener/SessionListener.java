package com.web.frame.web.listener;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener{
	
    public static HashMap<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();//后台用户session
    public static HashMap<String,HttpSession> webSessionMap = new HashMap<String,HttpSession>();//前台用户session
    
    
     public synchronized void delSession(HttpSession session) {
                if (session != null) {
                       // 删除单一登录中记录的变量
                        if(session.getAttribute("userInfo")!=null){//移除后台用户session
                        	Map tu =  (Map)session.getAttribute("userInfo");
                            SessionListener.sessionMap.remove(tu.get("ID"));     
                        }
                        
                        if(session.getAttribute("webUser")!=null){//移除前台用户session
                        	Map tu =  (Map)session.getAttribute("webUser");
                        	SessionListener.webSessionMap.remove(tu.get("ID"));  
                        }
                        
                }
    }

 	/**
  	 * 踢出前台用户
  	 * @param uid
  	 */
  	public static void forceLogoutWebUser(String uid) {
          // 删除单一登录中记录的变量
          if (SessionListener.webSessionMap.get(uid) != null) {
          		 HttpSession hs = (HttpSession) SessionListener.webSessionMap.get(uid);
          		 
          		 if(hs!=null){
          			 SessionListener.webSessionMap.remove(uid);
          			 Enumeration e = hs.getAttributeNames();
          			 while (e.hasMoreElements()) {
          				 String sessionName = (String) e.nextElement();
          				 // 清空session
          				 hs.removeAttribute(sessionName);
          			 }
//          			 hs.invalidate();
          		 }
          }
    }
     
 	/**
 	 * 踢出后台用户
 	 * @param uid
 	 */
 	public static void forceLogoutUser(String uid) {
         // 删除单一登录中记录的变量
         if (SessionListener.sessionMap.get(uid) != null) {
         		 HttpSession hs = (HttpSession) SessionListener.sessionMap.get(uid);
                 if(hs!=null){
                	 SessionListener.sessionMap.remove(uid);
                     Enumeration e = hs.getAttributeNames();
                     while (e.hasMoreElements()) {
                              String sessionName = (String) e.nextElement();
                              // 清空session
                              hs.removeAttribute(sessionName);
                     }
//                     hs.invalidate();
                 }
         }
     }

	public void sessionCreated(HttpSessionEvent se) {
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		
		HttpSession session = se.getSession();
		
		Iterator<String> sessionIterate = sessionMap.keySet().iterator();
		while(sessionIterate.hasNext()){
		   String key=sessionIterate.next();
		   HttpSession value = sessionMap.get(key);
		   if(session == value){
			   sessionMap.remove(key);
			   break;
		   }
		}
		
		Iterator<String> webSessionIterate = webSessionMap.keySet().iterator();
		while(webSessionIterate.hasNext()){
		   String key=webSessionIterate.next();
		   HttpSession value = webSessionMap.get(key);
		   if(session == value){
			   webSessionMap.remove(key);
			   break;
		   }
		}
		
	}
}