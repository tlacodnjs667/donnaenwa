package com.wooruk.donnaenwa.security.jwt;

import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Role;
import com.wooruk.donnaenwa.security.DonnaenwaUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {
  private final SecretKey key;
  private final long tokenValidityInMilliseconds;

  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
      @Value("${jwt.expiration}") long tokenValidityInMilliseconds) {
    byte[] keyBytes = Base64.getDecoder().decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
  }

  public String generateToken(Member member) {
    List<String> roles = member.getRoles().stream().map(Role::getRole).toList();

    Claims claims = Jwts.claims()
        .subject(member.getMembername())
        .add("id", member.getId())
        .add("roles", roles)
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

  public String getUsername(String token) {
    return Jwts.parser()
        .setSigningKey(this.key).build().parseClaimsJws(token).getPayload()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(this.key).build().parseClaimsJws(token).getPayload();

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.warn("Invalid JWT token: {}", e.getMessage());
    }

    return false;
  }

  public Long getCurrentUserPk() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal instanceof DonnaenwaUserDetails) {
        DonnaenwaUserDetails userDetails = (DonnaenwaUserDetails) principal;
        return userDetails.getId();
      }
    }

    return null;
  }
}
