package com.ljz.compilationVSM.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * a package of caffeine
 *
 * @author ljz
 * @since 2025-01-26
 */
@Component
@RequiredArgsConstructor
public class CacheUtil {

    private CacheManager cacheManager;

    // 缓存查询结果
    @Cacheable(value = "defaultCache", key = "#key")
    public <T> T getCache(String key, CacheableFunction<T> function) {
        return function.apply();
    }

    // 更新缓存
    @CachePut(value = "defaultCache", key = "#key")
    public <T> T updateCache(String key, CacheableFunction<T> function) {
        return function.apply();
    }

    // 移除缓存
    @CacheEvict(value = "defaultCache", key = "#key")
    public void removeCache(String key) {
        // 不需要方法体，缓存项会自动移除
    }

    // 清除所有缓存项
    @CacheEvict(value = "defaultCache", allEntries = true)
    public void clearAllCaches() {
        // 清除所有缓存
    }

    // 清除指定的缓存
    public void clearCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    // 功能接口
    @FunctionalInterface
    public interface CacheableFunction<T> {
        T apply();
    }
}
