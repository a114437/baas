package com.web.frame.service.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.web.TransactionDao;
import com.web.frame.entity.base.BoostrapTable;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.response.ResponseAccount;
import com.web.frame.entity.response.ResponseBalance;
import com.web.frame.entity.table.web.Transaction;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionDao transactionDao;
	
	public ResponseEntity<ResponseBase<BoostrapTable<Transaction>>> listTransaction(String accessToken, 
			String walletId, String page, String rows) {
		
		List<Transaction> list = new ArrayList<Transaction>();
		int count = transactionDao.queryTransactionCount(walletId, "", "");
		if(count==0) {
			return ResponseBase.createResponse(true, 10000, "查询交易记录成功", new BoostrapTable<Transaction>(count, list), HttpStatus.OK);
		}
		list = transactionDao.queryTransaction(walletId,"","",page,rows);
		return ResponseBase.createResponse(true, 10000, "查询交易记录成功", new BoostrapTable<Transaction>(count, list), HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<BoostrapTable<Transaction>>> listMoac(String walletId, String accessToken,
			String address, String page, String rows) {
		
		List<Transaction> list = new ArrayList<Transaction>();
		int count = transactionDao.queryTransactionCount(walletId, "0", address);
		if(count==0) {
			return ResponseBase.createResponse(true, 10000, "查询墨客交易记录成功", new BoostrapTable<Transaction>(count, list), HttpStatus.OK);
		}
		list = transactionDao.queryTransaction(walletId, "0", address,page,rows);
		return ResponseBase.createResponse(true, 10000, "查询墨客交易记录成功", new BoostrapTable<Transaction>(count, list), HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<BoostrapTable<Transaction>>> listJingTum(String walletId, String accessToken,
			String address, String page, String rows) {
		
		List<Transaction> list = new ArrayList<Transaction>();
		int count = transactionDao.queryTransactionCount(walletId, "1", address);
		if(count==0) {
			return ResponseBase.createResponse(true, 10000, "查询井通交易记录成功", new BoostrapTable<Transaction>(count, list), HttpStatus.OK);
		}
		list = transactionDao.queryTransaction(walletId, "1", address,page,rows);
		return ResponseBase.createResponse(true, 10000, "查询井通交易记录成功", new BoostrapTable<Transaction>(count, list), HttpStatus.OK);
	}
	
	
}

