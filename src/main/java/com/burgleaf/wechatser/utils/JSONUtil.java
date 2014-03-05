package com.burgleaf.wechatser.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangbin on 14-3-1.
 */

public class JSONUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    // 将 Java 对象转为 JSON 字符串
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = JSON.toJSONString(obj);
        } catch (Exception e) {
            logger.error("Java 转 JSON 出错！", e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    // 将 JSON 字符串转为 Java 对象
    public static <T> T fromJSON(String json, Class<T> type) {
        T obj;
        try {
            obj = JSON.parseObject(json,type);
        } catch (Exception e) {
            logger.error("JSON 转 Java 出错！", e);
            throw new RuntimeException(e);
        }
        return obj;
    }
}
