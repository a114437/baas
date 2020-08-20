package com.web.frame.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="VerifyWallet",description="认证钱包")
public class VerifyWallet {

	@ApiModelProperty(value="钱包id",name="walletId",required=true)
	private String walletId;
	
	@ApiModelProperty(value="用户姓名",name="userName",required=true)
	private String userName;
	
	@ApiModelProperty(value="用户身份证",name="idCard",required=true)
	private String idCard;

	
	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	
}
