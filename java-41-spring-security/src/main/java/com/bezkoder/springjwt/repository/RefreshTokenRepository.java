package com.bezkoder.springjwt.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.RefreshToken;

import jakarta.persistence.LockModeType;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select token from RefreshToken token where token.tokenHash = :tokenHash")
  Optional<RefreshToken> findByTokenHashForUpdate(@Param("tokenHash") String tokenHash);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("""
      update RefreshToken token
         set token.revokedAt = :revokedAt
       where token.user.id = :userId
         and token.revokedAt is null
      """)
  int revokeAllActiveByUserId(
      @Param("userId") Long userId,
      @Param("revokedAt") Instant revokedAt);
}
