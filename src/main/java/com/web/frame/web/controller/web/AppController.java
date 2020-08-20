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
import com.web.frame.entity.request.TokenAdd;
import com.web.frame.entity.request.RegisterApp;
import com.web.frame.entity.request.VerifyApp;
import com.web.frame.entity.response.ResponseApp;
import com.web.frame.entity.response.ResponseToken;
import com.web.frame.entity.table.web.AppToken;
import com.web.frame.exception.BussinessException;
import com.web.frame.service.web.AppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/app")
@Api(tags= {"1.应用接口"},description="相关接口")
public class AppController {

	@Autowired
	private AppService appService;
	
	@PostMapping("register")
    @ApiOperation(value="1.APP注册")
	public ResponseEntity<ResponseBase<ResponseApp>> register(HttpServletRequest request,HttpServletResponse response,
			@RequestBody RegisterApp registerApp) throws Exception {
		
			return appService.addApp(registerApp);
	}
	
	@PostMapping("verify")
	@ApiOperation(value="2.实名认证")
	public ResponseEntity<ResponseBase<String>> register(HttpServletRequest request,HttpServletResponse response,
			@RequestBody VerifyApp verifyApp) throws Exception {
		
			return appService.verifyApp(verifyApp);
	}
	
	@GetMapping("token")
	@ApiOperation(value="3.获取accessToken(两小时失效请及时刷新)")
	public ResponseEntity<ResponseBase<Map<String, Object>>> token(@ApiParam(value="appId",required=true) @RequestParam(required=true) String appId,
			@ApiParam(value="appSecret",required=true) @RequestParam(required=true) String appSecret) throws BussinessException {
		
		return appService.getToken(appId,appSecret);
	}
	
	@PostMapping("/symbol/moac/add")
	@ApiOperation(value="4.新增app墨客代币（app下所有用户都会显示该币）")
	public ResponseEntity<ResponseBase<ResponseToken>> addSymbol(@RequestHeader String accessToken,
			@RequestBody TokenAdd appTokenAdd,
			@ApiParam(value="签名（将私钥与非空参数，按字典排序后字符串，如：a=1&appSecret=666&b=2&c=3,进行MD5加密）",required=true)
			@RequestParam String sign) throws Exception {
		
		return appService.addSymbol(accessToken,appTokenAdd,sign);
	}
	
	@DeleteMapping("/symbol/{id}")
	@ApiOperation("5.删除app代币")
	public ResponseEntity<ResponseBase<String>> deleteAppToken(HttpServletRequest request,
			@RequestHeader String accessToken,
			@PathVariable("id") String id,
			@ApiParam(value="签名（将私钥与非空参数，按字典排序后字符串，如：a=1&b=2&c=3&appSecret=666,进行MD5加密）",required=true)
			@RequestParam String sign) throws Exception {
		
		return appService.deleteAppToken(accessToken,id,sign);
	}
	
	@GetMapping("/symbol/list")
	@ApiOperation("6.app代币列表")
	public ResponseEntity<ResponseBase<List<AppToken>>> listAppToken(HttpServletRequest request,
			@RequestHeader String accessToken) throws Exception {
		
		return appService.listAppToken(accessToken);
	}
	
}
