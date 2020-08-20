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
import com.web.frame.service.web.MoacContractService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/moac/contract")
@Api(tags= {"7.墨客合约接口"},description="相关接口")
public class MoacContractController {
	
	@Autowired
	private MoacContractService moacContractService;
	
	@PostMapping("/v1/deploy")
	@ApiOperation(value="1.部署合约",notes="返回部署结果")
	public ResponseEntity<ResponseBase<Map<String, String>>> deploy(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约编译代码（不包含0x）",required=true)@RequestParam String code,
			@ApiParam(value="合约构造函数参数，实例如："
					+ "[{\"type\":\"bool\",\"value\":\"false\"},"
					+ "{\"type\":\"string\",\"value\":\"1212\"},"
					+ "{\"type\":\"address\",\"value\":\"0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60\"},"
					+ "{\"type\":\"uint8\",\"value\":\"10\"},"
					+ "{\"type\":\"uint256\",\"value\":\"10000\"}]")
			@RequestParam(required=false) String params,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw) throws Exception {
		
		return moacContractService.deploy(accessToken,walletId,accountId,code,params,payPsw);
	}
	
	@GetMapping("/v1/{contractHash}")
	@ApiOperation(value="2.获取合约地址",notes="返回合约地址")
	public ResponseEntity<ResponseBase<Map<String, String>>> getContractAddress(HttpServletRequest request,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="部署合约返回hash",required=true) @PathVariable(value = "contractHash")String contractHash) throws Exception {
		
		return moacContractService.getContractAddress(accessToken,walletId,contractHash);
	}
	
	@ApiOperation(value="3.调用非交易合约方法")
	@GetMapping("/v1/call")
	public ResponseEntity<ResponseBase<List<String>>> call(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
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
			@ApiParam(value="合约出参，如：[{\"type\":\"bool\"}]")@RequestParam(required=false) String outParams) throws Exception {
		
		return moacContractService.call(accessToken,walletId,contractAddress,functionName,inParams,outParams,accountId);
	}
	
	@ApiOperation(value="4.调用交易类合约方法")
	@PostMapping("/v1/callTransaction")
	public ResponseEntity<ResponseBase<Map<String, String>>> callTransaction(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
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
			@ApiParam(value="原生币转账数量",required=true)@RequestParam(defaultValue="0") String value,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false) String gas,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false) String nonce) throws Exception {
		
		return moacContractService.callTransaction(accessToken,walletId,accountId,contractAddress,
				functionName,inParams,outParams,payPsw,value,gas,nonce);
	}
	
	
	@ApiOperation(value="5.获取代币余额（支持erc20,erc721）")
	@GetMapping("/v1/balance")
	public ResponseEntity<ResponseBase<Map<String, String>>> balance(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress) throws Exception {
		
		return moacContractService.balance(accessToken,walletId,accountId,contractAddress);
	}
	
	@ApiOperation(value="6.获取代币总发行量（支持erc20,erc721）")
	@GetMapping("/v1/totalSupply")
	public ResponseEntity<ResponseBase<Map<String, String>>> totalSupply(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress) throws Exception {
		
		return moacContractService.totalSupply(accessToken,walletId,contractAddress);
	}
	
	@ApiOperation(value="7.获取合约名称（支持erc20,erc721）")
	@GetMapping("/v1/name")
	public ResponseEntity<ResponseBase<Map<String, String>>> name(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress) throws Exception {
		
		return moacContractService.name(accessToken,walletId,contractAddress);
	}
	
	@ApiOperation(value="8.获取代币token编号（支持erc20,erc721）")
	@GetMapping("/v1/symbol")
	public ResponseEntity<ResponseBase<Map<String, String>>> symbol(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress) throws Exception {
		
		return moacContractService.symbol(accessToken,walletId,contractAddress);
	}
	
	//--------------------------------ERC20 start---------------------------------------------
	@ApiOperation(value="9.erc20获取代币decimals")
	@GetMapping("/v1/decimals20")
	public ResponseEntity<ResponseBase<Map<String, String>>> decimals20(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress) throws Exception {
		
		return moacContractService.decimals20(accessToken,walletId,contractAddress);
	}
	
	@ApiOperation(value="10.erc20代币转账")
	@PostMapping("/v1/transfer20")
	public ResponseEntity<ResponseBase<Map<String, String>>> transfer20(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="接受地址",required=true)@RequestParam String to,
			@ApiParam(value="token转账数量",required=true)@RequestParam String tokenValue,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false)String nonce,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false)String gas) throws Exception {
		
		return moacContractService.transfer20(accessToken,walletId,accountId,contractAddress,to,tokenValue,payPsw,nonce,gas);
	}
	
	@ApiOperation(value="11.erc20代币授权他人操作")
	@PostMapping("/v1/approve20")
	public ResponseEntity<ResponseBase<Map<String, String>>> approve20(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="被授权账户地址",required=true)@RequestParam String spender,
			@ApiParam(value="授权token数量",required=true)@RequestParam String tokenValue,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false) String nonce,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false) String gas) throws Exception {
		
		return moacContractService.approve20(accessToken,walletId,accountId,contractAddress,spender,tokenValue,payPsw,nonce,gas);
	}
	
	
	@ApiOperation(value="12.erc20获取授权代币数量")
	@GetMapping("/v1/allowance20")
	public ResponseEntity<ResponseBase<Map<String, String>>> allowance20(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="被授权账户地址",required=true)@RequestParam String spender) throws Exception {
		
		return moacContractService.allowance20(accessToken,walletId,accountId,contractAddress,spender);
	}
	
	
	@ApiOperation(value="13.erc20代替他人转账")
	@PostMapping("/v1/transferFrom20")
	public ResponseEntity<ResponseBase<Map<String, String>>> transferFrom20(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="授权账户地址",required=true)@RequestParam String from,
			@ApiParam(value="转账接收者",required=true)@RequestParam String to,
			@ApiParam(value="代理转账token数量",required=true)@RequestParam String tokenValue,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false) String nonce,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false) String gas) throws Exception {
		
		return moacContractService.transferFrom20(accessToken,walletId,accountId,contractAddress,from,to,tokenValue,payPsw,nonce,gas);
	}
	//--------------------------------ERC20 end-----------------------------------------------
	
	
	//--------------------------------ERC721 start---------------------------------------------
	@ApiOperation(value="14.erc721代币所属者")
	@GetMapping("/v1/ownerOf721")
	public ResponseEntity<ResponseBase<Map<String, String>>> ownerOf721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="721tokenId",required=true)@RequestParam String tokenId) throws Exception {
		
		return moacContractService.ownerOf721(accessToken,walletId,contractAddress,tokenId);
	}
	
	
	@ApiOperation(value="15.erc721代币是否存在")
	@GetMapping("/v1/exists721")
	public ResponseEntity<ResponseBase<Map<String, String>>> exists721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="721tokenId",required=true)@RequestParam String tokenId) throws Exception {
	
		return moacContractService.exists721(accessToken,walletId,contractAddress,tokenId);
	}
	
	@ApiOperation(value="16.erc721转账或代理转账")
	@PostMapping("/v1/transferFrom721")
	public ResponseEntity<ResponseBase<Map<String, String>>> transferFrom721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="授权地址",required=true)@RequestParam String from,
			@ApiParam(value="转账接收地址",required=true)@RequestParam String to,
			@ApiParam(value="721tokenId",required=true)@RequestParam String tokenId,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false) String nonce,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false)String gas) throws Exception {
		
		return moacContractService.transferFrom721(accessToken,walletId,accountId,contractAddress,from,to,tokenId,payPsw,nonce,gas);
	}
	
	
	@ApiOperation(value="17.erc721授权给他人操作某个币")
	@PostMapping("/v1/approve721")
	public ResponseEntity<ResponseBase<Map<String, String>>> approve721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="被授权账户地址",required=true)@RequestParam String spender,
			@ApiParam(value="授权的712tokenId",required=true)@RequestParam String tokenId,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false) String nonce,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false)String gas) throws Exception {
		
		return moacContractService.approve721(accessToken,walletId,accountId,contractAddress,spender,tokenId,payPsw,nonce,gas);
	}
	
	@ApiOperation(value="18.erc721获取该代币所授权的人")
	@GetMapping("/v1/getApproved721")
	public ResponseEntity<ResponseBase<Map<String, String>>> getApproved721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="712tokenId",required=true)@RequestParam String tokenId) throws Exception {
		
		return moacContractService.getApproved721(accessToken,walletId,contractAddress,tokenId);
	}
	
	@ApiOperation(value="19.erc721新增或取消全部授权")
	@PostMapping("/v1/setApprovalForAll721")
	public ResponseEntity<ResponseBase<Map<String, String>>> setApprovalForAll(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="被授权账户地址",required=true)@RequestParam String spender,
			@ApiParam(value="支付密码",required=true)@RequestParam String payPsw,
			@ApiParam(value="授权操作，0：授权   1：取消授权",required=true)@RequestParam String approved,
			@ApiParam(value="账户nonce，单次交易可不传")@RequestParam(required=false) String nonce,
			@ApiParam(value="gasLimit，可不传")@RequestParam(required=false) String gas) throws Exception {
		
		return moacContractService.setApprovalForAll(accessToken,walletId,accountId,contractAddress,spender,approved,payPsw,nonce,gas);
	}
	
	@ApiOperation(value="20.erc721是否全部授权给他人")
	@GetMapping("/v1/isApprovedForAll721")
	public ResponseEntity<ResponseBase<Map<String, String>>> isApprovedForAll721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="墨客账户id",required=true)@RequestParam String accountId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="被授权账户地址",required=true)@RequestParam String spender) throws Exception {
		
		return moacContractService.isApprovedForAll721(accessToken,walletId,accountId,contractAddress,spender);
	}
	
	@ApiOperation(value="21.erc721获取代币URI信息")
	@GetMapping("/v1/tokenURI721")
	public ResponseEntity<ResponseBase<Map<String, String>>> tokenURI721(HttpServletRequest request,HttpServletResponse response,
			@RequestHeader String accessToken,
			@RequestHeader String walletId,
			@ApiParam(value="合约地址",required=true)@RequestParam String contractAddress,
			@ApiParam(value="721tokenId",required=true)@RequestParam String tokenId) throws Exception {
		
		return moacContractService.tokenURI721(accessToken,walletId,contractAddress,tokenId);
	}
    //--------------------------------ERC721 end-----------------------------------------------
	
}
