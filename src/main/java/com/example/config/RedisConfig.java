package com.example.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
public class RedisConfig {

    /**
     * A dedicated ObjectMapper for Redis serialization ONLY.
     * Not exposed as a @Bean so it doesn't override Spring Boot's
     * global Jackson ObjectMapper (used by Spring MVC for REST responses).
     *
     * DefaultTyping embeds @class metadata in JSON so that generic types
     * like List<TrendingResponse> are correctly deserialized on cache read.
     */
    private ObjectMapper redisMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                        .allowIfBaseType(Object.class)
                        .build(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
        return mapper;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {

        GenericJackson2JsonRedisSerializer serializer =
                new GenericJackson2JsonRedisSerializer(redisMapper());

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(serializer));
    }

    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory) {

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .build();
    }
}
