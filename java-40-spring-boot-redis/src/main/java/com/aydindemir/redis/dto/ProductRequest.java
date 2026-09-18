package com.aydindemir.redis.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(@NotNull Long id, @NotBlank String name, @NotNull @Positive BigDecimal price) {
}