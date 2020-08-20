package com.web.frame.web.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.response.ResponseTransactionMoac;
import com.web.frame.service.web.MoacService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/moac")
@Api(tags= {"4.墨客接口"},description="相关接口")
public class MoacController {
	
	@Autowired
	private MoacService moacService;
	
	@GetMapping("/v1/nonce")
	@ApiOperation("1.墨客账户获取nonce，通过递增nonce多次转账可实现批量转账")
	public ResponseEntity<ResponseBase<Map<String, String>>> getNonce(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId
			) throws Exception {
		
		return moacService.getNonce(accessToken,walletId,accountId);
	}
	

	@PostMapping("/v1/transfer")
	@ApiOperation("2.墨客原生币转账/文本上链")
	public ResponseEntity<ResponseBase<Map<String, String>>> transfer(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="接收方账户地址",required=true)@RequestParam String to,
			@ApiParam(value="手续费（可不传，默认传最大值9E-12）")@RequestParam(required=false)String gas,
			@ApiParam(value="转账数量(文本上链则传0)",required=true)@RequestParam(defaultValue="0") String value,
			@ApiParam(value="文本内容（备注）")@RequestParam(required=false)String data,
			@ApiParam(value="账户账户nonce(可不传，默认为账户当前nonce)")@RequestParam(required=false)String nonce
			) throws Exception {
		
		return moacService.transfer(accessToken,walletId,accountId,payPsw,to,gas,value,data,nonce);
	}
	
	@GetMapping("/v1/transaction")
	@ApiOperation("3.墨客获取交易信息")
	public ResponseEntity<ResponseBase<ResponseTransactionMoac>> transaction(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="交易hash",required=true)@RequestParam String hash
			) throws Exception {
		
		return moacService.transaction(accessToken,walletId,hash);
	}
	
	@GetMapping("/v1/blockNumber")
	@ApiOperation("4.查询当前区块号")
	public ResponseEntity<ResponseBase<Map<String, String>>> blockNumber(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId
			) throws Exception {
		
		return moacService.blockNumber(accessToken,walletId);
	}
	
}
