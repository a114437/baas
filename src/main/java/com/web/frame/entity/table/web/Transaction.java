package com.web.frame.entity.table.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.web.frame.util.Uuid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="sfb_transaction")
@ApiModel(value="Transaction",description="交易表")
public class Transaction{

	@Id
	@GeneratedValue(generator="system_uuid")
    @GenericGenerator(name="system_uuid",strategy="uuid")
	@Column(name = "id", nullable = false,length=32)
	@ApiModelProperty(value="主键",name="id")
	private String id;
	
	@Column(name="create_date",length=30)
	@ApiModelProperty(value="创建时间",name="createDate")
	private String createDate;
	
	@Column(name="is_delete",length=1)
	@ApiModelProperty(value="是否删除",name="isDelete")
	private String isDelete = "0";
	
	@Column(name="mark",length=300)
	@ApiModelProperty(value="备注",name="mark")
	private String mark;
	
	@Column(name="create_id",length=32)
	@ApiModelProperty(value="创建人",name="createId")
	private String createId;
	
	@Column(name="last_update_id",length=32)
	@ApiModelProperty(value="最后修改人",name="lastUpdateId")
	private String lastUpdateId;
	
	@Column(name="last_update_time",length=30)
	@ApiModelProperty(value="最后修改时间",name="lastUpdateTime")
	private String lastUpdateTime;
	
	@Column(name="wallet_id",length=32)
	@ApiModelProperty(value="发起账户钱包id",name="walletId")
	private String walletId;
	
	@Column(name="from_address",length=150)
	@ApiModelProperty(value="发起交易账户地址",name="fromAddress")
	private String fromAddress;
	
	@Column(name="to_address",length=150)
	@ApiModelProperty(value="接收账户地址",name="toAddress")
	private String toAddress;
	
	@Column(name="token_address",length=100)
	@ApiModelProperty(value="代币地址，墨客为合约地址，井通为issuer地址",name="tokenAddress")
	private String tokenAddress;
	
	@Column(name="symbol",length=50)
	@ApiModelProperty(value="代币名",name="symbol")
	private String symbol;
	
	@Column(name="hash",length=200)
	@ApiModelProperty(value="交易hash",name="hash")
	private String hash;
	
	@Column(name="chain",length=1)
	@ApiModelProperty(value="区块链类型，0：墨客，1：井通",name="chain")
	private String chain;
	
	@Column(name="chain_type",length=1)
	@ApiModelProperty(value="墨客区块链类型，0:公链，1：子链",name="chain")
	private String chainType = "0";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getChainType() {
		return chainType;
	}

	public void setChainType(String chainType) {
		this.chainType = chainType;
	}

	
}
