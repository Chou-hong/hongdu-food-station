package com.hongdu.exception;

/**
 * 套餐启用失败异常
 */
public class ComboEnableFailedException extends BaseException {

    public ComboEnableFailedException(){}

    public ComboEnableFailedException(String msg){
        super(msg);
    }
}
