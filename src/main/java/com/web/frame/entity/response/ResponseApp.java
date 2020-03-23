package com.web.frame.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ResponseApp",description="app信息（仅在注册成功后返回，请妥善保存）")
public class ResponseApp {

	@ApiModelProperty(value="appId",name="appId")
	private String appId;
	
	@ApiModelProperty(value="appSecret",name="appSecret")
	private String appSecret;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	
}
