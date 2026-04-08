package com.prince.ems.ratelimiter;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBucketService {
	
	private final RedisTemplate<String, Object> redisTemplate;
	

	
	public TokenBucketService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public boolean allowRequest(String key, int maxToken, int refillRate, int refillIntervalSeconds) {
	
		 
		//throttle key stored in the redis-cli
		String redisKey = "bucket:" + key;
		
		TokenBucket bucket = (TokenBucket) redisTemplate.opsForValue().get(redisKey);
		
		Long now = System.currentTimeMillis();
		
		//First Request
		if(bucket == null) {
			bucket = new TokenBucket(maxToken - 1, now);
			redisTemplate.opsForValue().set(redisKey, bucket, 30, TimeUnit.MINUTES);
			return true; 
		}
		
		//Refill Token
		long secondsPassed = (now - bucket.getLastRefillTime()) / 1000;
		//	// how many intervals have passed
		long intervals = secondsPassed / refillIntervalSeconds;

		// tokens to add based on intervals
		int tokensToAdd = (int) (intervals * refillRate);

		if(tokensToAdd > 0) {
		    int newTokens = Math.min(maxToken, bucket.getToken() + tokensToAdd);
		    bucket.setToken(newTokens);

		    // IMPORTANT: move time forward only by used intervals
		    long newRefillTime = bucket.getLastRefillTime() + (intervals * refillIntervalSeconds * 1000);
		    bucket.setLastRefillTime(newRefillTime);
		}
		
		//Consume Token
		if(bucket.getToken() > 0) {
			bucket.setToken(bucket.getToken() - 1);
			redisTemplate.opsForValue().set(redisKey, bucket, 30, TimeUnit.MINUTES);
			return true;
		}
		return false;
	}

}
