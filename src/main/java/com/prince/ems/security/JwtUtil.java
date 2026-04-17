package com.prince.ems.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import com.prince.ems.exception.InvalidTokenException;
import com.prince.ems.exception.TokenExpiredException;

@Component
public class JwtUtil {
	
//	private final SecretKey SECRET =
//	        Keys.hmacShaKeyFor("SECRETPASSWORDSECRETPASSWORD123456".getBytes());
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.accessExpiration}")
	private long accessExpiration;
	
	@Value("${jwt.refreshExpiration}")
	private long refreshExpiration;
	
	private SecretKey SECRET;
	
    @jakarta.annotation.PostConstruct
    public void init() {
        this.SECRET = Keys.hmacShaKeyFor(secret.getBytes());
    }
	
	//Token Generation
	public String generateToken(String username) {
		
		return Jwts.builder()
				.setSubject(username)
				.claim("type", "access")
				.setIssuedAt(new Date())
				.setExpiration(new Date(
						System.currentTimeMillis() + accessExpiration)
				)
				.signWith(SECRET)
				.compact();
	}
	
	//Generate Fresh Token for 7 days
	public String generateRefreshToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.claim("type", "refresh")
				.setIssuedAt(new Date())
				.setExpiration(new Date(
						System.currentTimeMillis() + refreshExpiration)
				)
				.signWith(SECRET)
				.compact();
	}
	
	//Token Validation
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(SECRET)
				.build()
				.parseClaimsJws(token);
			return true;
			
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException("Token has Expired");
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException("Invalid Token");
		}
	}
	
	//Extract Claims/Username
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	//Utility Helper 
	public Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(SECRET)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
