package caugarde.vote.common.util;

import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCacheUtil {

    private final CacheManager cacheManager;

    public <K, V> Cache<K, V> getOrCreateCache(String cacheName, Class<K> keyClass, Class<V> valueClass) {
        Cache<K, V> cache = cacheManager.getCache(cacheName, keyClass, valueClass);
        if (cache == null) {
            cache = cacheManager.createCache(cacheName,
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(
                            keyClass, valueClass, ResourcePoolsBuilder.heap(100)
                    ).build());
        }
        return cache;
    }

}
