package com.bezkoder.springjwt.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.LogoutRequest;
import com.bezkoder.springjwt.payload.request.RefreshTokenRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.payload.response.TokenResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.RefreshTokenException;
import com.bezkoder.springjwt.security.services.RefreshTokenService;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final RefreshTokenService refreshTokenService;

  public AuthController(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      RefreshTokenService refreshTokenService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(
        signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        passwordEncoder.encode(signUpRequest.getPassword()));

    user.setRoles(resolveRoles(signUpRequest.getRole()));
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signin")
  public ResponseEntity<TokenResponse> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return ResponseEntity.ok(refreshTokenService.issueTokens(userDetails));
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshToken(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

    try {
      return ResponseEntity.ok(
          refreshTokenService.refresh(refreshTokenRequest.getRefreshToken()));
    } catch (RefreshTokenException exception) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new MessageResponse(exception.getMessage()));
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
    refreshTokenService.logout(logoutRequest.getRefreshToken());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/logout-all")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> logoutAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    refreshTokenService.logoutAll(userDetails.getId());
    return ResponseEntity.noContent().build();
  }

  private Set<Role> resolveRoles(Set<String> requestedRoles) {
    Set<Role> roles = new HashSet<>();

    if (requestedRoles == null || requestedRoles.isEmpty()) {
      roles.add(findRole(ERole.ROLE_USER));
      return roles;
    }

    requestedRoles.forEach(role -> roles.add(switch (role) {
      case "admin" -> findRole(ERole.ROLE_ADMIN);
      case "mod" -> findRole(ERole.ROLE_MODERATOR);
      default -> findRole(ERole.ROLE_USER);
    }));

    return roles;
  }

  private Role findRole(ERole roleName) {
    return roleRepository.findByName(roleName)
        .orElseThrow(() -> new IllegalStateException("Required role is not configured: " + roleName));
  }
}
