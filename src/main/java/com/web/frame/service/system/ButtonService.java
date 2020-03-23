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
import com.web.frame.dao.system.RoleDao;
import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;
import com.web.frame.util.Md5;
import com.web.frame.util.Uuid;

@Service
@Transactional
public class ButtonService {

	@Autowired
	private ButtonDao buttonDao;

	public BoostrapTable<Button> listButton(String page, String rows, String btnCnName,
			String btnEnName, String treeId) {
		
		int total = buttonDao.queryButtonCount(btnCnName,btnEnName,treeId);
		List<Button> list = new ArrayList<Button>();
		if(total==0) {
			Button button = new Button();
			button.setBtnCnName("暂无相关数据！");
			list.add(button);
		}else {
			list = buttonDao.queryButtonList(page,rows,btnCnName,btnEnName,treeId);
		}
		return new BoostrapTable<Button>(total, list);
	}

	public void addButton(Button button) throws Exception {
		
		buttonDao.addButton(button);
	}

	public Button queryButtonById(String id) {
		
		return buttonDao.queryButtonById(id);
	}

	public void updateButton(Button button) throws Exception {
		
		buttonDao.updateButton(button);
	}

	public void deleteButton(String ids) {
		
		buttonDao.deleteButton(ids);
	}

	public BoostrapTable<Button> listButtonUser(String page, String rows, String treeId, String roleId) {
		
		int total = buttonDao.queryButtonUserCount(treeId,roleId);
		List<Button> list = new ArrayList<Button>();
		if(total==0) {
			Button button = new Button();
			button.setBtnCnName("暂无相关数据！");
			list.add(button);
		}else {
			list = buttonDao.queryButtonUserList(page,rows,treeId,roleId);
		}
		return new BoostrapTable<Button>(total, list);
	}


	
	
	
}

