package pl.wrona.iotapollo;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

//    @Bean
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager("warsawStops");
//    }

//    @Override
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager() {
//
//            @Override
//            protected Cache createConcurrentMapCache(final String name) {
//                return buildCache(name, 60);
//            }
//        };
//    }
//
//    private Cache buildCache(String cacheName, long expirationInMinutes) {
//        return new ConcurrentMapCache(cacheName,
//                CacheBuilder.newBuilder()
//                        .expireAfterWrite(expirationInMinutes, TimeUnit.MINUTES)
//                        .build().asMap(),
//                false);
//    }

}
