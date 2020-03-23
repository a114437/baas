package com.web.frame.service.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.web.AccountDao;
import com.web.frame.dao.web.AppDao;
import com.web.frame.dao.web.WalletDao;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.response.ResponseAccount;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.response.ResponseBalanceItem;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.AccessTokenUtil;
import com.web.frame.util.EncryptSHA;
import com.web.frame.util.Md5;
import com.web.frame.util.Uuid;
import com.web.frame.util.jingtong.JingTong;
import com.web.frame.util.moac.Moac;
import com.web.frame.util.redis.RedisUtil;

@Service
@Transactional
public class AccountService {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private Moac moac;
	@Autowired
	private JingTong jingTong;
	@Autowired
	private AccessTokenUtil accessTokenUtil;
	@Autowired
	private AppDao appDao;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private EncryptSHA encryptSHA;
	
	public ResponseEntity<ResponseBase<ResponseAccount>> createMoacAccount(String accessToken, String walletId) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		List<String> list = moac.personNewAccount(walletId);
		
		Account account = new Account();
		String accountId = Uuid.getUuid();
		account.setId(accountId);
		account.setAppId(appId);
		account.setWalletId(walletId);
		account.setAddress(list.get(0));
		account.setPublicKey(list.get(1));
		account.setPrivateKey(Md5.encrypt(list.get(2)));//私钥加密存储
		account.setChain("0");
		accountDao.addAccount(account);//数据库保存
		
		ResponseAccount responseAccount = new ResponseAccount();
		responseAccount.setAddress(list.get(0));
		responseAccount.setPublicKey(list.get(1));
		responseAccount.setPrivateKey(list.get(2));
		responseAccount.setWalletId(walletId);
		responseAccount.setChain("0");
		responseAccount.setId(accountId);
		
		return ResponseBase.createResponse(true, 10000, "创建成功", responseAccount, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseBase<ResponseAccount>> importMoacAccount(String accessToken, String walletId,
			String privateKey) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		List<String> list = moac.importAccountByPrivateKey(privateKey);
		
		List<Account> saveAccount = accountDao.queryAccountExist(walletId,list.get(0));//查询该钱包下是否有该账户
		if(saveAccount.size()>0) {
			throw new BussinessException(40004,"账户已存在");
		}
		
		Account account = new Account();
		String accountId = Uuid.getUuid();
		account.setId(accountId);
		account.setAppId(appId);
		account.setWalletId(walletId);
		account.setAddress(list.get(0));
		account.setPublicKey(list.get(1));
		account.setPrivateKey(Md5.encrypt(list.get(2)));//私钥加密存储
		account.setChain("0");
		accountDao.addAccount(account);//数据库保存
		
		ResponseAccount responseAccount = new ResponseAccount();
		responseAccount.setAddress(list.get(0));
		responseAccount.setPublicKey(list.get(1));
		responseAccount.setPrivateKey(list.get(2));
		responseAccount.setWalletId(walletId);
		responseAccount.setChain("0");
		responseAccount.setId(accountId);
		
		return ResponseBase.createResponse(true, 10000, "导入成功", responseAccount, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<ResponseAccount>> createJingTumAccount(String accessToken, String walletId) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		List<String> list = jingTong.createWallet();
		
		Account account = new Account();
		String accountId = Uuid.getUuid();
		account.setId(accountId);
		account.setAppId(appId);
		account.setWalletId(walletId);
		account.setAddress(list.get(0));
		account.setPublicKey(list.get(1));
		account.setPrivateKey(Md5.encrypt(list.get(2)));//私钥加密存储
		account.setChain("1");
		accountDao.addAccount(account);//数据库保存
		
		ResponseAccount responseAccount = new ResponseAccount();
		responseAccount.setAddress(list.get(0));
		responseAccount.setPublicKey(list.get(1));
		responseAccount.setPrivateKey(list.get(2));
		responseAccount.setWalletId(walletId);
		responseAccount.setChain("1");
		responseAccount.setId(accountId);
		
		return ResponseBase.createResponse(true, 10000, "创建成功", responseAccount, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<ResponseAccount>> importJingTumAccount(String accessToken,
			String walletId, String privateKey) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		List<String> list = jingTong.importAccountBySecret(privateKey);
		
		List<Account> saveAccount = accountDao.queryAccountExist(walletId,list.get(0));//查询该钱包下是否有该账户
		if(saveAccount.size()>0) {
			throw new BussinessException(40004,"账户已存在");
		}
		
		Account account = new Account();
		String accountId = Uuid.getUuid();
		account.setId(accountId);
		account.setAppId(appId);
		account.setWalletId(walletId);
		account.setAddress(list.get(0));
		account.setPublicKey(list.get(1));
		account.setPrivateKey(Md5.encrypt(list.get(2)));//私钥加密存储
		account.setChain("1");
		accountDao.addAccount(account);//数据库保存
		
		ResponseAccount responseAccount = new ResponseAccount();
		responseAccount.setAddress(list.get(0));
		responseAccount.setPublicKey(list.get(1));
		responseAccount.setPrivateKey(list.get(2));
		responseAccount.setWalletId(walletId);
		responseAccount.setChain("1");
		responseAccount.setId(accountId);
		
		return ResponseBase.createResponse(true, 10000, "导入成功", responseAccount, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<String>> deleteAccount(String id) throws Exception {
		
		Account account = accountDao.queryAccountById(id);
		if(account==null)
			throw new BussinessException(40004,"账户不存在");
		accountDao.deleteAccount(id);
		return ResponseBase.createResponse(true, 10000, "删除成功", "", HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<List<ResponseAccount>>> listAccount(String accessToken, String walletId) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		List<Account> list = accountDao.queryAccountList(walletId);
		
		List<ResponseAccount> responseList = new ArrayList<ResponseAccount>();
		for (int i = 0; i < list.size(); i++) {
			ResponseAccount responseAccount = new ResponseAccount();
			responseAccount.setAddress(list.get(i).getAddress());
			responseAccount.setPublicKey(list.get(i).getPublicKey());
			responseAccount.setPrivateKey("");
			responseAccount.setWalletId(list.get(i).getWalletId());
			responseAccount.setChain(list.get(i).getChain());
			responseAccount.setId(list.get(i).getId());
			responseList.add(responseAccount);
		}
		return ResponseBase.createResponse(true, 10000, "查询成功", responseList, HttpStatus.OK);
	}

	
	public List<Account> listAccount(String walletId){
		
		return accountDao.queryAccountList(walletId);
	}

	public ResponseBalance balance(String id, String walletId, String accessToken) throws Exception {
		
		String appId = accessTokenUtil.getAppId(accessToken);
		
		Account account = accountDao.queryAccountById(id);
		if(account==null)
			throw new BussinessException(40004,"账户不存在");
		
		
		List<UserToken> list = walletDao.queryUserToken(walletId,appId);//token列表
			
		String chain = account.getChain();//账户类型 墨客or井通
		String address = account.getAddress();//账户地址
		
		ResponseBalance responseBalance = new ResponseBalance();//该账户余额信息
		List<ResponseBalanceItem> balanceItemList = new ArrayList<ResponseBalanceItem>();//账户余额列表
		responseBalance.setAddress(address);//设置账户地址
		responseBalance.setList(balanceItemList);//设置账户余额列表
		
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
		
		return responseBalance;
	}


	public ResponseEntity<ResponseBase<Account>> export(String id, String walletId, String accessToken, String payPsw) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id,walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		account.setPrivateKey(Md5.decrypt(account.getPrivateKey()));
		return ResponseBase.createResponse(true, 10000, "导出成功", account, HttpStatus.OK);
	}
	
	
}

