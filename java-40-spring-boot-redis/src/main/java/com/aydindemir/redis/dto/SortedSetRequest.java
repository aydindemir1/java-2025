package com.aydindemir.redis.dto;

import jakarta.validation.constraints.NotBlank;

public record SortedSetRequest(@NotBlank String key, @NotBlank String member, double score) {
}