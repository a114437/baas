package com.web.frame.dao.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.User;

/**
 * 用户管理
 * @author lj
 */
@Repository
public class UserInfoDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public int queryUserCount(String nickName, String phone, String loginName, String orgId) {
		
		String sql = "select t1.id from sfb_userinfo t1"
				+ " left join sfb_org t2 on t1.org_id = t2.id"
				+ " left join sfb_role t3 on t1.role_id = t3.id"
				+ " where t1.is_delete = '0' ";
		if(StringUtils.isNotEmpty(nickName)) {
			sql += " and t1.nick_name like '%"+nickName+"%'";
		}
		if(StringUtils.isNotEmpty(phone)) {
			sql += " and t1.phone = '"+phone+"'";
		}
		if(StringUtils.isNotEmpty(loginName)) {
			sql += " and t1.login_name = '"+loginName+"'";
		}
		if(StringUtils.isNotEmpty(orgId)) {
			sql += " and FIND_IN_SET('"+orgId+"',t2.pids)";
		}
		sql += " order by t1.create_date desc";
		return jdbcBaseDao.query(sql).size();
	}

	public List<User> queryUserList(String page, String rows, String nickName, String phone, String loginName, String orgId) {
		
		String sql = "select t1.*,t2.name as org_name,t3.role_name from sfb_userinfo t1"
				+ " left join sfb_org t2 on t1.org_id = t2.id"
				+ " left join sfb_role t3 on t1.role_id = t3.id"
				+ " where t1.is_delete = '0' ";
		if(StringUtils.isNotEmpty(nickName)) {
			sql += " and t1.nick_name like '%"+nickName+"%'";
		}
		if(StringUtils.isNotEmpty(phone)) {
			sql += " and t1.phone = '"+phone+"'";
		}
		if(StringUtils.isNotEmpty(loginName)) {
			sql += " and t1.login_name = '"+loginName+"'";
		}
		if(StringUtils.isNotEmpty(orgId)) {
			sql += " and FIND_IN_SET('"+orgId+"',t2.pids)";
		}
		sql += " order by t1.create_date desc";
		return jdbcBaseDao.query(sql, page, rows, User.class);
	}

	public void addUser(User user) throws Exception {
		
		jdbcBaseDao.insert(user);
	}

	public User queryUserById(String id) {
		
		String sql = "select * from sfb_userinfo where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, User.class);
	}

	public void updateUser(User user) throws Exception {
		
		jdbcBaseDao.update(user, "id");
	}

	public void deleteUser(String ids) {

		jdbcBaseDao.batchDeleteUpdate(ids, "sfb_userinfo", "id");
	}

	public User queryUserByLoginName(String loginName) {
		
		String sql = "select t1.id from sfb_userinfo t1"
				+ " left join sfb_org t2 on t1.org_id = t2.id"
				+ " left join sfb_role t3 on t1.role_id = t3.id"
				+ " where t1.is_delete = '0' ";
		if(StringUtils.isNotEmpty(loginName)) {
			sql += " and t1.login_name = '"+loginName+"'";
		}
		return jdbcBaseDao.queryUnique(sql, User.class);
	}

	public User queryUserByPhone(String phone) {
		
		String sql = "select t1.id from sfb_userinfo t1"
				+ " left join sfb_org t2 on t1.org_id = t2.id"
				+ " left join sfb_role t3 on t1.role_id = t3.id"
				+ " where t1.is_delete = '0' ";
		if(StringUtils.isNotEmpty(phone)) {
			sql += " and t1.phone = '"+phone+"'";
		}
		return jdbcBaseDao.queryUnique(sql, User.class);
	}

	
}
