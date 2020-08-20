package com.web.frame.exception;

/**
 * 业务操作异常
 * @author Administrator
 *
 */
public class BussinessException extends Exception{

	static final long serialVersionUID = -7034897190745766963L;
	
	private int code;
	
	public BussinessException() {
        super();
    }

    public BussinessException(String message) {
        super(message);
    }
    
    public BussinessException(int code,String message) {
    	super(message);
    	this.code = code;
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinessException(Throwable cause) {
        super(cause);
    }

    protected BussinessException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
    
    
}
