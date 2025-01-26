package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.dto.LexerTestCaseDTO;
import com.ljz.compilationVSM.common.dto.LoginUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
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

    private final RedisTemplate<String, LexerTestCaseDTO> lexerRedisTemplate;

    /**
     * string类型 查询key是否存在
     *
     * @param key 待查询的key
     * @return 查询结果
     */
    public boolean stringKeyExists(String key) {
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
     * 删除string类型的记录
     *
     * @param key redis key
     */
    public void stringDelete(String key) {
        stringMessageRedisTemplate.delete(key);
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
     * string类型 插入有效期值
     *
     * @param key    redis key
     * @param value  待插入的值
     * @param expire 过期时间，秒单位 如果为 0 则是永不过期
     */
    public void stringExpirePut(String key, String value, long expire) {
        stringMessageRedisTemplate.opsForValue().set(key, value);
        if (0 == expire) {
            return;
        }
        stringMessageRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * string类型 插入有效期值如果不存在
     *
     * @param key    redis key
     * @param value  待插入的值
     */
    public void stringExpirePutNX(String key, String value) {
        stringMessageRedisTemplate.opsForValue().setIfAbsent(key,value);
    }

    /**
     * string类型 执行Lua脚本
     *
     * @param luaScript Lua脚本
     * @param keys      操作的Key列表
     * @param args      传入Lua的参数
     * @return 执行结果
     */
    public Object stringLuaExecute(String luaScript, List<String> keys, Object... args) {
        RedisScript<Object> script = new DefaultRedisScript<>(luaScript, Object.class);
        return stringMessageRedisTemplate.execute(script, keys, args);
    }

    /**
     * 删除loginUser类型的记录
     *
     * @param key redis key
     */
    public void loginUserDelete(String key) {
        loginRedisTemplate.delete(key);
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

    /**
     * LexerTestCase类型 查询key是否存在
     *
     * @param key 待查询的key
     * @return 查询结果
     */
    public boolean lexerKeyExists(String key) {
        return Boolean.TRUE.equals(stringMessageRedisTemplate.hasKey(key));
    }

    /**
     * LexerTestCase类型 获取值
     *
     * @param key 待查询的key
     * @return 查询结果
     */
    public List<LexerTestCaseDTO> lexerTestCaseGet(String key) {
        Optional<List<LexerTestCaseDTO>> lexerTestCaseOptional = Optional.ofNullable(lexerRedisTemplate.opsForList().range(key, 0, -1));
        return lexerTestCaseOptional.orElse(null);
    }

    /**
     * LexerTestCase类型 插入值
     *
     * @param key   redis key
     * @param value 待插入的值
     */
    public void lexerListPut(String key, List<LexerTestCaseDTO> value) {
        lexerRedisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 删除LexerTestCase类型的记录
     *
     * @param key redis key
     */
    public void lexerListDelete(String key) {
        lexerRedisTemplate.delete(key);
    }

}
