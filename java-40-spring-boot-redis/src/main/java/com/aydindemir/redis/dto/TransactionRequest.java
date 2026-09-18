package com.aydindemir.redis.dto;

import jakarta.validation.constraints.NotBlank;

public record TransactionRequest(@NotBlank String key1, @NotBlank String value1, @NotBlank String key2,
		@NotBlank String value2) {
}