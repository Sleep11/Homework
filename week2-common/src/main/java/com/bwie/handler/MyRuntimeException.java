package com.bwie.handler;

/**
 * 自定义异常
 * @ClassName MyRuntimeException
 * @Description: TODO
 */

public class MyRuntimeException extends RuntimeException{

    public MyRuntimeException(String message) {
        super(message);
    }
}
