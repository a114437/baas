package com.web.frame.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegisterWallet",description="注册钱包")
public class RegisterWallet {

	@ApiModelProperty(value="支付密码(包含大写字母、小写字母、数字、特殊符号（不是字母，数字，下划线，汉字的字符）的8位以上)",name="payPsw",required=true)
	private String payPsw;
	
	@ApiModelProperty(value="钱包名称",name="walletName",required=true)
	private String walletName;

	public String getPayPsw() {
		return payPsw;
	}

	public void setPayPsw(String payPsw) {
		this.payPsw = payPsw;
	}

	public String getWalletName() {
		return walletName;
	}

	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}
	


	
}
