package com.bezkoder.spring.data.mongodb;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.bezkoder.spring.data.mongodb.controller.TutorialController;
import com.bezkoder.spring.data.mongodb.dto.TutorialRequest;
import com.bezkoder.spring.data.mongodb.dto.TutorialResponse;
import com.bezkoder.spring.data.mongodb.exception.ResourceNotFoundException;
import com.bezkoder.spring.data.mongodb.service.TutorialService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TutorialController.class)
public class TutorialControllerTests {

    @MockBean
    private TutorialService tutorialService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldCreateTutorial() throws Exception {

        TutorialRequest request =
                new TutorialRequest(
                        "Spring Boot @WebMvcTest",
                        "Description",
                        true
                );

        TutorialResponse response =
                new TutorialResponse(
                        "1",
                        "Spring Boot @WebMvcTest",
                        "Description",
                        true
                );

        when(tutorialService.createTutorial(any(TutorialRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.title").value("Spring Boot @WebMvcTest"))
        .andExpect(jsonPath("$.description").value("Description"))
        .andExpect(jsonPath("$.published").value(true))
        .andDo(print());
    }


    @Test
    void shouldReturnTutorial() throws Exception {

        String id = "1";

        TutorialResponse tutorial =
                new TutorialResponse(
                        id,
                        "Spring Boot @WebMvcTest",
                        "Description",
                        true
                );

        when(tutorialService.getTutorialById(id))
                .thenReturn(tutorial);

        mockMvc.perform(
                get("/api/tutorials/{id}", id)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.title").value(tutorial.title()))
        .andExpect(jsonPath("$.description").value(tutorial.description()))
        .andExpect(jsonPath("$.published").value(tutorial.published()))
        .andDo(print());
    }


    @Test
    void shouldReturnNotFoundTutorial() throws Exception {

        String id = "999";

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
        .andDo(print());
    }


    @Test
    void shouldReturnListOfTutorials() throws Exception {

        List<TutorialResponse> tutorials = List.of(

                new TutorialResponse(
                        "1",
                        "Spring Boot @WebMvcTest 1",
                        "Description 1",
                        true
                ),

                new TutorialResponse(
                        "2",
                        "Spring Boot @WebMvcTest 2",
                        "Description 2",
                        true
                ),

                new TutorialResponse(
                        "3",
                        "Spring Boot @WebMvcTest 3",
                        "Description 3",
                        true
                )
        );

        when(tutorialService.getAllTutorials(null))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()")
                .value(tutorials.size()))
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[0].title")
                .value("Spring Boot @WebMvcTest 1"))
        .andDo(print());
    }


    @Test
    void shouldReturnListOfTutorialsWithFilter()
            throws Exception {

        String title = "Boot";

        List<TutorialResponse> tutorials = List.of(

                new TutorialResponse(
                        "1",
                        "Spring Boot @WebMvcTest",
                        "Description 1",
                        true
                ),

                new TutorialResponse(
                        "3",
                        "Spring Boot Web MVC",
                        "Description 3",
                        true
                )
        );

        MultiValueMap<String, String> paramsMap =
                new LinkedMultiValueMap<>();

        paramsMap.add("title", title);

        when(tutorialService.getAllTutorials(title))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials")
                        .params(paramsMap)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()")
                .value(tutorials.size()))
        .andDo(print());
    }


    @Test
    void shouldReturnEmptyListWhenFilterHasNoResult()
            throws Exception {

        String title = "BezKoder";

        MultiValueMap<String, String> paramsMap =
                new LinkedMultiValueMap<>();

        paramsMap.add("title", title);

        when(tutorialService.getAllTutorials(title))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/api/tutorials")
                        .params(paramsMap)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0))
        .andDo(print());
    }


    @Test
    void shouldReturnPublishedTutorials()
            throws Exception {

        List<TutorialResponse> tutorials = List.of(

                new TutorialResponse(
                        "1",
                        "Published Tutorial 1",
                        "Description 1",
                        true
                ),

                new TutorialResponse(
                        "2",
                        "Published Tutorial 2",
                        "Description 2",
                        true
                )
        );

        when(tutorialService.findByPublished(true))
                .thenReturn(tutorials);

        mockMvc.perform(
                get("/api/tutorials/published/{flag}", true)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()")
                .value(tutorials.size()))
        .andExpect(jsonPath("$[0].published")
                .value(true))
        .andDo(print());
    }


    @Test
    void shouldUpdateTutorial() throws Exception {

        String id = "1";

        TutorialRequest request =
                new TutorialRequest(
                        "Updated",
                        "Updated Description",
                        true
                );

        TutorialResponse response =
                new TutorialResponse(
                        id,
                        "Updated",
                        "Updated Description",
                        true
                );

        when(
                tutorialService.updateTutorial(
                        eq(id),
                        any(TutorialRequest.class)
                )
        )
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
                .value("Updated"))
        .andExpect(jsonPath("$.description")
                .value("Updated Description"))
        .andExpect(jsonPath("$.published")
                .value(true))
        .andDo(print());
    }


    @Test
    void shouldReturnNotFoundUpdateTutorial()
            throws Exception {

        String id = "999";

        TutorialRequest request =
                new TutorialRequest(
                        "Updated",
                        "Updated Description",
                        true
                );

        when(
                tutorialService.updateTutorial(
                        eq(id),
                        any(TutorialRequest.class)
                )
        )
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


    @Test
    void shouldDeleteTutorial() throws Exception {

        String id = "1";

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
    void shouldDeleteAllTutorials()
            throws Exception {

        doNothing()
                .when(tutorialService)
                .deleteAllTutorials();

        mockMvc.perform(
                delete("/api/tutorials")
        )
        .andExpect(status().isNoContent())
        .andDo(print());
    }
    
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
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.validationErrors.title")
                .exists())
        .andDo(print());
    }
    
    @Test
    void shouldReturnBadRequestForInvalidPublishedFlag()
            throws Exception {

        mockMvc.perform(
                get("/api/tutorials/published/abc")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode")
                .value("TYPE_MISMATCH"))
        .andDo(print());
    }
    
    @Test
    void shouldReturnBadRequestForMalformedJson()
            throws Exception {

        String malformedJson = """
                {
                  "title": "Test",
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
}