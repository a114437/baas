package com.web.frame.service.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.BalanceStrategy;
import com.web.frame.dao.web.AccountDao;
import com.web.frame.dao.web.AppDao;
import com.web.frame.dao.web.WalletDao;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.request.BindEmail;
import com.web.frame.entity.request.RegisterWallet;
import com.web.frame.entity.request.TokenAdd;
import com.web.frame.entity.request.UpdatePayPsw;
import com.web.frame.entity.request.VerifyWallet;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.response.ResponseBalanceItem;
import com.web.frame.entity.response.ResponseToken;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.AppToken;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.AccessTokenUtil;
import com.web.frame.util.EncryptSHA;
import com.web.frame.util.IDCard;
import com.web.frame.util.PasswordGenerator;
import com.web.frame.util.SendmailUtil;
import com.web.frame.util.Uuid;
import com.web.frame.util.jingtong.JingTong;
import com.web.frame.util.moac.Moac;
import com.web.frame.util.redis.RedisUtil;

@Service
@Transactional
public class WalletService {

	@Autowired
	private WalletDao walletDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private EncryptSHA encryptSHA;
	@Autowired
	private SendmailUtil sendmailUtil;
	@Autowired
	private AccessTokenUtil accessTokenUtil;
	@Autowired
	private AppDao appDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private Moac moac;
	@Autowired
	private JingTong jingTong;
	@Autowired
	private IDCard idCard;
	
	
	public ResponseEntity<ResponseBase<Map<String, Object>>> create(RegisterWallet registerWallet, String accessToken) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		if(StringUtils.isEmpty(registerWallet.getWalletName())) {
			throw new BussinessException(40001,"钱包名称不能为空");
		}
		if(StringUtils.isEmpty(registerWallet.getPayPsw())) {
			throw new BussinessException(40001,"支付密码不能为空");
		}
		if(!registerWallet.getPayPsw()
				.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$")) {
			throw new BussinessException(40001,"支付密码格式不正确");
		}
			
			
		Wallet wallet = new Wallet();
		String walletId = Uuid.getUuid();
		wallet.setId(walletId);
		wallet.setAppId(appId);
		wallet.setWalletName(registerWallet.getWalletName());
		wallet.setPayPsw(encryptSHA.SHA256(registerWallet.getPayPsw()));
		wallet.setVerify("0");
		walletDao.addWallet(wallet);
		
		redisUtil.set(walletId, wallet);//钱包信息放入缓存
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("walletId", walletId);
		return ResponseBase.createResponse(true, 10000, "注册成功", map, HttpStatus.OK);
	}
	public ResponseEntity<ResponseBase<String>> verifyWallet(VerifyWallet verifyWallet, String accessToken) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		
		if(StringUtils.isEmpty(verifyWallet.getWalletId())) {
			throw new BussinessException(40001,"钱包id不能为空");
		}
		if(StringUtils.isEmpty(verifyWallet.getUserName())) {
			throw new BussinessException(40001,"姓名不能为空");
		}
		if(StringUtils.isEmpty(verifyWallet.getIdCard())) {
			throw new BussinessException(40001,"身份证不能为空");
		}
		if(!idCard.verify(verifyWallet.getIdCard())) {
			throw new BussinessException(40001,"身份证格式不正确");
		}
		
		String userName = verifyWallet.getUserName();
		String idCard = verifyWallet.getIdCard();
		
		boolean isValid = true;
		//调用百度or阿里认证接口
		if(!isValid)
		throw new BussinessException(40004,"认证失败");
		
		Wallet wallet = (Wallet) redisUtil.get(verifyWallet.getWalletId());
		if(wallet==null) {
			throw new BussinessException(40006, "该钱包不存在");
		}
		wallet.setUserName(userName);
		wallet.setIdCard(idCard);
		wallet.setVerify("1");
		walletDao.updateWalletById(wallet);//更新数据库
		
		redisUtil.set(verifyWallet.getWalletId(), wallet);//钱包信息放入缓存
		
		return ResponseBase.createResponse(true, 10000, "认证成功", "", HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseBase<String>> bindEmail(BindEmail bindEmail, String accessToken) throws Exception {
		
		if(StringUtils.isEmpty(bindEmail.getWalletId())) {
			throw new BussinessException(40001,"钱包id不能为空");
		}
		if(StringUtils.isEmpty(bindEmail.getPayPsw())) {
			throw new BussinessException(40001,"支付密码不能为空");
		}
		if(StringUtils.isEmpty(bindEmail.getEmail())) {
			throw new BussinessException(40001,"邮箱不能为空");
		}
		if (!bindEmail.getEmail().matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			throw new BussinessException(40001,"邮箱格式不正确");
		}
		Wallet wallet = walletDao.queryWalletById(bindEmail.getWalletId());
		if(!encryptSHA.SHA256(bindEmail.getPayPsw()).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004,"支付密码错误");
		}
		wallet = new Wallet();
		wallet.setId(bindEmail.getWalletId());
		wallet.setEmail(bindEmail.getEmail());
		walletDao.updateWalletById(wallet);//更新数据库
		return ResponseBase.createResponse(true, 10000, "绑定成功", "", HttpStatus.OK);
	}
	public ResponseEntity<ResponseBase<String>> updatePayPsw(UpdatePayPsw updatePayPsw, String accessToken) throws Exception {
		
		if(StringUtils.isEmpty(updatePayPsw.getWalletId())) {
			throw new BussinessException(40001,"钱包id不能为空");
		}
		if(StringUtils.isEmpty(updatePayPsw.getOldPayPsw())) {
			throw new BussinessException(40001,"原支付密码不能为空");
		}
		if(StringUtils.isEmpty(updatePayPsw.getNewPayPsw())) {
			throw new BussinessException(40001,"新支付密码不能为空");
		}
		if(!updatePayPsw.getNewPayPsw()
				.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$")) {
			throw new BussinessException(40001,"新支付密码格式不正确");
		}
		Wallet wallet = walletDao.queryWalletById(updatePayPsw.getWalletId());
		if(!encryptSHA.SHA256(updatePayPsw.getOldPayPsw()).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004,"原支付密码错误");
		}
		wallet = new Wallet();
		wallet.setId(updatePayPsw.getWalletId());
		wallet.setPayPsw(encryptSHA.SHA256(updatePayPsw.getNewPayPsw()));
		walletDao.updateWalletById(wallet);
		return ResponseBase.createResponse(true, 10000, "修改成功", "", HttpStatus.OK);
	}
	public ResponseEntity<ResponseBase<String>> findPayPsw(String walletId, String accessToken) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(StringUtils.isEmpty(wallet.getEmail())) {
			throw new BussinessException(40004,"钱包未绑定邮箱");
		}
//		String payPsw = new PasswordGenerator(8, 2).generateRandomPassword();
//		wallet.setPayPsw(encryptSHA.SHA256(payPsw));
//		walletDao.updateWalletById(wallet);//重置密码并修改
		redisUtil.set("reset-"+wallet.getId(),Uuid.getUuid(),10*60l);
//		sendmailUtil.sendFindMail(wallet.getEmail(), wallet.getId(), payPsw);
		sendmailUtil.sendVerifyMail(wallet.getEmail(),wallet.getId());
		return ResponseBase.createResponse(true, 10000, "请从邮件中查看新支付密码，并及时修改", "", HttpStatus.OK);
	}
	

	public ResponseEntity<ResponseBase<ResponseToken>> addSymbol(String accessToken, String walletId, TokenAdd appTokenAdd) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		
		List<AppToken> tokenList = appDao.queryAppTokenExist(appTokenAdd.getTokenAddress(),
				appTokenAdd.getSymbol(),appTokenAdd.getChain(),appId);
		if(tokenList.size()>0) {
			throw new BussinessException(40004,"代币已存在");
		}
		
		List<UserToken> list = walletDao.queryUserTokenExist(walletId,appTokenAdd.getTokenAddress());
		if(list.size()>0) {
			throw new BussinessException(40004,"代币已存在");
		}
		String symbol = moac.getContractSymbol(appTokenAdd.getTokenAddress());
		if(StringUtils.isEmpty(symbol)) {
			throw new BussinessException(40004,"未查询到代币编号");
		}
		
		UserToken userToken = new UserToken();
		String tokenId = Uuid.getUuid();
		userToken.setId(tokenId);
		userToken.setAppId(appId);
		userToken.setWalletId(walletId);
		userToken.setTokenAddress(appTokenAdd.getTokenAddress());
		userToken.setTokenType(appTokenAdd.getTokenType());
		userToken.setSymbol(symbol);
		userToken.setChain("0");
		walletDao.addUserToken(userToken);
		
		ResponseToken responseToken = new ResponseToken();
		responseToken.setId(tokenId);
		responseToken.setTokenAddress(appTokenAdd.getTokenAddress());
		responseToken.setTokenType(appTokenAdd.getTokenType());
		responseToken.setSymbol(appTokenAdd.getSymbol());
		responseToken.setChain(appTokenAdd.getChain());
		return ResponseBase.createResponse(true, 10000, "新增代币成功", responseToken, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<String>> deleteUserToken(String accessToken, String id, String walletId) {
		
		walletDao.deleteUserToken(id);
		return ResponseBase.createResponse(true, 10000, "删除代币成功", "", HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<List<UserToken>>> listUserToken(String accessToken, String walletId) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		List<UserToken> list = walletDao.queryUserToken(walletId,appId);
		return ResponseBase.createResponse(true, 10000, "查询代币成功", list, HttpStatus.OK);
	}
	public List<ResponseBalance> balance(String accessToken, String walletId) throws Exception {
		
		List<Account> accountList = accountDao.queryAccountList(walletId);//账户列表
		String appId = accessTokenUtil.getAppId(accessToken);//appid
		List<UserToken> list = walletDao.queryUserToken(walletId,appId);//token列表
		
		List<ResponseBalance> balanceList = new ArrayList<ResponseBalance>();//用户余额
		
		for (int i = 0; i < accountList.size(); i++) {
			
			String chain = accountList.get(i).getChain();//账户类型 墨客or井通
			String address = accountList.get(i).getAddress();//账户地址
			
			ResponseBalance responseBalance = new ResponseBalance();//该账户余额信息
			List<ResponseBalanceItem> balanceItemList = new ArrayList<ResponseBalanceItem>();//账户余额列表
			responseBalance.setAddress(address);//设置账户地址
			responseBalance.setList(balanceItemList);//设置账户余额列表
			balanceList.add(responseBalance);
			
			if("0".equals(chain)) {//墨客
				String value = moac.mcGetBalance(address).toString();
				ResponseBalanceItem balanceItem = new ResponseBalanceItem();
				balanceItem.setChain("0");//区块链类型-墨客or井通
				balanceItem.setSymbol("MC");//代币名
				balanceItem.setTokenAddress("");//代币地址
				balanceItem.setTokenType("");//代币类型 20 or 721
				balanceItem.setValue(value);//代币余额
				balanceItemList.add(balanceItem);//moac余额
				
				for (int j = 0; j < list.size(); j++) {
					String tokenAddress = list.get(j).getTokenAddress();//代币地址
					String tokenType = list.get(j).getTokenType();//0:20,1:721
					String contractBalance = moac.getContractBalance(address, tokenAddress);//代币余额
					if("0".equals(tokenType)) {
						String contractDecimalsErc20 = moac.getContractDecimalsErc20(tokenAddress);
						contractBalance = new BigDecimal(contractBalance)
								.divide(new BigDecimal(10).pow(Integer.parseInt(contractDecimalsErc20))).toString();
					}
					String contractSymbol = moac.getContractSymbol(tokenAddress);//代币名
					
					ResponseBalanceItem balanceItemToken = new ResponseBalanceItem();
					balanceItemToken.setChain("0");
					balanceItemToken.setSymbol(contractSymbol);
					balanceItemToken.setTokenAddress(tokenAddress);
					balanceItemToken.setTokenType(tokenType);
					balanceItemToken.setValue(contractBalance);
					balanceItemList.add(balanceItemToken);//代币余额
				}
				
			}else if("1".equals(chain)) {//井通
				balanceItemList = jingTong.getBalance(address);
				responseBalance.setList(balanceItemList);//设置账户余额列表
			}
		}
		
		return balanceList;
	}
	public String updateResetPassword(String walletId) {
		
		Object value = redisUtil.get("reset-"+walletId);
		if(value==null) {
			return "链接已失效";
		}
		Wallet wallet = new Wallet();
		wallet.setId(walletId);
		String payPsw = new PasswordGenerator(8, 2).generateRandomPassword();
		wallet.setPayPsw(encryptSHA.SHA256(payPsw));
		try {//重置密码并修改
			walletDao.updateWalletById(wallet);
			redisUtil.remove("reset-"+walletId);
			return "true:"+payPsw;
		} catch (Exception e) {
			e.printStackTrace();
			return "密码重置失败";
		}
	}
	public ResponseEntity<ResponseBase<String>> updateResetPassword(String walletId, String walletToken,String newPsw) {
		
		Object value = redisUtil.get("reset-"+walletId);
		if(value==null) {
			return ResponseBase.createResponse(false, "链接已失效，请重新重置", "", HttpStatus.OK);
		}
		if(!value.toString().equals(walletToken)) {
			return ResponseBase.createResponse(false, "链接已失效，请重新重置", "", HttpStatus.OK);
		}
		if(!newPsw
				.matches("^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$")) {
			return ResponseBase.createResponse(false, "新支付密码格式不正确", "", HttpStatus.OK);
		}
		Wallet wallet = new Wallet();
		wallet.setId(walletId);
		wallet.setPayPsw(encryptSHA.SHA256(newPsw));
		try {//重置密码并修改
			walletDao.updateWalletById(wallet);
			redisUtil.remove("reset-"+walletId);
			return ResponseBase.createResponse(true, "密码重置成功", "", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBase.createResponse(false, "密码重置失败", "", HttpStatus.OK);
		}
	}

	
}

