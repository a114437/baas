package com.web.frame.entity.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Component
@ApiModel(value="ResponseBase",description="响应数据")
public class ResponseBase<T extends Object>{

	@ApiModelProperty(name="success",value="调用状态")
	private boolean success;
	
	@ApiModelProperty(name="message",value="信息")
	private String message;
	
	@ApiModelProperty(name="code",value="代码")
	private int code;
	
	@ApiModelProperty(name="data",value="数据")
	private T data;
	
	public static <T> ResponseEntity<ResponseBase<T>> createResponse(boolean success,String message,T data,HttpStatus httpStatus){
		
		return new ResponseEntity<ResponseBase<T>>(new ResponseBase<T>(success, message, data), httpStatus);
	}
	
	public static <T> ResponseEntity<ResponseBase<T>> createResponse(boolean success,int code,String message,T data,HttpStatus httpStatus){
		
		return new ResponseEntity<ResponseBase<T>>(new ResponseBase<T>(success, code, message, data), httpStatus);
	}
	
	public static <T> ResponseEntity<T> createResponseOnlyData(T data,HttpStatus httpStatus){
		
		return new ResponseEntity<T>(data,httpStatus);
	}
	
	public ResponseBase() {
		
	}

	public ResponseBase(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	

	public ResponseBase(boolean success, int code, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.code = code;
	}

	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}
