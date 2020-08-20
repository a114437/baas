package com.web.frame.web.controller.web;

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
import com.web.frame.entity.response.ResponseTransactionJingTum;
import com.web.frame.entity.response.ResponseTxJson;
import com.web.frame.service.web.JingTongService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/jintum")
@Api(tags= {"5.井通接口"},description="相关接口")
public class JingTongController {
	
	@Autowired
	private JingTongService jingTongService;
	

	@PostMapping("/v1/transfer")
	@ApiOperation("1.井通原生币及其他代币转账/文本上链")
	public ResponseEntity<ResponseBase<ResponseTxJson>> transfer(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="井通账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="接收方账户地址",required=true)@RequestParam String to,
			@ApiParam(value="代币名，默认为'SWT'",required=true)@RequestParam(defaultValue="SWT")String currency,
			@ApiParam(value="银关地址，'SWT'转账可为空")@RequestParam(required=false)String issuer,
			@ApiParam(value="转账数量(文本上链也需大于0)",required=true)@RequestParam String value,
			@ApiParam(value="文本内容（备注）")@RequestParam(required=false)String data
			) throws Exception {
		
		return jingTongService.transfer(accessToken,walletId,accountId,payPsw,to,currency,issuer,value,data);
	}
	
	@GetMapping("/v1/transaction")
	@ApiOperation("2.井通获取交易信息")
	public ResponseEntity<ResponseBase<ResponseTransactionJingTum>> transaction(HttpServletRequest request,@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="交易hash",required=true)@RequestParam String hash
			) throws Exception {
		
		return jingTongService.transaction(accessToken,walletId,hash);
	}
	
}
