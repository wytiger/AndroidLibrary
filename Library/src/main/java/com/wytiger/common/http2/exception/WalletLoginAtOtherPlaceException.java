package com.wytiger.common.http2.exception;

/**
 * Author: xiongqi
 * Time: 2016/9/13
 * Desc:
 */
public class WalletLoginAtOtherPlaceException extends RuntimeException {
    private String resultCode;

    public WalletLoginAtOtherPlaceException(String resultCode, String resultMessage) {
        super(resultMessage);
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }
}
