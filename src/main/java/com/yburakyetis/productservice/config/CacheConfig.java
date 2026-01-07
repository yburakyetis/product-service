package com.yburakyetis.productservice.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yburakyetis.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.yburakyetis.productservice.mapper.ProductMapper.INSTANCE;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

  public static final String CACHE_NAME = "productCache";

  private static final int INITIAL_CAPACITY = 100;

  private static final int MAXIMUM_SIZE = 100;

  private static final int EXPIRE_AFTER_ACCESS_MINUTES = 5;

  private static final int EXPIRE_AFTER_WRITE_MINUTES = 10;

  private final ProductRepository productRepository;

  @Bean
  public CacheManager cacheManager() {
    var cacheManager = new CaffeineCacheManager(CACHE_NAME);
    cacheManager.setCaffeine(caffeineCacheBuilder());
    cacheManager.setCacheLoader(cacheLoader());
    return cacheManager;
  }

  Caffeine<Object, Object> caffeineCacheBuilder() {
    return Caffeine.newBuilder()
                   .initialCapacity(INITIAL_CAPACITY)
                   .maximumSize(MAXIMUM_SIZE)
                   .expireAfterAccess(EXPIRE_AFTER_ACCESS_MINUTES, TimeUnit.MINUTES)
                   .expireAfterWrite(EXPIRE_AFTER_WRITE_MINUTES, TimeUnit.MINUTES)
                   .recordStats();
  }

  private CacheLoader<Object, Object> cacheLoader() {
    return key -> productRepository.findById((Long) key)
                                   .map(INSTANCE::toDto)
                                   .orElse(null);
  }
}
