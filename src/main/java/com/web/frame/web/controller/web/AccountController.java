package com.web.frame.web.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.request.TokenAdd;
import com.web.frame.entity.response.ResponseAccount;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.response.ResponseToken;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.AppToken;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.service.web.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/account")
@Api(tags= {"3.账户接口"},description="相关接口")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/moac/create")
	@ApiOperation("1.创建墨客账户")
	public ResponseEntity<ResponseBase<ResponseAccount>> createMoacAccount(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId) throws Exception {
		
		return accountService.createMoacAccount(accessToken,walletId);
	}
	
	@PostMapping("/moac/import")
	@ApiOperation("2.导入墨客账户")
	public ResponseEntity<ResponseBase<ResponseAccount>> importMoacAccount(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,@ApiParam(value="私钥",required=true) @RequestParam String privateKey) throws Exception {
		
		return accountService.importMoacAccount(accessToken,walletId,privateKey);
	}
	
	@PostMapping("/jingtum/create")
	@ApiOperation("3.创建井通账户（需转入20SWT激活）")
	public ResponseEntity<ResponseBase<ResponseAccount>> createJingTumAccount(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId) throws Exception {
		
		return accountService.createJingTumAccount(accessToken,walletId);
	}
	
	@PostMapping("/jingtum/import")
	@ApiOperation("4.导入井通账户")
	public ResponseEntity<ResponseBase<ResponseAccount>> importJingTumAccount(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,@ApiParam(value="私钥",required=true) @RequestParam String privateKey) throws Exception {
		
		return accountService.importJingTumAccount(accessToken,walletId,privateKey);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("5.删除账户")
	public ResponseEntity<ResponseBase<String>> deleteAccount(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,@ApiParam(value="账户id",required=true) @PathVariable("id") String id) throws Exception {
		
		return accountService.deleteAccount(id);
	}
	
	@GetMapping("/list")
	@ApiOperation("6.账户列表（不再返回私钥）")
	public ResponseEntity<ResponseBase<List<ResponseAccount>>> listAccount(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId) throws Exception {
		
		return accountService.listAccount(accessToken,walletId);
	}
	
	@GetMapping("/v1/balance/{id}")
	@ApiOperation("7.获取账户余额")
	public ResponseBalance balance(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,@ApiParam(value="账户id",required=true) @PathVariable("id") String id) throws Exception {
		
		return accountService.balance(id,walletId,accessToken);
	}
	
	@GetMapping("/v1/export/{id}")
	@ApiOperation("8.导出账户信息")
	public ResponseEntity<ResponseBase<Account>> export(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="账户id",required=true) @PathVariable("id") String id,
			@ApiParam(value="支付密码",required=true) @RequestParam String payPsw) throws Exception {
		
		return accountService.export(id,walletId,accessToken,payPsw);
	}
	
}
