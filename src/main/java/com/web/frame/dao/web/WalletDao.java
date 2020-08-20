package com.web.frame.dao.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.web.UserToken;
import com.web.frame.entity.table.web.Wallet;

@Repository
public class WalletDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public void addWallet(Wallet wallet) throws Exception {
		
		jdbcBaseDao.insert(wallet);
	}

	public Wallet queryWalletById(String walletId) {
		
		String sql = "SELECT * FROM sfb_wallet WHERE id = '"+walletId+"'";
		return jdbcBaseDao.queryUnique(sql, Wallet.class);
	}

	public void updateWalletById(Wallet wallet) throws Exception {
		
		jdbcBaseDao.update(wallet, "id");
	}

	public List<Wallet> queryAllWallet() {
		
		String sql = "SELECT * FROM sfb_wallet WHERE is_delete = '0'";
		return jdbcBaseDao.query(sql, Wallet.class);
	}

	public List<UserToken> queryUserTokenExist(String walletId, String tokenAddress) {
		
		String sql = "SELECT id FROM sfb_user_token WHERE IS_DELETE = '0' AND token_address = '"+tokenAddress+"'";
		return jdbcBaseDao.query(sql, UserToken.class);
	}
	
	public void deleteUserToken(String id) {
			
		jdbcBaseDao.batchDeleteUpdate(id, "sfb_user_token", "id");
	}
	
	public List<UserToken> queryUserToken(String walletId, String appId) {
		
		String sql =  
				"SELECT id,wallet_id,token_address,token_type,symbol,chain,create_date FROM sfb_user_token"
				+ " WHERE wallet_id = '"+walletId+"'"
				+ " AND is_delete = '0' "
				+ " UNION "
				+" SELECT 'system','system',token_address,token_type,symbol,chain,create_date FROM sfb_app_token"
				+ " WHERE app_id = '"+appId+"' AND is_delete = '0' ";
		return jdbcBaseDao.query(sql, UserToken.class);
	}

	public void addUserToken(UserToken userToken) throws Exception {
		
		jdbcBaseDao.insert(userToken);
	}


	
}
