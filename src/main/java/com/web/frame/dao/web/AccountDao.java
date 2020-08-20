package com.web.frame.dao.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.entity.table.web.Wallet;

@Repository
public class AccountDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public void addAccount(Account account) throws Exception {
		
		jdbcBaseDao.insert(account);
	}

	public List<Account> queryAccountExist(String walletId, String address) {
		
		String sql = "SELECT id from sfb_account WHERE IS_DELETE = '0'"
				+ " AND wallet_id = '"+walletId+"' AND address = '"+address+"'";
		return jdbcBaseDao.query(sql,Account.class);
	}

	public void deleteAccount(String ids) {
		
		jdbcBaseDao.batchDeleteUpdate(ids, "sfb_account", "id");
	}

	public List<Account> queryAccountList(String walletId) {
		
		String sql = "SELECT * FROM sfb_account WHERE IS_DELETE = '0'"
				+ " AND wallet_id = '"+walletId+"' ORDER BY create_date ASC";
		return jdbcBaseDao.query(sql, Account.class);
	}

	public Account queryAccountById(String id) {
		
		String sql = "SELECT * FROM sfb_account WHERE IS_DELETE = '0' AND id = '"+id+"'";
		return jdbcBaseDao.queryUnique(sql, Account.class);
	}

	public Account queryAccountByIdAndWalletId(String id, String walletId) {
		
		String sql = "SELECT * FROM sfb_account WHERE id = '"+id+"' AND wallet_id = '"+walletId+"'";
		return jdbcBaseDao.queryUnique(sql, Account.class);
	}







	
}
