package pl.wrona.iotapollo.cache;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public void clearCache() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager
                .getCache(cacheName)
                .clear());
    }

}
