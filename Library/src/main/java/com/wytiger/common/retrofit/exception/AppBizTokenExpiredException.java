package com.wytiger.common.retrofit.exception;

/**
 * Author: wujiejiang
 * Time: 2016/8/30
 * Desc: 业务token过期异常
 */
public class AppBizTokenExpiredException extends RuntimeException{
    private String resultCode;

    public AppBizTokenExpiredException(String resultCode, String resultMessage) {
        super(resultMessage);
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }
}
