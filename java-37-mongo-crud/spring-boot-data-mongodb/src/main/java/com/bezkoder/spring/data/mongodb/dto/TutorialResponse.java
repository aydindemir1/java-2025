package com.bezkoder.spring.data.mongodb.dto;

public record TutorialResponse(

        String id,
        String title,
        String description,
        boolean published

) {
}