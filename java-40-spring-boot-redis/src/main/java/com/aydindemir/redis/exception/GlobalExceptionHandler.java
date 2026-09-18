package com.aydindemir.redis.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> notFound(ResourceNotFoundException e, HttpServletRequest r) {
		return build(HttpStatus.NOT_FOUND, e.getMessage(), r);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> validation(MethodArgumentNotValidException e, HttpServletRequest r) {
		String m = e.getBindingResult().getFieldErrors().stream().map(x -> x.getField() + ": " + x.getDefaultMessage())
				.findFirst().orElse("Validation error");
		return build(HttpStatus.BAD_REQUEST, m, r);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> general(Exception e, HttpServletRequest r) {
		return build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), r);
	}

	private ResponseEntity<ApiError> build(HttpStatus s, String m, HttpServletRequest r) {
		return ResponseEntity.status(s)
				.body(new ApiError(Instant.now(), s.value(), s.getReasonPhrase(), m, r.getRequestURI()));
	}
}
