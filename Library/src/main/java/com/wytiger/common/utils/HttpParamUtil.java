package com.wytiger.common.utils;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wytiger on 2017/3/2.
 */

public class HttpParamUtil {
    /**
     * 请求参数应该是 name1=value1&name2=value2
     * @param params
     * @return
     */
    public static String getKeyValue(Map<String, Object> params) {

        if(params == null){
            return "";
        }

        try {
            StringBuilder builder = new StringBuilder();
            if (params != null) {
                Iterator<String> iterator = params.keySet().iterator();
                while (iterator.hasNext()) {
                    final String key = iterator.next();
                    builder.append(key);
                    builder.append("=");
                    builder.append(params.get(key));
                    builder.append("&");
                }
                if (params.keySet().size() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 请求参数应该是 name1=value1&name2=value2
     * @param params
     * @return
     */
    public static String getKeyValuePost(Map<String, Object> params) {
        try {
            StringBuilder builder = new StringBuilder();
            if (params != null) {
                Iterator<String> iterator = params.keySet().iterator();
                while (iterator.hasNext()) {
                    final String key = iterator.next();
                    builder.append(key);
                    builder.append("=");
                    builder.append(params.get(key));
                    builder.append("&");
                }
                if (params.keySet().size() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
            }
            // return builder.toString();

            return URLEncoder.encode(builder.toString(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
