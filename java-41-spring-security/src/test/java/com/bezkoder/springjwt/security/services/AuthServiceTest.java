package com.bezkoder.springjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bezkoder.springjwt.exception.EmailAlreadyExistsException;
import com.bezkoder.springjwt.exception.UsernameAlreadyExistsException;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private RefreshTokenService refreshTokenService;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService = new AuthService(
        authenticationManager,
        userRepository,
        roleRepository,
        passwordEncoder,
        refreshTokenService);
  }

  @Test
  void registerAssignsUserRoleByDefault() {
    SignupRequest request = request("newuser", "new@example.com");

    when(userRepository.existsByUsername("newuser")).thenReturn(false);
    when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
    when(passwordEncoder.encode("Password123!")).thenReturn("encoded");
    when(roleRepository.findByName(ERole.ROLE_USER))
        .thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

    authService.register(request);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(captor.capture());

    User saved = captor.getValue();
    assertEquals("encoded", saved.getPassword());
    assertEquals(1, saved.getRoles().size());
    assertEquals(
        ERole.ROLE_USER,
        saved.getRoles().iterator().next().getName());
  }

  @Test
  void duplicateUsernameIsRejected() {
    SignupRequest request = request("existing", "new@example.com");
    when(userRepository.existsByUsername("existing")).thenReturn(true);

    assertThrows(
        UsernameAlreadyExistsException.class,
        () -> authService.register(request));
  }

  @Test
  void duplicateEmailIsRejected() {
    SignupRequest request = request("newuser", "existing@example.com");
    when(userRepository.existsByUsername("newuser")).thenReturn(false);
    when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

    assertThrows(
        EmailAlreadyExistsException.class,
        () -> authService.register(request));
  }

  private SignupRequest request(String username, String email) {
    SignupRequest request = new SignupRequest();
    request.setUsername(username);
    request.setEmail(email);
    request.setPassword("Password123!");
    return request;
  }
}
