package com.web.frame.dao.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.web.frame.dao.JdbcBaseDao;
import com.web.frame.entity.table.web.App;
import com.web.frame.entity.table.web.AppToken;

@Repository
public class AppDao{
	
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public void addApp(App app) throws Exception {
		
		jdbcBaseDao.insert(app);
	}

	public List<App> queryAllApp() {
		
		String sql = "SELECT * FROM sfb_app WHERE IS_DELETE = '0'";
		return jdbcBaseDao.query(sql, App.class);
	}

	public void updateAppByAppId(App app) throws Exception {
		
		jdbcBaseDao.update(app, "appId");
	}

	public void addAppToken(AppToken appToken) throws Exception {
		
		jdbcBaseDao.insert(appToken);
	}


	public List<AppToken> queryAppTokenExist(String tokenAddress, String symbol, String chain, String appId) {
		
		String sql = "SELECT id FROM sfb_app_token WHERE IS_DELETE = '0'"
				+ " AND token_address = '"+tokenAddress+"' AND app_id = '"+appId+"' "
				+ " AND symbol = '"+symbol+"' AND chain = '"+chain+"' ";
		return jdbcBaseDao.query(sql, AppToken.class);
	}

	public void deleteAppToken(String ids) {
		
		jdbcBaseDao.batchDeleteUpdate(ids, "sfb_app_token", "id");
	}

	public List<AppToken> queryAppToken(String appId) {
		
		String sql = "SELECT * FROM sfb_app_token WHERE IS_DELETE = '0' AND app_id = '"+appId+"'";
		return jdbcBaseDao.query(sql, AppToken.class);
	}


	
}
