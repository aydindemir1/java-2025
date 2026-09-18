package com.aydindemir.redis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record KeyValueRequest(@NotBlank String key, @NotBlank String value, @Positive Long ttlSeconds) {
}