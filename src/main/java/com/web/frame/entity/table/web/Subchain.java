package com.web.frame.entity.table.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="sfb_subchain")
@ApiModel(value="Subchain",description="子链基础信息")
public class Subchain{

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
	
	@Column(name="account_id",length=32)
	@ApiModelProperty(value="管理账户id",name="accountId")
	private String accountId;
	
	@Column(name="via",length=80)
	@ApiModelProperty(value="子链收益账户地址",name="via")
	private String via;
	
	@Column(name="vnode_code",columnDefinition="longtext")
	@ApiModelProperty(value="vnode合约编码",name="vnodeCode")
	private String vnodeCode;
	
	@Column(name="vnode_bmin",length=20)
	@ApiModelProperty(value="注册vnode最少押金",name="vnodeBmin")
	private String vnodeBmin;

	@Column(name="vnode_hash",length=200)
	@ApiModelProperty(value="vnode合约部署hash",name="vnodeHash")
	private String vnodeHash;
	
	@Column(name="vnode_address",length=80)
	@ApiModelProperty(value="vnode合约地址",name="vnodeAddress")
	private String vnodeAddress;
	
	@Column(name="subchain_protocol_base_code",columnDefinition="longtext")
	@ApiModelProperty(value="子链协议合约编码",name="subchainProtocolBaseCode")
	private String subchainProtocolBaseCode;
	
	@Column(name="subchain_protocol_name",length=80)
	@ApiModelProperty(value="子链协议合约名称",name="subchainProtocolName")
	private String subchainProtocolName;
	
	@Column(name="subchain_protocol_bmin",length=10)
	@ApiModelProperty(value="scs注册最少押金",name="subchainProtocolBmin")
	private String subchainProtocolBmin;
	
	@Column(name="subchain_protocol_type",length=1)
	@ApiModelProperty(value="0表示pos，1表示ipfs",name="subchainProtocolType")
	private String subchainProtocolType;
	
	@Column(name="subchain_protocol_hash",length=200)
	@ApiModelProperty(value="协议合约部署hash",name="subchainProtocolHash")
	private String subchainProtocolHash;
	
	@Column(name="subchain_protocol_address",length=200)
	@ApiModelProperty(value="协议合约地址",name="subchainProtocolAddress")
	private String subchainProtocolAddress;
	
	@Column(name="erc20_code",columnDefinition="longtext")
	@ApiModelProperty(value="erc20合约编码",name="erc20Code")
	private String erc20Code;
	
	@Column(name="erc20_hash",length=200)
	@ApiModelProperty(value="erc20合约部署hash",name="erc20Hash")
	private String erc20Hash;
	
	@Column(name="erc20_address",length=200)
	@ApiModelProperty(value="erc20合约地址",name="erc20Address")
	private String erc20Address;
	
	@Column(name="subchain_base_code",columnDefinition="longtext")
	@ApiModelProperty(value="子链合约编码",name="subchainBaseCode")
	private String subchainBaseCode;
	
	@Column(name="subchain_base_erc_rate",length=10)
	@ApiModelProperty(value="erc20代币与子链代币兑换比率",name="subchainBaseErcRate")
	private String subchainBaseErcRate;
	
	@Column(name="subchain_base_min",length=10)
	@ApiModelProperty(value="子链最少scs数，1、3、5、7选择",name="subchainBaseMin")
	private String subchainBaseMin;
	
	@Column(name="subchain_base_max",length=10)
	@ApiModelProperty(value="子链最多scs数，11、21、31、51、99选择",name="subchainBaseMax")
	private String subchainBaseMax;
	
	@Column(name="subchain_base_thousandth",length=10)
	@ApiModelProperty(value="不需修改",name="subchainBaseThousandth")
	private String subchainBaseThousandth = "1000";
	
	@Column(name="subchain_base_flush_round",length=10)
	@ApiModelProperty(value="周期数，40~500之间",name="subchainBaseFlushRound")
	private String subchainBaseFlushRound;
	
	@Column(name="subchain_base_monitor_bond",length=10)
	@ApiModelProperty(value="注册为monitor最少押金，合约中默认为1MOAC",name="subchainBaseMonitorBond")
	private String subchainBaseMonitorBond;
	
	@Column(name="subchain_base_hash",length=200)
	@ApiModelProperty(value="子链合约部署hash",name="subchainBaseHash")
	private String subchainBaseHash;
	
	@Column(name="subchain_base_address",length=200)
	@ApiModelProperty(value="子链合约地址",name="subchainBaseAddress")
	private String subchainBaseAddress;
	
	@Column(name="dappbase_code",columnDefinition="longtext")
	@ApiModelProperty(value="dappbase合约编码",name="dappbaseCode")
	private String dappbaseCode;
	
	@Column(name="dappbase_hash",length=200)
	@ApiModelProperty(value="dappbase合约部署hash(子链hash)",name="dappbaseHash")
	private String dappbaseHash;
	
	@Column(name="dappbase_address",length=200)
	@ApiModelProperty(value="dappbase合约地址(子链合约地址)",name="dappbaseAddress")
	private String dappbaseAddress;
	
	@Column(name="subchain_status",length=10)
	@ApiModelProperty(value="子链搭建结果，true:成功，false:失败,空:部署中",name="subchainStatus")
	private String subchainStatus;
	
	@Column(name="fail_reason",columnDefinition="longtext")
	@ApiModelProperty(value="子链搭建失败原因",name="failReason")
	private String failReason;
	
	@Column(name="end_block",length=20)
	@ApiModelProperty(value="子链部署完成区块数",name="endBlock")
	private String endBlock;

	
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getVnodeCode() {
		return vnodeCode;
	}

	public void setVnodeCode(String vnodeCode) {
		this.vnodeCode = vnodeCode;
	}

	public String getVnodeBmin() {
		return vnodeBmin;
	}

	public void setVnodeBmin(String vnodeBmin) {
		this.vnodeBmin = vnodeBmin;
	}

	public String getVnodeAddress() {
		return vnodeAddress;
	}

	public void setVnodeAddress(String vnodeAddress) {
		this.vnodeAddress = vnodeAddress;
	}

	public String getSubchainProtocolBaseCode() {
		return subchainProtocolBaseCode;
	}

	public void setSubchainProtocolBaseCode(String subchainProtocolBaseCode) {
		this.subchainProtocolBaseCode = subchainProtocolBaseCode;
	}

	public String getSubchainProtocolName() {
		return subchainProtocolName;
	}

	public void setSubchainProtocolName(String subchainProtocolName) {
		this.subchainProtocolName = subchainProtocolName;
	}

	public String getSubchainProtocolBmin() {
		return subchainProtocolBmin;
	}

	public void setSubchainProtocolBmin(String subchainProtocolBmin) {
		this.subchainProtocolBmin = subchainProtocolBmin;
	}

	public String getSubchainProtocolType() {
		return subchainProtocolType;
	}

	public void setSubchainProtocolType(String subchainProtocolType) {
		this.subchainProtocolType = subchainProtocolType;
	}

	public String getSubchainProtocolAddress() {
		return subchainProtocolAddress;
	}

	public void setSubchainProtocolAddress(String subchainProtocolAddress) {
		this.subchainProtocolAddress = subchainProtocolAddress;
	}

	public String getErc20Code() {
		return erc20Code;
	}

	public void setErc20Code(String erc20Code) {
		this.erc20Code = erc20Code;
	}

	public String getErc20Address() {
		return erc20Address;
	}

	public void setErc20Address(String erc20Address) {
		this.erc20Address = erc20Address;
	}

	public String getSubchainBaseCode() {
		return subchainBaseCode;
	}

	public void setSubchainBaseCode(String subchainBaseCode) {
		this.subchainBaseCode = subchainBaseCode;
	}

	public String getSubchainBaseErcRate() {
		return subchainBaseErcRate;
	}

	public void setSubchainBaseErcRate(String subchainBaseErcRate) {
		this.subchainBaseErcRate = subchainBaseErcRate;
	}

	public String getSubchainBaseMin() {
		return subchainBaseMin;
	}

	public void setSubchainBaseMin(String subchainBaseMin) {
		this.subchainBaseMin = subchainBaseMin;
	}

	public String getSubchainBaseMax() {
		return subchainBaseMax;
	}

	public void setSubchainBaseMax(String subchainBaseMax) {
		this.subchainBaseMax = subchainBaseMax;
	}

	public String getSubchainBaseThousandth() {
		return subchainBaseThousandth;
	}

	public void setSubchainBaseThousandth(String subchainBaseThousandth) {
		this.subchainBaseThousandth = subchainBaseThousandth;
	}

	public String getSubchainBaseFlushRound() {
		return subchainBaseFlushRound;
	}

	public void setSubchainBaseFlushRound(String subchainBaseFlushRound) {
		this.subchainBaseFlushRound = subchainBaseFlushRound;
	}

	public String getSubchainBaseMonitorBond() {
		return subchainBaseMonitorBond;
	}

	public void setSubchainBaseMonitorBond(String subchainBaseMonitorBond) {
		this.subchainBaseMonitorBond = subchainBaseMonitorBond;
	}

	public String getSubchainBaseAddress() {
		return subchainBaseAddress;
	}

	public void setSubchainBaseAddress(String subchainBaseAddress) {
		this.subchainBaseAddress = subchainBaseAddress;
	}

	public String getDappbaseCode() {
		return dappbaseCode;
	}

	public void setDappbaseCode(String dappbaseCode) {
		this.dappbaseCode = dappbaseCode;
	}

	public String getDappbaseAddress() {
		return dappbaseAddress;
	}

	public void setDappbaseAddress(String dappbaseAddress) {
		this.dappbaseAddress = dappbaseAddress;
	}

	public String getVnodeHash() {
		return vnodeHash;
	}

	public void setVnodeHash(String vnodeHash) {
		this.vnodeHash = vnodeHash;
	}

	public String getSubchainProtocolHash() {
		return subchainProtocolHash;
	}

	public void setSubchainProtocolHash(String subchainProtocolHash) {
		this.subchainProtocolHash = subchainProtocolHash;
	}

	public String getErc20Hash() {
		return erc20Hash;
	}

	public void setErc20Hash(String erc20Hash) {
		this.erc20Hash = erc20Hash;
	}

	public String getSubchainBaseHash() {
		return subchainBaseHash;
	}

	public void setSubchainBaseHash(String subchainBaseHash) {
		this.subchainBaseHash = subchainBaseHash;
	}

	public String getDappbaseHash() {
		return dappbaseHash;
	}

	public void setDappbaseHash(String dappbaseHash) {
		this.dappbaseHash = dappbaseHash;
	}

	public String getSubchainStatus() {
		return subchainStatus;
	}

	public void setSubchainStatus(String subchainStatus) {
		this.subchainStatus = subchainStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getEndBlock() {
		return endBlock;
	}

	public void setEndBlock(String endBlock) {
		this.endBlock = endBlock;
	}
	
	
	
}
