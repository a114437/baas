package com.web.frame.web.controller.system;

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
import com.web.frame.entity.table.system.Tree;
import com.web.frame.entity.table.system.User;
import com.web.frame.service.system.TreeService;
import com.web.frame.util.SessionContext;

@Controller
@RequestMapping("tree")
public class TreeController {

	@Autowired
	private TreeService treeService;
	@Autowired
	private ResponseBase<Object> responseBase;
	
	@RequestMapping("loadTree")
	public String loadTree() {
		
		return "system/treeMenu/treeList";
	}
	
	@RequestMapping("initTree")
	@ResponseBody
	public List<Tree> initTree(HttpServletRequest request,HttpServletResponse response){
		
		List<Tree> listTree = treeService.initTree();
		return listTree;
	}
	
	@RequestMapping("initTreeUser")
	@ResponseBody
	public List<Tree> initTreeUser(HttpServletRequest request,HttpServletResponse response){
		
		User user = SessionContext.get("user");
		String roleId = user.getRoleId();
		String orgId = user.getOrgId();
		if("0".equals(orgId)) {//超管--可以看所有菜单
			List<Tree> listTree = treeService.initTree();
			return listTree;
		}
		List<Tree> listTree = treeService.initTreeUser(roleId);
		return listTree;
	}
	
	@RequestMapping("loadAddTree")
	public String loadAddTree(HttpServletRequest request,HttpServletResponse response,
			String pid,String parentName,String pids) {
		
		request.setAttribute("pid", pid);
		request.setAttribute("pids", pids);
		request.setAttribute("parentName", parentName);
		return "system/treeMenu/treeAdd";
	}
	
	
	@RequestMapping("treeAdd")
	public ResponseEntity addTree(Tree tree) {
		
		try {
			tree = treeService.addTree(tree);
			return responseBase.createResponse(true, "", tree, HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "新增失败", tree, HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("loadUpdateTree")
	public String loadUpdateTree(HttpServletRequest request,String id) {
		
		Tree tree = treeService.queryTreeById(id);
		request.setAttribute("tree", tree);
		return "system/treeMenu/treeUpdate";
	}
	
	@RequestMapping("treeUpdate")
	public ResponseEntity updateTree(Tree tree) {
		
		try {
			treeService.updateTree(tree);
			return responseBase.createResponse(true, "", tree, HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "修改失败", tree, HttpStatus.OK);
		}
	}
	
	@RequestMapping("deleteTree")
	public ResponseEntity deleteTree(String id) {
		
		try {
			treeService.deleteTree(id);
			return responseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return responseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
	
	
}
