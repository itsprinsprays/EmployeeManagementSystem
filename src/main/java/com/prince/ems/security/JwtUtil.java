package com.prince.ems.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import com.prince.ems.exception.InvalidTokenException;
import com.prince.ems.exception.TokenExpiredException;

@Component
public class JwtUtil {
	
	private final SecretKey SECRET =
	        Keys.hmacShaKeyFor("SECRETPASSWORDSECRETPASSWORD123456".getBytes());
	
	//Token Generation
	public String generateToken(String username) {
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(
						System.currentTimeMillis() + 86400000)
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
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(SECRET)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
