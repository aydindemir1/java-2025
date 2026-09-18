package com.bezkoder.springjwt.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.bezkoder.springjwt.exception.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  private final ObjectMapper objectMapper;

  public AuthEntryPointJwt(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    logger.warn("Unauthorized request to {}: {}", request.getServletPath(), authException.getMessage());

    HttpStatus status = HttpStatus.UNAUTHORIZED;
    ApiErrorResponse body = ApiErrorResponse.of(
        status.value(),
        status.getReasonPhrase(),
        "UNAUTHORIZED",
        "Authentication is required to access this resource.",
        request.getRequestURI());

    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getOutputStream(), body);
  }
}
