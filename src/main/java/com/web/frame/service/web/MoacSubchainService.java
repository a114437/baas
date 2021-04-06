package com.web.frame.service.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
public class MoacSubchainService {

	@Autowired
	private Moac moac;
	@Autowired
	private DeployMicro deployMicro;
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

	/**
	 * 部署子链
	 * @param accessToken
	 * @param walletId
	 * @param id
	 * @param payPsw
	 * @param via 收益账号
	 * @param vnodeCode vnode合约编码
	 * @param vnodeBmin vnode押金
	 * @param subchainProtocolBaseCode 子链协议合约编码
	 * @param subchainProtocolName 子链协议名称
	 * @param subchainProtocolBmin 子链协议押金数
	 * @param subchainProtocolType 子链协议类型
	 * @param erc20Code ERC20代币编码
	 * @param subchainBaseCode 子链合约编码
	 * @param subchainBaseErcRate ERC代币与子链币兑换比率
	 * @param subchainBaseMin 子链scs最少数
	 * @param subchainBaseMax 子链scs最大数
	 * @param subchainBaseFlushRound 子链刷新周期
	 * @param subchainBaseMonitorBond  子链monitor最小押金数
	 * @param vnode vnode列表信息
	 * @param scs scs列表信息
	 * @param addFund 子链充值数
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseBase<Map<String, String>>> deploy(String accessToken, String walletId, String id,
			String payPsw, String via, String vnodeCode, String vnodeBmin, String subchainProtocolBaseCode,
			String subchainProtocolName, String subchainProtocolBmin, String subchainProtocolType, String erc20Code,
			String subchainBaseCode, String subchainBaseErcRate, String subchainBaseMin, String subchainBaseMax,
			String subchainBaseFlushRound, String subchainBaseMonitorBond,
			String vnode, String scs, String addFund) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥

		//子链id
		String subchainId = Uuid.getUuid();
		Subchain subchain = new Subchain();
		subchain.setId(subchainId);
		subchain.setAccountId(id);
		subchain.setVia(via);
		subchain.setVnodeCode(vnodeCode);
		subchain.setVnodeBmin(vnodeBmin);
		subchain.setSubchainProtocolBaseCode(subchainProtocolBaseCode);
		subchain.setSubchainProtocolName(subchainProtocolName);
		subchain.setSubchainProtocolBmin(subchainProtocolBmin);
		subchain.setSubchainProtocolType(subchainProtocolType);
		subchain.setErc20Code(erc20Code);
		subchain.setSubchainBaseCode(subchainBaseCode);
		subchain.setSubchainBaseErcRate(subchainBaseErcRate);
		subchain.setSubchainBaseMin(subchainBaseMin);
		subchain.setSubchainBaseMax(subchainBaseMax);
		subchain.setSubchainBaseFlushRound(subchainBaseFlushRound);
		subchain.setSubchainBaseMonitorBond(subchainBaseMonitorBond);
		//插入子链数据
		moacSubchainDao.addSubchain(subchain);

		deployMicro.deployErcSubChain(account.getAddress(), privateKey, via, vnodeCode, vnodeBmin, subchainProtocolBaseCode,
				subchainProtocolName, subchainProtocolBmin, subchainProtocolType, erc20Code, subchainBaseCode,
				subchainBaseErcRate, subchainBaseMin, subchainBaseMax, subchainBaseFlushRound, subchainBaseMonitorBond,
				vnode, scs, addFund,subchainId,walletId);

		Map<String,String> map = new HashMap<String,String>();
		map.put("subchainId", subchainId);
		return ResponseBase.createResponse(true, 10000, "子链正在部署中，稍后通过subchainId查询", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> deployRand(String accessToken, String walletId, String id,
				String payPsw, String via, String vnodeCode, String vnodeBmin, String subchainProtocolBaseCode,
				String subchainProtocolName, String subchainProtocolBmin, String subchainProtocolType, String erc20Code,
				String subchainBaseCode, String subchainBaseErcRate, String subchainBaseMin, String subchainBaseMax,
				String subchainBaseFlushRound, String subchainBaseMonitorBond,
				String vnode, String scs, String addFund, String vssBaseCode, String vssBaseThreshold) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥

		//子链id
		String subchainId = Uuid.getUuid();
		Subchain subchain = new Subchain();
		subchain.setId(subchainId);
		subchain.setAccountId(id);
		subchain.setVia(via);
		subchain.setVnodeCode(vnodeCode);
		subchain.setVnodeBmin(vnodeBmin);
		subchain.setSubchainProtocolBaseCode(subchainProtocolBaseCode);
		subchain.setSubchainProtocolName(subchainProtocolName);
		subchain.setSubchainProtocolBmin(subchainProtocolBmin);
		subchain.setSubchainProtocolType(subchainProtocolType);
		subchain.setErc20Code(erc20Code);
		subchain.setSubchainBaseCode(subchainBaseCode);
		subchain.setSubchainBaseErcRate(subchainBaseErcRate);
		subchain.setSubchainBaseMin(subchainBaseMin);
		subchain.setSubchainBaseMax(subchainBaseMax);
		subchain.setSubchainBaseFlushRound(subchainBaseFlushRound);
		subchain.setSubchainBaseMonitorBond(subchainBaseMonitorBond);
		subchain.setVssBaseCode(vssBaseCode);
		subchain.setVssBaseThreshold(vssBaseThreshold);
		//插入子链数据
		moacSubchainDao.addSubchain(subchain);

		deployMicro.deployErcSubChainRand(account.getAddress(), privateKey, via, vnodeCode, vnodeBmin, subchainProtocolBaseCode,
				subchainProtocolName, subchainProtocolBmin, subchainProtocolType, erc20Code, subchainBaseCode,
				subchainBaseErcRate, subchainBaseMin, subchainBaseMax, subchainBaseFlushRound, subchainBaseMonitorBond,
				vnode, scs, addFund,subchainId,walletId,vssBaseCode,vssBaseThreshold);

		Map<String,String> map = new HashMap<String,String>();
		map.put("subchainId", subchainId);
		return ResponseBase.createResponse(true, 10000, "子链正在部署中，稍后通过subchainId查询", map, HttpStatus.OK);
	}

	/**
	 * 获取子链信息
	 * @param subchainId
	 * @return
	 */
	public ResponseEntity<ResponseBase<ResponseSubchainData>> getSubchainInfo(String subchainId) {

		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		List<Vnode> vnodeList = moacSubchainDao.queryVnodeBySubchainId(subchainId);
		List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"");
		ResponseSubchainData subchainData = new ResponseSubchainData();
		subchainData.setSubchain(subchain);
		subchainData.setVnodeList(vnodeList);
		subchainData.setScsList(scsList);
		return ResponseBase.createResponse(true, 10000, "子链部署信息查询成功", subchainData, HttpStatus.OK);
	}

	/**
	 * 子链开启注册
	 * @param subchainId
	 * @param payPsw
	 * @param id
	 * @param walletId
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseBase<Map>> resisterOpen(String subchainId, String walletId, String id, String payPsw) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}

		String hash = deployMicro.registerOpen(moac, moac.getNonce(account.getAddress()).toString(),
				subchain.getSubchainBaseAddress(), account.getAddress(), privateKey, walletId, subchainId);

		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "registerOpen调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> scsCount(String subchainId) throws Exception {

		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		String scsCount = deployMicro.scsCount(moac, subchain.getSubchainProtocolAddress(), "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60");
		Map<String,String> map = new HashMap<String,String>();
		map.put("scsCount", scsCount);
		return ResponseBase.createResponse(true, 10000, "获取协议池中scs个数成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> nodeCount(String subchainId) throws Exception {

		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		String nodeCount = deployMicro.nodeCount(moac, subchain.getSubchainBaseAddress(), "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60");
		Map<String,String> map = new HashMap<String,String>();
		map.put("nodeCount", nodeCount);
		return ResponseBase.createResponse(true, 10000, "获取子链中scs个数成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> resisterClose(String subchainId, String walletId, String id,
			String payPsw) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}

		String hash = deployMicro.registerClose(moac, moac.getNonce(account.getAddress()).toString(),
				subchain.getSubchainBaseAddress(), account.getAddress(), privateKey,walletId,subchainId);

		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "registerClose调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> registerAsMonitor(String subchainId, String walletId, String id,
			String payPsw, String scsAddress, String monitorUrl) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}

		String hash = deployMicro.registerAsMonitor(moac, moac.getNonce(account.getAddress()).toString(),
				scsAddress, monitorUrl, subchain.getSubchainBaseAddress(),
				account.getAddress(), privateKey, subchain.getSubchainBaseMonitorBond(),
				walletId, subchainId);
		if(StringUtils.isNotEmpty(hash)) {
			//异步获取monitor注册结果，并保存信息
			deployMicro.getResisterAsMonitorStatus(moac, hash, subchainId, scsAddress, monitorUrl);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "registerAsMonitor调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> deployDappBase(String subchainId, String walletId, String id,
			String payPsw, String dappbaseCode) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}

		List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
		if(scsList.size()==0) {
			throw new BussinessException(40004, "无monitor信息，请先添加monitor");
		}
		//账户在子链上的nonce
		String nonce = moacMicro.getNonce(account.getAddress(), subchain.getSubchainBaseAddress(),scsList.get(0).getMonitorUrl()).toString();
		String hash = deployMicro.deployDappBase(moac, nonce, subchain.getErc20Address(),
				subchain.getSubchainBaseErcRate(), account.getAddress(), privateKey, dappbaseCode,
				subchain.getSubchainBaseAddress(), subchain.getVia(), walletId, subchainId);

		if(StringUtils.isNotEmpty(hash)) {
			//异步获取部署结果并保存dappbase合约信息
			deployMicro.getDappBaseAddress(moac, hash, scsList.get(0).getMonitorUrl(), subchain.getSubchainBaseAddress(), subchainId);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "部署dappBase合约调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> registerScs(String subchainId, String walletId, String id, String payPsw, String scsAddress) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String hash = deployMicro.registerScs(moac, moac.getNonce(account.getAddress()).toString(), scsAddress, subchain.getSubchainProtocolAddress(),
				account.getAddress(), privateKey, subchain.getSubchainProtocolBmin(), walletId, subchainId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链协议合约register方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> registerAdd(String subchainId, String walletId, String id, String payPsw,
			String addNum) throws Exception {

		int addNumInt = 1;
		try {
			addNumInt = Integer.parseInt(addNum);
		} catch (Exception e) {
			throw new BussinessException(40004, "addNum请输入数字");
		}
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String hash = deployMicro.registerAdd(moac, moac.getNonce(account.getAddress()).toString(), addNumInt,
				subchain.getSubchainBaseAddress(), account.getAddress(), privateKey, walletId, subchainId);

		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链合约registerAdd方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> reset(String subchainId, String walletId, String id, String payPsw) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String hash = deployMicro.reset(moac, moac.getNonce(account.getAddress()).toString(), subchain.getSubchainBaseAddress(),
				account.getAddress(), privateKey, walletId, subchainId);

		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链合约reset方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> addFund(String subchainId, String walletId, String id, String payPsw,
			String value) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String hash = deployMicro.addFund(moac, moac.getNonce(account.getAddress()).toString(), value,
				subchain.getSubchainBaseAddress(), account.getAddress(), privateKey, walletId, subchainId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链合约addFund方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> close(String subchainId, String walletId, String id, String payPsw) throws Exception {

		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
		if(subchain==null) {
			throw new BussinessException(40004, "子链信息不存在");
		}
		String hash = deployMicro.close(moac, moac.getNonce(account.getAddress()).toString(),
				subchain.getSubchainBaseAddress(), account.getAddress(), privateKey, walletId, subchainId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链合约close方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> info(String subchainId, String walletId, String monitorScsId, String monitorUrl) throws Exception {

		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
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
		String subChainInfo = moacMicro.getSubChainInfo(subchain.getSubchainBaseAddress(), monitorUrlReal);
		Map<String, Object> map = JsonFormat.jsonToMap(subChainInfo);
		return ResponseBase.createResponse(true, 10000, "子链信息获取成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<List<String>>> dapplist(String subchainId, String walletId, String monitorScsId,
			String monitorUrl) throws Exception {

		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
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
		String dappAddrList = moacMicro.getDappAddrList(subchain.getSubchainBaseAddress(), monitorUrlReal);
		List<String> list = JsonFormat.parseJSON2List(dappAddrList);
		return ResponseBase.createResponse(true, 10000, "子链合约获取成功", list, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map>> transaction(String subchainId, String walletId, String monitorScsId,
			String monitorUrl, String hash) throws Exception {

		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
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
		String transactionByHash = moacMicro.getTransactionByHash(subchain.getSubchainBaseAddress(), hash, monitorUrlReal);
		Map<String, Object> map = JsonFormat.jsonToMap(transactionByHash);
		if(map.isEmpty()) {
			throw new BussinessException(40004, "未查到该交易");
		}
		return ResponseBase.createResponse(true, 10000, "子链交易查询成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<String>> dappstate(String subchainId, String walletId, String monitorScsId,
			String monitorUrl) throws Exception {

		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
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
		Account account = accountDao.queryAccountById(subchain.getAccountId());
		String dappState = moacMicro.getDappState(subchain.getSubchainBaseAddress(), account.getAddress(), monitorUrlReal);
		return ResponseBase.createResponse(true, 10000, "子链基础合约状态查询成功", dappState, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<String>> balance(String subchainId, String walletId, String monitorScsId,
			String monitorUrl, String address) throws Exception {

		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
		}
		//查询子链基础信息
		Subchain subchain = moacSubchainDao.querySubchainById(subchainId);
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
		String balance = moacMicro.getBalance(moac, address, subchain.getSubchainBaseAddress(),
				monitorUrlReal, subchain.getErc20Address());
		return ResponseBase.createResponse(true, 10000, "子链余额查询成功", balance, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> recharge1(String subchainId, String walletId,
			String accountId, String payPsw, String value) throws Exception {

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
		String hash = moacMicro.approve(moac, subchain.getErc20Address(), account.getAddress(),
				privateKey, subchain.getSubchainBaseAddress(), value, "",walletId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "erc20授权(approve)方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> recharge2(String subchainId, String walletId,
			String accountId, String payPsw, String value) throws Exception {

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
		String hash = moacMicro.buyMintToken(moac, subchain.getErc20Address(), account.getAddress(), privateKey,
				subchain.getSubchainBaseAddress(), value, moac.getNonce(account.getAddress()).toString(), walletId);

		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链合约(subchainBase)授权(buyMintToken)方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> withdraw(String subchainId, String walletId,
			String accountId, String payPsw, String value, String monitorScsId, String monitorUrl) throws Exception {

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
		String hash = moacMicro.withdraw(moac, account.getAddress(), privateKey, value, subchain.getSubchainBaseAddress(),
				subchain.getVia(), subchain.getDappbaseAddress(), monitorUrlReal, walletId,subchain.getErc20Address());
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链提币调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> transfer(String subchainId, String walletId,
			String accountId, String payPsw, String value, String to, String monitorScsId, String monitorUrl, String nonce) throws Exception {

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
		String hash = moacMicro.sendRawTransactionMicro(moac, account.getAddress(), privateKey, to, value,
				subchain.getSubchainBaseAddress(), subchain.getVia(), nonce, monitorUrlReal, walletId, subchain.getErc20Address());
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "子链转账调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> getNonce(String subchainId, String walletId, String accountId,
			String monitorScsId, String monitorUrl) throws Exception {

		if(StringUtils.isEmpty(monitorScsId)&&StringUtils.isEmpty(monitorUrl)) {
			List<Scs> scsList = moacSubchainDao.queryScsBySubchainId(subchainId,"1");
			if(scsList.size()==0) {
				throw new BussinessException(40004, "无monitor信息，请先添加monitor");
			}
			monitorUrl = scsList.get(0).getMonitorUrl();
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
		String nonce = moacMicro.getNonce(account.getAddress(), subchain.getSubchainBaseAddress(), monitorUrlReal);
		Map<String,String> map = new HashMap<String,String>();
		map.put("nonce", nonce);
		return ResponseBase.createResponse(true, 10000, "获取nonce成功", map, HttpStatus.OK);
	}




}

