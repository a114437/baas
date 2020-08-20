package com.web.frame.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="UpdatePayPsw",description="修改支付密码")
public class UpdatePayPsw {


	@ApiModelProperty(value="钱包id",name="walletId",required=true)
	private String walletId;
	
	@ApiModelProperty(value="新支付密码(包含大写字母、小写字母、数字、特殊符号（不是字母，数字，下划线，汉字的字符）的8位以上)",name="newPayPsw",required=true)
	private String newPayPsw;
	
	@ApiModelProperty(value="原支付密码",name="oldPayPsw",required=true)
	private String oldPayPsw;

	
	
	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getNewPayPsw() {
		return newPayPsw;
	}

	public void setNewPayPsw(String newPayPsw) {
		this.newPayPsw = newPayPsw;
	}

	public String getOldPayPsw() {
		return oldPayPsw;
	}

	public void setOldPayPsw(String oldPayPsw) {
		this.oldPayPsw = oldPayPsw;
	}

	
}
