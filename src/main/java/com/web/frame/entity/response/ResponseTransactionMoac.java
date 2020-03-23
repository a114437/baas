package com.web.frame.entity.response;

import java.util.List;

import org.chain3j.protocol.core.methods.response.Log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ResponseTransactionMoac",description="墨客交易信息")
public class ResponseTransactionMoac {

	@ApiModelProperty(value="交易hash",name="hash")
	private String hash;
	
	@ApiModelProperty(value="账户nonce",name="nonce")
	private String nonce;
	
	@ApiModelProperty(value="区块hash",name="blockHash")
	private String blockHash;
	
	@ApiModelProperty(value="区块号",name="blockNumber")
	private String blockNumber;
	
	@ApiModelProperty(value="区块中该交易序号",name="transactionIndex")
	private String transactionIndex;
	
	@ApiModelProperty(value="发起者",name="from")
	private String from;
	
	@ApiModelProperty(value="接收者",name="to")
	private String to;
	
	@ApiModelProperty(value="转账金额",name="value")
	private String value;
	
	@ApiModelProperty(value="gas价格",name="gasPrice")
	private String gasPrice;
	
	@ApiModelProperty(value="gas使用数量",name="gas")
	private String gas;
	
	@ApiModelProperty(value="交易备注",name="input")
	private String input;
	
	private String creates;
	private String publicKey;
	private String raw;
	private String r;
	private String s;
	private long v;
	
	@ApiModelProperty(value="交易状态",name="status")
	private boolean status;
	
	@ApiModelProperty(value="gas实际使用数量",name="gasUsed")
	private String gasUsed;
	
	@ApiModelProperty(value="gas实际花费",name="actualGasCost")
	private String actualGasCost;
	
	@ApiModelProperty(value="对方账户或合约地址",name="contractAddress")
	private String contractAddress;
	private String root;
	private List<Log> logs;
	private String logsBloom;
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public String getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}
	public String getTransactionIndex() {
		return transactionIndex;
	}
	public void setTransactionIndex(String transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getCreates() {
		return creates;
	}
	public void setCreates(String creates) {
		this.creates = creates;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getRaw() {
		return raw;
	}
	public void setRaw(String raw) {
		this.raw = raw;
	}
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r = r;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public long getV() {
		return v;
	}
	public void setV(long v) {
		this.v = v;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	public String getActualGasCost() {
		return actualGasCost;
	}
	public void setActualGasCost(String actualGasCost) {
		this.actualGasCost = actualGasCost;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public List<Log> getLogs() {
		return logs;
	}
	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
	public String getLogsBloom() {
		return logsBloom;
	}
	public void setLogsBloom(String logsBloom) {
		this.logsBloom = logsBloom;
	}
	
}
