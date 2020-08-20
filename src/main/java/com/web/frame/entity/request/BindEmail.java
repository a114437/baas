package com.web.frame.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="BindEmail",description="绑定邮箱")
public class BindEmail {

	@ApiModelProperty(value="钱包id",name="walletId",required=true)
	private String walletId;

	@ApiModelProperty(value="支付密码",name="payPsw",required=true)
	private String payPsw;
	
	@ApiModelProperty(value="邮箱",name="email",required=true)
	private String email;

	
	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getPayPsw() {
		return payPsw;
	}

	public void setPayPsw(String payPsw) {
		this.payPsw = payPsw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
