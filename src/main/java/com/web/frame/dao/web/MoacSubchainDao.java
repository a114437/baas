package com.web.frame.dao.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.web.Scs;
import com.web.frame.entity.table.web.Subchain;
import com.web.frame.entity.table.web.Vnode;

@Repository
public class MoacSubchainDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public void addSubchain(Subchain subchain) throws Exception {
		
		jdbcBaseDao.insert(subchain);
	}

	public void addScs(Scs scs) throws Exception {
		
		jdbcBaseDao.insert(scs);
	}

	public void addVnode(Vnode vnode) throws Exception {
		
		jdbcBaseDao.insert(vnode);
	}

	public void updateSubchain(Subchain subchain) throws Exception {
		
		jdbcBaseDao.update(subchain, "id");
	}

	public Subchain querySubchainById(String subchainId) {
		
		String sql = "SELECT * FROM sfb_subchain WHERE id = '"+subchainId+"'";
		return jdbcBaseDao.queryUnique(sql, Subchain.class);
	}

	public List<Vnode> queryVnodeBySubchainId(String subchainId) {
		
		String sql = "SELECT * FROM sfb_vnode WHERE subchain_id = '"+subchainId+"'";
		return jdbcBaseDao.query(sql, Vnode.class);
	}

	public List<Scs> queryScsBySubchainId(String subchainId,String scsMonitor) {
		
		String sql = "SELECT * FROM sfb_scs WHERE subchain_id = '"+subchainId+"' ";
		if(StringUtils.isNotEmpty(scsMonitor)) {
			sql+=" and scs_monitor = '"+scsMonitor+"'";
		}
		sql+="  ORDER BY create_date DESC";
		return jdbcBaseDao.query(sql, Scs.class);
	}

	public Scs queryScsByScsId(String monitorScsId) {
		
		String sql = "SELECT * FROM sfb_scs WHERE id = '"+monitorScsId+"'";
		return jdbcBaseDao.queryUnique(sql, Scs.class);
	}





	
}
