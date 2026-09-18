package com.bezkoder.springjwt.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repository.RoleRepository;

@Component
public class RoleDataInitializer implements CommandLineRunner {

  private final RoleRepository roleRepository;

  public RoleDataInitializer(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void run(String... args) {
    Arrays.stream(ERole.values())
        .filter(roleName -> roleRepository.findByName(roleName).isEmpty())
        .map(Role::new)
        .forEach(roleRepository::save);
  }
}
