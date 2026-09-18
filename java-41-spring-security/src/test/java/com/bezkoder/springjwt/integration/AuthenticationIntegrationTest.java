package com.bezkoder.springjwt.integration;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

class AuthenticationIntegrationTest extends PostgreSqlIntegrationTestSupport {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void signupAndSigninReturnTokenPair() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "user1", "user1@example.com", "Password123!", "user");

    mockMvc.perform(post("/api/auth/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "username": "user1",
                  "password": "Password123!"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").isNotEmpty())
        .andExpect(jsonPath("$.refreshToken").isNotEmpty())
        .andExpect(jsonPath("$.tokenType").value("Bearer"))
        .andExpect(jsonPath("$.expiresIn").value(900))
        .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
  }

  @Test
  void duplicateUsernameReturns409() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "duplicate", "first@example.com", "Password123!", "user");

    mockMvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "username": "duplicate",
                  "email": "second@example.com",
                  "password": "Password123!",
                  "role": ["user"]
                }
                """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("USERNAME_ALREADY_EXISTS"));
  }

  @Test
  void invalidSignupReturnsFieldErrors() throws Exception {
    mockMvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "username": "ab",
                  "email": "not-an-email",
                  "password": "123"
                }
                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.fieldErrors", hasKey("username")))
        .andExpect(jsonPath("$.fieldErrors", hasKey("email")))
        .andExpect(jsonPath("$.fieldErrors", hasKey("password")));
  }

  @Test
  void wrongPasswordReturns401() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "wrongpass", "wrongpass@example.com", "Password123!", "user");

    mockMvc.perform(post("/api/auth/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "username": "wrongpass",
                  "password": "WrongPassword"
                }
                """))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"));
  }
}
