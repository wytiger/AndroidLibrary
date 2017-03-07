package com.wytiger.common.retrofit.response;

import java.io.InputStream;

import okhttp3.MediaType;

/**
 * Author: wujiejiang
 * Time: 2016/7/19
 * Desc:
 */
public class FileResponse {
    private long contentLength;
    private MediaType contentType;
    private InputStream inputStream;

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
