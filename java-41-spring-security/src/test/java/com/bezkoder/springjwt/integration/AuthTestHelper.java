package com.bezkoder.springjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class AuthTestHelper {

  private AuthTestHelper() {
  }

  public static void signup(
      MockMvc mockMvc,
      ObjectMapper objectMapper,
      String username,
      String email,
      String password,
      String role) throws Exception {

    String roleJson = role == null ? "[]" : "[\"" + role + "\"]";
    String body = """
        {
          "username": "%s",
          "email": "%s",
          "password": "%s",
          "role": %s
        }
        """.formatted(username, email, password, roleJson);

    mockMvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk());
  }

  public static JsonNode signin(
      MockMvc mockMvc,
      ObjectMapper objectMapper,
      String username,
      String password) throws Exception {

    String body = """
        {
          "username": "%s",
          "password": "%s"
        }
        """.formatted(username, password);

    String json = mockMvc.perform(post("/api/auth/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    return objectMapper.readTree(json);
  }
}
