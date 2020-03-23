package com.web.frame.service.web;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.web.frame.entity.response.ResponseTransactionJingTum;
import com.web.frame.entity.response.ResponseTransactionMoac;
import com.web.frame.entity.response.ResponseTxJson;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.Transaction;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.EncryptSHA;
import com.web.frame.util.Md5;
import com.web.frame.util.jingtong.JingTong;
import com.web.frame.util.moac.Moac;

@Service
@Transactional
public class JingTongService {
	
	@Autowired
	private JingTong jingTong;
	@Autowired
	private EncryptSHA encryptSHA;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private TransactionDao transactionDao;
	

	public ResponseEntity<ResponseBase<ResponseTransactionJingTum>> transaction(String accessToken, String walletId, String hash) throws Exception {
		
		ResponseTransactionJingTum transaction = jingTong.getTransaction(hash);
		
		return ResponseBase.createResponse(true, 10000, "查询交易成功", transaction, HttpStatus.OK);
	}


	public ResponseEntity<ResponseBase<ResponseTxJson>> transfer(String accessToken, String walletId, String id,
			String payPsw, String to, String currency, String issuer, String value, String data) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		ResponseTxJson responseTxJson = jingTong.pay(account.getAddress(), privateKey, to, value, currency, issuer, data);
		
		try {
			Transaction transaction = new Transaction();
			transaction.setWalletId(walletId);
			transaction.setChain("1");
			transaction.setFromAddress(account.getAddress());
			transaction.setToAddress(to);
			transaction.setSymbol(StringUtils.isEmpty(currency)?"SWT":currency);
			transaction.setTokenAddress(issuer);
			transaction.setHash(responseTxJson.getHash());
			transactionDao.addTransaction(transaction);//新增交易记录
		} catch (Exception e) {
		}
		
		return ResponseBase.createResponse(true, 10000, "转账调用成功", responseTxJson, HttpStatus.OK);
	}

	
	
}

