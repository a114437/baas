package com.web.frame.entity.response;

import java.util.List;

import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.Memo;
import com.blink.jtblc.client.bean.Meta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 交易信息
 */
@ApiModel(value="ResponseTransactionJingTum",description="井通交易信息")
public class ResponseTransactionJingTum {
	
	@ApiModelProperty(value="钱包地址",name="account")
	private String account;// 钱包地址
	
	@ApiModelProperty(value="交易金额",name="amount")
	private AmountInfo amount;// 交易金额
	
	@ApiModelProperty(value="交易对家地址",name="destination")
	private String destination;// 交易对家地址
	
	@ApiModelProperty(value="交易费",name="fee")
	private String fee;// 交易费
	
	@ApiModelProperty(value="交易标记",name="flags")
	private Long flags;// 交易标记
	
	@ApiModelProperty(value="备注",name="memos")
	private List<Memo> memos; //备注
	
	@ApiModelProperty(value="自身账号的交易号",name="sequence")
	private Integer sequence;// 自身账号的交易号
	
	@ApiModelProperty(value="签名公钥",name="signingPubKey")
	private String signingPubKey;// 签名公钥
	
	@ApiModelProperty(value="交易提交时间戳",name="timestamp")
	private Integer timestamp;// 交易提交时间戳
	
	@ApiModelProperty(value="交易类型",name="transactionType")
	private String transactionType;// 交易类型
	
	@ApiModelProperty(value="交易签名",name="txnSignature")
	private String txnSignature;// 交易签名
	
	@ApiModelProperty(value="交易进账本时间",name="date")
	private Integer date;// 交易进账本时间
	
	@ApiModelProperty(value="交易hash",name="hash")
	private String hash;// 交易hash
	
	@ApiModelProperty(value="交易所在的账本号",name="inLedger")
	private Integer inLedger;// 交易所在的账本号
	
	@ApiModelProperty(value="账本高度",name="ledgerIndex")
	private String ledgerIndex;// 账本高度
	
	@ApiModelProperty(value="交易影响的节点",name="meta")
	private Meta meta;// 交易影响的节点
	
	@ApiModelProperty(value="交易是否通过验证",name="validated")
	private Boolean validated;// 交易是否通过验证
	
	public Meta getMeta() {
		return meta;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
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
		return flags;
	}
	
	public void setFlags(Long flags) {
		this.flags = flags;
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
	
	public Integer getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
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
	
	public Integer getDate() {
		return date;
	}
	
	public void setDate(Integer date) {
		this.date = date;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Integer getInLedger() {
		return inLedger;
	}
	
	public void setInLedger(Integer inLedger) {
		this.inLedger = inLedger;
	}
	
	public String getLedgerIndex() {
		return ledgerIndex;
	}
	
	public void setLedgerIndex(String ledgerIndex) {
		this.ledgerIndex = ledgerIndex;
	}
	
	public Boolean getValidated() {
		return validated;
	}
	
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}
	
	public void setMeta(Meta meta) {
		this.meta = meta;
	}


	public AmountInfo getAmount() {
		return amount;
	}

	public void setAmount(AmountInfo amount) {
		this.amount = amount;
	}

	public List<Memo> getMemos() {
		return memos;
	}

	public void setMemos(List<Memo> memos) {
		this.memos = memos;
	}

	@Override
	public String toString() {
		return "Account [account=" + account + ", amount=" + amount + ", destination=" + destination + ", fee=" + fee
				+ ", flags=" + flags + ", memos=" + memos + ", sequence=" + sequence + ", signingPubKey="
				+ signingPubKey + ", timestamp=" + timestamp + ", transactionType=" + transactionType
				+ ", txnSignature=" + txnSignature + ", date=" + date + ", hash=" + hash + ", inLedger=" + inLedger
				+ ", ledgerIndex=" + ledgerIndex + ", meta=" + meta + ", validated=" + validated + "]";
	}
	
	
}
