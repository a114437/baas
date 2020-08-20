package com.web.frame.web.listener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.web.frame.dao.web.AppDao;
import com.web.frame.dao.web.WalletDao;
import com.web.frame.entity.table.web.App;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.util.Md5;
import com.web.frame.util.redis.RedisUtil;

@Component
public class StartupFinishListener implements ApplicationListener<ContextRefreshedEvent>{
	
	
	private Logger logger = Logger.getLogger(StartupFinishListener.class);
	
	@Autowired
	private AppDao appDao;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		List<App> list =  appDao.queryAllApp();
		for (int i = 0; i < list.size(); i++) {
			App app = list.get(i);
			if(redisUtil.get(app.getAppId())!=null) {//重启后redis缓存的token仍可使用
				App cacheApp = (App) redisUtil.get(app.getAppId());
				app.setAccessToken(cacheApp.getAccessToken());
			}
			try {
				app.setAppSecret(Md5.decrypt(app.getAppSecret()));
				redisUtil.set(app.getAppId(), app);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Wallet> walletList = walletDao.queryAllWallet();
		for (int i = 0; i < walletList.size(); i++) {
			Wallet wallet = walletList.get(i);
			redisUtil.set(wallet.getId(), wallet);
		}
		
		logger.info("启动完成");
	}

	
}