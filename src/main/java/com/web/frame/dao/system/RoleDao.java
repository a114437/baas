package com.web.frame.dao.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Resource;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;

/**
 * 角色管理
 * @author lj
 */
@Repository
public class RoleDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public int queryRoleCount(String roleName, String orgId) {
		
		String sql = "select * from sfb_role WHERE is_delete = '0' and org_id = '"+orgId+"'";
		if(StringUtils.isNotEmpty(roleName)) {
			sql+=" and role_name like '%"+roleName+"%'";
		}
		sql+=" order by create_date desc";
		return jdbcBaseDao.query(sql).size();
	}

	public List<Role> queryRoleList(String page, String rows, String roleName, String orgId) {
		
		String sql = "select * from sfb_role WHERE is_delete = '0' and org_id = '"+orgId+"'";
		if(StringUtils.isNotEmpty(roleName)) {
			sql+=" and role_name like '%"+roleName+"%'";
		}
		sql+=" order by create_date desc";
		return jdbcBaseDao.query(sql, page, rows, Role.class);
	}

	public void addRole(Role role) throws Exception {
		
		jdbcBaseDao.insert(role);
	}

	public Role queryRoleById(String id) {
		
		String sql = "select * from sfb_role where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, Role.class);
	}

	public void updateRole(Role role) throws Exception {
		
		jdbcBaseDao.update(role, "id");
	}

	public void deleteRole(String ids) {
		
		jdbcBaseDao.batchDeleteUpdate(ids, "sfb_role", "id");
	}

	public List<Resource> getMenuDistribute(String id) {
		
		String sql = "select * from sfb_resource where is_delete='0'"
				+ " and resource_type = '0' and role_id = '"+id+"'";
		return jdbcBaseDao.query(sql,Resource.class);
	}

	public void addResource(List<Resource> addList) throws Exception {
		
		jdbcBaseDao.batchInsert(addList);
	}

	public void deleteResource(String resourceId, String resourceType, String roleId) {
		
		String sql = "delete from sfb_resource where resource_id = '"+resourceId+"'"
				+ " and resource_type = '"+resourceType+"' and role_id = '"+roleId+"'";
		jdbcBaseDao.execute(sql);
	}

	public List<Resource> getBtnDistribute(String id) {
		
		String sql = "select * from sfb_resource where is_delete='0'"
				+ " and resource_type = '1' and role_id = '"+id+"'";
		return jdbcBaseDao.query(sql, Resource.class);
	}

	public List<Button> queryButtonResource(String roleId){
		
		String sql = "select * from sfb_button where id in "
				+ "(select resource_id from sfb_resource where is_delete='0'"  
				+" and resource_type = '1' and role_id = '"+roleId+"')";
		return jdbcBaseDao.query(sql, Button.class);
	}
	
}
