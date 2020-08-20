package com.web.frame.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegisterApp",description="注册信息")
public class RegisterApp {

	@ApiModelProperty(value="应用名称",name="appName",required=true)
	private String appName;
	
	@ApiModelProperty(value="应用介绍",name="introduce",required=true)
	private String introduce;

	@ApiModelProperty(value="用户类型，0：个人，1：企业",name="userType",required=true)
	private String userType;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	
}
