package com.web.frame.service.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.web.AppDao;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.request.RegisterApp;
import com.web.frame.entity.request.TokenAdd;
import com.web.frame.entity.request.VerifyApp;
import com.web.frame.entity.response.ResponseApp;
import com.web.frame.entity.response.ResponseToken;
import com.web.frame.entity.table.web.App;
import com.web.frame.entity.table.web.AppToken;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.AccessTokenUtil;
import com.web.frame.util.IDCard;
import com.web.frame.util.Md5;
import com.web.frame.util.Sign;
import com.web.frame.util.Uuid;
import com.web.frame.util.moac.Moac;
import com.web.frame.util.redis.RedisUtil;

@Service
@Transactional
public class AppService {

	@Autowired
	private AppDao appDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AccessTokenUtil accessTokenUtil;
	@Autowired
	private Moac moac;
	@Autowired
	private IDCard idCard;
	
	public ResponseEntity<ResponseBase<ResponseApp>> addApp(RegisterApp registerApp) throws Exception {
		
		if(StringUtils.isEmpty(registerApp.getAppName())) {
			throw new BussinessException(40001,"应用名称不能为空");
		}
		if(StringUtils.isEmpty(registerApp.getIntroduce())) {
			throw new BussinessException(40001,"应用介绍不能为空");
		}
		if(StringUtils.isEmpty(registerApp.getUserType())) {
			throw new BussinessException(40001,"用户类型不能为空");
		}
		App app = new App();
		String appId = Uuid.getUuid();
		String appSecret =  Uuid.getUuid();
		app.setAppId(appId);
		app.setAppSecret(Md5.encrypt(appSecret));
		app.setAppName(registerApp.getAppName());
		app.setIntroduce(registerApp.getIntroduce());
		app.setUserType(registerApp.getUserType());
		app.setVerify("0");
		appDao.addApp(app);//app注册
		
		app.setAppSecret(appSecret);
		redisUtil.set(appId, app);//放入缓存
		
		ResponseApp responseApp = new ResponseApp();
		responseApp.setAppId(appId);
		responseApp.setAppSecret(appSecret);
		return ResponseBase.createResponse(true, 10000, "", responseApp, HttpStatus.OK);
	}

	/**
	 * 获取accessToken
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws BussinessException 
	 */
	public ResponseEntity<ResponseBase<Map<String,Object>>> getToken(String appId, String appSecret) throws BussinessException {
		
		if(redisUtil.get(appId)==null) {
			throw new BussinessException(40006,"appId不存在");
		}
		App app = (App) redisUtil.get(appId);
		if(!appSecret.equals(app.getAppSecret())){
			throw new BussinessException(40006,"appSecret不正确");
		}
		return ResponseBase.createResponse(true, 10000, "", accessTokenUtil.createToken(appId, appSecret), HttpStatus.OK);
	}

	/**
	 * 实名认证
	 * @param verifyApp
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseBase<String>> verifyApp(VerifyApp verifyApp) throws Exception {
		
		if(StringUtils.isEmpty(verifyApp.getUserName())) {
			throw new BussinessException(40001,"姓名不能为空");
		}
		if(StringUtils.isEmpty(verifyApp.getIdCard())) {
			throw new BussinessException(40001,"身份证不能为空");
		}
		if(!idCard.verify(verifyApp.getIdCard())) {
			throw new BussinessException(40001,"身份证格式不正确");
		}
		
		String appId = verifyApp.getAppId();
		if(redisUtil.get(appId)==null)
		throw new BussinessException(40006,"appId不存在");
		
		String userName = verifyApp.getUserName();
		String idCard = verifyApp.getIdCard();
		
		boolean isValid = true;
		//调用百度or阿里认证接口
		if(!isValid)
		throw new BussinessException(40004,"认证失败");
		
		
		App app = new App();
		app.setAppId(appId);
		app.setUserName(userName);
		app.setIdCard(idCard);
		app.setVerify("1");
		appDao.updateAppByAppId(app);//更新数据库
		
		App saveApp = (App) redisUtil.get(appId);
		saveApp.setUserName(userName);
		saveApp.setIdCard(idCard);
		saveApp.setVerify("1");
		redisUtil.set(appId, saveApp);//更新后放入缓存
		
		return ResponseBase.createResponse(true, 10000, "认证成功", null, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<ResponseToken>> addSymbol(String accessToken,
			TokenAdd appTokenAdd, String sign) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		if(redisUtil.get(appId)==null) {
			throw new BussinessException(40006,"appId不存在");
		}
		
		App app = (App) redisUtil.get(appId);
		
		Map<String,Object> signParam = new HashMap<String, Object>();
		signParam = Sign.addParam("tokenAddress", appTokenAdd.getTokenAddress(), signParam);
		signParam = Sign.addParam("tokenType", appTokenAdd.getTokenType(), signParam);
		signParam = Sign.addParam("symbol", appTokenAdd.getSymbol(), signParam);
		signParam = Sign.addParam("chain", appTokenAdd.getChain(), signParam);
		signParam = Sign.addParam("appSecret", app.getAppSecret(), signParam);
		String sign2 = Sign.sign(signParam);
		if(!sign2.equals(sign)) {
			throw new BussinessException(40004,"签名不正确");
		}
		System.out.println(moac);
		String symbol = moac.getContractSymbol(appTokenAdd.getTokenAddress());
		if(StringUtils.isEmpty(symbol)) {
			throw new BussinessException(40004,"未查询到代币编号");
		}
		
		
		List<AppToken> tokenList = appDao.queryAppTokenExist(appTokenAdd.getTokenAddress(),
				appTokenAdd.getSymbol(),appTokenAdd.getChain(),appId);
		if(tokenList.size()>0) {
			throw new BussinessException(40004,"代币已存在");
		}
		
		AppToken appToken = new AppToken();
		String tokenId = Uuid.getUuid();
		appToken.setId(tokenId);
		appToken.setAppId(appId);
		appToken.setTokenAddress(appTokenAdd.getTokenAddress());
		appToken.setTokenType(appTokenAdd.getTokenType());
		appToken.setSymbol(symbol);
		appToken.setChain("0");
		appDao.addAppToken(appToken);
		
		ResponseToken responseToken = new ResponseToken();
		responseToken.setId(tokenId);
		responseToken.setTokenAddress(appTokenAdd.getTokenAddress());
		responseToken.setTokenType(appTokenAdd.getTokenType());
		responseToken.setSymbol(appTokenAdd.getSymbol());
		responseToken.setChain(appTokenAdd.getChain());
		
		return ResponseBase.createResponse(true, 10000, "新增app代币成功", responseToken, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<String>> deleteAppToken(String accessToken,String id, String sign) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		if(redisUtil.get(appId)==null) {
			throw new BussinessException(40006,"appId不存在");
		}
		
		App app = (App) redisUtil.get(appId);
		
		Map<String,Object> signParam = new HashMap<String, Object>();
		signParam = Sign.addParam("id", id, signParam);
		signParam = Sign.addParam("appSecret", app.getAppSecret(), signParam);
		String sign2 = Sign.sign(signParam);
		if(!sign2.equals(sign)) {
			throw new BussinessException(40004,"签名不正确");
		}
		
		appDao.deleteAppToken(id);
		
		return ResponseBase.createResponse(true, 10000, "删除app代币成功", "", HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<List<AppToken>>> listAppToken(String accessToken) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		if(redisUtil.get(appId)==null) {
			throw new BussinessException(40006,"appId不存在");
		}
		List<AppToken> list = appDao.queryAppToken(appId);
		
		return ResponseBase.createResponse(true, 10000, "查询app代币成功", list, HttpStatus.OK);
	}
	
	

	
	
}

