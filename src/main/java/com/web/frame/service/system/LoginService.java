package com.web.frame.service.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.web.frame.dao.system.LoginDao;
import com.web.frame.dao.system.RoleDao;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.User;
import com.web.frame.util.JsonFormat;
import com.web.frame.util.Md5;
import com.web.frame.util.SessionContext;

/**
 * 登录
 * */
@Service
@Transactional
public class LoginService {

	@Autowired 
	private JsonFormat jsonFormat;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private RoleDao roleDao;
	
	
	public String queryUser(String loginName, String password,HttpSession session,
			HttpServletResponse response, String memberPass, HttpServletRequest request) {

		if(!StringUtils.isNotEmpty(loginName)&&!StringUtils.isNotEmpty(password)){
			return "";
		}
		List<User> list = loginDao.queryUserByLoginName(loginName);
		if(list.size() == 0){
			return "账号不存在";
		}
		User user = list.get(0);
		String savePsw = user.getLoginPwd();
		if(!StringUtils.isNotEmpty(savePsw)){
			return "密码不正确";
		}
		try {
			savePsw = Md5.decrypt(savePsw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(savePsw.equals(password)){
			SessionContext.put("user", user);
			List<Button> button = roleDao.queryButtonResource(user.getRoleId());
			List<String> btnResource = new ArrayList<String>();
			for (int i = 0; i < button.size(); i++) {
				btnResource.add(button.get(i).getBtnEnName());
			}
			SessionContext.put("btnResource", btnResource);
			
			if(StringUtils.isNotEmpty(memberPass)) {
				Cookie nameCookie = new Cookie("loginName", loginName);
				nameCookie.setMaxAge(1*24*60*60); //设置有效时间1天
	            nameCookie.setPath(request.getContextPath());  // 设置cookie有效路径
	            response.addCookie(nameCookie);
	            
	            Cookie pswCookie = new Cookie("password", password);
	            pswCookie.setMaxAge(1*24*60*60); //设置有效时间1天
	            pswCookie.setPath(request.getContextPath());  // 设置cookie有效路径
	            response.addCookie(pswCookie);
			}else {
				Cookie nameCookie = new Cookie("loginName", null);
				nameCookie.setMaxAge(0); //删除
	            nameCookie.setPath(request.getContextPath());  // 设置cookie有效路径
	            response.addCookie(nameCookie);
	            
	            Cookie pswCookie = new Cookie("password", null);
	            pswCookie.setMaxAge(0); //删除
	            pswCookie.setPath(request.getContextPath());  // 设置cookie有效路径
	            response.addCookie(pswCookie);
			}
			
			return "true";
		}else{
			return "密码不正确";
		}
	}


	public String loginByCookie(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies();
		if(cookies==null) {
			return "";
		}
		String loginName = "";
		String password = "";
		for (int i = 0; i < cookies.length; i++) {
			String name = cookies[i].getName();
			if("loginName".equals(name)) {
				loginName = cookies[i].getValue();
				cookies[i].setMaxAge(0);
				cookies[i].setValue(null);
				response.addCookie(cookies[i]);
			}
			if("password".equals(name)) {
				password = cookies[i].getValue();
				cookies[i].setMaxAge(0);
				cookies[i].setValue(null);
				response.addCookie(cookies[i]);
			}
		}
		
		
		if(!StringUtils.isNotEmpty(loginName)&&!StringUtils.isNotEmpty(password)){
			return "";
		}
		List<User> list = loginDao.queryUserByLoginName(loginName);
		if(list.size() == 0){
			return "账号不存在";
		}
		User user = list.get(0);
		String savePsw = user.getLoginPwd();
		if(!StringUtils.isNotEmpty(savePsw)){
			return "密码不正确";
		}
		try {
			savePsw = Md5.decrypt(savePsw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(savePsw.equals(password)){
			SessionContext.put("user", user);
			List<Button> button = roleDao.queryButtonResource(user.getRoleId());
			List<String> btnResource = new ArrayList<String>();
			for (int i = 0; i < button.size(); i++) {
				btnResource.add(button.get(i).getBtnEnName());
			}
			SessionContext.put("btnResource", btnResource);
			return "true";
		}else{
			return "密码不正确";
		}
	}
	
	
	
	
}

