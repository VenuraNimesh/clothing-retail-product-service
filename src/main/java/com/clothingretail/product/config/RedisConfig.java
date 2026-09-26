package com.clothingretail.product.config;

import com.clothingretail.product.model.response.ProductDetailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheManager redisCacheManager(
            RedisConnectionFactory redisConnectionFactory,
            ObjectMapper objectMapper) {

        Jackson2JsonRedisSerializer<ProductDetailResponse> serializer =
                new Jackson2JsonRedisSerializer<>(
                        objectMapper,
                        ProductDetailResponse.class
                );

        RedisSerializationContext.SerializationPair<ProductDetailResponse> serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(serializer);

        RedisCacheConfiguration productsCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(new StringRedisSerializer())
                        )
                        .serializeValuesWith(serializationPair);

        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        return RedisCacheManager.builder(cacheWriter)
                .withInitialCacheConfigurations(
                        Map.of("products", productsCacheConfiguration)
                )
                .build();
    }

}
