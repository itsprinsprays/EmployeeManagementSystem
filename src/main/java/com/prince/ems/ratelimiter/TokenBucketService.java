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
	
	public boolean allowRequest(String key, int maxToken, int refillRate) {
	
		 
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
//		long intervalPassed = secondsPassed / 15; //If you want to make the refill token takes 15 seconds per 1 token
		int tokensToAdd = (int) (secondsPassed * refillRate);
		
		if(tokensToAdd > 0) {
			int newTokens = Math.min(maxToken, bucket.getToken() + tokensToAdd);
			bucket.setToken(newTokens);
			bucket.setLastRefillTime(now);
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
