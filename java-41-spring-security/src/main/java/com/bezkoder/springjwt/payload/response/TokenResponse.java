package com.bezkoder.springjwt.payload.response;

import java.util.List;

public class TokenResponse {

  private final String accessToken;
  private final String refreshToken;
  private final String tokenType;
  private final long expiresIn;
  private final Long id;
  private final String username;
  private final String email;
  private final List<String> roles;

  public TokenResponse(
      String accessToken,
      String refreshToken,
      long expiresIn,
      Long id,
      String username,
      String email,
      List<String> roles) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.tokenType = "Bearer";
    this.expiresIn = expiresIn;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public List<String> getRoles() {
    return roles;
  }
}
