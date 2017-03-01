package com.wytiger.common.http2.response;


/**
 * Author: wujiejiang
 * Time: 2016/5/19
 * Desc:
 */
public class AppResponse extends BaseResponse{

    /**
     *后台返回的json业务数据
     */
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
