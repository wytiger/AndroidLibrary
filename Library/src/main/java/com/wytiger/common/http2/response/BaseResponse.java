package com.wytiger.common.http2.response;

import java.io.Serializable;

/**
 * Author: wujiejiang
 * Time: 2016/7/13
 * Desc:
 */
public class BaseResponse implements Serializable{
    private static final long serialVersionUID = 1L;

    public static final String WALLET_HTTP_SUCCESS = "0";

    /**
     * 成功为0，失败为9位错误码
     */
    private String result;
    /**
     *展示给用户的错误信息描述
     */
    private String retinfo;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRetinfo() {
        return retinfo;
    }

    public void setRetinfo(String retinfo) {
        this.retinfo = retinfo;
    }
}
