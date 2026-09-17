package com.bezkoder.spring.data.mongodb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TutorialRequest(

        @NotBlank(message = "Title boş olamaz.")
        @Size(min = 2, max = 150,
              message = "Title 2 ile 150 karakter arasında olmalıdır.")
        String title,

        @NotBlank(message = "Description boş olamaz.")
        @Size(min = 5, max = 1000,
              message = "Description 5 ile 1000 karakter arasında olmalıdır.")
        String description,

        boolean published

) {
}