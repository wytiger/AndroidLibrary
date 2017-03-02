package com.wytiger.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: wujiejiang
 * Time: 2016/5/19
 * Desc:fastjson转换类
 */
public class FastJsonUtil {
    /**
     * 隐藏默认的构造方法
     */
    private FastJsonUtil() {

    }

    public static final String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static final <T> T fromJson(final String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static final <T> T fromJson(final String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

    public static final HashMap<String, String> fromJson2StringHashMap(final String json) {
        return JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {
        });
    }

    public static final HashMap<String, Object> fromJson2ObjectHashMap(final String json) {
        return JSON.parseObject(json, new TypeReference<HashMap<String, Object>>() {
        });
    }

    public static final ArrayList<HashMap<String, Object>> fromJson2ArrayList(final String json) {
        return JSON.parseObject(json, new TypeReference<ArrayList<HashMap<String, Object>>>() {
        });
    }

    public static final <T> List<T> fromJson2List(final String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    public static final String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

}
