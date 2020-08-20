package com.web.frame.dao.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Dic;
import com.web.frame.entity.table.system.DicDetail;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;

/**
 * 数据字典
 * @author lj
 */
@Repository
public class DicDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public int queryDicCount(String enName, String cnName) {
		
		String sql = "select * from sfb_dic where is_delete = '0'";
		if(StringUtils.isNotEmpty(enName)) {
			sql+=" and en_name like '%"+enName+"%'";
		}
		if(StringUtils.isNotEmpty(cnName)) {
			sql+=" and cn_name like '%"+cnName+"%'";
		}
		sql+=" order by create_date desc";
		return jdbcBaseDao.query(sql).size();
	}

	public List<Dic> queryDicList(String page, String rows, String enName, String cnName) {
		
		String sql = "select * from sfb_dic where is_delete = '0'";
		if(StringUtils.isNotEmpty(enName)) {
			sql+=" and en_name like '%"+enName+"%'";
		}
		if(StringUtils.isNotEmpty(cnName)) {
			sql+=" and cn_name like '%"+cnName+"%'";
		}
		sql+=" order by create_date desc";
		return jdbcBaseDao.query(sql, page, rows,Dic.class);
	}

	public void addDic(Dic dic) throws Exception {
		
		jdbcBaseDao.insert(dic);
	}

	public Dic queryDicById(String id) {
		
		String sql = "select * from sfb_dic where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, Dic.class);
	}

	public void updateDic(Dic dic) throws Exception {
		
		jdbcBaseDao.update(dic, "id");
	}

	public void deleteDic(String ids) {

		jdbcBaseDao.batchDelete(ids, "sfb_dic", "id");
	}

	public int queryDicDetailCount(String content, String dicVal, String groupId) {
		
		String sql = "select * from sfb_dic_detail where is_delete = '0'";
		if(StringUtils.isNotEmpty(groupId)) {
			sql+=" and dic_id = '"+groupId+"' ";
		}
		if(StringUtils.isNotEmpty(content)) {
			sql+=" and content like '%"+content+"%' ";
		}
		if(StringUtils.isNotEmpty(dicVal)) {
			sql+=" and dic_val like '%"+dicVal+"%' ";
		}
		sql+="order by create_date desc";
		return jdbcBaseDao.query(sql).size();
	}

	public List<DicDetail> queryDicDetailList(String page, String rows, String content, String dicVal, String groupId) {
		
		String sql = "select * from sfb_dic_detail where is_delete = '0'";
		if(StringUtils.isNotEmpty(groupId)) {
			sql+=" and dic_id = '"+groupId+"' ";
		}
		if(StringUtils.isNotEmpty(content)) {
			sql+=" and content like '%"+content+"%' ";
		}
		if(StringUtils.isNotEmpty(dicVal)) {
			sql+=" and dic_val like '%"+dicVal+"%' ";
		}
		sql+="order by create_date desc";
		return jdbcBaseDao.query(sql, page, rows, DicDetail.class);
	}

	public void addDicDetail(DicDetail dicDetail) throws Exception {

		jdbcBaseDao.insert(dicDetail);
	}

	public DicDetail queryDicDetailById(String id) {
		
		String sql = "select * from sfb_dic_detail where id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, DicDetail.class);
	}

	public void updateDicItem(DicDetail dicDetail) throws Exception {
		
		jdbcBaseDao.update(dicDetail, "id");
	}

	public void deleteDicItem(String ids) {
		
		jdbcBaseDao.batchDelete(ids, "sfb_dic_detail", "id");
	}


	
}
