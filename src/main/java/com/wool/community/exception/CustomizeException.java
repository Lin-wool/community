package com.wool.community.exception;

/**
 * @author WOOL
 * 自定义异常类
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
