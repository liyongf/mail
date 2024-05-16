package org.jeecgframework.web.cgform.exception;

@SuppressWarnings("serial")
public class NetServiceException extends Exception {
	
	public NetServiceException(String msg)
	 {
	  super(msg);
	 }
	
	public NetServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
