package com.web.frame.service.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.web.AccountDao;
import com.web.frame.dao.web.MoacSubchainDao;
import com.web.frame.dao.web.WalletDao;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.response.ResponseSubchainData;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.Scs;
import com.web.frame.entity.table.web.Subchain;
import com.web.frame.entity.table.web.Vnode;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.EncryptSHA;
import com.web.frame.util.JsonFormat;
import com.web.frame.util.Md5;
import com.web.frame.util.Uuid;
import com.web.frame.util.moac.Moac;
import com.web.frame.util.moac.MoacMicro;
import com.web.frame.util.moac.micro.DeployMicro;

@Service
@Transactional
public class MoacSubchainContractService {
	
	@Autowired
	private Moac moac;
	@Autowired
	private MoacMicro moacMicro;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private EncryptSHA encryptSHA;
	@Autowired
	private MoacSubchainDao moacSubchainDao;
	@Autowired
	private DeployMicro deployMicro;
	
	public ResponseEntity<ResponseBase<Map>> deploy(String subchainId, String walletId, String accountId, String payPsw,
			String code, String abi, String monitorScsId, String monitorUrl) throws Exception {
		
		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(accountId, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);	
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String monitorUrlReal = "";
		if(StringUtils.isNotEmpty(monitorUrl)) {
			monitorUrlReal = monitorUrl;
		}else {
			Scs scs = moacSubchainDao.queryScsByScsId(monitorScsId);
			if(scs==null) {
				throw new BussinessException(40004, "未查到monitor信息");
			}
			if(StringUtils.isEmpty(scs.getMonitorUrl())) {
				throw new BussinessException(40004, "查询到MonitorUrl为空");
			}
			monitorUrlReal = scs.getMonitorUrl();
		}
		String hash = moacMicro.deployDapp(moac, account.getAddress(), privateKey, code,
				subchain.getSubchainBaseAddress(), subchain.getVia(), abi, monitorUrlReal, walletId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链业务合约部署调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> register(String subchainId, String walletId, String accountId,
			String payPsw, String code, String abi, String monitorScsId, String monitorUrl, String from, String contractAddress) throws Exception {
		
		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(accountId, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);	
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String monitorUrlReal = "";
		if(StringUtils.isNotEmpty(monitorUrl)) {
			monitorUrlReal = monitorUrl;
		}else {
			Scs scs = moacSubchainDao.queryScsByScsId(monitorScsId);
			if(scs==null) {
				throw new BussinessException(40004, "未查到monitor信息");
			}
			if(StringUtils.isEmpty(scs.getMonitorUrl())) {
				throw new BussinessException(40004, "查询到MonitorUrl为空");
			}
			monitorUrlReal = scs.getMonitorUrl();
		}
		
		String hash = moacMicro.registerDapp(moac, account.getAddress(), privateKey, code, subchain.getSubchainBaseAddress(),
				subchain.getVia(), from, abi, contractAddress, subchain.getDappbaseAddress(), monitorUrlReal, walletId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链dappbase注册(registerDapp)业务合约调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> callTransaction(String accessToken, String walletId,
			String accountId, String contractAddress, String functionName, String inParams, String outParams,
			String payPsw, String value, String nonce, String subchainId, String monitorScsId, String monitorUrl) throws Exception {
		
		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(accountId, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);	
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String monitorUrlReal = "";
		if(StringUtils.isNotEmpty(monitorUrl)) {
			monitorUrlReal = monitorUrl;
		}else {
			Scs scs = moacSubchainDao.queryScsByScsId(monitorScsId);
			if(scs==null) {
				throw new BussinessException(40004, "未查到monitor信息");
			}
			if(StringUtils.isEmpty(scs.getMonitorUrl())) {
				throw new BussinessException(40004, "查询到MonitorUrl为空");
			}
			monitorUrlReal = scs.getMonitorUrl();
		}
		List<Type> in = moac.getInParams(inParams);
		List<TypeReference<?>> out = moac.getOutParams(outParams);
		String hash = moacMicro.callContractTransactionMicro(moac, subchain.getSubchainBaseAddress(), contractAddress, account.getAddress(), privateKey,
				functionName, in, out, value, nonce, subchain.getVia(), monitorUrlReal, walletId, subchain.getErc20Address());
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链业务合约方法("+functionName+")调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<String>> call(String accessToken, String walletId, String contractAddress,
			String functionName, String inParams, String accountId, String subchainId, String monitorScsId, String monitorUrl) throws Exception {
		
		List<Object> inParamsList = new ArrayList<Object>();
		try {
			inParamsList = JsonFormat.parseJSON2List(inParams);
		} catch (Exception e) {
			throw new BussinessException(40004, "入参格式不正确");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(accountId, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);	
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		String monitorUrlReal = "";
		if(StringUtils.isNotEmpty(monitorUrl)) {
			monitorUrlReal = monitorUrl;
		}else {
			Scs scs = moacSubchainDao.queryScsByScsId(monitorScsId);
			if(scs==null) {
				throw new BussinessException(40004, "未查到monitor信息");
			}
			if(StringUtils.isEmpty(scs.getMonitorUrl())) {
				throw new BussinessException(40004, "查询到MonitorUrl为空");
			}
			monitorUrlReal = scs.getMonitorUrl();
		}
		String res = moacMicro.callContractMicro(account.getAddress(), subchain.getSubchainBaseAddress(), contractAddress,
				functionName, monitorUrlReal, inParamsList);
		return ResponseBase.createResponse(true, 10000, "子链业务合约方法("+functionName+")调用成功", res, HttpStatus.OK);
	}

	
	
	
}

