package caugarde.vote.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class EhcacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder().build(true);
    }

    @Bean
    public Cache<Long, AtomicInteger> voteCache(CacheManager cacheManager) {
        return cacheManager.createCache("voteCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Long.class,
                        AtomicInteger.class,
                        ResourcePoolsBuilder.heap(1000)
                ).build());
    }

}

