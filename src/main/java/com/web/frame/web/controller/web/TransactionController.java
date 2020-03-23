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

import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.request.TokenAdd;
import com.web.frame.entity.response.ResponseAccount;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.response.ResponseToken;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.AppToken;
import com.web.frame.entity.table.web.Transaction;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.service.web.AccountService;
import com.web.frame.service.web.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/transaction")
@Api(tags= {"6.交易记录接口"},description="相关接口")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/v1/list")
	@ApiOperation("1.返回所有交易记录(注：该接口仅可查询由该钱包下账户所发起的交易)")
	public ResponseEntity<ResponseBase<BoostrapTable<Transaction>>> listTransaction(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="页",required=true)@RequestParam(required=true) String page,
			@ApiParam(value="条",required=true)@RequestParam(required=true) String rows) throws Exception {
		
		return transactionService.listTransaction(accessToken,walletId,page,rows);
	}
	
	@GetMapping("/v1/list/moac")
	@ApiOperation("2.返回墨客交易记录(注：该接口可查询该账户作为发起方或接收方的平台内所有交易)")
	public ResponseEntity<ResponseBase<BoostrapTable<Transaction>>> listMoac(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="发起或收账户地址",required=false)@RequestParam(required=false) String address,
			@ApiParam(value="页",required=true)@RequestParam(required=true) String page,
			@ApiParam(value="条",required=true)@RequestParam(required=true) String rows) throws Exception {
		
		return transactionService.listMoac(walletId,accessToken,address,page,rows);
	}
	
	@GetMapping("/v1/list/jingtum")
	@ApiOperation("3.返回井通交易记录(注：该接口可查询该账户作为发起方或接收方的平台内所有交易)")
	public ResponseEntity<ResponseBase<BoostrapTable<Transaction>>> listJingTum(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="发起或收账户地址",required=false)@RequestParam(required=false) String address,
			@ApiParam(value="页",required=true)@RequestParam(required=true) String page,
			@ApiParam(value="条",required=true)@RequestParam(required=true) String rows) throws Exception {
		
		return transactionService.listJingTum(walletId,accessToken,address,page,rows);
	}
	
	
}
