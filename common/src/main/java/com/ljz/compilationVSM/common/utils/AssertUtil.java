package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 断言工具类
 *
 * @author 劳金赞
 * @since 2025-03-03
 */
public class AssertUtil {

    /**
     * 校验对象是否为空，如果为空则抛出异常
     *
     * @param object  要校验的对象
     * @param message 异常信息
     * @return 原对象
     */
    public static Object notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new BizException(BizExceptionCodeEnum.PARAMETER_ERROR);
        }
        return object;
    }

    /**
     * 校验字符串是否为空或者长度为 0，如果为空则抛出异常
     *
     * @param str     要校验的字符串
     * @param message 异常信息
     * @return 原字符串
     */
    public static String notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BizException(BizExceptionCodeEnum.PARAMETER_ERROR);
        }
        return str;
    }

    /**
     * 校验集合是否为空
     * @param collection 集合
     * @param message 异常信息
     * @return 原集合
     */
    public static Collection<?> notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(BizExceptionCodeEnum.PARAMETER_ERROR);
        }
        return collection;
    }

    /**
     * 校验布尔值是否为 true，如果为 false 则抛出异常
     *
     * @param condition 要校验的布尔值
     * @param message   异常信息
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}