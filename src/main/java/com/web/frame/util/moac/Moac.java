package com.web.frame.util.moac;

import com.web.frame.entity.response.ResponseTransactionMoac;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.FunctionReturnDecoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.*;
import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.abi.datatypes.generated.Uint8;
import org.chain3j.crypto.*;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Service;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.*;
import org.chain3j.protocol.core.methods.response.McBlock.Block;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.Contract;
import org.chain3j.tx.ManagedTransaction;
import org.chain3j.utils.Convert;
import org.chain3j.utils.Numeric;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Moac{
	
	private Chain3j chain3j;
	
	@Autowired
	public Moac(@Value("${chain.moac.url}") String httpUrl) { 
		Service httpService = new HttpService(httpUrl);
		chain3j = Chain3j.build(httpService);
	}

	public static void main(String[] args) throws Exception{
		
	}
	
	
	public static boolean isMoacValidAddress(String input) {
	    if (StringUtils.isEmpty(input) || !input.startsWith("0x"))
	        return false;
	    String cleanInput = input.startsWith("0x")?input.substring(2) : input;
	    try {
	        new BigInteger(cleanInput, 16);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return cleanInput.length() == 40;
	}

	
	/**
	 * 签名发送交易
	 * @param address
	 * @param privateKey
	 * @param to
	 * @param gas
	 * @param value
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public String sendRawTransaction(String address,String privateKey,String to,String gas,
			String value,String data,String nonce) throws Exception {
		
		String transactionHash = "";
		BigInteger gasBigInteger = new BigInteger("9000000");
		if(StringUtils.isNotEmpty(gas)) {
			gasBigInteger = Convert.toSha(gas, Convert.Unit.MC).toBigInteger();
		}
		
		BigInteger valueBigInteger = new BigInteger("0");
		if(StringUtils.isNotEmpty(value)) {
			valueBigInteger = Convert.toSha(value, Convert.Unit.MC).toBigInteger();
		}
		
		if(StringUtils.isNotEmpty(data)) {
			data = Numeric.toHexString(data.getBytes());
		}else {
			data = "";
		}
		if(StringUtils.isEmpty(nonce)) {
			nonce = getNonce(address).toString();
		}
		
		RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(nonce), 
				mcGasPrice(), gasBigInteger, to, valueBigInteger, data);
		
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()), credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		transactionHash = mcSendTransaction.getTransactionHash();
		if(StringUtils.isEmpty(transactionHash)||mcSendTransaction.getError()!=null) {
			throw new BussinessException(40007, mcSendTransaction.getError().getMessage());
		}
		return transactionHash;
	}
	
	/**
	 * 子链代币提现
	 * @param address：提现账户
	 * @param privateKey：账户私钥
	 * @param value：提现数量
	 * @param subChainAddr：子链控制合约地址
	 * @param via：vnode节点配置文件
	 * @param nonce：调用MoacMicro获取nonce（直接获取）
	 * @return
	 * @throws Exception 
	 */
	public String sendRawTransactionMicroWithdraw(String address,String privateKey,String value,
			String subChainAddr,String via,String nonce,String dappbaseAddr) throws Exception {
		
		String transactionHash = "";
		if(!StringUtils.isNotEmpty(value)) {
			value = "0";
		}
		BigInteger valueBigInteger = new BigInteger(value);
		
		RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(nonce), new BigInteger("0"), 
				new BigInteger("0"), subChainAddr, valueBigInteger, dappbaseAddr+"89739c5b", 1, via);
		
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		
//			Credentials credentials = WalletUtils.loadCredentials("1", "C:\\Users\\Administrator\\AppData\\Roaming\\MoacNode\\testnet\\keystore\\UTC--2018-06-19T08-21-18.185054700Z--14f322e7c813f64fcaa2b3fb0bf57c9a15766e60");
		
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()), credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		transactionHash = mcSendTransaction.getTransactionHash();
		if(StringUtils.isEmpty(transactionHash)||mcSendTransaction.getError()!=null) {
			throw new BussinessException(40007, mcSendTransaction.getError().getMessage());
		}
		return transactionHash;
	}

	/**
	 * 子链交易
	 * @param address：发起地址
	 * @param privateKey：发起地址私钥
	 * @param to：接收账户
	 * @param value:转账数量
	 * @param subChainAddr：子链控制合约地址
	 * @param via：vnode配置文件中的
	 * @param nonce：调用MoacMicro获取nonce（直接获取）
	 * @return
	 * @throws Exception 
	 */
	public String sendRawTransactionMicro(String address,String privateKey,String to,String value,
			String subChainAddr,String via,String nonce) throws Exception {
		
		String transactionHash = "";
		if(!StringUtils.isNotEmpty(value)) {
			value = "0";
		}
		BigInteger valueBigInteger = new BigInteger(value);
		
		RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(nonce), new BigInteger("0"), 
				new BigInteger("0"), subChainAddr, valueBigInteger, to, 2, via);
		
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		
//			Credentials credentials = WalletUtils.loadCredentials("1", "C:\\Users\\Administrator\\AppData\\Roaming\\MoacNode\\testnet\\keystore\\UTC--2018-06-19T08-21-18.185054700Z--14f322e7c813f64fcaa2b3fb0bf57c9a15766e60");
		
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()), credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		transactionHash = mcSendTransaction.getTransactionHash();
		if(StringUtils.isEmpty(transactionHash)||mcSendTransaction.getError()!=null) {
			throw new BussinessException(40007, mcSendTransaction.getError().getMessage());
		}
		return transactionHash;
	}
	
	/**
	 * 部署dapp合约
	 * @param address
	 * @param privateKey
	 * @param data
	 * @param value
	 * @param subChainAddr
	 * @param via
	 * @param nonce
	 * @return
	 * @throws Exception 
	 * @throws CipherException 
	 * @throws NumberFormatException 
	 */
	public String deployDapp(String address,String privateKey,String data,String value,
			String subChainAddr,String via,String nonce) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String transactionHash = "";
		if(!StringUtils.isNotEmpty(value)) {
			value = "0";
		}
		BigInteger valueBigInteger = new BigInteger(value);
		
		RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(nonce), new BigInteger("0"), 
				new BigInteger("0"), subChainAddr, valueBigInteger, data, 3, via);
		
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		
//			Credentials credentials = WalletUtils.loadCredentials("1", "C:\\Users\\Administrator\\AppData\\Roaming\\MoacNode\\testnet\\keystore\\UTC--2018-06-19T08-21-18.185054700Z--14f322e7c813f64fcaa2b3fb0bf57c9a15766e60");
		
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()), credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		transactionHash = mcSendTransaction.getTransactionHash();
		if(StringUtils.isEmpty(transactionHash)||mcSendTransaction.getError()!=null) {
			throw new BussinessException(40007, mcSendTransaction.getError().getMessage());
		}
		return transactionHash;
	}
	
	/**
	 * 子链内合约交易方法调用
	 * @param micro
	 * @param subChainAddr 子链地址
	 * @param contractAddress 调用合约地址
	 * @param address 账号
	 * @param privateKey 私钥
	 * @param functionName 方法名
	 * @param inputParameters 入参
	 * @param outputParameters 出参
	 * @param value 币数量
	 * @param nonce 账户子链nonce值
	 * @param via 收益地址
	 * @return
	 * @throws Exception
	 */
	public String callContractTransactionMicro(MoacMicro micro,String subChainAddr,String contractAddress,
			String address,String privateKey,String functionName,
			List<Type> inputParameters,List<TypeReference<?>> outputParameters,
			String value,String nonce,String via,String monitorUrl) throws Exception{
		
		inputParameters = inputParameters==null?new ArrayList<Type>():inputParameters;
		outputParameters = outputParameters==null?new ArrayList<TypeReference<?>>():outputParameters;
		Function function = new Function(functionName, inputParameters, outputParameters);
		String encodedFunction = FunctionEncoder.encode(function);
		encodedFunction = Numeric.cleanHexPrefix(encodedFunction);
		
		BigInteger valueBig = BigInteger.ZERO;
		if(StringUtils.isNotEmpty(value)) {
			valueBig = new BigInteger(value);
		}
		if(!StringUtils.isNotEmpty(nonce)) {
			nonce = micro.getNonce(address, subChainAddr,monitorUrl);
		}
		RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(nonce), new BigInteger("0"), 
				new BigInteger("0"), subChainAddr, valueBig, contractAddress+encodedFunction, 1, via);
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()),credentials);
		//交易订单号
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		String transactionHash = mcSendTransaction.getTransactionHash();
		if(StringUtils.isEmpty(transactionHash)||mcSendTransaction.getError()!=null) {
			throw new BussinessException(40007, mcSendTransaction.getError().getMessage());
		}
		return transactionHash;
	}
	
	
	/**
	 * 获取随机串
	 * @param address
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public BigInteger getNonce(String address) throws Exception {
        McGetTransactionCount ethGetTransactionCount = chain3j.mcGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).send();
        return ethGetTransactionCount.getTransactionCount();
    }
	
	/**
	 * 创建账户 
	 * 0:账户地址
	 * 1：账户公钥
	 * 2：账户私钥
	 * @return
	 * @throws Exception 
	 */
	public List<String> personNewAccount(String password) throws Exception {
		
		ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
        String privateKey = privateKeyInDec.toString(16);
        ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		List<String> list = new ArrayList<String>();
		list.add(credentials.getAddress());
		list.add(Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPublicKey()));
		list.add(Numeric.toHexStringNoPrefix(credentials.getEcKeyPair().getPrivateKey()));
		return list;
	}
	
	/**
	 *  通过私钥导入
	 * 0:账户地址
	 * 1：账户公钥
	 * 2：账户私钥
	 * @param privateKey
	 * @return
	 */
	public List<String> importAccountByPrivateKey(String privateKey){
		
		List<String> list = new ArrayList<String>();
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		list.add(credentials.getAddress());
		list.add(Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPublicKey()));
		list.add(Numeric.toHexStringNoPrefix(credentials.getEcKeyPair().getPrivateKey()));
		return list;
	}
	
	/**
	 * 客户端版本
	 * @return
	 * @throws Exception
	 */
    public String getChain3ClientVersion() throws Exception {
        Chain3ClientVersion chain3ClientVersion = chain3j.chain3ClientVersion().send();
        String clientVersion = chain3ClientVersion.getChain3ClientVersion();
        return clientVersion;
    }

    /**
      * 返回指定数据的Keccak-256（不同于标准的SHA3-256算法）哈希值。
     * @param data
     * @return
     * @throws Exception
     */
    public String getChain3Sha3(String data) throws Exception {
        Chain3Sha3 chain3Sha3 = chain3j.chain3Sha3(data).send();
        return chain3Sha3.getResult();
    }

    /**
      * 返回当前连接网络的ID
     * @return
     * @throws Exception
     */
    public String getNetVersion() throws Exception {
        NetVersion netVersion = chain3j.netVersion().send();
        return netVersion.getResult();
    }

    /**
       * 如果客户端处于监听网络连接的状态，该调用返回true。
     * @return
     * @throws Exception
     */
    public boolean getNetListening() throws Exception {
        NetListening netListening = chain3j.netListening().send();
        return netListening.getResult();
    }

    /**
     * 连接的节点数
     * @return
     * @throws Exception
     */
    public String getNetPeerCount() throws Exception {
        NetPeerCount netPeerCount = chain3j.netPeerCount().send();
        return netPeerCount.getResult();
    }

    /**
     * 协议版本
     * @return
     * @throws Exception
     */
    public String mcProtocolVersion() throws Exception {
        McProtocolVersion ethProtocolVersion = chain3j.mcProtocolVersion().send();
        return ethProtocolVersion.getResult();
    }

    /**
     * 是否在同步区块
     * @return
     * @throws Exception
     */
    public boolean mcSyncing() throws Exception {
        McSyncing mcSyncing = chain3j.mcSyncing().send();
        return mcSyncing.isSyncing();
    }

    /**
      * 挖矿奖励账户
     * @return
     * @throws Exception
     */
    public String mcCoinbase() throws Exception {
        McCoinbase ethCoinbase = chain3j.mcCoinbase().send();
        return ethCoinbase.getResult();
    }

    /**
      * 是否在挖矿
     * @return
     * @throws Exception
     */
    public boolean mcMining() throws Exception {
        McMining ethMining = chain3j.mcMining().send();
        return ethMining.isMining();
    }

    /**
      * 挖矿速度
     * @return
     * @throws Exception
     */
    public String mcHashrate() throws Exception {
        McHashrate ethHashrate = chain3j.mcHashrate().send();
        return ethHashrate.getResult();
    }

    /**
      * 当前gas price
     * @return
     * @throws Exception
     */
    public BigInteger mcGasPrice() throws Exception {
        McGasPrice ethGasPrice = chain3j.mcGasPrice().send();
        return ethGasPrice.getGasPrice();
    }

    /**
       *节点账户列表
     * @return
     * @throws Exception
     */
    public List<String> mcAccounts() throws Exception {
        McAccounts ethAccounts = chain3j.mcAccounts().send();
        return ethAccounts.getAccounts();
    }

    /**
     * 区块数量
     * @return
     * @throws Exception
     */
    public BigInteger mcBlockNumber() throws Exception {
        McBlockNumber ethBlockNumber = chain3j.mcBlockNumber().send();
        return ethBlockNumber.getBlockNumber();
    }

    /**
     * 获取余额
     * @param address
     * @return
     * @throws Exception
     */
    public BigDecimal mcGetBalance(String address) throws Exception {
        McGetBalance ethGetBalance = chain3j.mcGetBalance(
        		address, DefaultBlockParameter.valueOf("latest")).send();
        
        BigDecimal moac = Convert.fromSha(ethGetBalance.getBalance().toString(), Convert.Unit.MC);
        return moac;
    }

    /**
     * 返回账号存储位置的值。
     * @param address
     * @return
     * @throws Exception
     */
    public String mcGetStorageAt(String address,String index) throws Exception {
        McGetStorageAt ethGetStorageAt = chain3j.mcGetStorageAt(
        		address,
        		new BigInteger(index),
                DefaultBlockParameter.valueOf("latest")).send();
        return ethGetStorageAt.getData();
    }

    /**
     * 获取账号交易次数   (该次数用nonce参数设置)
     * @param address
     * @return
     * @throws Exception
     */
    public BigInteger mcGetTransactionCount(String address) throws Exception {
        McGetTransactionCount ethGetTransactionCount = chain3j.mcGetTransactionCount(
                address,
                DefaultBlockParameter.valueOf("latest")).send();
        return ethGetTransactionCount.getTransactionCount();
    }

    /**
     * 返回指定块内的交易数量，使用哈希来指定块。
     * @param blockHash
     * @return
     * @throws Exception
     */
    public BigInteger mcGetBlockTransactionCountByHash(String blockHash) throws Exception {
        McGetBlockTransactionCountByHash ethGetBlockTransactionCountByHash =
                chain3j.mcGetBlockTransactionCountByHash(blockHash).send();
        return ethGetBlockTransactionCountByHash.getTransactionCount();
    }

    /**
     * 返回指定块内的交易数量，使用块序号。
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public BigInteger mcGetBlockTransactionCountByNumber(BigInteger blockNumber) throws Exception {
        McGetBlockTransactionCountByNumber ethGetBlockTransactionCountByNumber =
                chain3j.mcGetBlockTransactionCountByNumber(
                        DefaultBlockParameter.valueOf(blockNumber)).send();
        return ethGetBlockTransactionCountByNumber.getTransactionCount();
    }

    /**
     * 返回指定块 叔块，使用哈希来指定块。
     * @param blockHash
     * @return
     * @throws Exception
     */
    public BigInteger mcGetUncleCountByBlockHash(String blockHash) throws Exception {
        McGetUncleCountByBlockHash ethGetUncleCountByBlockHash =
                chain3j.mcGetUncleCountByBlockHash(blockHash).send();
        return ethGetUncleCountByBlockHash.getUncleCount();
    }

    /**
     * 返回指定块 叔块，使用序号来指定块。
     * @return
     * @throws Exception
     */
    public BigInteger mcGetUncleCountByBlockNumber() throws Exception {
        McGetUncleCountByBlockNumber ethGetUncleCountByBlockNumber =
                chain3j.mcGetUncleCountByBlockNumber(
                        DefaultBlockParameter.valueOf("latest")).send();
        return ethGetUncleCountByBlockNumber.getUncleCount();
    }

    /**
     * 返回指定地址的代码。
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public String mcGetCode(String contractAddress) throws Exception {
        McGetCode ethGetCode = chain3j.mcGetCode(contractAddress,
                DefaultBlockParameter.valueOf("latest")).send();
        return ethGetCode.getCode();
    }


    /**
     * 返回指快哈希的块信息。
     * @param blockHash
     * @return
     * @throws Exception
     */
    public Block mcGetBlockByHashReturnHashObjects(String blockHash) throws Exception {
        McBlock ethBlock = chain3j.mcGetBlockByHash(blockHash, false)
                .send();

        McBlock.Block block = ethBlock.getBlock();
        return block;
    }

    /**
     * 返回指快哈希的交易信息。
     * @param blockHash
     * @return
     * @throws Exception
     */
    public Block mcGetBlockByHashReturnFullTransactionObjects(String blockHash) throws Exception {
        McBlock ethBlock = chain3j.mcGetBlockByHash(blockHash, true)
                .send();

        McBlock.Block block = ethBlock.getBlock();
        return block;
    }

    /**
     * 返回指定编号的块信息。
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public Block mcGetBlockByNumberReturnHashObjects(BigInteger blockNumber) throws Exception {
        McBlock ethBlock = chain3j.mcGetBlockByNumber(
                DefaultBlockParameter.valueOf(blockNumber), false).send();
        McBlock.Block block = ethBlock.getBlock();
        return block;
    }


    /**
     * 返回指定编号的块信息---交易信息
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public Block mcGetBlockByNumberReturnTransactionObjects(BigInteger blockNumber) throws Exception {
        McBlock ethBlock = chain3j.mcGetBlockByNumber(
                DefaultBlockParameter.valueOf(blockNumber), true).send();

        McBlock.Block block = ethBlock.getBlock();
        return block;
    }

    /**
     * 返回指定交易哈希的交易信息
     * @param transactionHash
     * @return
     * @throws Exception
     */
    public ResponseTransactionMoac mcGetTransactionByHash(String transactionHash) throws Exception {
    	
        McTransaction ethTransaction = chain3j.mcGetTransactionByHash(
        		transactionHash).send();
        if(ethTransaction.getResult() == null) {
        	throw new BussinessException(40007, "未查询到交易信息");
        }
        org.chain3j.protocol.core.methods.response.Transaction transaction = ethTransaction.getTransaction().get();
        if(transaction == null) {
        	throw new BussinessException(40007, "未查询到交易信息");
        }
        
        try {
        	if(transaction.getBlockNumber() == null) {
            	throw new BussinessException(40007, "等待交易上链");
            }
		} catch (Exception e) {
			throw new BussinessException(40007, "等待交易上链"); 
		}
        
        ResponseTransactionMoac transactionMoac = new ResponseTransactionMoac();
        
    	BeanUtils.copyProperties(transaction, transactionMoac);
    	
    	transactionMoac.setValue(Convert.fromSha(transaction.getValue().toString(), Convert.Unit.MC).toString());
    	transactionMoac.setBlockNumber(transaction.getBlockNumber().toString());
    	transactionMoac.setNonce(transaction.getNonce().toString());
    	
    	
        String to = transaction.getTo();
        String code = this.mcGetCode(to);
        if(transaction.getInput()!=null&&"0x".equals(code)) {//文本备注
        	transaction.setInput(new String(Numeric.hexStringToByteArray(transaction.getInput())));
        }
        TransactionReceipt transactionReceipt = mcGetTransactionReceipt(transactionHash);
        
        transactionMoac.setGas(transaction.getGas().toString());
        transactionMoac.setGasPrice(Convert.fromSha(transaction.getGasPrice().toString(), Convert.Unit.MC).toString());
        transactionMoac.setGasUsed(transactionReceipt.getGasUsed().toString());
        transactionMoac.setActualGasCost(new BigDecimal(Convert.fromSha(transaction.getGasPrice().toString(), Convert.Unit.MC).toString())
        		.multiply(new BigDecimal(transactionReceipt.getGasUsed().toString())).toString());
        transactionMoac.setStatus(transactionReceipt.isStatusOK());
        transactionMoac.setTransactionIndex(transactionReceipt.getTransactionIndex().toString());
        transactionMoac.setContractAddress(transactionReceipt.getContractAddress());
        transactionMoac.setRoot(transactionReceipt.getRoot());
        transactionMoac.setLogsBloom(transactionReceipt.getLogsBloom());
        transactionMoac.setLogs(transactionReceipt.getLogs());
        
        return  transactionMoac;
    }

    /**
     * 返回指定制定快哈希的第INdex的交易信息
     * @param blockHash
     * @return
     * @throws Exception
     */
    public org.chain3j.protocol.core.methods.response.Transaction mcGetTransactionByBlockHashAndIndex(String blockHash) throws Exception {
        BigInteger index = BigInteger.ZERO;

        McTransaction ethTransaction = chain3j.mcGetTransactionByBlockHashAndIndex(
        		 blockHash, index).send();
        org.chain3j.protocol.core.methods.response.Transaction transaction = ethTransaction.getTransaction().get();
        return transaction;
    }

    /**
     * 返回指定制定快地址的第INdex的交易信息
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public org.chain3j.protocol.core.methods.response.Transaction mcGetTransactionByBlockNumberAndIndex(BigInteger blockNumber,String index) throws Exception {
        
        McTransaction ethTransaction = chain3j.mcGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(blockNumber), new BigInteger(index)).send();
        org.chain3j.protocol.core.methods.response.Transaction transaction = ethTransaction.getTransaction().get();
        return transaction;
    }

    /**
     * 返回指定交易的收据，使用哈希指定交易。
     * @param transactionHash
     * @return
     * @throws Exception
     */
    public TransactionReceipt mcGetTransactionReceipt(String transactionHash) {
    	
        TransactionReceipt transactionReceipt = null;
		try {
			McGetTransactionReceipt ethGetTransactionReceipt = chain3j.mcGetTransactionReceipt(
					transactionHash).send();
			transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt().get();
		} catch (IOException e) {
			transactionReceipt = null;
		}
        return transactionReceipt;
    }

    /**
     * 返回具有指定哈希的块具有指定索引位置的叔伯。
     * @param uncleBlockHash
     * @return
     * @throws Exception
     */
    public Block testMcGetUncleByBlockHashAndIndex(String uncleBlockHash) throws Exception {
        McBlock ethBlock = chain3j.mcGetUncleByBlockHashAndIndex(
        		uncleBlockHash, BigInteger.ZERO).send();
        return ethBlock.getBlock();
    }

    /**
     * 返回具有指定编号的块内具有指定索引序号的叔伯。
     * @param uncleBlockNumber
     * @return
     * @throws Exception
     */
    public Block mcGetUncleByBlockNumberAndIndex(BigInteger uncleBlockNumber) throws Exception {
        McBlock ethBlock = chain3j.mcGetUncleByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(uncleBlockNumber), BigInteger.ZERO)
                .send();
        return ethBlock.getBlock();
    }

    
    
    /**
	 * 创建合约
	 * @param contractCode 合约编码
	 * @param credentials 账户
	 * @param params 有以下类型:Address、Array<Type>、Bool、BytesType、NumericType、Utf8String
	 * @return
	 * @throws Exception
	 */
	public String deployContract(String contractCode,String address,String privateKey, List<Type> params,String nonce) throws Exception{
		
		//合约参数部分
		String encodedConstructor = "";
		if(params!=null&&params.size()>0){
			encodedConstructor = FunctionEncoder.encodeConstructor(params);
		}
		
		BigInteger userNonce = null;
		//发送合约
		if(StringUtils.isNotEmpty(nonce)) {
			userNonce = new BigInteger(nonce);
		}else {
			userNonce = getNonce(address);
		}
		

		RawTransaction rawTransaction = RawTransaction
				.createContractTransaction(userNonce, ManagedTransaction.GAS_PRICE,Contract.GAS_LIMIT, 
						new BigInteger("0"), "0x"+contractCode+encodedConstructor);
		
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()), credentials);
		//交易订单号
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		String transactionHash = mcSendTransaction.getTransactionHash();
		
		return transactionHash;
	}
	
	/**
	 * 获取合约地址
	 * @param transactionHash
	 * @return
	 * @throws Exception 
	 */
	public String getContractAddress(String transactionHash) throws Exception {
		
		McGetTransactionReceipt transactionReceipt = chain3j.mcGetTransactionReceipt(transactionHash).sendAsync().get();
		if (transactionReceipt.getTransactionReceipt().isPresent()) {
		    String contractAddress = transactionReceipt.getResult().getContractAddress();
		    return contractAddress;
		}else {
			return "";
		}
	}
	
	/**
	 * 调用合约方法（需要交易）
	 * @param contractAddress 合约地址
	 * @param credentials 账户
	 * @param inputParameters 输入参数类型 有以下类型:Address、Array<Type>、Bool、BytesType、NumericType、Utf8String
	 * @param outputParameters 输出参数类型 有以下类型:Address、Array<Type>、Bool、BytesType、NumericType、Utf8String
	 * @return
	 * @throws Exception 
	 */
	public String callContractTransaction(String contractAddress,String address,String privateKey,String functionName,
			List<Type> inputParameters,List<TypeReference<?>> outputParameters,String value,String gas,String nonce) throws Exception{
		
		inputParameters = inputParameters==null?new ArrayList<Type>():inputParameters;
		outputParameters = outputParameters==null?new ArrayList<TypeReference<?>>():outputParameters;
		Function function = new Function(functionName, inputParameters, outputParameters);
		String encodedFunction = FunctionEncoder.encode(function);
		
		boolean res = false;
		if(!outputParameters.isEmpty()) {
			McCall response = chain3j.mcCall(Transaction.createMcCallTransaction(address, contractAddress, encodedFunction),  DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> someTypes = FunctionReturnDecoder.decode(
					response.getValue(), function.getOutputParameters());
			
//			if(someTypes.size()==0) {
//				throw new BussinessException(40007, "调用合约失败，请检查参数与合约方法内容");
//			}
		}
		
		BigInteger gasBigInteger = new BigInteger("9000000");
		if(StringUtils.isNotEmpty(gas)) {
			gasBigInteger = Convert.toSha(gas, Convert.Unit.MC).toBigInteger();
		}
		
		BigInteger valueBig = BigInteger.ZERO;
		if(StringUtils.isNotEmpty(value)) {
			valueBig = new BigInteger(value);
		}
		if(!StringUtils.isNotEmpty(nonce)) {
			nonce = getNonce(address).toString();
		}
		RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(nonce), mcGasPrice(),gasBigInteger,
				contractAddress, valueBig ,encodedFunction);
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		Credentials credentials = Credentials.create(ecKeyPair);
		byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, Integer.parseInt(getNetVersion()),credentials);
		//交易订单号
		String hexValue = Numeric.toHexString(signedMessage);
		McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
		String transactionHash = mcSendTransaction.getTransactionHash();
		if(StringUtils.isNotEmpty(transactionHash)) {
			return transactionHash;
		}else {
			throw new BussinessException(40007, mcSendTransaction.getError().getMessage());
		}
	}
	
	/**
	 * 调用合约内方法（不需发起交易）
	 * @param contractAddress 合约地址
	 * @param address 发起地址
	 * @param functionName 方法名
	 * @param inputParameters 输入参数类型 有以下类型:Address、Array<Type>、Bool、BytesType、NumericType、Utf8String
	 * @param outputParameters 输出参数类型 有以下类型:Address、Array<Type>、Bool、BytesType、NumericType、Utf8String
	 * @return
	 * @throws Exception
	 */
	public List<Type> callContract(String contractAddress,String address,String functionName, 
			List<Type> inputParameters,List<TypeReference<?>> outputParameters) throws Exception{
		
		inputParameters = inputParameters==null?new ArrayList<Type>():inputParameters;
		outputParameters = outputParameters==null?new ArrayList<TypeReference<?>>():outputParameters;
		Function function = new Function(functionName, inputParameters, outputParameters);
		String encodedFunction = FunctionEncoder.encode(function);
		McCall response = chain3j.mcCall(Transaction.createMcCallTransaction(address, contractAddress, encodedFunction),  DefaultBlockParameterName.LATEST).sendAsync().get();
		List<Type> someTypes = FunctionReturnDecoder.decode(
	             response.getValue(), function.getOutputParameters());
		return someTypes;
	}
	
	
	/**
	 * 获取代币余额
	 * @param address
	 * @param contractAddress
	 * @return
	 * @throws Exception
	 */
	public String getContractBalance(String address,String contractAddress) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(address));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Uint256.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"balanceOf", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String balanceOf = callContract.get(0).getValue().toString();
		return balanceOf;
	}
	
	/**
	 * 获取代币名
	 * @param address
	 * @param contractAddress
	 * @return
	 * @throws Exception
	 */
	public String getContractSymbol(String contractAddress) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Utf8String.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"symbol", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String symbol = callContract.get(0).getValue().toString();
		return symbol;
	}
	
	/**
	 * 获取合约名
	 * @param contractAddress
	 * @return
	 * @throws Exception
	 */
	public String getContractName(String contractAddress) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Utf8String.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"name", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String name = callContract.get(0).getValue().toString();
		return name;
	}
	
	/**
	 * 获取总量
	 * @param contractAddress
	 * @return
	 * @throws Exception
	 */
	public String getContractTotalSupply(String contractAddress) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Uint256.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"totalSupply", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String totalSupply = callContract.get(0).getValue().toString();
		return totalSupply;
	}
	
	/**
	 * 获取erc20合约decimals
	 * @param contractAddress
	 * @return
	 * @throws Exception
	 */
	public String getContractDecimalsErc20(String contractAddress) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Uint8.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"decimals", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "0";
		}
		String decimals = callContract.get(0).getValue().toString();
		return decimals;
	}
	
	/**
	 * erc20合约转账
	 * @param address
	 * @param privateKey
	 * @param contractAddress
	 * @param to
	 * @param value
	 * @param gas 
	 * @return
	 * @throws Exception
	 */
	public String contractTransferErc20(String address,String privateKey,String contractAddress,String to,
			BigInteger value,String nonce, String gas) throws Exception {
		
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(to));
		inputParameters.add(new Uint256(value));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Bool.class));
		String callContractTransaction = callContractTransaction(contractAddress, address,
				privateKey, "transfer", inputParameters , outputParameters , "0", gas,nonce);
		return callContractTransaction;
	}
	
	/**
	 * erc20合约授权他人操作代币
	 * @param address
	 * @param privateKey
	 * @param contractAddress
	 * @param spender
	 * @param value
	 * @param gas 
	 * @return
	 * @throws Exception
	 */
	public String contractApproveErc20(String address,String privateKey,String contractAddress,String spender,
			BigInteger value,String nonce, String gas) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(spender));
		inputParameters.add(new Uint256(value));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Bool.class));
		String callContractTransaction = callContractTransaction(contractAddress, address,
				privateKey, "approve", inputParameters , outputParameters , "0", gas,nonce);
		return callContractTransaction;
	}
	
	/**
	 * erc20合约代人转账（与approve配合使用）
	 * @param address
	 * @param privateKey
	 * @param contractAddress
	 * @param from
	 * @param to
	 * @param value
	 * @param gas 
	 * @return
	 * @throws Exception
	 */
	public String contractTransferFromErc20(String address,String privateKey,String contractAddress,String from,
			String to,BigInteger value,String nonce, String gas) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(from));
		inputParameters.add(new Address(to));
		inputParameters.add(new Uint256(value));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Bool.class));
		String callContractTransaction = callContractTransaction(contractAddress, address,
				privateKey, "transferFrom", inputParameters , outputParameters , "0", gas,nonce);
		return callContractTransaction;
	}
	
	/**
	 * erc20合约获取所有者允许他人操作代币数量
	 * @param contractAddress
	 * @param owner
	 * @param spender
	 * @return
	 * @throws Exception
	 */
	public String contractAllowanceErc20(String contractAddress,String owner,String spender) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(owner));
		inputParameters.add(new Address(spender));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Uint256.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"allowance", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String symbol = callContract.get(0).getValue().toString();
		return symbol;
	}
	
	/**
	 * 获取erc721合约代币所属者
	 * @param contractAddress
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public String contractOwnerOfErc721(String contractAddress,BigInteger tokenId) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Uint256(tokenId));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Address.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"ownerOf", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String ownerOf = callContract.get(0).getValue().toString();
		return ownerOf;
	}
	
	/**
	 * erc721查询代币是否存在
	 * @param contractAddress
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public String contractExistsErc721(String contractAddress,BigInteger tokenId) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Uint256(tokenId));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Bool.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"exists", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String exists = callContract.get(0).getValue().toString();
		return exists;
	}
	
	/**
	 * erc721获取合约tokenURI信息
	 * @param contractAddress
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public String contractTokenURIErc721(String contractAddress,BigInteger tokenId) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Uint256(tokenId));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Utf8String.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"tokenURI", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String tokenURI = callContract.get(0).getValue().toString();
		return tokenURI;
	}
	
	/**
	 * erc721获取该代币所授权的人
	 * @param contractAddress
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public String contractGetApprovedErc721(String contractAddress,BigInteger tokenId) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Uint256(tokenId));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Address.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"getApproved", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String getApproved = callContract.get(0).getValue().toString();
		return getApproved;
	}
	
	/**
	 * erc721获取用户是否授权操作给他人
	 * @param contractAddress
	 * @param owner
	 * @param spender
	 * @return
	 * @throws Exception
	 */
	public String contractIsApprovedForAllErc721(String contractAddress,String owner,String spender) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(owner));
		inputParameters.add(new Address(spender));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		outputParameters.add(TypeReference.create(Bool.class));
		List<Type> callContract = callContract(contractAddress, "0x14f322e7c813f64fcaa2b3fb0bf57c9a15766e60", 
				"isApprovedForAll", inputParameters , outputParameters);
		if(callContract.size()==0) {
			return "";
		}
		String isApprovedForAll = callContract.get(0).getValue().toString();
		return isApprovedForAll;
	}
	
	/**
	 * erc721用户授权他人某个代币操作权限
	 * @param address
	 * @param privateKey
	 * @param contractAddress
	 * @param spender
	 * @param tokenId
	 * @param gas 
	 * @return
	 * @throws Exception
	 */
	public String contractApproveErc721(String address,String privateKey,String contractAddress,
			String spender,BigInteger tokenId,String nonce, String gas) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(spender));
		inputParameters.add(new Uint256(tokenId));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		String callContractTransaction = callContractTransaction(contractAddress, address,
				privateKey, "approve", inputParameters , outputParameters , "0", gas,nonce);
		return callContractTransaction;
	}
	
	/**
	 * erc721授权或取消授权给他人操作自己代币的权限
	 * @param address
	 * @param privateKey
	 * @param contractAddress
	 * @param spender
	 * @param approved
	 * @param gas 
	 * @return
	 * @throws Exception
	 */
	public String contractSetApprovalForAllErc721(String address,String privateKey,
			String contractAddress,String spender,boolean approved,String nonce, String gas) throws Exception {
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(spender));
		inputParameters.add(new Bool(approved));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		String callContractTransaction = callContractTransaction(contractAddress, address,
				privateKey, "setApprovalForAll", inputParameters , outputParameters , "0", gas,nonce);
		return callContractTransaction;
	}
	
	/**
	 * erc721消息发送者从所有者发送token给他人（消息发送者可为所有者、当前token授权人、具有所有者所有token操作权限人）
	 * @param address
	 * @param privateKey
	 * @param contractAddress
	 * @param from
	 * @param to
	 * @param tokenId
	 * @param gas 
	 * @return
	 * @throws Exception
	 */
	public String contractTransferFromErc721(String address,String privateKey,String contractAddress,
			String from,String to,BigInteger tokenId,String nonce, String gas) throws Exception {
		
		String contractSymbol = this.getContractSymbol(contractAddress);
		List<Type> inputParameters = new ArrayList<Type>();
		inputParameters.add(new Address(from));
		inputParameters.add(new Address(to));
		inputParameters.add(new Uint256(tokenId));
		List<TypeReference<?>> outputParameters = new ArrayList<TypeReference<?>>();
		String callContractTransaction = callContractTransaction(contractAddress, address,
				privateKey, "transferFrom", inputParameters , outputParameters , "0", gas,nonce);
		return callContractTransaction;
	}
	
	/**
	 * 获取输入参数
	 * @param params  [{"type":"","value":""}]
	 * @return
	 */
	public List<Type> getInParams(String params){
		
		List<Type> list = new ArrayList<Type>();
		if(!StringUtils.isNotEmpty(params)) {
			return list;
		}
		List<Map<String, Object>> paramsList = JsonFormat.parseJSON2List(params);
		for (int i = 0; i < paramsList.size(); i++) {
			String type = (String) paramsList.get(i).get("type");
			if("bool".equals(type)) {
				boolean value = false;
				try {
					String	valueTepm = (String) paramsList.get(i).get("value");
					if("true".equals(valueTepm)) {
						value = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(new Bool(value));
			}else if("string".equals(type)) {
				String value = "";
				try {
					value = (String) paramsList.get(i).get("value");
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(new Utf8String(value));
			}else if("uint8".equals(type)) {
				String value = "";
				try {
					value = (String) paramsList.get(i).get("value");
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(new Uint8(new BigInteger(value)));
			}else if("uint256".equals(type)) {
				String value = "";
				try {
					value = (String) paramsList.get(i).get("value");
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(new Uint256(new BigInteger(value)));
			}else if("address".equals(type)) {
				String value = "";
				try {
					value = (String) paramsList.get(i).get("value");
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(new Address(value));
			}
		}
		return list;
	}
	
	/**
	 * 获取输出参数
	 * @param params [{"type":""},{"type":""}]
	 * @return
	 */
	public List<TypeReference<?>> getOutParams(String params){
		
		List<TypeReference<?>> list = new ArrayList<TypeReference<?>>();
		if(!StringUtils.isNotEmpty(params)) {
			return list;
		}
		List<Map<String, Object>> paramsList = JsonFormat.parseJSON2List(params);
		for (int i = 0; i < paramsList.size(); i++) {
			String type = (String) paramsList.get(i).get("type");
			if("bool".equals(type)) {
				list.add(TypeReference.create(Bool.class));
			}else if("string".equals(type)) {
				list.add(TypeReference.create(Utf8String.class));
			}else if("uint8".equals(type)) {
				list.add(TypeReference.create(Uint8.class));
			}else if("uint256".equals(type)) {
				list.add(TypeReference.create(Uint256.class));
			}else if("address".equals(type)) {
				list.add(TypeReference.create(Address.class));
			}
		}
		return list;
	}
	
	
}






