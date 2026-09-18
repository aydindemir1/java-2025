package com.bezkoder.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

  public static final String SECURITY_SCHEME_NAME = "bearerAuth";

  @Bean
  public OpenAPI springJwtOpenApi() {
    SecurityScheme bearerScheme = new SecurityScheme()
        .name(SECURITY_SCHEME_NAME)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");

    return new OpenAPI()
        .info(new Info()
            .title("Spring Security JWT API")
            .version("1.0.0")
            .description(
                "Stateless Spring Security API with JWT access tokens, refresh-token rotation, RBAC, Flyway, PostgreSQL and MySQL support."))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME, bearerScheme));
  }
}
