package org.hswebframework.iot.logging;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * @author zhouhao
 * @since 1.0.1
 */
@AllArgsConstructor
public class AutoClearCache implements Cache {

    private Cache targetCache;

    @Override
    public String getName() {
        return targetCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return targetCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        try {
            return targetCache.get(key);
        } catch (Exception e) {
            evict(key);
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        try {
            return targetCache.get(key, type);
        } catch (Exception e) {
            evict(key);
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        try {
            return targetCache.get(key, valueLoader);
        } catch (Exception e) {
            evict(key);
        }
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        targetCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        try {
            return targetCache.putIfAbsent(key, value);
        } catch (Exception e) {
            targetCache.put(key, value);
        }
        return null;
    }

    @Override
    public void evict(Object key) {
        targetCache.evict(key);
    }

    @Override
    public void clear() {
        targetCache.clear();
    }
}
