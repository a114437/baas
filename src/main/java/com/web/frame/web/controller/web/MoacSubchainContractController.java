package com.web.frame.web.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.service.web.MoacSubchainContractService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/moac/subchain/contract")
@Api(tags= {"9.墨客合约类接口"},description="相关接口")
public class MoacSubchainContractController {
	
	@Autowired
	private MoacSubchainContractService moacSubchainService;
	
	@PostMapping("/v1/{subchainId}/deploy")
	@ApiOperation(value="1.部署子链业务合约")
	public ResponseEntity<ResponseBase<Map>> deploy(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="子链id",required=true) @PathVariable("subchainId") String subchainId,
			@ApiParam(value="合约编译代码",required=true)@RequestParam String code,
			@ApiParam(value="合约abi代码",required=true)@RequestParam String abi,
			@ApiParam(value="scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorScsId,
			@ApiParam(value="scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorUrl) throws Exception{
		
		return moacSubchainService.deploy(subchainId,walletId,accountId,payPsw,code,abi,monitorScsId,monitorUrl);
	}
	
	@PostMapping("/v1/{subchainId}/register")
	@ApiOperation(value="2.子链业务合约注册到dappbase")
	public ResponseEntity<ResponseBase<Map>> register(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id(部署子链的账户)",required=true)@RequestParam String accountId,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="子链id",required=true) @PathVariable("subchainId") String subchainId,
			@ApiParam(value="合约编译代码",required=true)@RequestParam String code,
			@ApiParam(value="合约abi代码",required=true)@RequestParam String abi,
			@ApiParam(value="部署子链业务合约的账户",required=true)@RequestParam String from,
			@ApiParam(value="子链业务合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorScsId,
			@ApiParam(value="scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorUrl) throws Exception{
		
		return moacSubchainService.register(subchainId,walletId,accountId,payPsw,code,abi,monitorScsId,monitorUrl,from,contractAddress);
	}
	
	@ApiOperation(value="3.调用交易类合约方法")
	@PostMapping("/v1/{subchainId}/callTransaction")
	public ResponseEntity<ResponseBase<Map<String, String>>> callTransaction(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="子链id",required=true) @PathVariable("subchainId") String subchainId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="合约方法名",required=true)@RequestParam String functionName, 
			@ApiParam(value="合约方法入参，如："
					+ "[{\"type\":\"bool\",\"value\":\"false\"},"
					+ "{\"type\":\"string\",\"value\":\"1212\"},"
					+ "{\"type\":\"address\",\"value\":\"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60\"},"
					+ "{\"type\":\"uint8\",\"value\":\"10\"},"
					+ "{\"type\":\"uint256\",\"value\":\"10000\"}]") 
			@RequestParam(required=false) String inParams,
			@ApiParam(value="合约出参，如：[{\"type\":\"bool\"}]")
			@RequestParam(required=false) String outParams,
			@ApiParam(value="支付密码")@RequestParam String payPsw,
			@ApiParam(value="子链币转账数量",required=true)@RequestParam(defaultValue="0") String value,
			@ApiParam(value="账户子链nonce，单次交易可不传")@RequestParam(required=false) String nonce,
			@ApiParam(value="scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorScsId,
			@ApiParam(value="scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorUrl) throws Exception{
		
		return moacSubchainService.callTransaction(accessToken,walletId,accountId,contractAddress,
				functionName,inParams,outParams,payPsw,value,nonce,subchainId,monitorScsId,monitorUrl);
	}
	
	
	@ApiOperation(value="4.调用非交易合约方法")
	@GetMapping("/v1/{subchainId}/call")
	public ResponseEntity<ResponseBase<String>> call(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="子链id",required=true) @PathVariable("subchainId") String subchainId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="合约方法名",required=true)@RequestParam String functionName, 
			@ApiParam(value="合约方法入参(多个参数用英文逗号隔开)，如："
					+ "[\"字符串入参\",true,\"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60\"]") 
			@RequestParam(required=false) String inParams,
			@ApiParam(value="scs-monitor在系统中的id（通过接口2获取，与monitorUrl二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorScsId,
			@ApiParam(value="scs-monitor监控rpc地址（如：http://192.168.2.124:2345/rpc，与monitorScsId二选一传递，或都不传则缺省使用最新的monitor）",required=false)
			@RequestParam(required=false) String monitorUrl) throws Exception{
		
		return moacSubchainService.call(accessToken,walletId,contractAddress,functionName,inParams,accountId,
				subchainId,monitorScsId,monitorUrl);
	}
	
	
}
