package com.web.frame.entity.response;

import java.util.List;

import com.web.frame.entity.table.web.Scs;
import com.web.frame.entity.table.web.Subchain;
import com.web.frame.entity.table.web.Vnode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ResponseSubchainData",description="子链信息")
public class ResponseSubchainData{

	@ApiModelProperty(value="子链基础信息",name="subchain")
	private Subchain subchain;
	
	@ApiModelProperty(value="子链scs列表信息",name="scsList")
	private List<Scs> scsList;
	
	@ApiModelProperty(value="子链vnode列表信息",name="vnodeList")
	private List<Vnode> vnodeList;

	public Subchain getSubchain() {
		return subchain;
	}

	public void setSubchain(Subchain subchain) {
		this.subchain = subchain;
	}

	public List<Scs> getScsList() {
		return scsList;
	}

	public void setScsList(List<Scs> scsList) {
		this.scsList = scsList;
	}

	public List<Vnode> getVnodeList() {
		return vnodeList;
	}

	public void setVnodeList(List<Vnode> vnodeList) {
		this.vnodeList = vnodeList;
	}

	
	
	
}
