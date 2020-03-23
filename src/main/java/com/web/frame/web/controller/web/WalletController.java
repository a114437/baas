package com.web.frame.web.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.request.BindEmail;
import com.web.frame.entity.request.RegisterApp;
import com.web.frame.entity.request.RegisterWallet;
import com.web.frame.entity.request.TokenAdd;
import com.web.frame.entity.request.UpdatePayPsw;
import com.web.frame.entity.request.VerifyApp;
import com.web.frame.entity.request.VerifyWallet;
import com.web.frame.entity.response.ResponseApp;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.response.ResponseToken;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.exception.BussinessException;
import com.web.frame.service.web.AppService;
import com.web.frame.service.web.WalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/wallet")
@Api(tags= {"2.钱包接口"},description="相关接口")
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@PostMapping("/v1/create")
    @ApiOperation(value="1.创建钱包")
	public ResponseEntity<ResponseBase<Map<String, Object>>> create(HttpServletRequest request,HttpServletResponse response,
			@RequestBody RegisterWallet registerWallet,@RequestHeader String accessToken) throws Exception {
		
			return walletService.create(registerWallet,accessToken);
	}
	
	@PostMapping("/v1/verify")
	@ApiOperation(value="2.认证钱包")
	public ResponseEntity<ResponseBase<String>> verify(HttpServletRequest request,HttpServletResponse response,
			@RequestBody VerifyWallet verifyWallet,@RequestHeader String accessToken) throws Exception {
		
		return walletService.verifyWallet(verifyWallet,accessToken);
	}
	
	@PostMapping("/v1/bindEmail")
	@ApiOperation(value="3.钱包绑定邮箱")
	public ResponseEntity<ResponseBase<String>> bindEmail(HttpServletRequest request,HttpServletResponse response,
			@RequestBody BindEmail bindEmail,@RequestHeader String accessToken) throws Exception {
		
		return walletService.bindEmail(bindEmail,accessToken);
	}
	
	@PostMapping("/v1/updatePayPsw")
	@ApiOperation(value="4.修改支付密码")
	public ResponseEntity<ResponseBase<String>> updatePayPsw(HttpServletRequest request,HttpServletResponse response,
			@RequestBody UpdatePayPsw updatePayPsw,@RequestHeader String accessToken) throws Exception {
		
		return walletService.updatePayPsw(updatePayPsw,accessToken);
	}
	
	@GetMapping("/v1/findPayPsw")
	@ApiOperation(value="5.重置支付密码（邮件）")
	public ResponseEntity<ResponseBase<String>> findPayPsw(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(value="钱包id",required=true) @RequestParam String walletId,@RequestHeader String accessToken) throws Exception {
		
		return walletService.findPayPsw(walletId,accessToken);
	}
	
	
	@PostMapping("/v1/symbol/moac/add")
	@ApiOperation(value="6.新增app墨客代币（用户代币）")
	public ResponseEntity<ResponseBase<ResponseToken>> addSymbol(@RequestHeader String accessToken,
			@RequestHeader String walletId,@RequestBody TokenAdd appTokenAdd) throws Exception {
		
		return walletService.addSymbol(accessToken,walletId,appTokenAdd);
	}
	
	
	@DeleteMapping("/v1/symbol/{id}")
	@ApiOperation("7.删除代币")
	public ResponseEntity<ResponseBase<String>> deleteUserToken(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@PathVariable("id") String id) throws Exception {
		
		return walletService.deleteUserToken(accessToken,id,walletId);
	}
	
	@GetMapping("/v1/symbol/list")
	@ApiOperation("8.代币列表(app代币--id为'system')")
	public ResponseEntity<ResponseBase<List<UserToken>>> listUserToken(HttpServletRequest request,
			@RequestHeader String accessToken,@RequestHeader String walletId) throws Exception {
		
		return walletService.listUserToken(accessToken,walletId);
	}
	
	@GetMapping("/v1/balance")
	@ApiOperation("9.获取钱包余额")
	public List<ResponseBalance> balance(HttpServletRequest request,
			@RequestHeader String accessToken,@RequestHeader String walletId) throws Exception {
		
		return walletService.balance(accessToken,walletId);
	}
	
}
