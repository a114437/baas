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
import com.web.frame.entity.table.system.Dic;
import com.web.frame.entity.table.system.DicDetail;
import com.web.frame.entity.table.system.Role;
import com.web.frame.entity.table.system.Tree;
import com.web.frame.entity.table.system.User;
import com.web.frame.service.system.DicService;
import com.web.frame.service.system.RoleService;
import com.web.frame.util.Md5;
import com.web.frame.util.SessionContext;

@Controller
@RequestMapping("dic")
public class DicController {

	@Autowired
	private DicService dicService;
	
	@RequestMapping("loadDic")
	public String loadDic(HttpServletRequest request,HttpServletResponse response) {
		
		return "/system/dic/dictionaryList";
	}
	
	@RequestMapping("listDic")
	@ResponseBody
	public BoostrapTable<Dic> listDic(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String rows,
			String enName,String cnName){
		
		BoostrapTable<Dic> listDic = dicService.listDic(page,rows,enName,cnName);
		return listDic;
	}
	
	
	@RequestMapping("loadAddDic")
	public String loadAddDic(HttpServletRequest request,HttpServletResponse response) {
		
		return "/system/dic/dictionaryAdd";
	}
	
	
	
	@RequestMapping("addDic")
	public ResponseEntity addDic(Dic dic) {
		
		try {
			dicService.addDic(dic);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBase.createResponse(false, "新增失败", "", HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("loadUpdateDic")
	public String loadUpdateDic(HttpServletRequest request,String id) {
		
		Dic dic = dicService.queryDicById(id);
		request.setAttribute("dic", dic);
		return "/system/dic/dictionaryUpdate";
	}
	
	@RequestMapping("updateDic")
	public ResponseEntity updateDic(Dic dic) {
		
		try {
			dicService.updateDic(dic);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseBase.createResponse(false, "修改失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("deleteDic")
	public ResponseEntity deleteDic(String ids) {
		
		try {
			dicService.deleteDic(ids);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("listDicItem")
	@ResponseBody
	public BoostrapTable<DicDetail> listDicItem(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String rows,
			String content,String dicVal,String groupId){
		
		BoostrapTable<DicDetail> listDicItem = dicService.listDicItem(page,rows,content,dicVal,groupId);
		return listDicItem;
	}
	
	@RequestMapping("loadAddDicItem")
	public String loadAddDicItem(HttpServletRequest request,HttpServletResponse response,String groupId) {
		
		request.setAttribute("groupId", groupId);
		return "/system/dic/dictionaryItemAdd";
	}
	
	
	@RequestMapping("addDicItem")
	public ResponseEntity addDicItem(DicDetail dicDetail) {
		
		try {
			dicService.addDicItem(dicDetail);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBase.createResponse(false, "新增失败", "", HttpStatus.OK);
		}
	}
	
	@RequestMapping("loadUpdateDicItem")
	public String loadUpdateDicItem(HttpServletRequest request,String id) {
		
		DicDetail dicDetail = dicService.queryDicDetailById(id);
		request.setAttribute("dicDetail", dicDetail);
		return "/system/dic/dictionaryItemUpdate";
	}
	
	@RequestMapping("updateDicItem")
	public ResponseEntity updateDicItem(DicDetail dicDetail) {
		
		try {
			dicService.updateDicItem(dicDetail);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBase.createResponse(false, "修改失败", "", HttpStatus.OK);
		}
	}
	
	
	
	@RequestMapping("deleteDicItem")
	public ResponseEntity deleteDicItem(String ids) {
		
		try {
			dicService.deleteDicItem(ids);
			return ResponseBase.createResponse(true, "", "", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseBase.createResponse(false, "删除失败", "", HttpStatus.OK);
		}
	}
	
}

