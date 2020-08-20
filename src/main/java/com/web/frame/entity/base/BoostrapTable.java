package com.web.frame.entity.base;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="BoostrapTable",description="分页查询结果")
public class BoostrapTable<T extends Object>{

	
	public BoostrapTable() {
		super();
	}
	
	public BoostrapTable(int total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	@ApiModelProperty(value="总条数",name="total")
	private int total;
	
	@ApiModelProperty(value="分页记录",name="rows")
	private List<T> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
}
