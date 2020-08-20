package com.web.frame.dao.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;

/**
 * 按钮
 * @author lj
 */
@Repository
public class ButtonDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public int queryButtonCount(String btnCnName, String btnEnName, String treeId) {
		
		String sql = "select * from sfb_button where is_delete = '0' and tree_id = '"+treeId+"' ";
		if(StringUtils.isNotEmpty(btnCnName)) {
			sql+=" and btn_cn_name like '%"+btnCnName+"%'";
		}
		if(StringUtils.isNotEmpty(btnEnName)) {
			sql+=" and btn_en_name like '%"+btnEnName+"%'";
		}
		sql+=" order by create_date desc";
		return jdbcBaseDao.query(sql).size();
	}

	public List<Button> queryButtonList(String page, String rows, String btnCnName, String btnEnName, String treeId) {
		
		String sql = "select * from sfb_button where is_delete = '0' and tree_id = '"+treeId+"' ";
		if(StringUtils.isNotEmpty(btnCnName)) {
			sql+=" and btn_cn_name like '%"+btnCnName+"%'";
		}
		if(StringUtils.isNotEmpty(btnEnName)) {
			sql+=" and btn_en_name like '%"+btnEnName+"%'";
		}
		sql+=" order by create_date desc";
		return jdbcBaseDao.query(sql, page, rows, Button.class);
	}

	public void addButton(Button button) throws Exception {
		
		jdbcBaseDao.insert(button);
	}

	public Button queryButtonById(String id) {
		
		String sql = "select * from sfb_button where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, Button.class);
	}

	public void updateButton(Button button) throws Exception {
		
		jdbcBaseDao.update(button, "id");
	}

	public void deleteButton(String ids) {
		
		jdbcBaseDao.batchDeleteUpdate(ids, "sfb_button", "id");
	}

	public int queryButtonUserCount(String treeId, String roleId) {
		
		String sql = "select * from sfb_button"
				+ " where id in (select resource_id from sfb_resource where role_id = '"+roleId+"')"
				+ " and tree_id = '"+treeId+"' and is_delete = '0'";
		return jdbcBaseDao.query(sql).size();
	}

	public List<Button> queryButtonUserList(String page, String rows, String treeId, String roleId) {
		
		String sql = "select * from sfb_button"
				+ " where id in (select resource_id from sfb_resource where role_id = '"+roleId+"')"
				+ " and tree_id = '"+treeId+"' and is_delete = '0'";
		return jdbcBaseDao.query(sql, Button.class);
	}


	
}
