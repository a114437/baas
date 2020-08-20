package com.web.frame.service.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.system.ButtonDao;
import com.web.frame.dao.system.DicDao;
import com.web.frame.dao.system.RoleDao;
import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Dic;
import com.web.frame.entity.table.system.DicDetail;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;
import com.web.frame.util.Md5;
import com.web.frame.util.Uuid;

@Service
@Transactional
public class DicService {

	@Autowired
	private DicDao dicDao;

	public BoostrapTable<Dic> listDic(String page, String rows, String enName, String cnName) {
		
		int total = dicDao.queryDicCount(enName,cnName);
		List<Dic> list = new ArrayList<Dic>();
		if(total==0) {
			Dic dic = new Dic();
			dic.setEnName("暂无相关数据！");
			list.add(dic);
		}else {
			list = dicDao.queryDicList(page,rows,enName,cnName);
		}
		return new BoostrapTable<Dic>(total, list);
	}

	public void addDic(Dic dic) throws Exception {
		
		dicDao.addDic(dic);
	}

	public Dic queryDicById(String id) {
		
		return dicDao.queryDicById(id);
	}

	public void updateDic(Dic dic) throws Exception {
		
		dicDao.updateDic(dic);
	}

	public void deleteDic(String ids) {
		
		dicDao.deleteDic(ids);
	}

	public BoostrapTable<DicDetail> listDicItem(String page, String rows, String content, String dicVal,
			String groupId) {
		
		int total = dicDao.queryDicDetailCount(content,dicVal,groupId);
		List<DicDetail> list = new ArrayList<DicDetail>();
		if(total==0) {
			DicDetail dic = new DicDetail();
			dic.setContent("暂无相关数据！");
			list.add(dic);
		}else {
			list = dicDao.queryDicDetailList(page,rows,content,dicVal,groupId);
		}
		return new BoostrapTable<DicDetail>(total, list);
	}

	public void addDicItem(DicDetail dicDetail) throws Exception {
		
		dicDao.addDicDetail(dicDetail);
	}

	public DicDetail queryDicDetailById(String id) {
		
		return dicDao.queryDicDetailById(id);
	}

	public void updateDicItem(DicDetail dicDetail) throws Exception {

		dicDao.updateDicItem(dicDetail);
	}

	public void deleteDicItem(String ids) throws Exception {
	
		dicDao.deleteDicItem(ids);
	}


	
	
	
}

