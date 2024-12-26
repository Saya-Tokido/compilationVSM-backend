package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.dto.LoginUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author ljz
 * @since 2024-12-25
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {


    private final RedisTemplate<String, String> stringMessageRedisTemplate;

    private final RedisTemplate<String, LoginUserDTO> loginRedisTemplate;

    /**
     * string类型 查询key是否存在
     *
     * @param key 待查询的key
     * @return 查询结果
     */
    public boolean stringHashKeyExists(String key) {
        return Boolean.TRUE.equals(stringMessageRedisTemplate.hasKey(key));
    }

    /**
     * string类型 获取值
     *
     * @param key 待查询的key
     * @return 查询结果
     */
    public String stringValueGet(String key) {
        Optional<Object> stringOptional = Optional.ofNullable(stringMessageRedisTemplate.opsForValue().get(key));
        return (String) stringOptional.orElse(null);
    }

    /**
     * LoginUser类型 获取值
     *
     * @param key 待查询的key
     * @return 查询结果
     */
    public LoginUserDTO loginUserGet(String key) {
        Optional<Object> loginUserOptional = Optional.ofNullable(loginRedisTemplate.opsForValue().get(key));
        return (LoginUserDTO) loginUserOptional.orElse(null);
    }

    /**
     * 删除string类型的记录
     *
     * @param key redis key
     * @return 是否删除
     */
    public boolean stringDelete(String key) {
        return Boolean.TRUE.equals(stringMessageRedisTemplate.delete(key));
    }

    /**
     * 删除loginUser类型的记录
     *
     * @param key redis key
     * @return 是否删除
     */
    public boolean loginUserDelete(String key) {
        return Boolean.TRUE.equals(loginRedisTemplate.delete(key));
    }

    /**
     * string类型 插入有效期值
     *
     * @param key    redis key
     * @param value  待插入的值
     * @param expire 过期时间，秒单位
     */
    public void stringHashExpirePut(String key, String value, long expire) {
        stringMessageRedisTemplate.opsForValue().set(key, value);
        stringMessageRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * loginUser类型 插入有效期值
     *
     * @param key    redis key
     * @param value  待插入的值
     * @param expire 过期时间，秒单位
     */
    public void loginUserExpirePut(String key, LoginUserDTO value, long expire) {
        loginRedisTemplate.opsForValue().set(key, value);
        loginRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
}
