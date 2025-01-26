package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 分布式锁工具类
 *
 * @author ljz
 * @since 2025-01-22
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributeLockUtil {

    private final RedisUtil redisUtil;

    /**
     * 加锁
     *
     * @param key 加锁业务键
     * @param time 锁过期时长
     * @return 是否加锁成功
     */
    public boolean lock(String key, long time) {
        Long userId = UserContextHolder.getUserId();
        String luaScript = """
                local key = KEYS[1]
                local value = ARGV[1]
                local timeout = ARGV[2]
                local result = redis.call('SET', key, value, 'NX', 'PX', timeout)
                if result == 'OK' then
                    return 1
                else
                    return 0
                end
                """;
        Object result = redisUtil.stringLuaExecute(luaScript, Collections.singletonList(key), userId, time);
        return result.equals(1);
    }

    /**
     * 解锁
     *
     * @param key 键
     */
    public void unlock(String key) {
        Long userId = UserContextHolder.getUserId();
        String luaScript = """
                local key = KEYS[1]
                local value = ARGV[1]
                if redis.call('GET', key) == value then
                    return redis.call('DEL', key)
                else
                    return 0
                end
                """;
        Object result = redisUtil.stringLuaExecute(luaScript, Collections.singletonList(key), userId);
        if(result.equals(0)){
            log.error("分布式锁解锁失败,key = {}, value = {}",key,userId);
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }
}
