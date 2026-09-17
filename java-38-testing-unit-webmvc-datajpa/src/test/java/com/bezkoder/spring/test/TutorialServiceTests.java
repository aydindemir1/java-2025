package com.bezkoder.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bezkoder.spring.test.dto.TutorialRequest;
import com.bezkoder.spring.test.dto.TutorialResponse;
import com.bezkoder.spring.test.exception.ResourceNotFoundException;
import com.bezkoder.spring.test.model.Tutorial;
import com.bezkoder.spring.test.repository.TutorialRepository;
import com.bezkoder.spring.test.service.TutorialService;

@ExtendWith(MockitoExtension.class)
class TutorialServiceTests {

    @Mock
    private TutorialRepository tutorialRepository;

    @InjectMocks
    private TutorialService tutorialService;


    // ---------------------------------------------------------
    // GET ALL
    // ---------------------------------------------------------

    @Test
    void shouldReturnAllTutorialsWhenTitleIsNull() {

        List<Tutorial> tutorials = List.of(
                new Tutorial(
                        1L,
                        "Spring Boot",
                        "Spring Boot description",
                        true
                ),
                new Tutorial(
                        2L,
                        "Spring Data JPA",
                        "JPA description",
                        false
                )
        );

        when(tutorialRepository.findAll())
                .thenReturn(tutorials);

        List<TutorialResponse> result =
                tutorialService.getAllTutorials(null);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).title())
                .isEqualTo("Spring Boot");
        assertThat(result.get(0).description())
                .isEqualTo("Spring Boot description");
        assertThat(result.get(0).published())
                .isTrue();

        assertThat(result.get(1).id()).isEqualTo(2L);
        assertThat(result.get(1).title())
                .isEqualTo("Spring Data JPA");
        assertThat(result.get(1).published())
                .isFalse();

        verify(tutorialRepository).findAll();
        verify(tutorialRepository, never())
                .findByTitleContaining(any());
    }


    @Test
    void shouldReturnAllTutorialsWhenTitleIsBlank() {

        List<Tutorial> tutorials = List.of(
                new Tutorial(
                        1L,
                        "Spring Boot",
                        "Description",
                        true
                )
        );

        when(tutorialRepository.findAll())
                .thenReturn(tutorials);

        List<TutorialResponse> result =
                tutorialService.getAllTutorials("   ");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);

        verify(tutorialRepository).findAll();

        verify(tutorialRepository, never())
                .findByTitleContaining(any());
    }


    @Test
    void shouldReturnTutorialsFilteredByTitle() {

        String title = "Boot";

        List<Tutorial> tutorials = List.of(
                new Tutorial(
                        1L,
                        "Spring Boot",
                        "Description 1",
                        true
                ),
                new Tutorial(
                        2L,
                        "Spring Boot Testing",
                        "Description 2",
                        false
                )
        );

        when(tutorialRepository.findByTitleContaining(title))
                .thenReturn(tutorials);

        List<TutorialResponse> result =
                tutorialService.getAllTutorials(title);

        assertThat(result).hasSize(2);

        assertThat(result)
                .extracting(TutorialResponse::title)
                .containsExactly(
                        "Spring Boot",
                        "Spring Boot Testing"
                );

        verify(tutorialRepository)
                .findByTitleContaining(title);

        verify(tutorialRepository, never())
                .findAll();
    }


    @Test
    void shouldReturnEmptyListWhenNoTutorialMatchesTitle() {

        String title = "Unknown";

        when(tutorialRepository.findByTitleContaining(title))
                .thenReturn(Collections.emptyList());

        List<TutorialResponse> result =
                tutorialService.getAllTutorials(title);

        assertThat(result).isEmpty();

        verify(tutorialRepository)
                .findByTitleContaining(title);
    }


    // ---------------------------------------------------------
    // GET BY ID
    // ---------------------------------------------------------

    @Test
    void shouldReturnTutorialById() {

        long id = 1L;

        Tutorial tutorial = new Tutorial(
                id,
                "Spring Boot",
                "Spring Boot Description",
                true
        );

        when(tutorialRepository.findById(id))
                .thenReturn(Optional.of(tutorial));

        TutorialResponse result =
                tutorialService.getTutorialById(id);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.title())
                .isEqualTo("Spring Boot");
        assertThat(result.description())
                .isEqualTo("Spring Boot Description");
        assertThat(result.published()).isTrue();

        verify(tutorialRepository).findById(id);
    }


    @Test
    void shouldThrowResourceNotFoundWhenTutorialDoesNotExist() {

        long id = 999L;

        when(tutorialRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> tutorialService.getTutorialById(id)
        )
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage(
                "Tutorial not found with id: " + id
        );

        verify(tutorialRepository).findById(id);
    }


    // ---------------------------------------------------------
    // FIND BY PUBLISHED
    // ---------------------------------------------------------

    @Test
    void shouldReturnPublishedTutorials() {

        List<Tutorial> tutorials = List.of(
                new Tutorial(
                        1L,
                        "Tutorial 1",
                        "Description 1",
                        true
                ),
                new Tutorial(
                        2L,
                        "Tutorial 2",
                        "Description 2",
                        true
                )
        );

        when(tutorialRepository.findByPublished(true))
                .thenReturn(tutorials);

        List<TutorialResponse> result =
                tutorialService.findByPublished(true);

        assertThat(result).hasSize(2);

        assertThat(result)
                .allMatch(TutorialResponse::published);

        verify(tutorialRepository)
                .findByPublished(true);
    }


    @Test
    void shouldReturnUnpublishedTutorials() {

        List<Tutorial> tutorials = List.of(
                new Tutorial(
                        1L,
                        "Draft Tutorial",
                        "Draft Description",
                        false
                )
        );

        when(tutorialRepository.findByPublished(false))
                .thenReturn(tutorials);

        List<TutorialResponse> result =
                tutorialService.findByPublished(false);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).published())
                .isFalse();

        verify(tutorialRepository)
                .findByPublished(false);
    }


    @Test
    void shouldReturnEmptyListWhenNoPublishedTutorialExists() {

        when(tutorialRepository.findByPublished(true))
                .thenReturn(Collections.emptyList());

        List<TutorialResponse> result =
                tutorialService.findByPublished(true);

        assertThat(result).isEmpty();

        verify(tutorialRepository)
                .findByPublished(true);
    }


    // ---------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------

    @Test
    void shouldCreateTutorial() {

        TutorialRequest request =
                new TutorialRequest(
                        "JUnit Mockito",
                        "Service unit test tutorial",
                        true
                );

        Tutorial savedTutorial =
                new Tutorial(
                        1L,
                        "JUnit Mockito",
                        "Service unit test tutorial",
                        true
                );

        when(tutorialRepository.save(any(Tutorial.class)))
                .thenReturn(savedTutorial);

        TutorialResponse result =
                tutorialService.createTutorial(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title())
                .isEqualTo("JUnit Mockito");
        assertThat(result.description())
                .isEqualTo("Service unit test tutorial");
        assertThat(result.published()).isTrue();

        ArgumentCaptor<Tutorial> tutorialCaptor =
                ArgumentCaptor.forClass(Tutorial.class);

        verify(tutorialRepository)
                .save(tutorialCaptor.capture());

        Tutorial tutorialPassedToRepository =
                tutorialCaptor.getValue();

        assertThat(tutorialPassedToRepository.getTitle())
                .isEqualTo(request.title());

        assertThat(tutorialPassedToRepository.getDescription())
                .isEqualTo(request.description());

        assertThat(tutorialPassedToRepository.isPublished())
                .isEqualTo(request.published());
    }


    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------

    @Test
    void shouldUpdateExistingTutorial() {

        long id = 1L;

        Tutorial existingTutorial =
                new Tutorial(
                        id,
                        "Old Title",
                        "Old Description",
                        false
                );

        TutorialRequest request =
                new TutorialRequest(
                        "Updated Title",
                        "Updated Description",
                        true
                );

        Tutorial savedTutorial =
                new Tutorial(
                        id,
                        "Updated Title",
                        "Updated Description",
                        true
                );

        when(tutorialRepository.findById(id))
                .thenReturn(Optional.of(existingTutorial));

        when(tutorialRepository.save(any(Tutorial.class)))
                .thenReturn(savedTutorial);

        TutorialResponse result =
                tutorialService.updateTutorial(id, request);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.title())
                .isEqualTo("Updated Title");
        assertThat(result.description())
                .isEqualTo("Updated Description");
        assertThat(result.published()).isTrue();

        ArgumentCaptor<Tutorial> tutorialCaptor =
                ArgumentCaptor.forClass(Tutorial.class);

        verify(tutorialRepository)
                .save(tutorialCaptor.capture());

        Tutorial tutorialPassedToRepository =
                tutorialCaptor.getValue();

        assertThat(tutorialPassedToRepository.getId())
                .isEqualTo(id);

        assertThat(tutorialPassedToRepository.getTitle())
                .isEqualTo(request.title());

        assertThat(tutorialPassedToRepository.getDescription())
                .isEqualTo(request.description());

        assertThat(tutorialPassedToRepository.isPublished())
                .isEqualTo(request.published());

        verify(tutorialRepository).findById(id);
    }


    @Test
    void shouldThrowResourceNotFoundWhenUpdatingMissingTutorial() {

        long id = 999L;

        TutorialRequest request =
                new TutorialRequest(
                        "Updated Title",
                        "Updated Description",
                        true
                );

        when(tutorialRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> tutorialService.updateTutorial(id, request)
        )
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage(
                "Tutorial not found with id: " + id
        );

        verify(tutorialRepository).findById(id);

        verify(tutorialRepository, never())
                .save(any(Tutorial.class));
    }


    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------

    @Test
    void shouldDeleteExistingTutorial() {

        long id = 1L;

        Tutorial tutorial =
                new Tutorial(
                        id,
                        "Delete Tutorial",
                        "Delete Description",
                        false
                );

        when(tutorialRepository.findById(id))
                .thenReturn(Optional.of(tutorial));

        tutorialService.deleteTutorial(id);

        verify(tutorialRepository).findById(id);
        verify(tutorialRepository).delete(tutorial);
    }


    @Test
    void shouldThrowResourceNotFoundWhenDeletingMissingTutorial() {

        long id = 999L;

        when(tutorialRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> tutorialService.deleteTutorial(id)
        )
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage(
                "Tutorial not found with id: " + id
        );

        verify(tutorialRepository).findById(id);

        verify(tutorialRepository, never())
                .delete(any(Tutorial.class));
    }


    // ---------------------------------------------------------
    // DELETE ALL
    // ---------------------------------------------------------

    @Test
    void shouldDeleteAllTutorials() {

        tutorialService.deleteAllTutorials();

        verify(tutorialRepository).deleteAll();

        verifyNoMoreInteractions(tutorialRepository);
    }
}