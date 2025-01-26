package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.enums.HashAlgEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于redis的布隆过滤器
 *
 * @author ljz
 * @since 2025-01-21
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BloomFilterUtil {

    private final RedisUtil redisUtil;

    @Value("${redis-key-prefix.bloom-filter}")
    private String bloomFilterKey;

    @Value("${redis-key-prefix.hash-seed}")
    private String hashSeedKey;

    /**
     * initial hash seed and bloom filter
     */
    @PostConstruct
    public void init() {
        // initial hash seed
        HashMap<String, String> hashSeedMap = new HashMap<>();
        for (HashAlgEnum hashAlgEnum : HashAlgEnum.values()) {
            hashSeedMap.put(hashAlgEnum.getCode(), String.valueOf(HashAlgorithmUtil.generateSeed()));
        }
        String json = JsonUtil.map2Json(hashSeedMap);
        redisUtil.stringExpirePutNX(hashSeedKey, json);
        log.info("hash seed initialized!");
        // initial bloom filter
        String luaScript = """
                return tostring(redis.call('SETBIT', KEYS[1], tonumber(ARGV[1]), 0))
                """;
        //false positive rate 3%
        redisUtil.stringLuaExecute(luaScript, Collections.singletonList(bloomFilterKey), String.valueOf(8184));
        log.info("bloom filter initialized!");
    }

    /**
     * warm up data
     *
     * @param values existed values
     */
    public void warmUp(List<String> values) {
        values.forEach(this::add);
    }

    /**
     * add value
     */
    public void add(String value) {
        String json = redisUtil.stringValueGet(hashSeedKey);
        Map<String, String> hashSeedMap = JsonUtil.json2Map(json);
        List<String> hashCodeList = HashAlgorithmUtil.generate(value, hashSeedMap).stream().map(hashCode -> String.valueOf(hashCode % 8192)).toList();
        String luaScript = """
                redis.call('SETBIT', KEYS[1], tonumber(ARGV[1]), 1)
                redis.call('SETBIT', KEYS[1], tonumber(ARGV[2]), 1)
                redis.call('SETBIT', KEYS[1], tonumber(ARGV[3]), 1)
                return tostring(1)
                """;
        redisUtil.stringLuaExecute(luaScript, Collections.singletonList(bloomFilterKey), hashCodeList.get(0), hashCodeList.get(1), hashCodeList.get(2));
    }

    /**
     * value exists
     *
     * @param value value to query
     * @return exists
     */
    public boolean exists(String value) {
        String json = redisUtil.stringValueGet(hashSeedKey);
        Map<String, String> hashSeedMap = JsonUtil.json2Map(json);
        List<String> hashCodeList = HashAlgorithmUtil.generate(value, hashSeedMap).stream().map(hashCode -> String.valueOf(hashCode % 8192)).toList();
        String luaScript = """
                local result1 = redis.call('GETBIT', KEYS[1], tonumber(ARGV[1]))
                local result2 = redis.call('GETBIT', KEYS[1], tonumber(ARGV[2]))
                local result3 = redis.call('GETBIT', KEYS[1], tonumber(ARGV[3]))
                return 'OK' == result1 and 'OK' == result2 and 'OK' == result3
                """;
        Object result = redisUtil.stringLuaExecute(luaScript, Collections.singletonList(bloomFilterKey), hashCodeList.get(0), hashCodeList.get(1), hashCodeList.get(2));
        return result.equals(true);
    }

}
