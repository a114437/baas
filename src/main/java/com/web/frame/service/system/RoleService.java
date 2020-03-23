package com.web.frame.service.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.system.RoleDao;
import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Resource;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;
import com.web.frame.util.Md5;
import com.web.frame.util.Uuid;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	public BoostrapTable<Role> listRole(String page, String rows, String roleName, String orgId) {
		
		int total = roleDao.queryRoleCount(roleName,orgId);
		List<Role> list = new ArrayList<Role>();
		if(total==0) {
			Role role = new Role();
			role.setRoleName("暂无相关数据！");
			list.add(role);
		}else {
			list = roleDao.queryRoleList(page,rows,roleName,orgId);
		}
		return new BoostrapTable<Role>(total, list);
	}

	public void addRole(Role role) throws Exception {
		
		roleDao.addRole(role);
	}

	public Role queryRoleById(String id) {
		
		return roleDao.queryRoleById(id);
	}

	public void updateRole(Role role) throws Exception {
		
		roleDao.updateRole(role);
	}

	public void deleteRole(String ids) {
		
		roleDao.deleteRole(ids);
	}

	public String getMenuDistribute(String id) {
		
		String result = "";
		List<Resource> list = roleDao.getMenuDistribute(id);
		for(int i = 0; i < list.size();i++){
			result+=list.get(i).getResourceId()+",";
		}
		if(!"".equals(result)){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}

	public void relateMenu(String roleId, String addIds, String delIds) throws Exception {
		
		List<Resource> addList = new ArrayList<Resource>();
		String[] addArr = addIds.split(",");
		String[] delArr = delIds.split(",");
		for (int i = 0; i < addArr.length; i++) {
			Resource resource = new Resource();
			resource.setResourceId(addArr[i]);
			resource.setResourceType("0");
			resource.setRoleId(roleId);
			addList.add(resource);
		}
		roleDao.addResource(addList);//新增资源
		
		for (int i = 0; i < delArr.length; i++) {
			roleDao.deleteResource(delArr[i],"0",roleId);//删除资源
		}
		
	}

	public String getBtnDistribute(String id) {
		
		String result = "";
		List<Resource> list = roleDao.getBtnDistribute(id);
		for (int i = 0; i < list.size(); i++) {
			result+=list.get(i).getResourceId()+",";
		}
		if(!"".equals(result)){
			result = result.substring(0, result.length()-1);
		}
 		return result;
	}

	public void relateBtn(String roleId, String addIds, String delIds) throws Exception {
		
		List<Resource> addList = new ArrayList<Resource>();
		String[] addArr = addIds.split(",");
		String[] delArr = delIds.split(",");
		for (int i = 0; i < addArr.length; i++) {
			Resource resource = new Resource();
			resource.setResourceId(addArr[i]);
			resource.setResourceType("1");
			resource.setRoleId(roleId);
			addList.add(resource);
		}
		roleDao.addResource(addList);//新增资源
		
		for (int i = 0; i < delArr.length; i++) {
			roleDao.deleteResource(delArr[i],"1",roleId);//删除资源
		}
	}

}

