package com.web.frame.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.frame.service.system.LoginService;
import com.web.frame.util.SessionContext;
import com.web.frame.util.Sign;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,HttpServletResponse response,String flag) {
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			String loginName = "";
			for (int i = 0; i < cookies.length; i++) {
				if("loginName".equals(cookies[i].getName())) {
					loginName = cookies[i].getValue();
				}
			}
			if(StringUtils.isNotEmpty(loginName)&&!StringUtils.isNotEmpty(flag)) {
				return "redirect:/main";
			}
		}
		request.setAttribute("flag", flag);
		return "login";
	}
	
	@RequestMapping("main")
	public String main(HttpServletRequest request,HttpServletResponse response,
			HttpSession session,String loginName,String password,String memberPass,String flag){
		
		if(!StringUtils.isNotEmpty(flag)) {
			String cookieLoginRes = loginService.loginByCookie(request,response);//cookie登录
			if("true".equals(cookieLoginRes)){
				return "home";
			}
		}
		String result = loginService.queryUser(loginName,password,session,response,memberPass,request);
		if("true".equals(result)){
			return "home";
		}else{
			request.setAttribute("error", result);
			request.setAttribute("flag", flag);
			return "login";
		}
	}
	
	@RequestMapping("exit")
	public String exit(HttpServletRequest request,HttpServletResponse response) {
		
		return "redirect:/login?flag=1";
	}
}
