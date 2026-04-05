package com.prince.ems.config;


import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@Configuration
public class RedisConfig {
	
	@Bean
    public RedisCacheConfiguration cacheConfiguration() {
		ObjectMapper om = new ObjectMapper();
	    om.registerModule(new JavaTimeModule()); // handle LocalDate/LocalDateTime
	    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	    // enable type info for dynamic deserialization
	    om.activateDefaultTyping(
	        LaissezFaireSubTypeValidator.instance,
	        ObjectMapper.DefaultTyping.NON_FINAL,
	        JsonTypeInfo.As.PROPERTY
	    );

	    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(om);

	    return RedisCacheConfiguration.defaultCacheConfig()
	        .entryTtl(Duration.ofMinutes(10))
	        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }
	
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // optional: set serializers if you want consistency with your cache
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(template.getStringSerializer());
        return template;
    }


}
