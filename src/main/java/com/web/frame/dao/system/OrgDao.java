package com.web.frame.dao.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.Org;

@Repository
public class OrgDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public List<Org> initOrg() {
		
		String sql = "select * from sfb_org where is_delete = '0' order by remark+0 asc";
		return jdbcBaseDao.query(sql, Org.class);
	}

	public void addOrg(Org org) throws Exception {
		
		jdbcBaseDao.insert(org);
	}

	public Org queryOrgById(String id) {
		
		String sql = "select * from sfb_org where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, Org.class);
	}

	public void updateOrg(Org org) throws Exception {
		
		jdbcBaseDao.update(org, "id");;
	}

	public void deleteOrg(String id) {
		
		jdbcBaseDao.batchDeleteUpdate(id, "sfb_org", "id");
	}

	public List<Org> queryOrgUser(String orgId) {
		
		String sql = "select * from sfb_org where FIND_IN_SET('"+orgId+"',pids)"
				+ " and is_delete='0' order by remark+0 asc";
		return jdbcBaseDao.query(sql, Org.class);
	}

	
}
