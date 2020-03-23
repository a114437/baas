package com.web.frame.service.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.system.UserInfoDao;
import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.table.system.User;
import com.web.frame.util.Md5;
import com.web.frame.util.Uuid;

/**
 * 用户管理
 * */
@Service
@Transactional
public class UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	public BoostrapTable<User> listUser(String page, String rows, String nickName, String phone, String loginName, String orgId) {
		
		int total = userInfoDao.queryUserCount(nickName,phone,loginName,orgId);
		List<User> list = new ArrayList<User>();
		if(total==0) {
			User userData = new User();
			userData.setNickName("暂无相关数据！");
			userData.setCreateDate("");
			list.add(userData);
		}else {
			list = userInfoDao.queryUserList(page,rows,nickName,phone,loginName,orgId);
		}
		return new BoostrapTable<User>(total, list);
	}

	public void addUser(User user) throws Exception {
		
		user.setId(Uuid.getUuid());
		user.setLoginPwd(Md5.encrypt(user.getLoginPwd()));
		user.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		userInfoDao.addUser(user);
	}

	public int queryUser(String loginName) {
		
		return userInfoDao.queryUserCount("", "", loginName,"");
	}
	
	public User queryUserByLoginName(String loginName) {
		
		return userInfoDao.queryUserByLoginName(loginName);
	}
	
	public User queryUserByPhone(String phone) {
		
		return userInfoDao.queryUserByPhone(phone);
	}

	public User queryUserById(String id) {
		
		return userInfoDao.queryUserById(id);
	}

	public void updateUser(User user) throws Exception {
		
		if(StringUtils.isNotEmpty(user.getLoginPwd())) {
			user.setLoginPwd(Md5.encrypt(user.getLoginPwd()));
		}
		userInfoDao.updateUser(user);
	}

	public void deleteUser(String ids) {
		
		userInfoDao.deleteUser(ids);
	}


	public ResponseEntity<ResponseBase<String>> updatePassword(String userId, String oldPsw, String newPsw) throws Exception {
		
		User user = userInfoDao.queryUserById(userId);
		if(user!=null){
			String password = user.getLoginPwd();
			password = Md5.decrypt(password);
			if(!oldPsw.equals(password)){
				return ResponseBase.createResponse(false, "原密码不正确", "", HttpStatus.OK);
			}
			newPsw = Md5.encrypt(newPsw);
			user.setLoginPwd(newPsw);
			userInfoDao.updateUser(user);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		}else{
			return ResponseBase.createResponse(false, "未查询到用户", "", HttpStatus.OK);
		}
		
	}
	
	
}

