package com.bezkoder.springjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class OpenApiIntegrationTest extends PostgreSqlIntegrationTestSupport {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void openApiJsonIsPubliclyAvailable() throws Exception {
    mockMvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.openapi").exists())
        .andExpect(jsonPath("$.components.securitySchemes.bearerAuth").exists());
  }

  @Test
  void swaggerUiEntryPointIsPubliclyAvailable() throws Exception {
    mockMvc.perform(get("/swagger-ui.html"))
        .andExpect(status().is3xxRedirection());
  }
}
