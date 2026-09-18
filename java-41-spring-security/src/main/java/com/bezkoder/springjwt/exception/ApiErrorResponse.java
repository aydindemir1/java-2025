package com.bezkoder.springjwt.exception;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

  private final Instant timestamp;
  private final int status;
  private final String error;
  private final String code;
  private final String message;
  private final String path;
  private final Map<String, String> fieldErrors;

  public ApiErrorResponse(
      Instant timestamp,
      int status,
      String error,
      String code,
      String message,
      String path,
      Map<String, String> fieldErrors) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.code = code;
    this.message = message;
    this.path = path;
    this.fieldErrors = fieldErrors;
  }

  public static ApiErrorResponse of(
      int status,
      String error,
      String code,
      String message,
      String path) {
    return new ApiErrorResponse(
        Instant.now(),
        status,
        error,
        code,
        message,
        path,
        null);
  }

  public static ApiErrorResponse withFieldErrors(
      int status,
      String error,
      String code,
      String message,
      String path,
      Map<String, String> fieldErrors) {
    return new ApiErrorResponse(
        Instant.now(),
        status,
        error,
        code,
        message,
        path,
        fieldErrors);
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getFieldErrors() {
    return fieldErrors;
  }
}
