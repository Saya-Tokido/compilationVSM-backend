package com.ljz.compilationVSM.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

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
    public  <T> Set<T> getCache(String key, CacheableFunction<Set<T>> function) {
        return function.apply();
    }

    // 更新缓存
    @CachePut(value = "defaultCache", key = "#key")
    public  <T> Set<T> updateCache(String key, CacheableFunction<Set<T>> function) {
        // 获取缓存中的旧数据
        Set<T> existingSet = cacheManager.getCache("defaultCache").get(key, Set.class);

        // 如果缓存中没有数据，则调用函数获取新数据
        if (existingSet == null) {
            existingSet = function.apply();
        } else {
            // 基于现有数据进行更新，例如添加新元素
            Set<T> newData = function.apply();
            existingSet.addAll(newData);  // 将新数据合并到现有Set中
        }

        // 更新缓存并返回更新后的Set
        cacheManager.getCache("defaultCache").put(key, existingSet);
        return existingSet;
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
