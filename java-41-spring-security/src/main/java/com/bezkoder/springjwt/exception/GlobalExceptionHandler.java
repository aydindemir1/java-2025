package com.bezkoder.springjwt.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bezkoder.springjwt.security.services.RefreshTokenException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
      MethodArgumentNotValidException exception,
      HttpServletRequest request) {

    Map<String, String> fieldErrors = new LinkedHashMap<>();
    exception.getBindingResult().getFieldErrors().forEach(fieldError ->
        fieldErrors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));

    return build(
        HttpStatus.BAD_REQUEST,
        "VALIDATION_ERROR",
        "Validation failed.",
        request,
        fieldErrors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorResponse> handleMalformedRequest(
      HttpMessageNotReadableException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.BAD_REQUEST,
        "MALFORMED_REQUEST",
        "Request body is missing or malformed.",
        request,
        null);
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiErrorResponse> handleUsernameAlreadyExists(
      UsernameAlreadyExistsException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.CONFLICT,
        "USERNAME_ALREADY_EXISTS",
        exception.getMessage(),
        request,
        null);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(
      EmailAlreadyExistsException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.CONFLICT,
        "EMAIL_ALREADY_EXISTS",
        exception.getMessage(),
        request,
        null);
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleRoleNotFound(
      RoleNotFoundException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "ROLE_NOT_FOUND",
        exception.getMessage(),
        request,
        null);
  }

  @ExceptionHandler(RefreshTokenException.class)
  public ResponseEntity<ApiErrorResponse> handleRefreshToken(
      RefreshTokenException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.UNAUTHORIZED,
        "REFRESH_TOKEN_INVALID",
        exception.getMessage(),
        request,
        null);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleBadCredentials(
      BadCredentialsException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.UNAUTHORIZED,
        "INVALID_CREDENTIALS",
        "Username or password is invalid.",
        request,
        null);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleUsernameNotFound(
      UsernameNotFoundException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.UNAUTHORIZED,
        "INVALID_CREDENTIALS",
        "Username or password is invalid.",
        request,
        null);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiErrorResponse> handleAuthentication(
      AuthenticationException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.UNAUTHORIZED,
        "UNAUTHORIZED",
        "Authentication is required to access this resource.",
        request,
        null);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiErrorResponse> handleAccessDenied(
      AccessDeniedException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.FORBIDDEN,
        "ACCESS_DENIED",
        "You do not have permission to access this resource.",
        request,
        null);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiErrorResponse> handleIllegalState(
      IllegalStateException exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "APPLICATION_STATE_ERROR",
        exception.getMessage(),
        request,
        null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleUnexpected(
      Exception exception,
      HttpServletRequest request) {

    return build(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "INTERNAL_SERVER_ERROR",
        "An unexpected error occurred.",
        request,
        null);
  }

  private ResponseEntity<ApiErrorResponse> build(
      HttpStatus status,
      String code,
      String message,
      HttpServletRequest request,
      Map<String, String> fieldErrors) {

    ApiErrorResponse response = fieldErrors == null
        ? ApiErrorResponse.of(
            status.value(),
            status.getReasonPhrase(),
            code,
            message,
            request.getRequestURI())
        : ApiErrorResponse.withFieldErrors(
            status.value(),
            status.getReasonPhrase(),
            code,
            message,
            request.getRequestURI(),
            fieldErrors);

    return ResponseEntity.status(status).body(response);
  }
}
