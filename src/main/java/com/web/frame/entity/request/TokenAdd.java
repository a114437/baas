package com.web.frame.entity.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.web.frame.util.Uuid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="TokenAdd",description="新增代币")
public class TokenAdd{
	
	@ApiModelProperty(value="代币地址，墨客为合约地址，井通为issuer地址",name="tokenAddress",required=true)
	private String tokenAddress;
	
	@ApiModelProperty(value="token类型（墨客代币有效，0：ERC20,1:ERC721）",name="tokenType",required=true)
	private String tokenType;
	
	@ApiModelProperty(value="代币名",name="symbol",required=true)
	private String symbol;
	
	@ApiModelProperty(value="区块链类型，0：墨客，1：井通",name="chain",required=true)
	private String chain;


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
