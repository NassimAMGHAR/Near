package com.near.friendly.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ehcache.InstrumentedEhcache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import java.util.Set;
import java.util.SortedSet;

/**
 *
 */
@Slf4j
@Configuration
@EnableCaching(proxyTargetClass=true )
@AutoConfigureAfter(value = { MetricsConfiguration.class, DataBaseConfiguration.class })
public class CacheConfig {

    private final static String maxBytesLocalHeap = "16M";
    private final static int timeToLiveSeconds = 3600;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private MetricRegistry metricRegistry;

    private net.sf.ehcache.CacheManager cacheManager;

    @PreDestroy
    public void destroy() {
        log.info("Remove Cache Manager metrics");
        SortedSet<String> names = metricRegistry.getNames();
        names.forEach(metricRegistry::remove);
        log.info("Closing Cache Manager");
        cacheManager.shutdown();
    }

    @Bean
    public CacheManager cacheManager() {
        log.debug("Starting Ehcache");
        cacheManager = net.sf.ehcache.CacheManager.create();
        cacheManager.getConfiguration().setMaxBytesLocalHeap(maxBytesLocalHeap);
        log.debug("Registering Ehcache Metrics gauges");
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            String name = entity.getName();
            if (name == null || entity.getJavaType() != null) {
                name = entity.getJavaType().getName();
            }
            Assert.notNull(name, "entity cannot exist without an identifier");
            reconfigureCache(name);
            for (PluralAttribute pluralAttribute : entity.getPluralAttributes()) {
                reconfigureCache(name + "." + pluralAttribute.getName());
            }
        }
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    private void reconfigureCache(String name) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(name);
        if (cache != null) {
            cache.getCacheConfiguration().setTimeToLiveSeconds(timeToLiveSeconds);
            net.sf.ehcache.Ehcache decoratedCache = InstrumentedEhcache.instrument(metricRegistry, cache);
            cacheManager.replaceCacheWithDecoratedCache(cache, decoratedCache);
        }
    }

}
