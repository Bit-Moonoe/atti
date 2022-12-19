package com.example.back.base.jwt;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.example.back.domain.User;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private final static String SECRET_KEY = "dlrjqjsdurgksmstkfkaqkqhdlazz";

	public String makeJwtToken(User user) {
		Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

		return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET_KEY).setSubject(user.getNickName())
				.setIssuer("atti app").setIssuedAt(new Date()).setExpiration(expiryDate).compact();
	}

	public String validateAndGetUserNickName(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

}