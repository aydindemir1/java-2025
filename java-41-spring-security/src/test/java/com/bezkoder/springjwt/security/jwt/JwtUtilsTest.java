package com.bezkoder.springjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bezkoder.springjwt.security.services.UserDetailsImpl;

class JwtUtilsTest {

  private static final String TEST_SECRET =
      "VGhpc0lzQVRlc3RPbmx5U2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaEZvckhTMjU2";

  @Test
  void generatedTokenCanBeValidatedAndParsed() {
    JwtUtils jwtUtils = new JwtUtils(TEST_SECRET, 60000);

    UserDetailsImpl user = new UserDetailsImpl(
        1L,
        "jwtuser",
        "jwtuser@example.com",
        "encoded",
        List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateAccessToken(user);

    assertTrue(jwtUtils.validateJwtToken(token));
    assertEquals("jwtuser", jwtUtils.getUserNameFromJwtToken(token));
    assertEquals(60, jwtUtils.getAccessTokenExpirationSeconds());
  }

  @Test
  void malformedTokenIsRejected() {
    JwtUtils jwtUtils = new JwtUtils(TEST_SECRET, 60000);
    assertFalse(jwtUtils.validateJwtToken("not-a-jwt"));
  }
}
