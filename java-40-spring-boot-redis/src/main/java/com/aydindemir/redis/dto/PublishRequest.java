package com.aydindemir.redis.dto;

import jakarta.validation.constraints.NotBlank;

public record PublishRequest(@NotBlank String message) {
}