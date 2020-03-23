package com.web.frame.util.moac;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Bool;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.utils.Convert;
import org.chain3j.utils.Convert.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.kevinsawicki.http.HttpRequest;
import com.web.frame.dao.web.TransactionDao;
import com.web.frame.entity.table.web.Transaction;
import com.web.frame.util.JsonFormat;

@Component
public class MoacMicro{
	
	private static Logger logger = Logger.getLogger(MoacMicro.class);
	@Autowired
	private TransactionDao transactionDao;
	
	public static void main(String[] args) throws Exception{
		
		MoacMicro micro = new MoacMicro();
		Moac moac = new Moac("http://118.190.1.235:8545");
		
		String address = "0x249ea1a73234f6c461f142eaf07224078e32f65e";
		String secret = "2f1fa8d53a10c92c0294f637f9808d1d2417223acbfe3f4136da243f360a3ea2";
		String subchainAddr = "0x93cf4e9ced4cbea78cd5ab77274660158c42cfcb";
		String monitorUrl = "http://192.168.2.100:2345/rpc";
		String dappBaseAddr = "0x96305a76d2b722bf56bd9401fbfdbe22e6a3862f";
		String erc20Addr = "0x854733268a4e6e09e3405442c79b7e48b1fc36b8";
		String walletId = "241aef6fc2814155b431b9165ad79d24";
		String via = "0x249ea1a73234f6c461f142eaf07224078e32f65e";
		
		micro.getNonce(address, subchainAddr, monitorUrl);
		micro.getSubChainInfo(subchainAddr, monitorUrl);
		micro.getDappAddrList(subchainAddr, monitorUrl);
		micro.getTransactionByHash(subchainAddr, "0x906047f0bac0294c9559efaf97b0c19cd300ff31e9c6395110afa195bb5ad178", monitorUrl);
		micro.getDappState(subchainAddr, address,monitorUrl);
		micro.getBalance(moac,dappBaseAddr, subchainAddr, monitorUrl,erc20Addr);
		micro.getReceiptByHash(subchainAddr, "0xc0bbbd055d99c4c254d8b67bc46fff27e35d7984b6e9849d17061a4fa9af7104", monitorUrl);
		
		//http://192.168.2.124:2346/rpc
		
//		String nonce = micro.getNonce(MicroConstant.administratorAddr, MicroConstant.subchainBaseAddress,"");
//		micro.getBalance(MicroConstant.dappbaseAddress, MicroConstant.subchainBaseAddress);
//		micro.getBalance(MicroConstant.administratorAddr, MicroConstant.subchainBaseAddress);
//		micro.getSubChainInfo(MicroConstant.subchainBaseAddress);
//		micro.getBlockNumber(MicroConstant.subchainBaseAddress);
//		micro.getBlock(MicroConstant.subchainBaseAddress, 1);
//		micro.getBlocks(MicroConstant.subchainBaseAddress, 1,2);
//		micro.getTxpool(MicroConstant.subchainBaseAddress);
//		micro.getDappAddrList(MicroConstant.subchainBaseAddress);
		
//		micro.getTransactionByHash(MicroConstant.subchainBaseAddress, "0xa1f685beb42ef44fcd5da04b1ee75430780a4f0f9b2ca3465c4b3096fe1d336f");
//		micro.getTransactionByNonce(MicroConstant.subchainBaseAddress, MicroConstant.administratorAddr, 20);
//		micro.getReceiptByHash(MicroConstant.subchainBaseAddress, "0xa1f685beb42ef44fcd5da04b1ee75430780a4f0f9b2ca3465c4b3096fe1d336f");
//		micro.getReceiptByNonce(MicroConstant.subchainBaseAddress, MicroConstant.administratorAddr, 20);
		
		//1.充值第一步（授权）
//		BigInteger motherNonce = moac.getNonce(MicroConstant.administratorAddr);
		/*boolean approve = micro.approve(moac, MicroConstant.erc20Address, MicroConstant.administratorAddr,
				MicroConstant.administratorSecret, MicroConstant.subchainBaseAddress, "100000000", motherNonce.toString());
		if(!approve) {
			return;
		}

		motherNonce = motherNonce.add(new BigInteger("1"));
		//2.充值第二步
		boolean buyMintToken = micro.buyMintToken(moac, MicroConstant.erc20Address, MicroConstant.administratorAddr,
				MicroConstant.administratorSecret, MicroConstant.subchainBaseAddress, "100000000", motherNonce.toString());
		if(!buyMintToken) {
			return;
		}
		
		//充值后重新查询余额
		micro.getBalance(MicroConstant.administratorAddr, MicroConstant.subchainBaseAddress);
		*/
		
		/*
		//子链转账
		micro.sendRawTransactionMicro(moac, MicroConstant.administratorAddr,
				MicroConstant.administratorSecret, Address.teamAddr, "10",
				MicroConstant.subchainBaseAddress, MicroConstant.profitAddress);
		
		//转账后重新查询发送账户余额
		micro.getBalance(MicroConstant.administratorAddr, MicroConstant.subchainBaseAddress);
		//转账后重新查询接受账户余额
		micro.getBalance(Address.teamAddr, MicroConstant.subchainBaseAddress);
		
		//提现
		micro.withdraw(moac, Address.administratorAddr, Address.administratorSecret, "10", 
				MicroConstant.subchainBaseAddress, MicroConstant.profitAddress, MicroConstant.dappbaseAddress);
		
		//查询账户子链余额
		micro.getBalance(MicroConstant.administratorAddr, MicroConstant.subchainBaseAddress);
		micro.getBalance(Address.teamAddr, MicroConstant.subchainBaseAddress);
		
		*/
		
		
		
//		String contractBalance = moac.getContractBalance(MicroConstant.administratorAddr, MicroConstant.erc20Address);
//		System.out.println("erc20余额："+contractBalance);
		
//		String code1 = "0x608060405234801561001057600080fd5b5060408051808201825260018082527f3100000000000000000000000000000000000000000000000000000000000000602092830152825180840190935282527f3200000000000000000000000000000000000000000000000000000000000000910152610694806100836000396000f3006080604052600436106100565763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663693ec85e811461005b578063b0c8f9dc14610091578063c6ddb642146100be575b600080fd5b34801561006757600080fd5b5061007b610076366004610505565b6100de565b6040516100889190610592565b60405180910390f35b34801561009d57600080fd5b506100b16100ac366004610505565b6101d9565b604051610088919061057e565b3480156100ca57600080fd5b5061007b6100d93660046104df565b6101ff565b336000908152602081815260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845260609384938493849384939283018282801561017a5780601f1061014f5761010080835404028352916020019161017a565b820191906000526020600020905b81548152906001019060200180831161015d57829003601f168201915b505050505093506040805190810160405280600181526020017f3a0000000000000000000000000000000000000000000000000000000000000081525092506101c38684610299565b91506101cf8285610299565b9695505050505050565b33600090815260208181526040822083516101f6928501906103dd565b50600192915050565b600060208181529181526040908190208054825160026001831615610100026000190190921691909104601f8101859004850282018501909352828152929091908301828280156102915780601f1061026657610100808354040283529160200191610291565b820191906000526020600020905b81548152906001019060200180831161027457829003601f168201915b505050505081565b606080606060008085518751016040519080825280601f01601f1916602001820160405280156102d3578160200160208202803883390190505b50935083925060009150600090505b86518110156103585786818151811015156102f957fe5b90602001015160f860020a900460f860020a02838380600101945081518110151561032057fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053506001016102e2565b5060005b85518110156103d257858181518110151561037357fe5b90602001015160f860020a900460f860020a02838380600101945081518110151561039a57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535060010161035c565b509195945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061041e57805160ff191683800117855561044b565b8280016001018555821561044b579182015b8281111561044b578251825591602001919060010190610430565b5061045792915061045b565b5090565b61047591905b808211156104575760008155600101610461565b90565b600061048482356105fb565b9392505050565b6000601f8201831361049c57600080fd5b81356104af6104aa826105ca565b6105a3565b915080825260208301602083018583830111156104cb57600080fd5b6104d6838284610614565b50505092915050565b6000602082840312156104f157600080fd5b60006104fd8484610478565b949350505050565b60006020828403121561051757600080fd5b813567ffffffffffffffff81111561052e57600080fd5b6104fd8482850161048b565b610543816105f6565b82525050565b6000610554826105f2565b808452610568816020860160208601610620565b61057181610650565b9093016020019392505050565b6020810161058c828461053a565b92915050565b602080825281016104848184610549565b60405181810167ffffffffffffffff811182821017156105c257600080fd5b604052919050565b600067ffffffffffffffff8211156105e157600080fd5b506020601f91909101601f19160190565b5190565b151590565b73ffffffffffffffffffffffffffffffffffffffff1690565b82818337506000910152565b60005b8381101561063b578181015183820152602001610623565b8381111561064a576000848401525b50505050565b601f01601f1916905600a265627a7a72305820ab121b41640f648e3388eb7c1c06c34349dd8c067c26014bafd8b0c151e61e026c6578706572696d656e74616cf50037";
//		String abi1 = "[{\"constant\":true,\"inputs\":[{\"name\":\"info\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"name\":\"res\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"num\",\"type\":\"string\"}],\"name\":\"add\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"}],\"name\":\"infos\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";
//		String dapp1Address = "0xb03c3dd8ce75280f85987c313ed2c81ed6cf0ea6";
		
//		micro.deployDapp(moac, address, secret,
//				code1, subchainAddr, via, abi1,monitorUrl,walletId);
		
//		micro.registerDapp(moac, address, secret, code1, subchainAddr, via, address, abi1, contractAddress, dapp1Address, monitorUrl, walletId);
		
//		Type[] in = {new Utf8String("ggg")};
//		TypeReference[] out = {TypeReference.create(Bool.class)};
//		Map map = moac.callContractTransactionMicro(micro, MicroConstant.subchainBaseAddress,
//				dapp1Address, MicroConstant.administratorAddr,
//				MicroConstant.administratorSecret, "add",
//				Arrays.asList(in), Arrays.asList(out), "0", "20", MicroConstant.profitAddress);
//		System.out.println(map);
		
//		micro.callContractMicro(MicroConstant.administratorAddr,
//				MicroConstant.subchainBaseAddress, dapp1Address, "get", "哈哈");
		
	}
	
	
	/**
	 * 查询子链nonce，返回"1"表示成功
	 * @param sender：部署账户
	 * @param subChainAddr：子链控制合约地址
	 * @return
	 */
	public String getNonce(String sender,String subChainAddr,String monitorUrl){
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetNonce");
		Map<String,String> params = new HashMap<String, String>();
		params.put("Sender", sender);
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String nonce = JsonFormat.jsonToMap(result).get("result").toString();
		logger.error(sender+"的nonce值："+nonce);
		return nonce;
	}
	
	/**
	 * 获取子链上的余额
	 * @param sender：若为账户，表示账户在子链上的余额，若为dapp地址，表示dapp在子链上的发行量
	 * @param subChainAddr：子链控制合约地址
	 * @return
	 * @throws Exception 
	 */
	public String getBalance(Moac moac,String sender,String subChainAddr,String monitorUrl,String erc20Addr) throws Exception{
		
		String decimals = moac.getContractDecimalsErc20(erc20Addr);
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetBalance");
		Map<String,String> params = new HashMap<String, String>();
		params.put("Sender", sender);
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"0":JsonFormat.jsonToMap(result).get("result").toString();
		res = new BigInteger(res).divide(new BigInteger("10").pow(Integer.parseInt(decimals))).toString();
		logger.error(sender+"查询余额："+res);
		return res;
	}
	
	/**
	 * 获得当前应用链的信息
	 * @param subChainAddr
	 * @return
	 */
	public String getSubChainInfo(String subChainAddr,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetSubChainInfo");
		Map<String,String> params = new HashMap<String, String>();
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"{}":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("应用链信息："+res);
		return res;
	}
	
	/**
	 * 获得当前应用链的区块高度
	 * @param subChainAddr
	 * @return
	 */
	public String getBlockNumber(String subChainAddr,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetBlockNumber");
		Map<String,String> params = new HashMap<String, String>();
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("当前区块高度："+res);
		return res;
	}
	
	/**
	 * 获得当前应用链的指定的区块信息
	 * @param subChainAddr
	 * @param blockNumber
	 * @return
	 */
	public String getBlock(String subChainAddr,int blockNumber,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetBlock");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("number", blockNumber);
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("区块（"+blockNumber+"）信息："+res);
		return res;
	}
	
	/**
	 * 获取某一区间内的区块信息
	 * @param subChainAddr
	 * @param startBlock
	 * @param endBlock
	 * @return
	 */
	public String getBlocks(String subChainAddr,int startBlock,int endBlock,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetBlocks");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("Start", startBlock);
		params.put("End", endBlock);
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("区块（"+startBlock+"-"+endBlock+"）信息："+res);
		return res;
	}
	
	/**
	 * 获得应用链交易池信息
	 * @param subChainAddr
	 * @return
	 */
	public String getTxpool(String subChainAddr,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetTxpool");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("交易池信息："+res);
		return res;
	}
	
	/**
	 * 获得应用链基础合约合约的状态
	 * @param subChainAddr
	 * @param sender
	 * @return
	 */
	public String getDappState(String subChainAddr,String sender,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetDappState");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		params.put("Sender", sender);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("基础合约状态："+res);
		return res;
	}
	
	/**
	 * 通过subchainaddr获取应用链内所有多合约的地址列表，需要应用链业务逻辑合约调用基础合约registerDapp方法后才能生效
	 * @param subChainAddr
	 * @return 第零位是dappbase的地址，从第一位开始时业务逻辑合约地址
	 */
	public String getDappAddrList(String subChainAddr,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetDappAddrList");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"[]":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("合约地址列表："+res);
		return res;
	}
	
	/**
	 * 通过账号和Nonce获取应用链的tx信息
	 * @param subChainAddr
	 * @param sender
	 * @param nonce
	 * @return
	 */
	public String getTransactionByNonce(String subChainAddr,String sender,int nonce,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetTransactionByNonce");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		params.put("Sender", sender);
		params.put("nonce", nonce);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("通过sender("+sender+"),nonce("+nonce+")查询交易详情："+res);
		return res;
	}
	
	/**
	 * 通过交易hash获取应用链的tx信息
	 * @param subChainAddr
	 * @param hash
	 * @return
	 */
	public String getTransactionByHash(String subChainAddr,String hash,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetTransactionByHash");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		params.put("Hash", hash);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("通过hash("+hash+")查询交易详情："+res);
		if(StringUtils.isEmpty(res)) {
			res = "{}";
		}else {
			Map<String, Object> transactionMap = JsonFormat.jsonToMap(res);
			String receipt = getReceiptByHash(subChainAddr, hash, monitorUrl);
			Map<String, Object> receiptMap = JsonFormat.jsonToMap(receipt);
			boolean status = (boolean) receiptMap.get("failed");
			String contractAddress = receiptMap.get("contractAddress")==null?"":receiptMap.get("contractAddress").toString();
			status = !status;
			String value = transactionMap.get("value").toString();
			value = Convert.fromSha(value, Unit.MC).toString();
			transactionMap.put("status", status);
			transactionMap.put("contractAddress", contractAddress);
			transactionMap.put("value", value);
			res = JsonFormat.beanToJson(transactionMap);
		}
		return res;
	}
	
	/**
	 * 通过账号和Nonce获取应用链的tx执行结果
	 * 注意：如果这是个合约部署的交易，则在contractAddress将会显示合约地址；如果是一个有返回值的方法调用，则在result中显示调用结果
	 * @param subChainAddr
	 * @param sender
	 * @return
	 */
	public String getReceiptByNonce(String subChainAddr,String sender,int nonce,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetReceiptByNonce");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		params.put("Sender", sender);
		params.put("Nonce", nonce);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("通过sender("+sender+"),nonce("+nonce+")查询交易回执："+res);
		return res;
	}
	
	/**
	 * 通过交易hash获取应用链的tx执行结果
	 * 注意：如果这是个合约部署的交易，则在contractAddress将会显示合约地址；如果是一个有返回值的方法调用，则在result中显示调用结果
	 * @param subChainAddr
	 * @param hash
	 * @return
	 */
	public String getReceiptByHash(String subChainAddr,String hash,String monitorUrl) {
		
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.GetReceiptByHash");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("SubChainAddr", subChainAddr);
		params.put("Hash", hash);
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result")==null?"":JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("通过hash("+hash+")查询交易回执："+res);
		return res;
	}
	
	/**
	 * 子链充值第一步，授权代发
	 * @param moac
	 * @param address：授权者账户
	 * @param privateKey：授权者私钥
	 * @param subchainAddr：子链地址
	 * @param value：授权数量
	 * @param nonce：授权者账户主链nonce
	 * @return
	 * @throws Exception
	 */
	public String approve(Moac moac,String erc20Address,String address,String privateKey,
			String subchainAddr,String value,String nonce,String walletId) throws Exception {
		
		String decimals = moac.getContractDecimalsErc20(erc20Address);
		value = new BigInteger("10").pow(Integer.parseInt(decimals)).multiply(new BigInteger(value)).toString();
		String hash = moac.contractApproveErc20(address, privateKey, erc20Address, 
				subchainAddr, new BigInteger(value), nonce, "");
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, erc20Address, hash, 
					"调用erc20合约approve方法","0");
		}
		logger.error("授权返回hash："+hash);
		return hash;
	}
	
	/**
	 * 保存交易记录
	 * @param walletId
	 * @param address
	 * @param to
	 * @param hash
	 * @param mark
	 * @param chainType 墨客区块链类型，0:公链，1：子链
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
	 * 子链充值第二步
	 * @param moac
	 * @param address 授权者账户
	 * @param privateKey 授权者私钥
	 * @param subchainAddr 子链地址
	 * @param value 授权数量
	 * @param nonce 授权者账户主链nonce
	 * @return
	 * @throws Exception
	 */
	public String buyMintToken(Moac moac,String erc20Address,String address,String privateKey,String subchainAddr,
			String value,String nonce,String walletId) throws Exception {
		
		String decimals = moac.getContractDecimalsErc20(erc20Address);
		value = new BigInteger("10").pow(Integer.parseInt(decimals)).multiply(new BigInteger(value)).toString();
		Type[] inParams = {new Uint256(new BigInteger(value))};
		TypeReference [] outParams = {TypeReference.create(Bool.class)};
		String hash = moac.callContractTransaction(subchainAddr, address, privateKey, "buyMintToken", 
				Arrays.asList(inParams), Arrays.asList(outParams), "0", "", nonce);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, erc20Address, hash, 
					"调用subchainbase合约buyMintToken方法","0");
		}
		logger.error("子链充值返回hash："+hash);
		return hash;
	}
	
	/**
	 * 提现
	 * @param moac
	 * @param address
	 * @param privateKey
	 * @param value
	 * @param subChainAddr
	 * @param via
	 * @return
	 * @throws Exception 
	 */
	public String withdraw(Moac moac,String address,String privateKey,String value,
			String subChainAddr,String via,String dappbaseAddr,String monitorUrl,String walletId,String erc20Addr) throws Exception {
		
		String decimals = moac.getContractDecimalsErc20(erc20Addr);
		value = new BigInteger(value).multiply(new BigInteger("10").pow(Integer.parseInt(decimals))).toString();
		String nonce = getNonce(address, subChainAddr,monitorUrl);//账户在子链上的nonce(调用ScsRPCMethod.GetNonce获得)
		String hash = moac.sendRawTransactionMicroWithdraw(address, privateKey, value,
				subChainAddr, via, nonce,dappbaseAddr);
		logger.error("子链币提现返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, subChainAddr, hash, 
					"子链提币","1");
		}
		return hash;
	}
	
	/**
	 * 子链内代币转账
	 * @param moac
	 * @param address
	 * @param privateKey
	 * @param to
	 * @param value
	 * @param subChainAddr
	 * @param via
	 * @throws Exception 
	 */
	public String sendRawTransactionMicro(Moac moac,String address,String privateKey,String to,String value,
			String subChainAddr,String via,String nonce,String monitorUrl,String walletId,String erc20Addr) throws Exception {
	
		String decimals = moac.getContractDecimalsErc20(erc20Addr);
		value = new BigInteger(value).multiply(new BigInteger("10").pow(Integer.parseInt(decimals))).toString();
		if(!StringUtils.isNotEmpty(nonce)) {
			nonce = getNonce(address, subChainAddr,monitorUrl);//账户在子链上的nonce(调用ScsRPCMethod.GetNonce获得)
		}
		String hash = moac.sendRawTransactionMicro(address, privateKey, to, value, subChainAddr, via, nonce);
		logger.error("子链币转账返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, to, hash, 
					"子链转账","1");
		}
		return hash;
	}
	
	
	/**
	 * 部署子链业务合约
	 * @param moac
	 * @param address
	 * @param secret
	 * @param code 编译代码
	 * @param subchainBaseAddress
	 * @param via
	 * @param abi 业务合约abi信息
	 * @return 
	 * @throws Exception
	 */
	public String deployDapp(Moac moac,String address,String secret,
			String code,String subchainBaseAddress,
			String via,String abi,String monitorUrl,String walletId) throws Exception {
		
		String hash = moac.deployDapp(address, secret, code,
				Convert.toSha("0", Convert.Unit.MC).toBigInteger().toString(),
				subchainBaseAddress, via, getNonce(address, subchainBaseAddress,monitorUrl));
		logger.error("业务合约部署返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, subchainBaseAddress, hash, 
					"子链业务合约部署","1");
		}
		return hash;
	}
	
	/**
	 * 在dappbase中注册业务合约
	 * @param moac
	 * @param address
	 * @param secret
	 * @param code
	 * @param subchainBaseAddress
	 * @param via
	 * @param from
	 * @param abi
	 * @param contractAddress
	 * @return 
	 * @throws Exception
	 */
	public String registerDapp(Moac moac,String address,String secret,
			String code,String subchainBaseAddress,
			String via,String from,String abi,String contractAddress,String dappBaseAddress,String monitorUrl,String walletId) throws Exception {
		
		Type[] inParams = {new org.chain3j.abi.datatypes.Address(contractAddress),
			  new org.chain3j.abi.datatypes.Address(from),
			  new Utf8String(abi)};
		
		String hash = moac.callContractTransactionMicro(this, subchainBaseAddress,
				dappBaseAddress, address, secret,
			"registerDapp", Arrays.asList(inParams), null, "0",
			 getNonce(address, subchainBaseAddress,monitorUrl), via,monitorUrl);
		logger.error("registerDapp成功,hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, subchainBaseAddress, hash, 
					"子链dappbase合约registerDapp方法调用","1");
		}
		return hash;
	}
	
	/**
	 * 子链业务合约交易类方法调用
	 * @param moac
	 * @param subChainAddr
	 * @param contractAddress
	 * @param address
	 * @param privateKey
	 * @param functionName
	 * @param inputParameters
	 * @param outputParameters
	 * @param value
	 * @param nonce
	 * @param via
	 * @return
	 * @throws Exception
	 */
	public String callContractTransactionMicro(Moac moac,String subChainAddr,String contractAddress,
			String address,String privateKey,String functionName,
			List<Type> inputParameters,List<TypeReference<?>> outputParameters,
			String value,String nonce,String via,String monitorUrl,String walletId,String erc20Address) throws Exception {
		
		if(!StringUtils.isNotEmpty(nonce)) {
			nonce = getNonce(address, subChainAddr,monitorUrl);
		}
		String decimals = moac.getContractDecimalsErc20(erc20Address);
		value = new BigInteger("10").pow(Integer.parseInt(decimals)).multiply(new BigInteger(value)).toString();
		
		String hash = moac.callContractTransactionMicro(this, subChainAddr, contractAddress, address,
				privateKey, functionName, inputParameters, outputParameters, value, nonce, via,monitorUrl);
		logger.error("调用返回hash："+hash);
		if(StringUtils.isNotEmpty(hash)) {
			addTransaction(walletId, address, subChainAddr, hash, 
					"子链dappbase合约"+functionName+"方法调用","1");
		}
		return hash;
	}
	
	/**
	 * 子链业务合约非交易类方法调用
	 * @param sender
	 * @param subChainAddr
	 * @param dappAddress
	 * @param functionName
	 * @param inParams
	 * @return
	 */
	public String callContractMicro(String sender,String subChainAddr,
			String dappAddress,String functionName,String monitorUrl,List<Object> inputParams){
		HttpRequest httpRequest = HttpRequest.post(monitorUrl);
		httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
		httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("jsonrpc", "2.0");
		dataMap.put("id", 1);
		dataMap.put("method", "ScsRPCMethod.AnyCall");
		
		inputParams.add(0, functionName);;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("Sender", sender);
		params.put("SubChainAddr", subChainAddr);
		params.put("DappAddr", dappAddress);
		params.put("Params", inputParams);
		
		
		dataMap.put("params", params );
		String beanToJson = JsonFormat.beanToJson(dataMap);
		httpRequest.send(beanToJson);
		String result = httpRequest.body();
		String res = JsonFormat.jsonToMap(result).get("result").toString();
		logger.error("调用结果："+res);
		return res;
	}
	
}






