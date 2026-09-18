package com.bezkoder.springjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class RefreshTokenIntegrationTest extends PostgreSqlIntegrationTestSupport {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void refreshRotatesTokenAndOldTokenReuseRevokesActiveSessions() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "rotateuser", "rotate@example.com", "Password123!", "user");

    JsonNode login = AuthTestHelper.signin(
        mockMvc, objectMapper, "rotateuser", "Password123!");

    String refreshA = login.get("refreshToken").asText();

    String refreshResponse = mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(refreshA)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.refreshToken").isNotEmpty())
        .andReturn()
        .getResponse()
        .getContentAsString();

    String refreshB = objectMapper.readTree(refreshResponse).get("refreshToken").asText();

    mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(refreshA)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value("REFRESH_TOKEN_INVALID"));

    mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(refreshB)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value("REFRESH_TOKEN_INVALID"));
  }

  @Test
  void logoutRevokesCurrentRefreshToken() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "logoutuser", "logout@example.com", "Password123!", "user");

    JsonNode login = AuthTestHelper.signin(
        mockMvc, objectMapper, "logoutuser", "Password123!");

    String refreshToken = login.get("refreshToken").asText();

    mockMvc.perform(post("/api/auth/logout")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(refreshToken)))
        .andExpect(status().isNoContent());

    mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(refreshToken)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void logoutAllRevokesEveryRefreshSession() throws Exception {
    AuthTestHelper.signup(
        mockMvc, objectMapper,
        "alluser", "alluser@example.com", "Password123!", "user");

    JsonNode firstLogin = AuthTestHelper.signin(
        mockMvc, objectMapper, "alluser", "Password123!");
    JsonNode secondLogin = AuthTestHelper.signin(
        mockMvc, objectMapper, "alluser", "Password123!");

    mockMvc.perform(post("/api/auth/logout-all")
            .header(HttpHeaders.AUTHORIZATION,
                "Bearer " + firstLogin.get("accessToken").asText()))
        .andExpect(status().isNoContent());

    mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(firstLogin.get("refreshToken").asText())))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(refreshBody(secondLogin.get("refreshToken").asText())))
        .andExpect(status().isUnauthorized());
  }

  private String refreshBody(String refreshToken) {
    return """
        {
          "refreshToken": "%s"
        }
        """.formatted(refreshToken);
  }
}
