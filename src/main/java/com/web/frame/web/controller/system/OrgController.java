package com.web.frame.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.table.system.Org;
import com.web.frame.entity.table.system.User;
import com.web.frame.service.system.OrgService;
import com.web.frame.util.SessionContext;

@Controller
@RequestMapping("org")
public class OrgController {

	@Autowired
	private OrgService orgService;
	@Autowired
	private ResponseBase<Object> responseBase;
	
	@RequestMapping("loadOrg")
	public String loadTree() {
		
		return "system/org/orgList";
	}
	
	@RequestMapping("initOrg")
	@ResponseBody
	public List<Org> initOrg(HttpServletRequest request,HttpServletResponse response){
		
		List<Org> listTree = orgService.initOrg();
		return listTree;
	}
	
	@RequestMapping("initOrgUser")
	@ResponseBody
	public List<Org> initOrgUser(HttpServletRequest request,HttpServletResponse response){
		
		User user = SessionContext.get("user");
		if(user == null) {
			return new ArrayList<Org>();
		}
		List<Org> listTree = orgService.initOrgUser(user.getOrgId());
		return listTree;
	}
	
	@RequestMapping("loadAddOrg")
	public String loadAddOrg(HttpServletRequest request,HttpServletResponse response,
			String pid,String parentName,String pids) {
		
		request.setAttribute("pid", pid);
		request.setAttribute("pids", pids);
		request.setAttribute("parentName", parentName);
		return "system/org/orgAdd";
	}
	
	
	@RequestMapping("orgAdd")
	public ResponseEntity addOrg(Org org) {
		
		try {
			org = orgService.addOrg(org);
			return responseBase.createResponse(true, "", org, HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "新增失败", org, HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("loadUpdateOrg")
	public String loadUpdateOrg(HttpServletRequest request,String id) {
		
		Org tree = orgService.queryOrgById(id);
		request.setAttribute("org", tree);
		return "system/org/orgUpdate";
	}
	
	@RequestMapping("orgUpdate")
	public ResponseEntity updateOrg(Org org) {
		
		try {
			orgService.updateOrg(org);
			return responseBase.createResponse(true, "", org, HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "修改失败", org, HttpStatus.OK);
		}
	}
	
	@RequestMapping("deleteOrg")
	public ResponseEntity deleteOrg(String id) {
		
		try {
			orgService.deleteOrg(id);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
}
