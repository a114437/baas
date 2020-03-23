package com.web.frame.dao.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.Tree;

@Repository
public class TreeDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public List<Tree> initTree() {
		
		String sql = "select * from sfb_tree where is_delete = '0' order by remark+0 asc";
		return jdbcBaseDao.query(sql, Tree.class);
	}

	public void addTree(Tree tree) throws Exception {
		
		jdbcBaseDao.insert(tree);
	}

	public Tree queryTreeById(String id) {
		
		String sql = "select * from sfb_tree where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, Tree.class);
	}

	public void updateTree(Tree tree) throws Exception {
		
		jdbcBaseDao.update(tree, "id");;
	}

	public void deleteTree(String id) {
		
		jdbcBaseDao.batchDeleteUpdate(id, "sfb_tree", "id");
	}

	public List<Tree> queryTreeUser(String roleId) {
		
		String sql = "select * from sfb_tree"
				+ " where id in (select resource_id from sfb_resource where role_id = '"+roleId+"')"
				+ " and is_delete = '0'";
		return jdbcBaseDao.query(sql, Tree.class);
	}

	
}
