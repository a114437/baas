package com.web.frame.web.controller.system;

import java.util.List;

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
import com.web.frame.entity.table.system.Tree;
import com.web.frame.entity.table.system.User;
import com.web.frame.service.system.ButtonService;
import com.web.frame.service.system.RoleService;
import com.web.frame.util.Md5;
import com.web.frame.util.SessionContext;

@Controller
@RequestMapping("button")
public class ButtonController {

	@Autowired
	private ButtonService buttonService;
	@Autowired
	private ResponseBase<Object> responseBase;
	
	@RequestMapping("loadButton")
	public String loadButton(HttpServletRequest request,HttpServletResponse response,
			String treeId) {
		
		request.setAttribute("treeId", treeId);
		return "system/button/buttonList";
	}
	
	@RequestMapping("listButton")
	@ResponseBody
	public BoostrapTable<Button> listButton(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String rows,
			String btnCnName,String btnEnName,String treeId){
		
		BoostrapTable<Button> listButton = buttonService.listButton(page,rows,btnCnName,btnEnName,treeId);
		return listButton;
	}
	
	@RequestMapping("listButtonUser")
	@ResponseBody
	public BoostrapTable<Button> listButtonUser(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String rows,String treeId){
		
		User user = SessionContext.get("user");
		String roleId = user.getRoleId();
		String orgId = user.getOrgId();
		if("0".equals(orgId)) {//超管--可以看所有按钮
			BoostrapTable<Button> listButton = buttonService.listButton(page,rows,"","",treeId);
			return listButton;
		}
		//只可看当前菜单下，登录用户角色下的按钮
		BoostrapTable<Button> listButton = buttonService.listButtonUser(page,rows,treeId,roleId);
		return listButton;
	}
	
	@RequestMapping("loadAddButton")
	public String loadAddButton(HttpServletRequest request,HttpServletResponse response,String treeId) {
		
		request.setAttribute("treeId", treeId);
		return "system/button/buttonAdd";
	}
	
	
	@RequestMapping("addButton")
	public ResponseEntity addButton(Button button) {
		
		try {
			buttonService.addButton(button);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "新增失败", "", HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("loadUpdateButton")
	public String loadUpdateButton(HttpServletRequest request,String id) {
		
		Button button = buttonService.queryButtonById(id);
		request.setAttribute("button", button);
		return "system/button/buttonUpdate";
	}
	
	@RequestMapping("updateButton")
	public ResponseEntity updateButton(Button button) {
		
		try {
			buttonService.updateButton(button);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "修改失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("deleteButton")
	public ResponseEntity deleteButton(String ids) {
		
		try {
			buttonService.deleteButton(ids);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
}
