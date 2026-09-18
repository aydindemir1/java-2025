package com.bezkoder.springjwt.security.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.springjwt.exception.EmailAlreadyExistsException;
import com.bezkoder.springjwt.exception.RoleNotFoundException;
import com.bezkoder.springjwt.exception.UsernameAlreadyExistsException;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.TokenResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final RefreshTokenService refreshTokenService;

  public AuthService(
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

  @Transactional
  public void register(SignupRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new UsernameAlreadyExistsException(request.getUsername());
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException(request.getEmail());
    }

    User user = new User(
        request.getUsername(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword()));

    user.setRoles(resolveRoles(request.getRole()));
    userRepository.save(user);
  }

  public TokenResponse authenticate(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()));

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return refreshTokenService.issueTokens(userDetails);
  }

  public TokenResponse refresh(String rawRefreshToken) {
    return refreshTokenService.refresh(rawRefreshToken);
  }

  public void logout(String rawRefreshToken) {
    refreshTokenService.logout(rawRefreshToken);
  }

  public void logoutAll(Long userId) {
    refreshTokenService.logoutAll(userId);
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
        .orElseThrow(() -> new RoleNotFoundException(roleName));
  }
}
