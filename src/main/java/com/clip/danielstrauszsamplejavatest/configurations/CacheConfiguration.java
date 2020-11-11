package com.clip.danielstrauszsamplejavatest.configurations;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class CacheConfiguration {

    @Bean
    public Cache<Long, ArrayList> cache() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        return cacheManager.createCache(
            "transactions",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Long.class,
                ArrayList.class,
                ResourcePoolsBuilder.heap(5000)
            )
            .withExpiry(ExpiryPolicy.NO_EXPIRY)
            .build()
        );
    }
}
