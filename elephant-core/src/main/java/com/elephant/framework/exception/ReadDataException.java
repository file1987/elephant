package com.elephant.framework.exception;
/**
 * 读取数据异常（一般是load数据配置）
 * @author file
 * @since 2014年5月10日 下午3:10:57
 * @version 1.0
 */
public class ReadDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReadDataException() {
		super();
	}
	
	public ReadDataException(String message) {
		super(message);
	}
	
	public ReadDataException(String message, Throwable cause) {
        super(message, cause);
    }
	
	 public ReadDataException(Throwable cause) {
	     super(cause);
	 }

}
