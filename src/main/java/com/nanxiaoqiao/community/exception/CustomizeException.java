package com.nanxiaoqiao.community.exception;

public class CustomizeException extends RuntimeException {
    private final String message;

    public CustomizeException(ICustomizeErrorCode e) {
        this.message = e.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
