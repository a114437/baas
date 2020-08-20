package com.web.frame.web.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.service.web.WalletService;
import com.web.frame.util.redis.RedisUtil;

@Controller
@RequestMapping("password")
public class PasswordController {
	
	@Autowired
	private WalletService walletService;
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping("/reset")
	public String reset(HttpServletRequest request,String walletId) {
		
//		String result = walletService.updateResetPassword(walletId);
//		if(result.contains("true")) {
//			request.setAttribute("result", result.split(":")[1]);
//			return "password";
//		}else {
//			request.setAttribute("error", result);
//			return "error";
//		}
		Object walletToken = redisUtil.get("reset-"+walletId);
		if(walletToken == null) {
			request.setAttribute("error", "链接已失效");
			return "pswerror";
		}
		request.setAttribute("walletId", walletId);
		request.setAttribute("walletToken", walletToken);
		return "password";
	}
	
	
	@RequestMapping("updatePassword")
	public ResponseEntity<ResponseBase<String>> updatePassword(HttpServletRequest request,
			String walletId,String walletToken,String newPsw) {
		
		return walletService.updateResetPassword(walletId,walletToken,newPsw);
	}
	
}
