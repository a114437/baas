package com.web.frame.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ResponseToken",description="代币信息")
public class ResponseToken {
	
	@ApiModelProperty(value="代币id",name="id")
	private String id;
	
	@ApiModelProperty(value="代币地址，墨客为合约地址，井通为issuer地址",name="tokenAddress")
	private String tokenAddress;
	
	@ApiModelProperty(value="token类型（墨客代币有效，0：ERC20,1:ERC721）",name="tokenType")
	private String tokenType;
	
	@ApiModelProperty(value="代币名",name="symbol")
	private String symbol;
	
	@ApiModelProperty(value="区块链类型，0：墨客，1：井通",name="chain")
	private String chain;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	
}
