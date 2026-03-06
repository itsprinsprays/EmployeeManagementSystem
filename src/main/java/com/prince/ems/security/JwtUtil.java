package com.prince.ems.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import com.prince.ems.exception.InvalidTokenException;
import com.prince.ems.exception.TokenExpiredException;

@Component
public class JwtUtil {
	
	private final SecretKey SECRET =
	        Keys.hmacShaKeyFor("SECRETPASSWORDSECRETPASSWORD123456".getBytes());
	
	public String generateToken(String username) {
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(
						System.currentTimeMillis() + 86400000)
				)
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}
	
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

}
