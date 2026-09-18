package com.bezkoder.springjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class AuthorizationIntegrationTest extends PostgreSqlIntegrationTestSupport {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void publicEndpointDoesNotRequireToken() throws Exception {
    mockMvc.perform(get("/api/test/all"))
        .andExpect(status().isOk())
        .andExpect(content().string("Public Content."));
  }

  @Test
  void protectedEndpointWithoutTokenReturns401() throws Exception {
    mockMvc.perform(get("/api/test/user"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
  }

  @Test
  void invalidTokenReturns401() throws Exception {
    mockMvc.perform(get("/api/test/user")
            .header(HttpHeaders.AUTHORIZATION, "Bearer definitely-not-a-jwt"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
  }

  @Test
  void userCanAccessUserEndpointButNotAdminEndpoint() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "normaluser", "normaluser@example.com", "Password123!", "user");

    JsonNode login = AuthTestHelper.signin(
        mockMvc, objectMapper, "normaluser", "Password123!");

    String accessToken = login.get("accessToken").asText();

    mockMvc.perform(get("/api/test/user")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
        .andExpect(status().isOk());

    mockMvc.perform(get("/api/test/admin")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.code").value("ACCESS_DENIED"));
  }

  @Test
  void adminCanAccessAdminEndpoint() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "adminuser", "adminuser@example.com", "Password123!", "admin");

    JsonNode login = AuthTestHelper.signin(
        mockMvc, objectMapper, "adminuser", "Password123!");

    mockMvc.perform(get("/api/test/admin")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + login.get("accessToken").asText()))
        .andExpect(status().isOk())
        .andExpect(content().string("Admin Board."));
  }
}
