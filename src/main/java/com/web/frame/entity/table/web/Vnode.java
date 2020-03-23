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
@Table(name="sfb_vnode")
@ApiModel(value="Vnode",description="子链Vnode信息")
public class Vnode{

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
	
	@Column(name="subchain_id",length=32)
	@ApiModelProperty(value="子链id",name="subchainId")
	private String subchainId;
	
	@Column(name="vnode_url",length=200)
	@ApiModelProperty(value="vnode链接",name="vnodeUrl")
	private String vnodeUrl;
	
	@Column(name="vnode_address",length=200)
	@ApiModelProperty(value="注册vnode使用账户",name="vnodeAddress")
	private String vnodeAddress;

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

	public String getSubchainId() {
		return subchainId;
	}

	public void setSubchainId(String subchainId) {
		this.subchainId = subchainId;
	}

	public String getVnodeUrl() {
		return vnodeUrl;
	}

	public void setVnodeUrl(String vnodeUrl) {
		this.vnodeUrl = vnodeUrl;
	}

	public String getVnodeAddress() {
		return vnodeAddress;
	}

	public void setVnodeAddress(String vnodeAddress) {
		this.vnodeAddress = vnodeAddress;
	}

	
	
}
