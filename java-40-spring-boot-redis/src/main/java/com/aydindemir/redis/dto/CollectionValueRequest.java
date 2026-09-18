package com.aydindemir.redis.dto;

import jakarta.validation.constraints.NotBlank;

public record CollectionValueRequest(@NotBlank String key, @NotBlank String value) {
}