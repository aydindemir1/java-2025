package com.bezkoder.springjwt.exception;

public class EmailAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public EmailAlreadyExistsException(String email) {
    super("Email is already in use: " + email);
  }
}
