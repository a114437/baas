package com.web.frame.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="VerifyApp",description="认证APP")
public class VerifyApp {

	@ApiModelProperty(value="appId",name="appId",required=true)
	private String appId;
	
	@ApiModelProperty(value="姓名",name="userName",required=true)
	private String userName;
	
	@ApiModelProperty(value="身份证",name="idCard",required=true)
	private String idCard;

	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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
