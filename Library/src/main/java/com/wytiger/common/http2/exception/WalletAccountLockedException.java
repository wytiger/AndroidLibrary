package com.wytiger.common.http2.exception;

/**
 * Author: wujiejiang
 * Time: 2016/11/1
 * Desc: 用户账户被锁定异常
 */
public class WalletAccountLockedException extends RuntimeException{
    private String resultCode;

    public WalletAccountLockedException(String resultCode, String resultMessage) {
        super(resultMessage);
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }
}
