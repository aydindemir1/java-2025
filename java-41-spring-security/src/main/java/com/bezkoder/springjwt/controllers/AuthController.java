package com.bezkoder.springjwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.config.OpenApiConfig;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.LogoutRequest;
import com.bezkoder.springjwt.payload.request.RefreshTokenRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.payload.response.TokenResponse;
import com.bezkoder.springjwt.security.services.AuthService;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Registration, login and token lifecycle endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @Operation(
      summary = "Register user",
      description = "Creates a new user and assigns USER by default unless another supported role is requested.")
  @ApiResponse(responseCode = "200", description = "User registered")
  @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
  @ApiResponse(responseCode = "409", description = "Username or email already exists", content = @Content)
  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(
      @Valid @RequestBody SignupRequest request) {

    authService.register(request);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @Operation(
      summary = "Sign in",
      description = "Authenticates credentials and returns a short-lived access JWT plus a refresh token.")
  @ApiResponse(responseCode = "200", description = "Authentication successful",
      content = @Content(schema = @Schema(implementation = TokenResponse.class)))
  @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
  @PostMapping("/signin")
  public ResponseEntity<TokenResponse> authenticateUser(
      @Valid @RequestBody LoginRequest request) {

    return ResponseEntity.ok(authService.authenticate(request));
  }

  @Operation(
      summary = "Refresh tokens",
      description = "Rotates the supplied refresh token and returns a new access/refresh token pair.")
  @ApiResponse(responseCode = "200", description = "Token rotation successful",
      content = @Content(schema = @Schema(implementation = TokenResponse.class)))
  @ApiResponse(responseCode = "401", description = "Refresh token invalid, expired, revoked or reused", content = @Content)
  @PostMapping("/refresh")
  public ResponseEntity<TokenResponse> refreshToken(
      @Valid @RequestBody RefreshTokenRequest request) {

    return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
  }

  @Operation(
      summary = "Log out current refresh session",
      description = "Revokes the supplied refresh token. The operation is idempotent.")
  @ApiResponse(responseCode = "204", description = "Refresh token revoked")
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
    authService.logout(request.getRefreshToken());
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Log out all refresh sessions",
      description = "Revokes all active refresh tokens for the currently authenticated user.",
      security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
  @ApiResponse(responseCode = "204", description = "All refresh sessions revoked")
  @ApiResponse(responseCode = "401", description = "Authentication required", content = @Content)
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/logout-all")
  public ResponseEntity<Void> logoutAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    authService.logoutAll(userDetails.getId());
    return ResponseEntity.noContent().build();
  }
}
