package com.prince.ems.ratelimiter;

public class TokenBucket {
	
	private int token;
	private long lastRefillTime;
	
	public TokenBucket() { }
	
	public TokenBucket(int token, long lastRefillTime) {
		this.token = token;
		this.lastRefillTime = lastRefillTime;
	}
	
	public int getToken() { return token; }
	public long getLastRefillTime() { return lastRefillTime; }
	
	public void setToken(int token) { this.token = token; }
	public void setLastRefillTime(long lastRefillTime) { this.lastRefillTime = lastRefillTime; }

}
