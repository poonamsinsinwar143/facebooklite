package com.um.util;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import com.um.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtility {
	private static final String SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	
	public static String generateToken(User user) {
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET), 
		                            SignatureAlgorithm.HS256.getJcaName());

		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
		        .claim("userId", user.getUserId())
		        .setSubject("user-management-security")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(new Date())
		        .setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES)))
		        .signWith(hmacKey)
		        .compact();
		
		return jwtToken;
	}
	
	public static Integer validateToken(String token) {
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET), SignatureAlgorithm.HS256.getJcaName());

		Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
		Claims claims = jwt.getBody();
		return (Integer)claims.get("userId");
	}
	
}
