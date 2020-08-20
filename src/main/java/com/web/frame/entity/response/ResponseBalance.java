package com.web.frame.entity.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ResponseBalance",description="代币余额")
public class ResponseBalance {
	
	@ApiModelProperty(value="账户地址",name="address")
	private String address;
	
	@ApiModelProperty(value="余额信息",name="address")
	private List<ResponseBalanceItem> list;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<ResponseBalanceItem> getList() {
		return list;
	}

	public void setList(List<ResponseBalanceItem> list) {
		this.list = list;
	}
	
	

	
}
