package com.web.frame.service.web;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.web.AccountDao;
import com.web.frame.dao.web.TransactionDao;
import com.web.frame.dao.web.WalletDao;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.response.ResponseTransactionMoac;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.Transaction;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.EncryptSHA;
import com.web.frame.util.Md5;
import com.web.frame.util.moac.Moac;

@Service
@Transactional
public class MoacService {
	
	@Autowired
	private Moac moac;
	@Autowired
	private EncryptSHA encryptSHA;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private TransactionDao transactionDao;
	
	public ResponseEntity<ResponseBase<Map<String, String>>> getNonce(String accessToken, String walletId, String id) throws Exception {
		
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String nonce = moac.getNonce(account.getAddress()).toString();
		Map<String,String> map = new HashMap<String,String>();
		map.put("nonce", nonce);
		return ResponseBase.createResponse(true, 10000, "获取nonce成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> transfer(String accessToken, String walletId, String id, String payPsw, String to, String gas,
			String value, String data, String nonce) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		String hash = moac.sendRawTransaction(account.getAddress(), privateKey, to, gas, value, data, nonce);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		
		try {
			Transaction transaction = new Transaction();
			transaction.setWalletId(walletId);
			transaction.setChain("0");
			transaction.setFromAddress(account.getAddress());
			transaction.setToAddress(to);
			transaction.setSymbol("MC");
			transaction.setTokenAddress("");
			transaction.setHash(hash);
			transactionDao.addTransaction(transaction);//新增交易记录
		} catch (Exception e) {
		}
		
		return ResponseBase.createResponse(true, 10000, "转账调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<ResponseTransactionMoac>> transaction(String accessToken, String walletId, String hash) throws Exception {
		
		return ResponseBase.createResponse(true, 10000, "查询交易成功", moac.mcGetTransactionByHash(hash), HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> blockNumber(String accessToken, String walletId) throws Exception {
		
		String blockNumber = moac.mcBlockNumber().toString();
		Map<String,String> map = new HashMap<String,String>();
		map.put("blockNumber", blockNumber);
		return ResponseBase.createResponse(true, 10000, "获取区块号成功", map, HttpStatus.OK);
	}

	
	
}

