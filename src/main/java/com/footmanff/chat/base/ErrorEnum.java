package com.footmanff.chat.base;

/**
 * @author zhangli on 10/01/2018.
 */
public enum ErrorEnum {
    
    AUTH_FAILED(1000, "注册失败"),
    ERROR_CLIENT_MSG(1001, "错误的客户端消息"),
    NO_PERMISSION(1002, "无权限进行当前操作");

    private int code;
    private String msg;

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    
}
