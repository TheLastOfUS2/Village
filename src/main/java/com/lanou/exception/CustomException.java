package com.lanou.exception;

/**
 * Created by dllo on 2017/11/7.
 */
public class CustomException extends Exception {
    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
