package com.bezkoder.springjwt.security.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.springjwt.models.RefreshToken;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.TokenResponse;
import com.bezkoder.springjwt.repository.RefreshTokenRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;

@Service
public class RefreshTokenService {

  private static final int REFRESH_TOKEN_BYTES = 32;

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;
  private final long refreshExpirationMs;
  private final SecureRandom secureRandom = new SecureRandom();

  public RefreshTokenService(
      RefreshTokenRepository refreshTokenRepository,
      UserRepository userRepository,
      JwtUtils jwtUtils,
      @Value("${bezkoder.app.refreshExpirationMs}") long refreshExpirationMs) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
    this.jwtUtils = jwtUtils;
    this.refreshExpirationMs = refreshExpirationMs;
  }

  @Transactional
  public TokenResponse issueTokens(UserDetailsImpl userDetails) {
    User user = userRepository.findById(userDetails.getId())
        .orElseThrow(() -> new IllegalStateException("Authenticated user no longer exists."));

    IssuedRefreshToken refreshToken = createRefreshToken(user);
    return buildTokenResponse(userDetails, refreshToken.rawToken());
  }

  @Transactional(noRollbackFor = RefreshTokenException.class)
  public TokenResponse refresh(String rawRefreshToken) {
    Instant now = Instant.now();
    String tokenHash = hashToken(rawRefreshToken);

    RefreshToken currentToken = refreshTokenRepository.findByTokenHashForUpdate(tokenHash)
        .orElseThrow(() -> new RefreshTokenException("Refresh token is invalid."));

    if (currentToken.isRevoked()) {
      if (currentToken.getReplacedByTokenId() != null) {
        refreshTokenRepository.revokeAllActiveByUserId(currentToken.getUser().getId(), now);
        throw new RefreshTokenException(
            "Refresh token reuse detected. All active refresh tokens were revoked.");
      }

      throw new RefreshTokenException("Refresh token has been revoked.");
    }

    if (currentToken.isExpired(now)) {
      currentToken.setRevokedAt(now);
      refreshTokenRepository.save(currentToken);
      throw new RefreshTokenException("Refresh token has expired.");
    }

    User user = currentToken.getUser();
    UserDetailsImpl userDetails = UserDetailsImpl.build(user);

    IssuedRefreshToken replacementToken = createRefreshToken(user);

    currentToken.setRevokedAt(now);
    currentToken.setReplacedByTokenId(replacementToken.entity().getId());
    refreshTokenRepository.save(currentToken);

    return buildTokenResponse(userDetails, replacementToken.rawToken());
  }

  @Transactional
  public void logout(String rawRefreshToken) {
    String tokenHash = hashToken(rawRefreshToken);

    refreshTokenRepository.findByTokenHashForUpdate(tokenHash)
        .ifPresent(token -> {
          if (!token.isRevoked()) {
            token.setRevokedAt(Instant.now());
            refreshTokenRepository.save(token);
          }
        });
  }

  @Transactional
  public void logoutAll(Long userId) {
    refreshTokenRepository.revokeAllActiveByUserId(userId, Instant.now());
  }

  private IssuedRefreshToken createRefreshToken(User user) {
    String rawToken = generateSecureToken();
    Instant createdAt = Instant.now();
    Instant expiresAt = createdAt.plusMillis(refreshExpirationMs);

    RefreshToken entity = new RefreshToken(
        hashToken(rawToken),
        user,
        createdAt,
        expiresAt);

    RefreshToken savedToken = refreshTokenRepository.saveAndFlush(entity);
    return new IssuedRefreshToken(savedToken, rawToken);
  }

  private TokenResponse buildTokenResponse(UserDetailsImpl userDetails, String refreshToken) {
    String accessToken = jwtUtils.generateAccessToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(authority -> authority.getAuthority())
        .toList();

    return new TokenResponse(
        accessToken,
        refreshToken,
        jwtUtils.getAccessTokenExpirationSeconds(),
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles);
  }

  private String generateSecureToken() {
    byte[] bytes = new byte[REFRESH_TOKEN_BYTES];
    secureRandom.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  private String hashToken(String rawToken) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(hash);
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("SHA-256 is not available.", exception);
    }
  }

  private record IssuedRefreshToken(RefreshToken entity, String rawToken) {
  }
}
