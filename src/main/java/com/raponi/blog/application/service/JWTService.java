package com.raponi.blog.application.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

  @Value("${jwt.secret}")
  private String secretKey;

  public String generateToken(String username, Account account) {
    Map<String, Object> claims = new HashMap<>();

    return Jwts.builder().claims().add(claims).subject(account.getId())
        .add("username", username)
        .add("role", account.getRole())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
        .and()
        .signWith(this.getKey())
        .compact();

  }

  public SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean validateToken(String token, String accountId) {
    String tokenId = extractAccountId(token);
    return (tokenId.equals(accountId) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, claims -> claims.get("username", String.class));
  }

  public String extractAccountId(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(this.getKey()).build().parseSignedClaims(token).getPayload();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

}
