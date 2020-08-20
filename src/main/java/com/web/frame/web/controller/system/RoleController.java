package com.web.frame.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.table.system.Button;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.User;
import com.web.frame.service.system.RoleService;
import com.web.frame.util.Md5;
import com.web.frame.util.SessionContext;

@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private ResponseBase<Object> responseBase;
	
	@RequestMapping("loadRole")
	public String loadRole() {
		
		return "system/role/roleList";
	}
	
	@RequestMapping("listRole")
	@ResponseBody
	public BoostrapTable<Role> listRole(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String rows,
			String roleName,String orgId){
		
		BoostrapTable<Role> listRole = roleService.listRole(page,rows,roleName,orgId);
		return listRole;
	}
	
	@RequestMapping("loadAddRole")
	public String loadAddRole(HttpServletRequest request,HttpServletResponse response,String orgId) {
		
		request.setAttribute("orgId", orgId);
		return "system/role/roleAdd";
	}
	
	
	@RequestMapping("addRole")
	public ResponseEntity addRole(Role role) {
		
		try {
			roleService.addRole(role);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "新增失败", "", HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("loadUpdateRole")
	public String loadUpdateRole(HttpServletRequest request,String id) {
		
		Role role = roleService.queryRoleById(id);
		request.setAttribute("role", role);
		return "system/role/roleUpdate";
	}
	
	@RequestMapping("updateRole")
	public ResponseEntity updateRole(Role role) {
		
		try {
			roleService.updateRole(role);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "修改失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("deleteRole")
	public ResponseEntity deleteRole(String ids) {
		
		try {
			roleService.deleteRole(ids);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("loadMenuDistribute")
	public String loadMenuDistribute(HttpServletRequest request,HttpServletResponse response,String id) {
		
		String result = roleService.getMenuDistribute(id);
		request.setAttribute("result", result);
		return "system/role/menuDistribution";
	}
	
	@RequestMapping("relateMenu")
	public ResponseEntity relateMenu(String roleId,String addIds,String delIds) {
		
		try {
			roleService.relateMenu(roleId,addIds,delIds);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "分配失败", "", HttpStatus.OK);
		}
	} 
	
	@RequestMapping("loadBtnDistribute")
	public String loadBtnDistribute(HttpServletRequest request,String id){
		
		String result = roleService.getBtnDistribute(id);
		request.setAttribute("result", result);
		return "system/role/buttonDistribution";
	}
	
	@RequestMapping("relateBtn")
	public ResponseEntity relateBtn(String roleId,String addIds,String delIds) {
		
		try {
			roleService.relateBtn(roleId,addIds,delIds);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "分配失败", "", HttpStatus.OK);
		}
	} 
	
}
