package com.web.frame.dao.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.web.Transaction;

@Repository
public class TransactionDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public void addTransaction(Transaction transaction) throws Exception {
		
		jdbcBaseDao.insert(transaction);
	}

	public List<Transaction> queryTransaction(String walletId, String chain, String address, String page, String rows) {
		
		String sql = "SELECT * FROM sfb_transaction WHERE 1=1 ";
		if(StringUtils.isNotEmpty(walletId)) {
			sql+=" AND wallet_id = '"+walletId+"'";
		}
		if(StringUtils.isNotEmpty(chain)) {
			sql+=" AND chain = '"+chain+"'";
		}
		if(StringUtils.isNotEmpty(address)) {
			sql+=" AND (to_address = '"+address+"' or from_address = '"+address+"')";
		}
		sql += " ORDER BY create_date DESC";
		return jdbcBaseDao.query(sql,page,rows,Transaction.class);
	}

	public int queryTransactionCount(String walletId, String chain, String address) {
		
		String sql = "SELECT IFNULL(COUNT(ID),0) as COUNT FROM sfb_transaction WHERE 1=1 ";
		if(StringUtils.isNotEmpty(walletId)) {
			sql+=" AND wallet_id = '"+walletId+"'";
		}
		if(StringUtils.isNotEmpty(chain)) {
			sql+=" AND chain = '"+chain+"'";
		}
		if(StringUtils.isNotEmpty(address)) {
			sql+=" AND (to_address = '"+address+"' or from_address = '"+address+"')";
		}
		sql += " ORDER BY create_date DESC";
		return Integer.parseInt(jdbcBaseDao.query(sql).get(0).get("COUNT").toString());
	}

	

	
}
