package com.web.frame.entity.response;

import java.util.List;

import com.blink.jtblc.client.bean.Account;
import com.blink.jtblc.client.bean.AmountInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * 交易内容
 */
@ApiModel(value="ResponseTxJson",description="井通转账回执信息")
public class ResponseTxJson {
	// 账号地址
	@ApiModelProperty(value="账号地址",name="account")
	private String account;
	// 交易金额
	@ApiModelProperty(value="账号地址",name="amount")
	private String amount;
	// 对家
	@ApiModelProperty(value="对家",name="destination")
	private String destination;
	// 交易费
	@ApiModelProperty(value="交易费",name="fee")
	private String fee;
	// 交易标记
	@ApiModelProperty(value="交易标记",name="Flags")
	private Long Flags;
	// 备注
	@ApiModelProperty(value="交易标记",name="Flags")
	private List<Object> memos;
	// 单子序列号
	@ApiModelProperty(value="单子序列号",name="sequence")
	private Integer sequence;
	// 签名公钥
	@ApiModelProperty(value="签名公钥",name="signingPubKey")
	private String signingPubKey;
	// 交易类型:TrustSet信任;RelationDel解冻；RelationSet 授权/冻结
	@ApiModelProperty(value="交易类型",name="transactionType")
	private String transactionType;
	// 交易签名
	@ApiModelProperty(value="交易签名",name="txnSignature")
	private String txnSignature;
	// 交易hash
	@ApiModelProperty(value="交易hash",name="hash")
	private String hash;
	// 关系的额度
	@ApiModelProperty(value="关系的额度",name="limitAmount")
	private Account limitAmount;
	// 关系类型：0信任；1授权；3冻结/解冻
	@ApiModelProperty(value="关系类型：0信任；1授权；3冻结/解冻",name="relationType")
	private Integer relationType;
	// 关系对家
	@ApiModelProperty(value="关系对家",name="target")
	private String target;
	// 时间戳
	@ApiModelProperty(value="时间戳",name="timestamp")
	private Integer timestamp;
	// 取消的单子号
	@ApiModelProperty(value="取消的单子号",name="offerSequence")
	private Integer offerSequence;
	// 对家得到的Object
	@ApiModelProperty(value="对家得到的Object",name="takerGets")
	private AmountInfo takerGets;
	// 对家支付的Object
	@ApiModelProperty(value="对家支付的Object",name="takerGets")
	private String takerpays;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	public Long getFlags() {
		return Flags;
	}
	
	public void setFlags(Long flags) {
		Flags = flags;
	}
	
	public List<Object> getMemos() {
		return memos;
	}
	
	public void setMemos(List<Object> memos) {
		this.memos = memos;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public String getSigningPubKey() {
		return signingPubKey;
	}
	
	public void setSigningPubKey(String signingPubKey) {
		this.signingPubKey = signingPubKey;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getTxnSignature() {
		return txnSignature;
	}
	
	public void setTxnSignature(String txnSignature) {
		this.txnSignature = txnSignature;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Account getLimitAmount() {
		return limitAmount;
	}
	
	public void setLimitAmount(Account limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	public Integer getRelationType() {
		return relationType;
	}
	
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public Integer getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}
	
	public Integer getOfferSequence() {
		return offerSequence;
	}
	
	public void setOfferSequence(Integer offerSequence) {
		this.offerSequence = offerSequence;
	}
	
	public AmountInfo getTakerGets() {
		return takerGets;
	}
	
	public void setTakerGets(AmountInfo takerGets) {
		this.takerGets = takerGets;
	}
	
	public String getTakerpays() {
		return takerpays;
	}
	
	public void setTakerpays(String takerpays) {
		this.takerpays = takerpays;
	}
}
