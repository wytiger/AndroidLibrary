package com.wytiger.common.http2.exception;

/**
 * Author: wujiejiang
 * Time: 2016/6/28
 * Desc:
 */
public class AppHttpException extends RuntimeException{
    //成功为0，失败为9位错误码
    private String resultCode;
    private String resultMessage;

    public AppHttpException(String resultCode, String resultMessage) {
        super(resultMessage);
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }

//    /**
//     * 这个值可能为空，所以不要用这个
//     * @return
//     */
//    public String getResultMessage() {
//        return resultMessage;
//    }
}
