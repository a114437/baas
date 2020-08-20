package com.web.frame.web.interceptor;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * @author wangshubin
 * @category 定义一个servlet。
 * 供一个 getSession() 的 static 方法，以供外界随时获取 Session
 * ThreadLocal是一个依附于本地线程的变量，按照我的理解，每次对服务器请求除了 index.jsp、
 * 以及图片视频动画资源,js,css等以外都主动创建session，都会使用到一个线程，
 * ThreadLocal的作用就是在这个线程的使用过程中只为这个线程所用。
 * 通过从request中取得的session赋予 sessionContext工具类操作session的能力
 */
@SuppressWarnings("serial")
@WebListener
public class SessionServlet implements ServletRequestListener    {
	private Logger logger = Logger.getLogger(SessionServlet.class);
	 private static final ThreadLocal<HttpSession> sessionContainer = new ThreadLocal<HttpSession>();
	 /**
	  * 通过实现sessionCreated方法  获取 Session，并放入 ThreadLocal 中。
	  */

	 public static HttpSession getSession() {
	        return sessionContainer.get();
	    }
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request =(HttpServletRequest) event.getServletRequest();
		String uri=request.getRequestURI();
		String [] source = new String[]{".css",".js",".png",".jpeg",".png","jpg","ico","bmp",".psd",".gif",".swf",".avi",".mp3",".mp4",".avi","index.jsp"};
		for (String str : source) {
			if(uri.indexOf(str)==-1){
				HttpSession session= request.getSession();
				   sessionContainer.set(session);
			}
		}
		
	}
	
	
	
	 
}
