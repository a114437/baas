package com.web.frame.dao.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.User;

/**
 * 登录
 * @author lj
 */
@Repository
public class LoginDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public List<User> queryUserByLoginName(String loginName) {

		String sql = "select * from sfb_userinfo where (login_name='"+loginName+"' or phone = '"+loginName+"') and is_delete = '0'";
		return jdbcBaseDao.query(sql,User.class);
	}
	
	
}
