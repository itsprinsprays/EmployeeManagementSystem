package com.prince.ems.ratelimiter;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBucketService {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	private static final int maxToken = 10;
	private static final int refillRate = 1; //per second
	
	public TokenBucketService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public boolean allowRequest(String key) {
	
		 
		//throttle key stored in the redis-cli
		String redisKey = "bucket:" + key;
		
		TokenBucket bucket = (TokenBucket) redisTemplate.opsForValue().get(redisKey);
		
		Long now = System.currentTimeMillis();
		
		//First Request
		if(bucket == null) {
			bucket = new TokenBucket(maxToken - 1, now);
			redisTemplate.opsForValue().set(redisKey, bucket, 1, TimeUnit.MINUTES);
			return true; 
		}
		
		//Refill Token
		long secondsPassed = (now - bucket.getLastRefillTime()) / 1000;
//		long intervalPassed = secondsPassed / 15;
		int tokensToAdd = (int) (secondsPassed * refillRate);
		
		if(tokensToAdd > 0) {
			int newTokens = Math.min(maxToken, bucket.getToken() + tokensToAdd);
			bucket.setToken(newTokens);
			bucket.setLastRefillTime(now);
		}
		
		//Consume Token
		if(bucket.getToken() > 0) {
			bucket.setToken(bucket.getToken() - 1);
			redisTemplate.opsForValue().set(redisKey, bucket, 1, TimeUnit.HOURS);
			return true;
		}
		return false;
	}

}
