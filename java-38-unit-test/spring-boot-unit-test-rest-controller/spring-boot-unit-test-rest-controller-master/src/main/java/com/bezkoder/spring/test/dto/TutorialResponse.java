package com.bezkoder.spring.test.dto;

public record TutorialResponse(
        long id,
        String title,
        String description,
        boolean published
) {
}
