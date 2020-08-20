package com.web.frame.entity.response;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.web.frame.util.Uuid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ResponseAccount",description="账户信息")
public class ResponseAccount{

	@ApiModelProperty(value="账户id",name="id")
	private String id;
	
	@ApiModelProperty(value="钱包id",name="walletId")
	private String walletId;
	
	@ApiModelProperty(value="账户地址",name="address")
	private String address;
	
	@ApiModelProperty(value="公钥",name="publicKey")
	private String publicKey;
	
	@ApiModelProperty(value="私钥",name="privateKey")
	private String privateKey;
	
	@ApiModelProperty(value="区块链类型，0：墨客，1：井通",name="chain")
	private String chain;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
