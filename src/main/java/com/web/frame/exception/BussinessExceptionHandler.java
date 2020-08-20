package com.web.frame.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.web.frame.entity.base.ResponseBase;
import com.web.frame.util.JsonFormat;
import com.web.frame.web.listener.StartupFinishListener;

@RestControllerAdvice
public class BussinessExceptionHandler {
	
	private Logger logger = Logger.getLogger(BussinessExceptionHandler.class);
	
	/*
	 * 10000	接口调用成功
	 * 20000	服务不可用
	 * 20001	授权权限不足
	 * 40001	缺少必选参数
	 * 40002	非法的参数
	 * 40004	业务处理失败
	 * 40006	权限不足
	 * 
	 * 40007	区块链调用失败
	 */

	@ExceptionHandler(BussinessException.class)
	public ResponseEntity<ResponseBase<String>> bussinessHandler(BussinessException ex){
		
		ex.printStackTrace();
		logger.error("BussinessError,stackTrace:"+JsonFormat.beanToJson(ex.getStackTrace()[0])+",message:"+ex.getMessage());
		return ResponseBase.createResponse(false, ex.getCode(), ex.getMessage(), "", HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseBase<String>> systemHandler(Exception ex){
		
		ex.printStackTrace();
		logger.error("SystemError,stackTrace:"+JsonFormat.beanToJson(ex.getStackTrace()[0])+",message:"+ex.getMessage());
		return ResponseBase.createResponse(false, 100, "程序异常", "", HttpStatus.OK);
	}
	
}
