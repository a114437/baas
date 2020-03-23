var baseUrl = "https://dapi.sparkchain.cn/";

/**
 * 获取墨客币价格(美元)
 */
function getMoacPrice(){
	var moacPrice = 0;
	$.ajax({
		type:"GET",
		url:path+"/autwallet/queryMoacPrice.do",
		async:false,
		success:function(data){
			try{
				moacPrice = parseFloat(data);
			}catch(e){
				moacPrice = 0;
			}
		}
	});
	return moacPrice;
}

/**
 * 获取AUT价格(元)
 */
function getAutPrice(){
	var autPrice = 0;
	$.ajax({
		type:"POST",
		url:path+"/autwallet/queryHuangjin.do",
		async:false,
		success:function(data){
			autPrice = parseFloat(data)/100000;
		}
	});
	return autPrice;
}

/**
 * 外部转账后需要同步余额
 * @param walletAddr
 * @param chainCode
 * @param tokenCode
 */
function syncBalance(walletAddr,chainCode,tokenCode){
	var res = {};
	$.ajax({
		type:"POST",
		url:path+"/autwallet/syncBalance.do",
		data:{
			walletAddr:walletAddr,
			chainCode:chainCode,
			tokenCode:tokenCode
		},
		async:false,
		success:function(data){
			res = JSON.parse(data);
		}
	});
	return res;
}

/**
 * 转账
 * @param srcAccount 支付方账户
 * @param payPassword 支付密码
 * @param chainCode 区块链编码
 * @param tokenCode 通证编码
 * @param destAccount 接收方账户
 * @param amount 转账金额
 */
function transfer(srcAccount,payPassword,chainCode,tokenCode,destAccount,amount,mark,callBack){
	$.ajax({
		type:"POST",
		url:path+"/autwallet/transfer.do",
		data:{
			srcAccount:srcAccount,
			payPassword:payPassword,
			chainCode:chainCode,
			tokenCode:tokenCode,
			destAccount:destAccount,
			amount:amount,
			memo:mark
		},
		success:function(data){
			callBack(JSON.parse(data));
		}
	});
}

/**
 * 验证支付密码
 * @param walletAddr
 * @param payPassword
 * @param callBack
 */
function validPayPassword(walletAddr,payPassword){
	var res = {};
	$.ajax({
		type:"POST",
		url:path+"/autwallet/validPayPassword.do",
		data:{
			walletAddr:walletAddr,
			payPassword:payPassword
		},
		async:false,
		success:function(data){
			res = JSON.parse(data);
		}
	});
	return res;
}

/**
 * 获取交易信息
 * @param account
 * @param chainCode
 * @param tranHash
 * @param callBack
 */
function getTransferInfo(account,chainCode,tranHash,callBack){
	$.ajax({
		type:"POST",
		url:baseUrl+"/v1/account/transInfo",
		data:{
			account:account,
			chainCode:chainCode,
			tranHash:tranHash
		},
		success:function(data){
			callBack(data);
		}
	});
}

/**
 * 获取账户支付历史
 * @param account
 * @param pagesize
 * @param pagenum
 * @param callBack
 */
function getTransferList(account,chainCode,tokenCode,pagenum,pagesize,callBack){
	
	$.ajax({//同步流水
		type:"POST",
		url:baseUrl+"/v1/account/syncWaterflow",
		data:{
			account:account,
			chainCode:chainCode,
			tokenCode:tokenCode
		},
		async:false,
		success:function(data){
			//callBack(data);
		}
	});
	
	$.ajax({//获取账户支付历史
		type:"POST",
		url:baseUrl+"/v1/account/transInfoList",
		data:{
			account:account,
			pagesize:pagesize,
			pagenum:pagenum
		},
		success:function(data){
			callBack(data);
		}
	});
}

/**
 * 修改查询密码
 * @param walletAddr 钱包地址
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 * @param callBack
 */
function updatePassword(walletAddr,oldPassword,newPassword,callBack){
	$.ajax({
		type:"POST",
		url:path+"/autwallet/updatePassword.do",
		data:{
			walletAddr:walletAddr,
			oldPassword:oldPassword,
			newPassword:newPassword
		},
		success:function(data){
			callBack(JSON.parse(data));
		}
	});
}

/**
 * 修改支付密码
 * @param walletAddr
 * @param oldPayPassword
 * @param newPayPassword
 */
function updatePayPassword(walletAddr,oldPayPassword,newPayPassword,callBack){
	$.ajax({
		type:"POST",
		url:path+"/autwallet/updatePayPassword.do",
		data:{
			walletAddr:walletAddr,
			oldPayPassword:oldPayPassword,
			newPayPassword:newPayPassword
		},
		success:function(data){
			callBack(JSON.parse(data));
		}
	});
}

/**
 * 验证查询密码
 * @param walletAddr
 * @param password
 * @param callBack
 */
function validPassword(walletAddr,password){
	var res = {};
	$.ajax({
		type:"POST",
		url:path+"/autwallet/validPassword.do",
		data:{
			walletAddr:walletAddr,
			password:password
		},
		async:false,
		success:function(data){
			res = JSON.parse(data);
		}
	});
	return res;
}

/**
 * 获取私钥
 * @param walletAddr
 * @param payPassword
 * @param chainCode
 * @param callBack
 */
function getPrivateKey(walletAddr,payPassword,chainCode,callBack){
	$.ajax({
		type:"POST",
		url:path+"/autwallet/getPrivateKey.do",
		data:{
			walletAddr:walletAddr,
			payPassword:payPassword,
			chainCode:chainCode
		},
		success:function(data){
			callBack(JSON.parse(data));
		}
	});
}

/*获取应用的区块链信息*/
function chainInfo(callBack){
	$.ajax({
		type:"POST",
		url:path+"/autwallet/chainInfo.do",
		success:function(data){
			callBack(JS0N.parse(data));
		}
	});
}