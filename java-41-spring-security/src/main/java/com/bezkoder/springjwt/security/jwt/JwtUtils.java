package com.bezkoder.springjwt.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bezkoder.springjwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  private final String jwtSecret;
  private final int jwtExpirationMs;

  public JwtUtils(
      @Value("${bezkoder.app.jwtSecret}") String jwtSecret,
      @Value("${bezkoder.app.jwtExpirationMs}") int jwtExpirationMs) {
    this.jwtSecret = jwtSecret;
    this.jwtExpirationMs = jwtExpirationMs;
  }

  public String generateAccessToken(UserDetailsImpl userPrincipal) {
    Date issuedAt = new Date();
    Date expiresAt = new Date(issuedAt.getTime() + jwtExpirationMs);

    return Jwts.builder()
        .setSubject(userPrincipal.getUsername())
        .setIssuedAt(issuedAt)
        .setExpiration(expiresAt)
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public long getAccessTokenExpirationSeconds() {
    return jwtExpirationMs / 1000L;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key())
          .build()
          .parseClaimsJws(authToken);
      return true;
    } catch (SecurityException | MalformedJwtException exception) {
      logger.error("Invalid JWT signature/token: {}", exception.getMessage());
    } catch (ExpiredJwtException exception) {
      logger.error("JWT token is expired: {}", exception.getMessage());
    } catch (UnsupportedJwtException exception) {
      logger.error("JWT token is unsupported: {}", exception.getMessage());
    } catch (IllegalArgumentException exception) {
      logger.error("JWT claims string is empty: {}", exception.getMessage());
    }

    return false;
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }
}
