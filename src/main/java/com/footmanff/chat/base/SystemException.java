package com.footmanff.chat.base;

/**
 * 系统异常
 * 
 * @author zhangli on 10/01/2018.
 */
public class SystemException extends RuntimeException {

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
