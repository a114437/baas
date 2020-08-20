package com.web.frame.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.table.system.Tree;
import com.web.frame.entity.table.system.User;
import com.web.frame.service.system.UserInfoService;
import com.web.frame.util.Md5;
import com.web.frame.util.SessionContext;

@Controller
@RequestMapping("user")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ResponseBase<Object> responseBase;
	
	@RequestMapping("loadUser")
	public String loadUser() {
		
		return "system/user/userList";
	}
	
	@RequestMapping("listUser")
	@ResponseBody
	public BoostrapTable<User> listUser(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String rows,
			String nickName,String phone,String loginName){
		
		User user = SessionContext.get("user");
		String orgId = user.getOrgId();
		BoostrapTable<User> listUser = userInfoService.listUser(page,rows,nickName,phone,loginName,orgId);
		return listUser;
	}
	
	@RequestMapping("loadAddUser")
	public String loadAddUser() {
		
		return "system/user/userAdd";
	}
	
	
	@RequestMapping("addUser")
	public ResponseEntity addUser(User user) {
		
		try {
			User query = userInfoService.queryUserByLoginName(user.getLoginName());
			if(query!=null) {
				return responseBase.createResponse(false, "登录账号已存在", "", HttpStatus.OK);
			}
			query = userInfoService.queryUserByPhone(user.getPhone());
			if(query!=null) {
				return responseBase.createResponse(false, "手机号已存在", "", HttpStatus.OK);
			}
			userInfoService.addUser(user);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "新增失败", "", HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("loadUpdateUser")
	public String loadUpdateUser(HttpServletRequest request,String id) {
		
		User user = userInfoService.queryUserById(id);
		if(user == null) {
			user = new User();
		}else {
			try {
				if(StringUtils.isNotEmpty(user.getLoginPwd()))
				user.setLoginPwd(Md5.decrypt(user.getLoginPwd()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("user", user);
		return "system/user/userUpdate";
	}
	
	@RequestMapping("updateUser")
	public ResponseEntity updateUser(User user) {
		
		try {
			if(StringUtils.isNotEmpty(user.getLoginName())) {
				User query = userInfoService.queryUserByLoginName(user.getLoginName());
				if(query!=null&&!query.getId().equals(user.getId())) {
					return responseBase.createResponse(false, "登录账号已存在", "", HttpStatus.OK);
				}
			}
			User query = userInfoService.queryUserByPhone(user.getPhone());
			if(query!=null&&!query.getId().equals(user.getId())) {
				return responseBase.createResponse(false, "手机号已存在", "", HttpStatus.OK);
			}
			userInfoService.updateUser(user);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "修改失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("deleteUser")
	public ResponseEntity deleteUser(String ids) {
		
		try {
			userInfoService.deleteUser(ids);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("loadDistributeRole")
	public String loadDistributeRole(HttpServletRequest request,String id) {
		
		User user = userInfoService.queryUserById(id);
		request.setAttribute("user", user);
		return "system/user/distributeRole";
	}
	
	@RequestMapping("loadUpdatePsw")
	public String loadUpdatePsw(HttpServletRequest request,String id) {
		
		return "system/user/updatePassword";
	}
	
	@RequestMapping("updatePassword")
	public ResponseEntity<ResponseBase<String>> updatePassword(HttpServletRequest request,String userId,String oldPsw,String newPsw) {
		
		try {
			return userInfoService.updatePassword(userId, oldPsw, newPsw);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBase.createResponse(false, "修改失败（程序异常）", "", HttpStatus.OK);
		}
	}
}
