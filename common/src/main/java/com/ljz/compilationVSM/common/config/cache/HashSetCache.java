package com.ljz.compilationVSM.common.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.*;

/**
 * hashset本地缓存
 *
 * @author ljz
 * @since 2024-02-10
 */
@Component
public class HashSetCache {

    // Caffeine 缓存，存储 HashSet，每个 key 对应一个 HashSet
    private final Cache<String, ConcurrentHashMap<String, Long>> cache;

    public HashSetCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // 整个 HashSet 10 分钟过期
                .build();
    }

    // 添加元素，带有单独过期时间
    public void addElement(String cacheKey, String value, long ttlSeconds) {
        cache.asMap().computeIfAbsent(cacheKey, k -> new ConcurrentHashMap<>())
                .put(value, System.currentTimeMillis() + ttlSeconds * 1000);
    }

    // 获取 HashSet（自动剔除过期的元素）
    public Set<String> getCachedSet(String cacheKey) {
        ConcurrentHashMap<String, Long> map = cache.getIfPresent(cacheKey);
        if (map == null) return Set.of();

        long now = System.currentTimeMillis();
        map.entrySet().removeIf(entry -> entry.getValue() < now); // 删除过期元素

        return map.keySet();
    }

    // 删除某个元素
    public void removeElement(String cacheKey, String value) {
        ConcurrentHashMap<String, Long> map = cache.getIfPresent(cacheKey);
        if (map != null) {
            map.remove(value);
        }
    }
}
