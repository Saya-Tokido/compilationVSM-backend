package com.ljz.compilationVSM.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

/**
 * json 工具类
 *
 * @author ljz
 * @since 2024-12-26
 */
@Slf4j
public class JsonUtil {

    /**
     * 将对象序列化为json字符串
     *
     * @param object 待序列化的对象
     * @return 序列化后的json字符串
     */
    public static String toJsonStr(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(object);
            return jsonString;
        } catch (JsonProcessingException e) {
            log.error("对象序列化为json字符串异常");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 将json字符串反序列化为map
     *
     * @param jsonStr 待反序列化的json字符串
     * @return map
     */
    public static Map<String, Set<String>> toMap(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("json字符串反序列化为map对象异常");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 将map对象序列化为json字符串
     *
     * @param map 待序列化map
     * @return 序列化后的json字符串
     */
    public static String map2Json(Map<String, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error("map对象序列化为json字符串异常");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 将json字符串反序列化为map
     *
     * @param jsonStr 待反序列化的json字符串
     * @return map
     */
    public static Map<String, String> json2Map(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("json字符串反序列化为map对象异常");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }
}
