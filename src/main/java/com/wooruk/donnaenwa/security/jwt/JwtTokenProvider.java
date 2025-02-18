package com.wooruk.donnaenwa.security.jwt;

import com.wooruk.donnaenwa.domain.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Slf4j
@Component
public class JwtTokenProvider {
  private final SecretKey key;
  private final long tokenValidityInMilliseconds;

  public JwtTokenProvider(@Value("${jwt.secret}" ) String secretKey,
      @Value("${jwt.expiration}") long tokenValidityInMilliseconds
      ) {
    byte[] keyBytes = Base64.getDecoder().decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.tokenValidityInMilliseconds=tokenValidityInMilliseconds;
  }

  public String generateToken (String username, Set<Role> roles) {
    Claims claims = Jwts.claims()
        .subject(username)
        .add("roles", roles.stream().map(Role::getRole).toList())
        .build();

    Date now = new Date();
    Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

    return Jwts.builder()
        .claims(claims)
        .issuedAt(now)
        .expiration(validity)
        .signWith(this.key)
        .compact();
  }

  public String getUsername (String token) {
    return Jwts.parser()
        .decryptWith(this.key)
        .build()
        .parseEncryptedClaims(token)
        .getPayload()
        .getSubject();
  }

  public boolean validateToken (String token) {
    try {
      Jwts.parser()
          .decryptWith(this.key)
          .build()
          .parseEncryptedClaims(token);

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.warn ("Invalid JWT token: {}", e.getMessage());
    }

    return false;
  }
}
