package com.bezkoder.springjwt.exception;

import com.bezkoder.springjwt.models.ERole;

public class RoleNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RoleNotFoundException(ERole role) {
    super("Required role is not configured: " + role);
  }
}
