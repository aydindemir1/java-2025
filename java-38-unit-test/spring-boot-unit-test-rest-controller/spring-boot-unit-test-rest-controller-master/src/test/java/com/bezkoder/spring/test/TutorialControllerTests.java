package com.bezkoder.spring.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bezkoder.spring.test.controller.TutorialController;
import com.bezkoder.spring.test.dto.TutorialRequest;
import com.bezkoder.spring.test.dto.TutorialResponse;
import com.bezkoder.spring.test.exception.ResourceNotFoundException;
import com.bezkoder.spring.test.service.TutorialService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TutorialController.class)
class TutorialControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TutorialService tutorialService;


    // ---------------------------------------------------------
    // GET ALL
    // ---------------------------------------------------------

    @Test
    void shouldReturnAllTutorials() throws Exception {

        List<TutorialResponse> tutorials = List.of(
                new TutorialResponse(
                        1L,
                        "Spring Boot",
                        "Description 1",
                        true
                ),
                new TutorialResponse(
                        2L,
                        "Spring Data JPA",
                        "Description 2",
                        false
                )
        );

        when(tutorialService.getAllTutorials(null))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].title").value("Spring Boot"))
        .andExpect(jsonPath("$[0].published").value(true))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].title").value("Spring Data JPA"))
        .andExpect(jsonPath("$[1].published").value(false))
        .andDo(print());
    }


    @Test
    void shouldReturnEmptyListWhenNoTutorialExists() throws Exception {

        when(tutorialService.getAllTutorials(null))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/api/tutorials")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // TITLE FILTER
    // ---------------------------------------------------------

    @Test
    void shouldReturnTutorialsFilteredByTitle() throws Exception {

        String title = "Boot";

        List<TutorialResponse> tutorials = List.of(
                new TutorialResponse(
                        1L,
                        "Spring Boot",
                        "Description",
                        true
                )
        );

        when(tutorialService.getAllTutorials(title))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials")
                        .param("title", title)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].title").value("Spring Boot"))
        .andDo(print());
    }


    @Test
    void shouldReturnEmptyListWhenTitleDoesNotMatch() throws Exception {

        String title = "Unknown";

        when(tutorialService.getAllTutorials(title))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/api/tutorials")
                        .param("title", title)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // GET BY ID
    // ---------------------------------------------------------

    @Test
    void shouldReturnTutorialById() throws Exception {

        long id = 1L;

        TutorialResponse response =
                new TutorialResponse(
                        id,
                        "Spring Boot",
                        "Description",
                        true
                );

        when(tutorialService.getTutorialById(id))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/tutorials/{id}", id)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.title").value("Spring Boot"))
        .andExpect(jsonPath("$.description").value("Description"))
        .andExpect(jsonPath("$.published").value(true))
        .andDo(print());
    }


    @Test
    void shouldReturnNotFoundWhenTutorialDoesNotExist() throws Exception {

        long id = 999L;

        when(tutorialService.getTutorialById(id))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Tutorial not found with id: " + id
                        )
                );

        mockMvc.perform(
                get("/api/tutorials/{id}", id)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode")
                .value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.message")
                .value("Tutorial not found with id: " + id))
        .andExpect(jsonPath("$.path")
                .value("/api/tutorials/" + id))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // PUBLISHED
    // ---------------------------------------------------------

    @Test
    void shouldReturnPublishedTutorials() throws Exception {

        List<TutorialResponse> tutorials = List.of(
                new TutorialResponse(
                        1L,
                        "Published Tutorial",
                        "Description",
                        true
                )
        );

        when(tutorialService.findByPublished(true))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials/published/{flag}", true)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].published").value(true))
        .andDo(print());
    }


    @Test
    void shouldReturnUnpublishedTutorials() throws Exception {

        List<TutorialResponse> tutorials = List.of(
                new TutorialResponse(
                        1L,
                        "Draft Tutorial",
                        "Description",
                        false
                )
        );

        when(tutorialService.findByPublished(false))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials/published/{flag}", false)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].published").value(false))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------

    @Test
    void shouldCreateTutorial() throws Exception {

        TutorialRequest request =
                new TutorialRequest(
                        "JUnit",
                        "Controller test description",
                        true
                );

        TutorialResponse response =
                new TutorialResponse(
                        1L,
                        "JUnit",
                        "Controller test description",
                        true
                );

        when(tutorialService.createTutorial(any(TutorialRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("JUnit"))
        .andExpect(jsonPath("$.description")
                .value("Controller test description"))
        .andExpect(jsonPath("$.published").value(true))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // VALIDATION
    // ---------------------------------------------------------

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {

        TutorialRequest request =
                new TutorialRequest(
                        "",
                        "Valid description",
                        false
                );

        mockMvc.perform(
                post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.validationErrors.title")
                .value("Title boş olamaz."))
        .andDo(print());
    }


    @Test
    void shouldReturnBadRequestWhenDescriptionIsBlank() throws Exception {

        TutorialRequest request =
                new TutorialRequest(
                        "Valid Title",
                        "",
                        false
                );

        mockMvc.perform(
                post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.validationErrors.description")
                .value("Description boş olamaz."))
        .andDo(print());
    }


    @Test
    void shouldReturnBadRequestWhenDescriptionIsTooShort() throws Exception {

        TutorialRequest request =
                new TutorialRequest(
                        "Valid Title",
                        "abc",
                        false
                );

        mockMvc.perform(
                post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.validationErrors.description")
                .value(
                        "Description 5 ile 1000 karakter arasında olmalıdır."
                ))
        .andDo(print());
    }


    @Test
    void shouldReturnBadRequestForMalformedJson() throws Exception {

        String malformedJson = """
                {
                  "title": "JUnit",
                  "description": "Broken JSON",
                  "published": true
                """;

        mockMvc.perform(
                post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("INVALID_REQUEST_BODY"))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // TYPE MISMATCH
    // ---------------------------------------------------------

    @Test
    void shouldReturnBadRequestForInvalidId() throws Exception {

        mockMvc.perform(
                get("/api/tutorials/abc")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("TYPE_MISMATCH"))
        .andExpect(jsonPath("$.message")
                .value("Parametre tipi geçersiz: id"))
        .andDo(print());
    }


    @Test
    void shouldReturnBadRequestForInvalidPublishedFlag() throws Exception {

        mockMvc.perform(
                get("/api/tutorials/published/abc")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("TYPE_MISMATCH"))
        .andExpect(jsonPath("$.message")
                .value("Parametre tipi geçersiz: flag"))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------

    @Test
    void shouldUpdateTutorial() throws Exception {

        long id = 1L;

        TutorialRequest request =
                new TutorialRequest(
                        "Updated Title",
                        "Updated Description",
                        true
                );

        TutorialResponse response =
                new TutorialResponse(
                        id,
                        "Updated Title",
                        "Updated Description",
                        true
                );

        when(tutorialService.updateTutorial(
                eq(id),
                any(TutorialRequest.class)
        ))
        .thenReturn(response);

        mockMvc.perform(
                put("/api/tutorials/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.title")
                .value("Updated Title"))
        .andExpect(jsonPath("$.description")
                .value("Updated Description"))
        .andExpect(jsonPath("$.published").value(true))
        .andDo(print());
    }


    @Test
    void shouldReturnNotFoundWhenUpdatingMissingTutorial()
            throws Exception {

        long id = 999L;

        TutorialRequest request =
                new TutorialRequest(
                        "Updated Title",
                        "Updated Description",
                        true
                );

        when(tutorialService.updateTutorial(
                eq(id),
                any(TutorialRequest.class)
        ))
        .thenThrow(
                new ResourceNotFoundException(
                        "Tutorial not found with id: " + id
                )
        );

        mockMvc.perform(
                put("/api/tutorials/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode")
                .value("RESOURCE_NOT_FOUND"))
        .andDo(print());
    }


    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------

    @Test
    void shouldDeleteTutorial() throws Exception {

        long id = 1L;

        doNothing()
                .when(tutorialService)
                .deleteTutorial(id);

        mockMvc.perform(
                delete("/api/tutorials/{id}", id)
        )
        .andExpect(status().isNoContent())
        .andDo(print());
    }


    @Test
    void shouldDeleteAllTutorials() throws Exception {

        doNothing()
                .when(tutorialService)
                .deleteAllTutorials();

        mockMvc.perform(
                delete("/api/tutorials")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
    }
}