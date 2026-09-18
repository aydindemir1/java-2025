package com.bezkoder.springjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bezkoder.springjwt.models.RefreshToken;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.RefreshTokenRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

  @Mock
  private RefreshTokenRepository refreshTokenRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtUtils jwtUtils;

  private RefreshTokenService refreshTokenService;

  @BeforeEach
  void setUp() {
    refreshTokenService = new RefreshTokenService(
        refreshTokenRepository,
        userRepository,
        jwtUtils,
        604800000L);
  }

  @Test
  void invalidRefreshTokenIsRejected() {
    when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
        .thenReturn(Optional.empty());

    assertThrows(
        RefreshTokenException.class,
        () -> refreshTokenService.refresh("invalid-token"));
  }

  @Test
  void expiredRefreshTokenIsRevokedAndRejected() {
    User user = new User();
    user.setId(10L);

    RefreshToken token = new RefreshToken(
        "hash",
        user,
        Instant.now().minusSeconds(100),
        Instant.now().minusSeconds(1));

    when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
        .thenReturn(Optional.of(token));

    assertThrows(
        RefreshTokenException.class,
        () -> refreshTokenService.refresh("expired-token"));

    verify(refreshTokenRepository).save(token);
  }

  @Test
  void reusedRotatedTokenRevokesAllActiveTokens() {
    User user = new User();
    user.setId(20L);

    RefreshToken token = new RefreshToken(
        "hash",
        user,
        Instant.now().minusSeconds(100),
        Instant.now().plusSeconds(100));

    token.setRevokedAt(Instant.now().minusSeconds(10));
    token.setReplacedByTokenId(99L);

    when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
        .thenReturn(Optional.of(token));

    assertThrows(
        RefreshTokenException.class,
        () -> refreshTokenService.refresh("reused-token"));

    verify(refreshTokenRepository).revokeAllActiveByUserId(any(Long.class), any(Instant.class));
    verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
  }
}
