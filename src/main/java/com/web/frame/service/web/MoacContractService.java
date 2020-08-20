package com.web.frame.service.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.frame.dao.web.AccountDao;
import com.web.frame.dao.web.TransactionDao;
import com.web.frame.dao.web.WalletDao;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.entity.table.web.Account;
import com.web.frame.entity.table.web.Transaction;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.EncryptSHA;
import com.web.frame.util.Md5;
import com.web.frame.util.moac.Moac;

@Service
@Transactional
public class MoacContractService {
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private EncryptSHA encryptSHA;
	@Autowired
	private Moac moac;
	@Autowired
	private TransactionDao transactionDao;

	public ResponseEntity<ResponseBase<Map<String, String>>> deploy(String accessToken, String walletId, String id,
			String code, String params, String payPsw) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		
		List<Type> inParams = moac.getInParams(params);
		
		String hash = moac.deployContract(code, account.getAddress(), Md5.decrypt(account.getPrivateKey()), inParams, "");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "部署调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> getContractAddress(String accessToken, String walletId,
			String contractHash) throws Exception {
		
		String contractAddress = moac.getContractAddress(contractHash);
		Map<String,String> map = new HashMap<String,String>();
		map.put("contractAddress", contractAddress);
		return ResponseBase.createResponse(true, 10000, "获取合约地址调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<List<String>>> call(String accessToken, String walletId,
			String contractAddress, String functionName,String inParams, String outParams, String id) throws Exception {
		
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		List<Type> in = moac.getInParams(inParams);
		List<TypeReference<?>> out = moac.getOutParams(outParams);
		List<Type> callContract = moac.callContract(contractAddress, account.getAddress(), functionName, in, out);
		
		List<String> data = new ArrayList<String>();
		if(callContract!=null) {
			for (int i = 0; i < callContract.size(); i++) {
				data.add(callContract.get(i).getValue().toString());
			}
		}
		
		return ResponseBase.createResponse(true, 10000, "非交易类合约方法调用成功", data, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> callTransaction(String accessToken, String walletId,
			String id, String contractAddress, String functionName, String inParams, String outParams, String payPsw,
			String value, String gas, String nonce) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		List<Type> in = moac.getInParams(inParams);
		List<TypeReference<?>> out = moac.getOutParams(outParams);
		String callContractTransaction = moac.callContractTransaction(contractAddress, account.getAddress(), Md5.decrypt(account.getPrivateKey()), functionName,
				in, out, value, gas, nonce);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", callContractTransaction);
		return ResponseBase.createResponse(true, 10000, "合约方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> balance(String accessToken, String walletId, String id, String contractAddress) throws Exception {
		
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String contractBalance = moac.getContractBalance(account.getAddress(), contractAddress);
		Map<String,String> map = new HashMap<String,String>();
		map.put("balance", contractBalance);
		return ResponseBase.createResponse(true, 10000, "余额方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> totalSupply(String accessToken, String walletId,
			String contractAddress) throws Exception {
		
		String contractTotalSupply = moac.getContractTotalSupply(contractAddress);
		Map<String,String> map = new HashMap<String,String>();
		map.put("totalSupply", contractTotalSupply);
		return ResponseBase.createResponse(true, 10000, "总发行量方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> name(String accessToken, String walletId,
			String contractAddress) throws Exception {
		
		String name = moac.getContractName(contractAddress);
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		return ResponseBase.createResponse(true, 10000, "名称方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> symbol(String accessToken, String walletId, String contractAddress) throws Exception {
		
		String contractSymbol = moac.getContractSymbol(contractAddress);
		Map<String,String> map = new HashMap<String,String>();
		map.put("symbol", contractSymbol);
		return ResponseBase.createResponse(true, 10000, "简称方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> decimals20(String accessToken, String walletId, String contractAddress) throws Exception {
		
		String contractDecimalsErc20 = moac.getContractDecimalsErc20(contractAddress);
		Map<String,String> map = new HashMap<String,String>();
		map.put("decimals", contractDecimalsErc20);
		return ResponseBase.createResponse(true, 10000, "精度方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> transfer20(String accessToken, String walletId, String id,
			String contractAddress, String to, String tokenValue, String payPsw, String nonce, String gas) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String privateKey = Md5.decrypt(account.getPrivateKey());//私钥
		String hash = moac.contractTransferErc20(account.getAddress(), privateKey, contractAddress, to, new BigInteger(tokenValue), nonce, gas);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		
		String symbol = "";
		try {
			symbol = moac.getContractSymbol(contractAddress);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Transaction transaction = new Transaction();
			transaction.setWalletId(walletId);
			transaction.setChain("0");
			transaction.setFromAddress(account.getAddress());
			transaction.setToAddress(to);
			transaction.setSymbol(symbol);
			transaction.setTokenAddress(contractAddress);
			transaction.setHash(hash);
			transactionDao.addTransaction(transaction);//新增交易记录
		} catch (Exception e) {
		}
		
		return ResponseBase.createResponse(true, 10000, "转账调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> approve20(String accessToken, String walletId, String id,
			String contractAddress, String spender, String tokenValue, String payPsw, String nonce, String gas) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String contractApproveErc20 = moac.contractApproveErc20(account.getAddress(), Md5.decrypt(account.getPrivateKey()),
				contractAddress, spender, new BigInteger(tokenValue), nonce, gas);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", contractApproveErc20);
		return ResponseBase.createResponse(true, 10000, "授权方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> allowance20(String accessToken, String walletId, String id,
			String contractAddress, String spender) throws Exception {
		
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String contractAllowanceErc20 = moac.contractAllowanceErc20(contractAddress, account.getAddress(), spender);
		Map<String,String> map = new HashMap<String,String>();
		map.put("allowance", contractAllowanceErc20);
		return ResponseBase.createResponse(true, 10000, "获取授权余额方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> transferFrom20(String accessToken, String walletId,
			String id, String contractAddress, String from, String to, String tokenValue, String payPsw, String nonce,
			String gas) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String hash = moac.contractTransferFromErc20(account.getAddress(), Md5.decrypt(account.getPrivateKey()),
				contractAddress, from, to, new BigInteger(tokenValue), nonce, gas);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		
		String symbol = "";
		try {
			symbol = moac.getContractSymbol(contractAddress);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Transaction transaction = new Transaction();
			transaction.setWalletId(walletId);
			transaction.setChain("0");
			transaction.setFromAddress(from);
			transaction.setToAddress(to);
			transaction.setSymbol(symbol);
			transaction.setTokenAddress(contractAddress);
			transaction.setHash(hash);
			transaction.setMark(account.getAddress()+"代"+from+"转账20代币");
			transactionDao.addTransaction(transaction);//新增交易记录
		} catch (Exception e) {
		}
		return ResponseBase.createResponse(true, 10000, "代理转账调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> ownerOf721(String accessToken, String walletId,
			String contractAddress, String tokenId) throws Exception {
		
		String contractOwnerOfErc721 = moac.contractOwnerOfErc721(contractAddress, new BigInteger(tokenId));
		Map<String,String> map = new HashMap<String,String>();
		map.put("ownerOf", contractOwnerOfErc721);
		return ResponseBase.createResponse(true, 10000, "代币所属者方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> exists721(String accessToken, String walletId,
			String contractAddress, String tokenId) throws Exception {
		
		String contractExistsErc721 = moac.contractExistsErc721(contractAddress, new BigInteger(tokenId));
		Map<String,String> map = new HashMap<String,String>();
		map.put("exists", contractExistsErc721);
		return ResponseBase.createResponse(true, 10000, "代币存在与否方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> transferFrom721(String accessToken, String walletId,
			String id, String contractAddress, String from, String to, String tokenId, String payPsw, String nonce,
			String gas) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String hash = moac.contractTransferFromErc721(account.getAddress(), Md5.decrypt(account.getPrivateKey()), contractAddress,
				from, to,  new BigInteger(tokenId), nonce, gas);
		
		String symbol = "";
		try {
			symbol = moac.getContractSymbol(contractAddress);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Transaction transaction = new Transaction();
			transaction.setWalletId(walletId);
			transaction.setChain("0");
			transaction.setFromAddress(from);
			transaction.setToAddress(to);
			transaction.setSymbol(symbol);
			transaction.setTokenAddress(contractAddress);
			transaction.setHash(hash);
			transaction.setMark(account.getAddress()+"代"+from+"转账721代币");
			transactionDao.addTransaction(transaction);//新增交易记录
		} catch (Exception e) {
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "代理转账调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> approve721(String accessToken, String walletId, String id,
			String contractAddress, String spender, String tokenId, String payPsw, String nonce, String gas) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String hash = moac.contractApproveErc721(account.getAddress(), Md5.decrypt(account.getPrivateKey()),
				contractAddress, spender, new BigInteger(tokenId), nonce, gas);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "授权方法调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> getApproved721(String accessToken, String walletId,
			String contractAddress, String tokenId) throws Exception {
		
		String approved = moac.contractGetApprovedErc721(contractAddress, new BigInteger(tokenId));
		Map<String,String> map = new HashMap<String,String>();
		map.put("approved", approved);
		return ResponseBase.createResponse(true, 10000, "获取授权用户调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> setApprovalForAll(String accessToken, String walletId, String id, String contractAddress,
			String spender, String approved, String payPsw, String nonce, String gas) throws Exception {
		
		Wallet wallet = walletDao.queryWalletById(walletId);
		if(!encryptSHA.SHA256(payPsw).equals(wallet.getPayPsw())) {
			throw new BussinessException(40004, "支付密码错误");
		}
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String hash = moac.contractSetApprovalForAllErc721(account.getAddress(), Md5.decrypt(account.getPrivateKey()),
				contractAddress, spender, "0".equals(approved), nonce, gas);
		Map<String,String> map = new HashMap<String,String>();
		map.put("hash", hash);
		return ResponseBase.createResponse(true, 10000, "全部授权调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> isApprovedForAll721(String accessToken, String walletId,
			String id, String contractAddress, String spender) throws Exception {
		
		Account account = accountDao.queryAccountByIdAndWalletId(id, walletId);
		if(account == null) {
			throw new BussinessException(40004, "账户不存在");
		}
		String isApprovedForAll = moac.contractIsApprovedForAllErc721(contractAddress, account.getAddress(), spender);
		Map<String,String> map = new HashMap<String,String>();
		map.put("isApprovedForAll", isApprovedForAll);
		return ResponseBase.createResponse(true, 10000, "授权与否调用成功", map, HttpStatus.OK);
	}

	public ResponseEntity<ResponseBase<Map<String, String>>> tokenURI721(String accessToken, String walletId,
			String contractAddress, String tokenId) throws Exception {
		
		String tokenURI = moac.contractTokenURIErc721(contractAddress, new BigInteger(tokenId));
		Map<String,String> map = new HashMap<String,String>();
		map.put("tokenURI", tokenURI);
		return ResponseBase.createResponse(true, 10000, "获取token信息调用成功", map, HttpStatus.OK);
	}
	

	
	
}

