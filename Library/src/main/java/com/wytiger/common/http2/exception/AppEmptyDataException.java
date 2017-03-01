package com.wytiger.common.http2.exception;


import com.wytiger.common.constants.AppConstants;

/**
 * Author: wujiejiang
 * Time: 2016/8/10
 * Desc:
 */
public class AppEmptyDataException extends RuntimeException{
    private String resultCode = AppConstants.EMPTY_DATA_CODE;

    public AppEmptyDataException() {
        super(AppConstants.EMPTY_DATA_MESSAGE);
    }

    public String getResultCode() {
        return resultCode;
    }

    public static AppEmptyDataException newInstance() {
        return new AppEmptyDataException();
    }
}
