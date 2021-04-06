package com.web.frame.util.moac.micro;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Address;
import org.chain3j.abi.datatypes.Bool;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Uint;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.web.frame.dao.web.MoacSubchainDao;
import com.web.frame.dao.web.TransactionDao;
import com.web.frame.entity.response.ResponseTransactionMoac;
import com.web.frame.entity.table.web.Scs;
import com.web.frame.entity.table.web.Subchain;
import com.web.frame.entity.table.web.Transaction;
import com.web.frame.entity.table.web.Vnode;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.JsonFormat;
import com.web.frame.util.moac.Moac;
import com.web.frame.util.moac.MoacMicro;

/**
 * 一键发链
 * @author Administrator
 *
 */
@Component
public class DeployMicro{

	private static Logger logger = Logger.getLogger(DeployMicro.class);

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private MoacMicro moacMicro;

	public static void main(String[] args) throws Exception {

		DeployMicro deployMicro = new DeployMicro();
		Moac moac = new Moac("http://127.0.0.1:8545");
		String administratorAddr = "0x249ea1a73234f6c461f142eaf07224078e32f65e";
		String administratorSecret = "2f1fa8d53a10c92c0294f637f9808d1d2417223acbfe3f4136da243f360a3ea2";
		String nonce = moac.getNonce(administratorAddr).toString();
		String scsAddress = "56294413f84ff7d12d2d7244912c93d02b15a77a";
		String subchainProtocolAddress = "0xd0f7ae68a0465ab170adb8a820d01f1aaeba1275";
		String subchainProtocolBmin  = "1";
		String walletId = "";
		String subchainId = "";
		String subchainBaseAddress = "0x6ac2a4f30d7e110f669d5dd1f5070bed626f4b88";

//		String registerScs = deployMicro.registerScs(moac, nonce, scsAddress, subchainProtocolAddress, administratorAddr, administratorSecret,
//				subchainProtocolBmin, walletId, subchainId);

		deployMicro.reset(moac, nonce, subchainBaseAddress, administratorAddr, administratorSecret,walletId,subchainId);
	}

	@Autowired
	private Moac moac;
	@Autowired
	private MoacSubchainDao moacSubchainDao;

	@Async("taskExecutor")
	public void deployErcSubChain(String administratorAddr,String administratorSecret,String via, String vnodeCode,
			String vnodeBmin, String subchainProtocolBaseCode,
			String subchainProtocolName, String subchainProtocolBmin, String subchainProtocolType, String erc20Code,
			String subchainBaseCode, String subchainBaseErcRate, String subchainBaseMin, String subchainBaseMax,
			String subchainBaseFlushRound, String subchainBaseMonitorBond,
			String vnode, String scs,String addFund, String subchainId, String walletId) {

		String vnodeHash = "";
		String vnodeAddress = "";
		String erc20Hash = "";
		String erc20Address = "";
		String subchainProtocolHash = "";
		String subchainProtocolAddress = "";
		String subchainBaseHash = "";
		String subchainBaseAddress = "";

		BigInteger nonce;
		try {
			nonce = moac.getNonce(administratorAddr);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"未获取到账户nonce","false");
			return;
		}


		//1.部署vnode
		try {
			vnodeHash = deployVnode(moac, nonce.toString(), vnodeBmin, vnodeCode, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"vnode合约部署失败","false");
			return;
		}
		if(StringUtils.isEmpty(vnodeHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"vnode合约部署失败，未返回hash","false");
			return;
		}
		addTransaction(walletId, administratorAddr, "", vnodeHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署vnode合约\"}","0");


		//2.部署erc20
		nonce = nonce.add(new BigInteger("1"));
		try {
			erc20Hash = deployErc20(moac, nonce.toString(), erc20Code, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"erc20合约部署失败","false");
			return;
		}
		if(StringUtils.isEmpty(erc20Hash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"erc20合约部署失败，未返回hash","false");
			return;
		}
		addTransaction(walletId, administratorAddr, "", erc20Hash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署erc20合约\"}","0");


		//3.部署subchainprotocolbase
		nonce = nonce.add(new BigInteger("1"));
		try {
			subchainProtocolHash = deploySubchainProtocolBase(moac, nonce.toString(), subchainProtocolName,
					subchainProtocolBmin, subchainProtocolType, subchainProtocolBaseCode, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainProtocol合约部署失败","false");
			return;
		}
		if(StringUtils.isEmpty(subchainProtocolHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainProtocol合约部署失败，未返回hash","false");
			return;
		}
		addTransaction(walletId, administratorAddr, "", subchainProtocolHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署subchainProtocol合约\"}","0");

		//4.等待前三个合约部署完成
		List<String> addressList = new ArrayList<String>();
		try {
			addressList = waitContractAddress(moac, vnodeHash, erc20Hash, subchainProtocolHash);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"未获取到合约地址","false");
			return;
		}
		//vnode合约地址
		vnodeAddress = addressList.get(0);
		//erc20合约地址
		erc20Address = addressList.get(1);
		//协议合约地址
		subchainProtocolAddress = addressList.get(2);

		//5.部署subchainbase
		nonce = nonce.add(new BigInteger("1"));
		String subchainBaseThousandth = "1000";//不需要修改
		try {
			subchainBaseHash = deploySubchainBase(moac, nonce.toString(), subchainProtocolAddress, vnodeAddress, erc20Address,
					subchainBaseErcRate, subchainBaseMin, subchainBaseMax, subchainBaseThousandth,
					subchainBaseFlushRound, subchainBaseCode, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainBase合约部署失败","false");
			return;
		}
		if(StringUtils.isEmpty(subchainBaseHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainBase合约部署失败，未返回hash","false");
			return;
		}
		addTransaction(walletId, administratorAddr, "", subchainBaseHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署subchainBase合约\"}","0");

		//子链合约地址
		try {
			subchainBaseAddress = getAddress(moac, subchainBaseHash);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"未获取到subchainBase合约地址","false");
			return;
		}
		//修改子链信息
		updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
				subchainBaseHash,subchainBaseAddress,subchainId,"","");


		//6.给子链充币
		nonce = nonce.add(new BigInteger("1"));
		String addFundHash = "";
		try {
			addFundHash = addFund(moac, nonce.toString(), addFund, subchainBaseAddress,
					administratorAddr, administratorSecret,walletId,subchainId);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainStatus(subchainId, "给子链充值失败", "false","");
			return;
		}
		if(StringUtils.isEmpty(addFundHash)) {
			updateSubchainStatus(subchainId, "给子链充值失败，未返回hash,请检查余额是否充足", "false","");
			return;
		}
		boolean addFundStatus = false;
		try {
			addFundStatus = getTransactionStatus(moac, addFundHash);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainStatus(subchainId, "给子链充值失败", "false","");
			return;
		}
		if(!addFundStatus) {
			updateSubchainStatus(subchainId, "给子链充值失败，未返回hash,请检查余额是否充足", "false","");
			return;
		}

		//给scs充值作为gas
		String sendToScsMoac = "1";
		List<Map<String, Object>> scsList = JsonFormat.parseJSON2List(scs);
		for (int i = 0; i < scsList.size(); i++) {
			//7.scs充值
			nonce = nonce.add(new BigInteger("1"));
			String sendToScsHash = "";
			try {
				sendToScsHash = sendToScs(moac, nonce.toString(), scsList.get(i).get("scsAddress").toString(),
						administratorAddr, administratorSecret, sendToScsMoac,walletId,subchainId);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "给scs充值失败", "false","");
				return;
			}
			if(StringUtils.isEmpty(sendToScsHash)) {
				updateSubchainStatus(subchainId, "给scs充值失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}
			boolean sendToScsStatus = false;
			try {
				sendToScsStatus = getTransactionStatus(moac, sendToScsHash);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "给scs充值获取交易结果失败", "false","");
				return;
			}
			if(!sendToScsStatus) {
				updateSubchainStatus(subchainId, "给scs充值失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}
		}

		List<Map<String, Object>> vnodeList = JsonFormat.parseJSON2List(vnode);
		for (int i = 0; i < vnodeList.size(); i++) {
			//8.vnode注册
			nonce = nonce.add(new BigInteger("1"));
			String registerVnodeHash = "";
			try {
				registerVnodeHash = registerVnode(moac, nonce.toString(),
						vnodeList.get(i).get("vnodeUrl").toString(),
						vnodeList.get(i).get("vnodeAddress").toString(),
						vnodeAddress, administratorAddr, administratorSecret, vnodeBmin,walletId,subchainId);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "vnode注册失败", "false","");
				return;
			}
			if(StringUtils.isEmpty(registerVnodeHash)) {
				updateSubchainStatus(subchainId, "vnode注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}

			boolean registerVnodeStatus = false;
			try {
				registerVnodeStatus = getTransactionStatus(moac, registerVnodeHash);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "vnode注册失败", "false","");
				return;
			}
			if(!registerVnodeStatus) {
				updateSubchainStatus(subchainId, "vnode注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}

			//保存vnode信息
			addVnode(subchainId,vnodeList.get(i).get("vnodeUrl").toString(),vnodeList.get(i).get("vnodeAddress").toString());
		}

		//vnode池中vnode个数
		//vnodeCount(moac, vnodeAddress, administratorAddr);

		//9.scs注册子链
		for (int i = 0; i < scsList.size(); i++) {
			nonce = nonce.add(new BigInteger("1"));
			String registerScsHash = "";
			try {
				registerScsHash = registerScs(moac, nonce.toString(), scsList.get(i).get("scsAddress").toString(),
						subchainProtocolAddress, administratorAddr, administratorSecret, subchainProtocolBmin,walletId,subchainId);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "scs注册失败", "false","");
				return;
			}
			if(StringUtils.isEmpty(registerScsHash)) {
				updateSubchainStatus(subchainId, "scs注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}

			boolean registerScsStatus = false;
			try {
				registerScsStatus = getTransactionStatus(moac, registerScsHash);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "获取scs注册结果失败", "false","");
				return;
			}
			if(!registerScsStatus) {
				updateSubchainStatus(subchainId, "scs注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}
			//保存scs信息
			addScs(subchainId,scsList.get(i).get("scsAddress").toString(),"0","");
		}

		String endBlock = "";
		String failReason = "未获取到当前区块，请手动获取当前区块并过至少5个区块后进行registerOpen";
		try {
			endBlock = moac.mcBlockNumber().toString();
			failReason = "在"+new BigInteger(endBlock).add(new BigInteger("5")).toString()+"区块后再进行registerOpen";
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateSubchainStatus(subchainId, failReason, "true",endBlock);
	}

	@Async("taskExecutor")
	public void deployErcSubChainRand(String administratorAddr,String administratorSecret,String via, String vnodeCode,
								  String vnodeBmin, String subchainProtocolBaseCode,
								  String subchainProtocolName, String subchainProtocolBmin, String subchainProtocolType, String erc20Code,
								  String subchainBaseCode, String subchainBaseErcRate, String subchainBaseMin, String subchainBaseMax,
								  String subchainBaseFlushRound, String subchainBaseMonitorBond,
								  String vnode, String scs,String addFund, String subchainId, String walletId,
								  String vssBaseCode,String vssBaseThreshold) {

		String vnodeHash = "";
		String vnodeAddress = "";
		String erc20Hash = "";
		String erc20Address = "";
		String subchainProtocolHash = "";
		String subchainProtocolAddress = "";
		String vssBaseHash = "";
		String vssBaseAddress = "";
		String subchainBaseHash = "";
		String subchainBaseAddress = "";

		BigInteger nonce;
		try {
			nonce = moac.getNonce(administratorAddr);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"未获取到账户nonce","false",vssBaseHash,vssBaseAddress);
			return;
		}


		//1.部署vnode
		try {
			vnodeHash = deployVnode(moac, nonce.toString(), vnodeBmin, vnodeCode, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"vnode合约部署失败","false",vssBaseHash,vssBaseAddress);
			return;
		}
		if(StringUtils.isEmpty(vnodeHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"vnode合约部署失败，未返回hash","false",vssBaseHash,vssBaseAddress);
			return;
		}
		addTransaction(walletId, administratorAddr, "", vnodeHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署vnode合约\"}","0");


		//2.部署erc20
		nonce = nonce.add(new BigInteger("1"));
		try {
			erc20Hash = deployErc20(moac, nonce.toString(), erc20Code, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"erc20合约部署失败","false",vssBaseHash,vssBaseAddress);
			return;
		}
		if(StringUtils.isEmpty(erc20Hash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"erc20合约部署失败，未返回hash","false",vssBaseHash,vssBaseAddress);
			return;
		}
		addTransaction(walletId, administratorAddr, "", erc20Hash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署erc20合约\"}","0");


		//3.部署subchainprotocolbase
		nonce = nonce.add(new BigInteger("1"));
		try {
			subchainProtocolHash = deploySubchainProtocolBase(moac, nonce.toString(), subchainProtocolName,
					subchainProtocolBmin, subchainProtocolType, subchainProtocolBaseCode, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainProtocol合约部署失败","false",vssBaseHash,vssBaseAddress);
			return;
		}
		if(StringUtils.isEmpty(subchainProtocolHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainProtocol合约部署失败，未返回hash","false",vssBaseHash,vssBaseAddress);
			return;
		}
		addTransaction(walletId, administratorAddr, "", subchainProtocolHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署subchainProtocol合约\"}","0");

		//4.部署vss
		nonce = nonce.add(new BigInteger("1"));
		try {
			vssBaseHash = deploySubchainVssBase(moac, nonce.toString(), vssBaseCode,vssBaseThreshold, administratorAddr, administratorSecret);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainProtocol合约部署失败","false",vssBaseHash,vssBaseAddress);
			return;
		}
		if(StringUtils.isEmpty(vssBaseHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"vssBase合约部署失败，未返回hash","false",vssBaseHash,vssBaseAddress);
			return;
		}
		addTransaction(walletId, administratorAddr, "", vssBaseHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署vssBase合约\"}","0");


		//5.等待前四个合约部署完成
		List<String> addressList = new ArrayList<String>();
		try {
			addressList = waitContractAddress(moac, vnodeHash, erc20Hash, subchainProtocolHash,vssBaseHash);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"未获取到合约地址","false",vssBaseHash,vssBaseAddress);
			return;
		}
		//vnode合约地址
		vnodeAddress = addressList.get(0);
		//erc20合约地址
		erc20Address = addressList.get(1);
		//协议合约地址
		subchainProtocolAddress = addressList.get(2);
		//vss合约地址
		vssBaseAddress = addressList.get(3);

		//6.部署subchainbase
		nonce = nonce.add(new BigInteger("1"));
		String subchainBaseThousandth = "1000";//不需要修改
		try {
			subchainBaseHash = deploySubchainBaseRand(moac, nonce.toString(), subchainProtocolAddress, vnodeAddress, erc20Address,
					subchainBaseErcRate, subchainBaseMin, subchainBaseMax, subchainBaseThousandth,
					subchainBaseFlushRound, subchainBaseCode, administratorAddr, administratorSecret,vssBaseAddress);
		} catch (Exception e) {
			e.printStackTrace();
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainBase合约部署失败","false",vssBaseHash,vssBaseAddress);
			return;
		}
		if(StringUtils.isEmpty(subchainBaseHash)) {
			//修改子链信息
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"subchainBase合约部署失败，未返回hash","false",vssBaseHash,vssBaseAddress);
			return;
		}
		addTransaction(walletId, administratorAddr, "", subchainBaseHash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署subchainBase合约\"}","0");

		//子链合约地址
		try {
			subchainBaseAddress = getAddress(moac, subchainBaseHash);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
					subchainBaseHash,subchainBaseAddress,subchainId,"未获取到subchainBase合约地址","false",vssBaseHash,vssBaseAddress);
			return;
		}
		//修改子链信息
		updateSubchainData(vnodeHash,vnodeAddress,subchainProtocolHash,subchainProtocolAddress,erc20Hash,erc20Address,
				subchainBaseHash,subchainBaseAddress,subchainId,"","",vssBaseHash,vssBaseAddress);

		//7.将vss合约的调用者设置为subchanBase的地址
		nonce = nonce.add(new BigInteger("1"));
		String setCallerHash = "";
		try {
			setCallerHash = setCaller(moac, nonce.toString(), vssBaseAddress, subchainBaseAddress,
					administratorAddr, administratorSecret,walletId,subchainId);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainStatus(subchainId, "vss合约setCaller失败", "false","");
			return;
		}
		if(StringUtils.isEmpty(setCallerHash)) {
			updateSubchainStatus(subchainId, "vss合约setCaller失败，未返回hash", "false","");
			return;
		}
		boolean setCallerStatus = false;
		try {
			setCallerStatus = getTransactionStatus(moac, setCallerHash);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainStatus(subchainId, "vss合约setCaller失败", "false","");
			return;
		}
		if(!setCallerStatus) {
			updateSubchainStatus(subchainId, "vss合约setCaller失败，未返回hash,请检查余额是否充足", "false","");
			return;
		}


		//8.给子链充币
		nonce = nonce.add(new BigInteger("1"));
		String addFundHash = "";
		try {
			addFundHash = addFund(moac, nonce.toString(), addFund, subchainBaseAddress,
					administratorAddr, administratorSecret,walletId,subchainId);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainStatus(subchainId, "给子链充值失败", "false","");
			return;
		}
		if(StringUtils.isEmpty(addFundHash)) {
			updateSubchainStatus(subchainId, "给子链充值失败，未返回hash,请检查余额是否充足", "false","");
			return;
		}
		boolean addFundStatus = false;
		try {
			addFundStatus = getTransactionStatus(moac, addFundHash);
		} catch (Exception e) {
			e.printStackTrace();
			updateSubchainStatus(subchainId, "给子链充值失败", "false","");
			return;
		}
		if(!addFundStatus) {
			updateSubchainStatus(subchainId, "给子链充值失败，未返回hash,请检查余额是否充足", "false","");
			return;
		}

		//给scs充值作为gas
		String sendToScsMoac = "1";
		List<Map<String, Object>> scsList = JsonFormat.parseJSON2List(scs);
		for (int i = 0; i < scsList.size(); i++) {
			//7.scs充值
			nonce = nonce.add(new BigInteger("1"));
			String sendToScsHash = "";
			try {
				sendToScsHash = sendToScs(moac, nonce.toString(), scsList.get(i).get("scsAddress").toString(),
						administratorAddr, administratorSecret, sendToScsMoac,walletId,subchainId);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "给scs充值失败", "false","");
				return;
			}
			if(StringUtils.isEmpty(sendToScsHash)) {
				updateSubchainStatus(subchainId, "给scs充值失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}
			boolean sendToScsStatus = false;
			try {
				sendToScsStatus = getTransactionStatus(moac, sendToScsHash);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "给scs充值获取交易结果失败", "false","");
				return;
			}
			if(!sendToScsStatus) {
				updateSubchainStatus(subchainId, "给scs充值失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}
		}

		List<Map<String, Object>> vnodeList = JsonFormat.parseJSON2List(vnode);
		for (int i = 0; i < vnodeList.size(); i++) {
			//8.vnode注册
			nonce = nonce.add(new BigInteger("1"));
			String registerVnodeHash = "";
			try {
				registerVnodeHash = registerVnodeRand(moac, nonce.toString(),
						vnodeList.get(i).get("vnodeUrl").toString(),
						vnodeList.get(i).get("vnodeAddress").toString(),
						vnodeAddress, administratorAddr, administratorSecret, vnodeBmin,walletId,subchainId,
						via,
//						vnodeList.get(i).get("rpcUrl").toString()
						""
						);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "vnode注册失败", "false","");
				return;
			}
			if(StringUtils.isEmpty(registerVnodeHash)) {
				updateSubchainStatus(subchainId, "vnode注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}

			boolean registerVnodeStatus = false;
			try {
				registerVnodeStatus = getTransactionStatus(moac, registerVnodeHash);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "vnode注册失败", "false","");
				return;
			}
			if(!registerVnodeStatus) {
				updateSubchainStatus(subchainId, "vnode注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}

			//保存vnode信息
			addVnode(subchainId,vnodeList.get(i).get("vnodeUrl").toString(),vnodeList.get(i).get("vnodeAddress").toString());
		}

		//vnode池中vnode个数
		//vnodeCount(moac, vnodeAddress, administratorAddr);

		//9.scs注册子链
		for (int i = 0; i < scsList.size(); i++) {
			nonce = nonce.add(new BigInteger("1"));
			String registerScsHash = "";
			try {
				registerScsHash = registerScs(moac, nonce.toString(), scsList.get(i).get("scsAddress").toString(),
						subchainProtocolAddress, administratorAddr, administratorSecret, subchainProtocolBmin,walletId,subchainId);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "scs注册失败", "false","");
				return;
			}
			if(StringUtils.isEmpty(registerScsHash)) {
				updateSubchainStatus(subchainId, "scs注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}

			boolean registerScsStatus = false;
			try {
				registerScsStatus = getTransactionStatus(moac, registerScsHash);
			} catch (Exception e) {
				e.printStackTrace();
				updateSubchainStatus(subchainId, "获取scs注册结果失败", "false","");
				return;
			}
			if(!registerScsStatus) {
				updateSubchainStatus(subchainId, "scs注册失败，未返回hash,请检查余额是否充足", "false","");
				return;
			}
			//保存scs信息
			addScs(subchainId,scsList.get(i).get("scsAddress").toString(),"0","");
		}

		String endBlock = "";
		String failReason = "未获取到当前区块，请手动获取当前区块并过至少5个区块后进行registerOpen";
		try {
			endBlock = moac.mcBlockNumber().toString();
			failReason = "在"+new BigInteger(endBlock).add(new BigInteger("5")).toString()+"区块后再进行registerOpen";
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateSubchainStatus(subchainId, failReason, "true",endBlock);
	}

	/**
	 * vss设置调用者
	 * @param moac
	 * @param nonce
	 * @param vssBaseAddress
	 * @param subchainBaseAddress
	 * @param administratorAddr
	 * @param administratorSecret
	 * @param walletId
	 * @param subchainId
	 * @return
	 */
	private String setCaller(Moac moac, String nonce, String vssBaseAddress,
							 String subchainBaseAddress, String administratorAddr,
							 String administratorSecret, String walletId, String subchainId) throws Exception {

		Type[] in = {new Address(subchainBaseAddress)};
		String hash = moac.callContractTransaction(vssBaseAddress, administratorAddr, administratorSecret,
				"setCaller", Arrays.asList(in), null, "","", nonce);
		logger.error("vss设置Caller返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash, "{\"subchainId\":\""+subchainId+"\","
					+ "\"mark\",\"调用vssBase合约setCaller方法\"}","0");
		}
		return hash;
	}


	/**
	 * 获取注册为monitor的结果并保存
	 * @param moac
	 * @param hash
	 * @param subchainId
	 * @param scsAddress
	 * @param monitorUrl
	 * @throws Exception
	 */
	@Async("taskExecutor")
	public void getResisterAsMonitorStatus(Moac moac,String hash,String subchainId,String scsAddress,String monitorUrl) throws Exception {
		if(getTransactionStatus(moac, hash)) {
			Scs scs = new Scs();
			scs.setSubchainId(subchainId);
			scs.setScsAddress(scsAddress);
			scs.setScsMonitor("1");
			scs.setMonitorUrl(monitorUrl);
			moacSubchainDao.addScs(scs);
		}
	}

	/**
	 * 获取dappbase合约地址并保存
	 * @param moac
	 * @param hash
	 * @param monitorUrl
	 * @param subChainAddr
	 * @param subchainId
	 */
	@Async("taskExecutor")
	public void getDappBaseAddress(Moac moac,String hash,String monitorUrl,String subChainAddr,String subchainId) {

		/*while (true) {
			String transactionByHash = moacMicro.getTransactionByHash(subChainAddr, hash, monitorUrl);
			if(StringUtils.isEmpty(transactionByHash)) {
				return;
			}
			Object blockNumber = JsonFormat.jsonToMap(transactionByHash).get("blockNumber");
			if(blockNumber!=null&&StringUtils.isNotEmpty(blockNumber.toString())) {
				break;
			}
		}*/
		String dappAddrList = moacMicro.getDappAddrList(subChainAddr, monitorUrl);
		if(StringUtils.isNotEmpty(dappAddrList)) {
			JSONArray jsonArray = JSON.parseArray(dappAddrList);
			Iterator<Object> iterator = jsonArray.iterator();
			if(iterator.hasNext()){
				String dappBaseAddr = (String) iterator.next();
				Subchain subchain = new Subchain();
				subchain.setId(subchainId);
				subchain.setDappbaseHash(hash);
				subchain.setDappbaseAddress(dappBaseAddr);
				try {
					moacSubchainDao.updateSubchain(subchain);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存scs信息
	 * @param subchainId
	 * @param scsAddress scs地址
	 * @param scsMonitor 0:普通scs,1:monitor scs
	 * @param monitorUrl monitor查询接口地址
	 * @throws Exception
	 */
	private void addScs(String subchainId, String scsAddress, String scsMonitor, String monitorUrl) {

		try {
			Scs scs = new Scs();
			scs.setSubchainId(subchainId);
			scs.setScsAddress(scsAddress);
			scs.setScsMonitor(scsMonitor);
			scs.setMonitorUrl(monitorUrl);
			moacSubchainDao.addScs(scs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存vnode信息
	 * @param subchainId
	 * @param vnodeUrl
	 * @param vnodeAddress
	 * @throws Exception
	 */
	private void addVnode(String subchainId, String vnodeUrl, String vnodeAddress) {

		try {
			Vnode vnode = new Vnode();
			vnode.setSubchainId(subchainId);
			vnode.setVnodeUrl(vnodeUrl);
			vnode.setVnodeAddress(vnodeAddress);
			moacSubchainDao.addVnode(vnode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改子链基础信息
	 * @param vnodeHash
	 * @param vnodeAddress
	 * @param subchainProtocolHash
	 * @param subchainProtocolAddress
	 * @param erc20Hash
	 * @param erc20Address
	 * @param subchainBaseHash
	 * @param subchainBaseAddress
	 * @param subchainId
	 * @param failReason
	 * @param subchainStatus
	 * @throws Exception
	 */
	private void updateSubchainData(String vnodeHash, String vnodeAddress, String subchainProtocolHash, String subchainProtocolAddress,
			String erc20Hash, String erc20Address, String subchainBaseHash, String subchainBaseAddress, String subchainId,String failReason,
			String subchainStatus) {

		try {
			Subchain subchain = new Subchain();
			subchain.setId(subchainId);
			subchain.setVnodeHash(vnodeHash);
			subchain.setVnodeAddress(vnodeAddress);
			subchain.setSubchainProtocolHash(subchainProtocolHash);
			subchain.setSubchainProtocolAddress(subchainProtocolAddress);
			subchain.setErc20Hash(erc20Hash);
			subchain.setErc20Address(erc20Address);
			subchain.setSubchainBaseHash(subchainBaseHash);
			subchain.setSubchainBaseAddress(subchainBaseAddress);
			subchain.setFailReason(failReason);
			subchain.setSubchainStatus(subchainStatus);
			moacSubchainDao.updateSubchain(subchain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改子链基础信息
	 * @param vnodeHash
	 * @param vnodeAddress
	 * @param subchainProtocolHash
	 * @param subchainProtocolAddress
	 * @param erc20Hash
	 * @param erc20Address
	 * @param subchainBaseHash
	 * @param subchainBaseAddress
	 * @param subchainId
	 * @param failReason
	 * @param subchainStatus
	 * @param vssBaseHash
	 * @param vssBaseAddress
	 */
	private void updateSubchainData(String vnodeHash, String vnodeAddress, String subchainProtocolHash, String subchainProtocolAddress,
									String erc20Hash, String erc20Address, String subchainBaseHash, String subchainBaseAddress, String subchainId,String failReason,
									String subchainStatus,String vssBaseHash,String vssBaseAddress) {

		try {
			Subchain subchain = new Subchain();
			subchain.setId(subchainId);
			subchain.setVnodeHash(vnodeHash);
			subchain.setVnodeAddress(vnodeAddress);
			subchain.setSubchainProtocolHash(subchainProtocolHash);
			subchain.setSubchainProtocolAddress(subchainProtocolAddress);
			subchain.setErc20Hash(erc20Hash);
			subchain.setErc20Address(erc20Address);
			subchain.setSubchainBaseHash(subchainBaseHash);
			subchain.setSubchainBaseAddress(subchainBaseAddress);
			subchain.setFailReason(failReason);
			subchain.setSubchainStatus(subchainStatus);
			subchain.setVssBaseHash(vssBaseHash);
			subchain.setVssBaseAddress(vssBaseAddress);
			moacSubchainDao.updateSubchain(subchain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改子链部署状态
	 * @param subchainId
	 * @param failReason
	 * @param subchainStatus
	 * @param endBlock
	 * @throws Exception
	 */
	private void updateSubchainStatus(String subchainId,String failReason,
			String subchainStatus,String endBlock) {

		try {
			Subchain subchain = new Subchain();
			subchain.setId(subchainId);
			subchain.setFailReason(failReason);
			subchain.setSubchainStatus(subchainStatus);
			subchain.setEndBlock(endBlock);
			moacSubchainDao.updateSubchain(subchain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增交易记录
	 * @param walletId 钱包id
	 * @param address 发起账户地址
	 * @param to 接收账户地址
	 * @param hash 交易hash
	 * @param mark 备注信息
	 * @param chainType 0:公链，1：子链
	 */
	private void addTransaction(String walletId,String address,String to,String hash,String mark,String chainType) {
		try {
			Transaction transaction = new Transaction();
			transaction.setWalletId(walletId);
			transaction.setChain("0");
			transaction.setFromAddress(address);
			transaction.setToAddress(to);
			transaction.setSymbol("MC");
			transaction.setTokenAddress("");
			transaction.setHash(hash);
			transaction.setMark(mark);
			if(StringUtils.isNotEmpty(chainType)) {
				transaction.setChainType(chainType);
			}
			transactionDao.addTransaction(transaction);//新增交易记录
		} catch (Exception e) {
		}
	}

	/**
	 * scs注册入池子后等待5个区块
	 * @param moac
	 * @throws Exception
	 */
	private void waitFiveBlocks(Moac moac) throws Exception {

		String theBlockNumber = moac.mcBlockNumber().toString();
		logger.error("注册入池子后当前区块:"+theBlockNumber);
		while (true) {
			String endBlockNumber = moac.mcBlockNumber().toString();
			if(new BigInteger(endBlockNumber).subtract(new BigInteger(theBlockNumber)).compareTo(new BigInteger("5"))>=0) {
				logger.error("注册入池子后5个区块后当前区块:"+endBlockNumber);
				break;
			}
		}
	}

	/**
	 * 部署vnode节点
	 * @param moac
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	public String deployVnode(Moac moac,String nonce,String vnodeBmin,String vnodeCode,String administratorAddr,String administratorSecret) throws Exception {

		Type [] params = {new Uint(new BigInteger(vnodeBmin))};
		String hash = moac.deployContract(vnodeCode, administratorAddr, administratorSecret, Arrays.asList(params), nonce);
		logger.error("部署vnode合约返回hash:"+hash);
		return hash;
	}

	/**
	 * 部署erc20节点
	 * @param moac
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	public String deployErc20(Moac moac,String nonce,String erc20Code,String administratorAddr,String administratorSecret)throws Exception{

		String hash = moac.deployContract(erc20Code, administratorAddr, administratorSecret, null, nonce);
		logger.error("部署erc20合约返回hash:"+hash);
		return hash;
	}

	/**
	 * 部署协议合约
	 * @param moac
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	public String deploySubchainProtocolBase(Moac moac,String nonce,String subchainProtocolName,
			String subchainProtocolBmin,String subchainProtocolType,String subchainProtocolBaseCode,
			String administratorAddr,String administratorSecret)throws Exception{

		Type [] params = {new Utf8String(subchainProtocolName),new Uint(new BigInteger(subchainProtocolBmin)),
						  new Uint(new BigInteger(subchainProtocolType))};
		String hash = moac.deployContract(subchainProtocolBaseCode, administratorAddr, administratorSecret,
				Arrays.asList(params), nonce);
		logger.error("部署协议合约返回hash:"+hash);
		return hash;
	}

	/**
	 * 部署vss合约
	 * @param moac
	 * @param nonce
	 * @param vssBaseCode
	 * @param vssBaseThreshold
	 * @param administratorAddr
	 * @param administratorSecret
	 * @return
	 * @throws Exception
	 */
	public String deploySubchainVssBase(Moac moac,String nonce,String vssBaseCode,String vssBaseThreshold,
											 String administratorAddr,String administratorSecret)throws Exception{

		Type [] params = {new Uint(new BigInteger(vssBaseThreshold))};
		String hash = moac.deployContract(vssBaseCode, administratorAddr, administratorSecret,
				Arrays.asList(params), nonce);
		logger.error("部署vss合约返回hash:"+hash);
		return hash;
	}

	/**
	 * 等待获取合约地址
	 * @param moac
	 * @param vnodeHash
	 * @param erc20Hash
	 * @param subchainProtocolHash
	 * @throws Exception
	 */
	public List<String> waitContractAddress(Moac moac,String vnodeHash,String erc20Hash,String subchainProtocolHash) throws Exception {

		String vnodeAddress = getAddress(moac, vnodeHash);
		String erc20Address = getAddress(moac, erc20Hash);
		String subchainProtocolAddress = getAddress(moac, subchainProtocolHash);
		List<String> addressList = new ArrayList<String>();
		addressList.add(vnodeAddress);
		addressList.add(erc20Address);
		addressList.add(subchainProtocolAddress);
		logger.error("vnode合约地址:"+vnodeAddress);
		logger.error("erc20合约地址:"+erc20Address);
		logger.error("协议合约地址:"+subchainProtocolAddress);
		return addressList;
	}

	public List<String> waitContractAddress(Moac moac,String vnodeHash,String erc20Hash,String subchainProtocolHash,String vssBaseHash) throws Exception {

		String vnodeAddress = getAddress(moac, vnodeHash);
		String erc20Address = getAddress(moac, erc20Hash);
		String subchainProtocolAddress = getAddress(moac, subchainProtocolHash);
		String vssBaseAddress = getAddress(moac, vssBaseHash);
		List<String> addressList = new ArrayList<String>();
		addressList.add(vnodeAddress);
		addressList.add(erc20Address);
		addressList.add(subchainProtocolAddress);
		addressList.add(vssBaseAddress);
		logger.error("vnode合约地址:"+vnodeAddress);
		logger.error("erc20合约地址:"+erc20Address);
		logger.error("协议合约地址:"+subchainProtocolAddress);
		logger.error("vssBase合约地址:"+vssBaseAddress);
		return addressList;
	}

	/**
	 * 部署子链合约
	 * @param moac
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	public String deploySubchainBase(Moac moac,String nonce,String subchainProtocolAddress,String vnodeAddress,String erc20Address,String subchainBaseErcRate,
			String subchainBaseMin,String subchainBaseMax,String subchainBaseThousandth,String subchainBaseFlushRound,String subchainBaseCode,
			String administratorAddr,String administratorSecret)throws Exception{

		Type [] params = {new Address(subchainProtocolAddress),new Address(vnodeAddress),
						  new Address(erc20Address),new Uint(new BigInteger(subchainBaseErcRate)),
						  new Uint(new BigInteger(subchainBaseMin)),new Uint(new BigInteger(subchainBaseMax)),
						  new Uint(new BigInteger(subchainBaseThousandth)),new Uint(new BigInteger(subchainBaseFlushRound))};
		String hash = moac.deployContract(subchainBaseCode, administratorAddr, administratorSecret,
				Arrays.asList(params), nonce);
		logger.error("部署子链合约返回hash:"+hash);
		return hash;
	}

	/**
	 * 部署子链合约
	 * @param moac
	 * @param nonce
	 * @param subchainProtocolAddress
	 * @param vnodeAddress
	 * @param erc20Address
	 * @param subchainBaseErcRate
	 * @param subchainBaseMin
	 * @param subchainBaseMax
	 * @param subchainBaseThousandth
	 * @param subchainBaseFlushRound
	 * @param subchainBaseCode
	 * @param administratorAddr
	 * @param administratorSecret
	 * @param vssBaseAddress
	 * @return
	 * @throws Exception
	 */
	public String deploySubchainBaseRand(Moac moac,String nonce,String subchainProtocolAddress,String vnodeAddress,String erc20Address,String subchainBaseErcRate,
		 String subchainBaseMin,String subchainBaseMax,String subchainBaseThousandth,String subchainBaseFlushRound,String subchainBaseCode,
		 String administratorAddr,String administratorSecret,String vssBaseAddress)throws Exception{

		Type [] params = {new Address(subchainProtocolAddress),new Address(vnodeAddress),
				new Address(erc20Address),new Uint(new BigInteger(subchainBaseErcRate)),
				new Uint(new BigInteger(subchainBaseMin)),new Uint(new BigInteger(subchainBaseMax)),
				new Uint(new BigInteger(subchainBaseThousandth)),new Uint(new BigInteger(subchainBaseFlushRound)),new Address(vssBaseAddress)};
		String hash = moac.deployContract(subchainBaseCode, administratorAddr, administratorSecret,
				Arrays.asList(params), nonce);
		logger.error("部署子链合约返回hash:"+hash);
		return hash;
	}

	/**
	 * 管理员给子链充币，用于支付给scs挖矿gas费
	 * @param moac
	 * @param nonce
	 * @param value
	 * @throws Exception
	 */
	public String addFund(Moac moac,String nonce,String value,String subchainBaseAddress,
			String administratorAddr,String administratorSecret,String walletId,String subchainId) throws Exception{

		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"addFund", null, null, Convert.toSha(value, Convert.Unit.MC).toBigInteger().toString(),"", nonce);
		logger.error("子链充值返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash, "{\"subchainId\":\""+subchainId+"\","
					+ "\"mark\",\"调用subchainBase合约addFund方法，转入"+value+"MOAC\"}","0");
		}
		return hash;
	}


	/**
	 * 给 scs充gas费
	 * @param moac
	 * @param nonce
	 * @param scsAddress
	 * @return
	 * @throws Exception
	 */
	public String sendToScs(Moac moac,String nonce,String scsAddress,
			String administratorAddr,String administratorSecret,String sendToScsMoac,String walletId,String subchainId) throws Exception {

		String hash = moac.sendRawTransaction(administratorAddr, administratorSecret, scsAddress, "", sendToScsMoac, "", nonce);
		logger.error("scs:"+scsAddress+"充值返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, scsAddress, hash, "{\"subchainId\":\""+subchainId+"\",\"mark\",\"给scs转入1MOAC\"}","0");
		}
		return hash;
	}

	/**
	 * 注册vnode
	 * @param moac
	 * @param nonce
	 * @throws Exception
	 */
	public String registerVnode(Moac moac,String nonce,String vnodeUrl,String vnode,
			String vnodeAddress,String administratorAddr,String administratorSecret,
			String vnodeBmin,String walletId,String subchainId) throws Exception {

		Type [] inParams = {new Address(vnode),new Utf8String(vnodeUrl)};
		TypeReference [] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(vnodeAddress, administratorAddr, administratorSecret, "register",
				Arrays.asList(inParams), Arrays.asList(outParams),
				Convert.toSha(vnodeBmin, Convert.Unit.MC).toBigInteger().toString(), "", nonce);
		logger.error("注册vnode返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, vnodeAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"调用vnode合约register方法,"
							+vnodeUrl+","+vnodeAddress+"\"}","0");
		}
		return hash;
	}

	/**
	 * 注册vnode
	 * @param moac
	 * @param nonce
	 * @param vnodeUrl
	 * @param vnode
	 * @param vnodeAddress
	 * @param administratorAddr
	 * @param administratorSecret
	 * @param vnodeBmin
	 * @param walletId
	 * @param subchainId
	 * @param via
	 * @param rpcAddress
	 * @return
	 * @throws Exception
	 */
	public String registerVnodeRand(Moac moac,String nonce,String vnodeUrl,String vnode,
								String vnodeAddress,String administratorAddr,String administratorSecret,
								String vnodeBmin,String walletId,String subchainId,String via,String rpcAddress) throws Exception {

		Type [] inParams = {new Address(vnode),new Address(via),new Utf8String(vnodeUrl),new Utf8String(rpcAddress)};
		TypeReference [] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(vnodeAddress, administratorAddr, administratorSecret, "register",
				Arrays.asList(inParams), Arrays.asList(outParams),
				Convert.toSha(vnodeBmin, Convert.Unit.MC).toBigInteger().toString(), "", nonce);
		logger.error("注册vnode返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, vnodeAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"调用vnode合约register方法,"
							+vnodeUrl+","+vnodeAddress+"\"}","0");
		}
		return hash;
	}

	/**
	 * scs注入入池子
	 * @param moac
	 * @param nonce
	 * @param scsAddress
	 * @return
	 * @throws Exception
	 */
	public String registerScs(Moac moac,String nonce,String scsAddress,String subchainProtocolAddress,
			String administratorAddr,String administratorSecret,String subchainProtocolBmin,String walletId,String subchainId) throws Exception {

		Type [] inParams = {new Address(scsAddress)};
		TypeReference [] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(subchainProtocolAddress, administratorAddr, administratorSecret,
				"register", Arrays.asList(inParams), Arrays.asList(outParams),
				Convert.toSha(subchainProtocolBmin, Convert.Unit.MC).toBigInteger().toString(), "", nonce);
		logger.error("scs注册子链返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainProtocolAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"调用subchainProtocol合约register方法，押金"+subchainProtocolBmin+"MOAC\"}","0");
		}
		return hash;
	}

	/**
	 * 开启子链scs注册
	 * @param moac
	 * @param nonce
	 * @throws Exception
	 */
	public String registerOpen(Moac moac,String nonce,String subchainBaseAddress,String administratorAddr,
			String administratorSecret,String walletId,String subchainId) throws Exception {

		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"registerOpen", null,null, "0", "", nonce);
		logger.error("子链开启注册返回hash:"+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"调用subchainBase合约registerOpen方法\"}","0");
		}
		return hash;
	}

	/**
	 * vnode池中vnode个数
	 * @param moac
	 * @return
	 * @throws Exception
	 */
	public String vnodeCount(Moac moac,String vnodeAddress,String administratorAddr) throws Exception {

		TypeReference [] outParams = {TypeReference.create(Uint.class)};
		List<Type> list = moac.callContract(vnodeAddress, administratorAddr, "vnodeCount", null, Arrays.asList(outParams));
		if(list.size()>0) {
			logger.error("vnode池中vnode个数："+list.get(0).getValue().toString());
			return list.get(0).getValue().toString();
		}else {
			logger.error("vnode池中vnode个数未成功获取");
			return "0";
		}
	}

	/**
	 * scs注册子链节点个数
	 * @param moac
	 * @throws Exception
	 */
	public String nodeCount(Moac moac,String subchainBaseAddress,String administratorAddr) throws Exception {

		TypeReference [] outParams = {TypeReference.create(Uint.class)};
		List<Type> list = moac.callContract(subchainBaseAddress, administratorAddr, "nodeCount", null, Arrays.asList(outParams));
		if(list.size()>0) {
			logger.error("scs注册进入子链个数："+list.get(0).getValue().toString());
			return list.get(0).getValue().toString();
		}else {
			logger.error("scs注册进入子链个数未成功获取");
			return "0";
		}
	}

	/**
	 * scs注册入池子最少押金数
	 * @param moac
	 * @return
	 * @throws Exception
	 */
	public String bondMin(Moac moac,String subchainProtocolAddress,String administratorAddr) throws Exception {

		TypeReference [] outParams = {TypeReference.create(Uint.class)};
		List<Type> list = moac.callContract(subchainProtocolAddress, administratorAddr, "bondMin", null, Arrays.asList(outParams));
		if(list.size()>0) {
			logger.error("scs注册最少押金数："+list.get(0).getValue().toString());
			return list.get(0).getValue().toString();
		}else {
			logger.error("scs注册最少押金数未成功获取");
			return "0";
		}
	}

	/**
	 * 获取池子中scs个数
	 * @param moac
	 * @return
	 * @throws Exception
	 */
	public String scsCount(Moac moac,String subchainProtocolAddress,String administratorAddr) throws Exception {

		TypeReference [] outParams = {TypeReference.create(Uint.class)};
		List<Type> list = moac.callContract(subchainProtocolAddress, administratorAddr, "scsCount", null, Arrays.asList(outParams));
		if(list.size()>0) {
			logger.error("池子中scs个数："+list.get(0).getValue().toString());
			return list.get(0).getValue().toString();
		}else {
			logger.error("池子中scs个数未成功获取");
			return "0";
		}
	}

	/**
	 * 关闭注册
	 * @param moac
	 * @param nonce
	 * @throws Exception
	 */
	public String registerClose(Moac moac,String nonce,String subchainBaseAddress,
			String administratorAddr,String administratorSecret,String walletId,String subchainId) throws Exception {

		TypeReference[] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"registerClose", null, Arrays.asList(outParams), "0", "", nonce);
		logger.error("registerClose返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"调用subchainBase合约registerClose方法\"}","0");
		}
		return hash;
	}

	/**
	 * 注册scs观察者
	 * @param moac
	 * @param nonce
	 * @throws Exception
	 */
	public String registerAsMonitor(Moac moac,String nonce,String scsMonitorAddress,
			String monitorUrl,String subchainBaseAddress,String administratorAddr,
			String administratorSecret,String subchainBaseMonitorBond,String walletId,String subchainId) throws Exception {

		Type[] inParams = {new Address(scsMonitorAddress),new Utf8String(monitorUrl)};
		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"registerAsMonitor", Arrays.asList(inParams), null,
				Convert.toSha(subchainBaseMonitorBond, Convert.Unit.MC).toBigInteger().toString(), "", nonce);
		logger.error("registerAsMonitor返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"调用subchainBase合约registerAsMonitor方法\"}","0");
		}
		return hash;
	}

	/**
	 * 部署DappBase合约---从子链查询交易
	 * @param moac
	 * @param nonce 子链的nonce,第一次部署应该传0,调用MoacMicro.getNonce方法
	 * @param initNum 初始化代币数量，如totalSupply=100000000 * (10 ** decimals);  则传入100000000
	 * @return
	 * @throws Exception
	 */
	public String deployDappBase(Moac moac,String nonce,String erc20Address,String subchainBaseErcRate,
			String administratorAddr,String administratorSecret,String dappbaseCode,
			String subchainBaseAddress,String via,String walletId,String subchainId) throws Exception {

		String totalSupply = moac.getContractTotalSupply(erc20Address);
		String decimals = moac.getContractDecimalsErc20(erc20Address);

//		ERCDecimals = 18 - ERCDecimals;
//		uint256 total = erc20.totalSupply();
//		BALANCE = total * ERCRate * (10 ** (ERCDecimals));

		decimals = new BigInteger("18").subtract(new BigInteger(decimals)).toString();
		String value = new BigInteger(totalSupply).multiply(new BigInteger(subchainBaseErcRate))
				.multiply((new BigInteger("10").pow(Integer.parseInt(decimals)))).toString();

		String hash = moac.deployDapp(administratorAddr, administratorSecret, dappbaseCode,
				value,
				subchainBaseAddress, via, nonce);
		logger.error("DappBase部署返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"部署dappBase合约\"}","1");
		}
		return hash;
	}



	//-----------------------------------------------------

	/**
	 * 子链运行中时添加scs节点
	 * @param moac
	 * @param nonce
	 * @param addNum
	 * @throws Exception
	 */
	public String registerAdd(Moac moac,String nonce,int addNum,String subchainBaseAddress,
			String administratorAddr,String administratorSecret,String walletId,String subchainId) throws Exception {

		String nodeCount = nodeCount(moac, subchainBaseAddress, administratorAddr);
		int nodeToAdd = Integer.parseInt(nodeCount)+addNum;

		Type[]inParams = {new Uint(new BigInteger(nodeToAdd+""))};
		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"registerAdd", Arrays.asList(inParams), null, "0", "", nonce);
		logger.error("添加scs节点返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"添加子链scs节点,registerAdd方法\"}","0");
		}
		return hash;
	}

	/**
	 * scs节点主动退出
	 * @param moac
	 * @param nonce
	 * @param senderType  1：scs发起请求       2：收益账号发出请求
	 * @param index scs序号（参考ScsRPCMethod.GetSubChainInfo中scs的列表）
	 * @throws Exception
	 */
	public String requestRelease(Moac moac,String nonce,String senderType,String index,String subchainBaseAddress,
			String administratorAddr,String administratorSecret) throws Exception {

		Type[]inParams = {new Uint(new BigInteger(senderType)),new Uint(new BigInteger(index))};
		TypeReference[] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"requestRelease", Arrays.asList(inParams), Arrays.asList(outParams), "0", "", nonce);
		logger.error("scs节点主动退出返回hash："+hash);
		return hash;
	}

	/**
	 * scs节点主动立即退出
	 * @param moac
	 * @param nonce
	 * @param senderType  1：scs发起请求       2：收益账号发出请求
	 * @param index scs序号（参考ScsRPCMethod.GetSubChainInfo中scs的列表）
	 * @throws Exception
	 */
	public String requestReleaseImmediate(Moac moac,String nonce,String senderType,String index,
			String subchainBaseAddress,String administratorAddr,String administratorSecret) throws Exception {

		Type[]inParams = {new Uint(new BigInteger(senderType)),new Uint(new BigInteger(index))};
		TypeReference[] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"requestReleaseImmediate", Arrays.asList(inParams), Arrays.asList(outParams), "0", "", nonce);
		logger.error("scs节点主动立即退出返回hash："+hash);
		return hash;
	}

	/**
	 * 子链关闭（关闭请求发送后，需等待一轮flush后生效，相关应用链维护费用也将退回到应用链部署账号中。 可以通过查询余额进行验证）
	 * @param moac
	 * @param nonce
	 * @throws Exception
	 */
	public String close(Moac moac,String nonce,String subchainBaseAddress,String administratorAddr,String administratorSecret,
			String walletId,String subchainId) throws Exception {

		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"close", null , null, "0", "", nonce);
		logger.error("子链关闭返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"子链关闭,close方法\"}","0");
		}
		return hash;
	}

	/**
	 * 子链重置
	 * @param moac
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	public String reset(Moac moac,String nonce,String subchainBaseAddress,String administratorAddr,String administratorSecret,
			String walletId,String subchainId) throws Exception {

		String hash = moac.callContractTransaction(subchainBaseAddress, administratorAddr, administratorSecret,
				"reset", null , null, "0", "", nonce);
		logger.error("子链重置返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, administratorAddr, subchainBaseAddress, hash,
					"{\"subchainId\":\""+subchainId+"\",\"mark\",\"子链充值,reset方法\"}","0");
		}
		return hash;
	}


	//-----------------------------------------------------


	/**
	 * 获取合约地址
	 * @param moac
	 * @param contractHash
	 * @return
	 * @throws Exception
	 */
	public String getAddress(Moac moac,String contractHash) throws Exception {

		String contractAddress = "";
		while (true) {
			try {
				contractAddress = moac.getContractAddress(contractHash);
				if(!"".equals(contractAddress)) {
					break;
				}else {
					Thread.sleep(600);
				}
			} catch (Exception e) {
				Thread.sleep(600);
			}
		}
		return contractAddress;
	}

	/**
	 *  获取交易状态结果
	 * @param moac
	 * @param hash
	 * @return
	 * @throws Exception
	 */
	public boolean getTransactionStatus(Moac moac,String hash) throws Exception {
		if(hash!=null) {
			while (true) {
				ResponseTransactionMoac mcGetTransactionByHash;
				try {
					mcGetTransactionByHash = moac.mcGetTransactionByHash(hash);
					if(StringUtils.isNotEmpty(mcGetTransactionByHash.getBlockNumber())) {
						if(mcGetTransactionByHash.isStatus()) {
							return true;
						}else {
							return false;
						}
					}
				} catch (BussinessException e) {
					if(e.getMessage().equals("等待交易上链")) {
						Thread.sleep(600);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			return false;
		}
	}

}
