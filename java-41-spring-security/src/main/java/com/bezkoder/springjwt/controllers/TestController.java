package com.bezkoder.springjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.config.OpenApiConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authorization Tests", description = "Public and role-protected endpoints for Spring Security testing")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Operation(summary = "Public endpoint")
  @ApiResponse(responseCode = "200", description = "Public content returned")
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @Operation(
      summary = "User endpoint",
      security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
  @ApiResponse(responseCode = "200", description = "Authorized")
  @ApiResponse(responseCode = "401", description = "Authentication required")
  @ApiResponse(responseCode = "403", description = "Required role missing")
  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @Operation(
      summary = "Moderator endpoint",
      security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
  @ApiResponse(responseCode = "200", description = "Authorized")
  @ApiResponse(responseCode = "401", description = "Authentication required")
  @ApiResponse(responseCode = "403", description = "MODERATOR role required")
  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @Operation(
      summary = "Admin endpoint",
      security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
  @ApiResponse(responseCode = "200", description = "Authorized")
  @ApiResponse(responseCode = "401", description = "Authentication required")
  @ApiResponse(responseCode = "403", description = "ADMIN role required")
  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
