package com.bezkoder.spring.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bezkoder.spring.test.model.Tutorial;
import com.bezkoder.spring.test.repository.TutorialRepository;

@DataJpaTest
class TutorialRepositoryTests {

    @Autowired
    private TutorialRepository tutorialRepository;


    @Test
    void shouldSaveTutorial() {

        Tutorial tutorial = new Tutorial(
                "Spring Boot JPA",
                "Repository save test",
                true
        );

        Tutorial savedTutorial =
                tutorialRepository.save(tutorial);

        assertThat(savedTutorial.getId()).isPositive();
        assertThat(savedTutorial.getTitle())
                .isEqualTo("Spring Boot JPA");
        assertThat(savedTutorial.getDescription())
                .isEqualTo("Repository save test");
        assertThat(savedTutorial.isPublished())
                .isTrue();
    }


    @Test
    void shouldFindTutorialById() {

        Tutorial tutorial = tutorialRepository.save(
                new Tutorial(
                        "Spring Boot",
                        "Find by id test",
                        false
                )
        );

        Tutorial result = tutorialRepository
                .findById(tutorial.getId())
                .orElseThrow();

        assertThat(result.getId())
                .isEqualTo(tutorial.getId());

        assertThat(result.getTitle())
                .isEqualTo("Spring Boot");

        assertThat(result.getDescription())
                .isEqualTo("Find by id test");

        assertThat(result.isPublished())
                .isFalse();
    }


    @Test
    void shouldFindTutorialsByPublishedTrue() {

        tutorialRepository.saveAll(
                List.of(
                        new Tutorial(
                                "Tutorial 1",
                                "Description 1",
                                true
                        ),
                        new Tutorial(
                                "Tutorial 2",
                                "Description 2",
                                false
                        ),
                        new Tutorial(
                                "Tutorial 3",
                                "Description 3",
                                true
                        )
                )
        );

        List<Tutorial> result =
                tutorialRepository.findByPublished(true);

        assertThat(result).hasSize(2);

        assertThat(result)
                .allMatch(Tutorial::isPublished);
    }


    @Test
    void shouldFindTutorialsByPublishedFalse() {

        tutorialRepository.saveAll(
                List.of(
                        new Tutorial(
                                "Tutorial 1",
                                "Description 1",
                                true
                        ),
                        new Tutorial(
                                "Tutorial 2",
                                "Description 2",
                                false
                        ),
                        new Tutorial(
                                "Tutorial 3",
                                "Description 3",
                                false
                        )
                )
        );

        List<Tutorial> result =
                tutorialRepository.findByPublished(false);

        assertThat(result).hasSize(2);

        assertThat(result)
                .allMatch(tutorial ->
                        !tutorial.isPublished()
                );
    }


    @Test
    void shouldReturnEmptyListWhenNoPublishedTutorialExists() {

        tutorialRepository.saveAll(
                List.of(
                        new Tutorial(
                                "Tutorial 1",
                                "Description 1",
                                false
                        ),
                        new Tutorial(
                                "Tutorial 2",
                                "Description 2",
                                false
                        )
                )
        );

        List<Tutorial> result =
                tutorialRepository.findByPublished(true);

        assertThat(result).isEmpty();
    }


    @Test
    void shouldFindTutorialsContainingTitle() {

        tutorialRepository.saveAll(
                List.of(
                        new Tutorial(
                                "Spring Boot Testing",
                                "Description 1",
                                true
                        ),
                        new Tutorial(
                                "Spring Data JPA",
                                "Description 2",
                                true
                        ),
                        new Tutorial(
                                "Java Basics",
                                "Description 3",
                                false
                        )
                )
        );

        List<Tutorial> result =
                tutorialRepository
                        .findByTitleContaining("Spring");

        assertThat(result).hasSize(2);

        assertThat(result)
                .extracting(Tutorial::getTitle)
                .containsExactlyInAnyOrder(
                        "Spring Boot Testing",
                        "Spring Data JPA"
                );
    }


    @Test
    void shouldReturnEmptyListWhenTitleDoesNotMatch() {

        tutorialRepository.saveAll(
                List.of(
                        new Tutorial(
                                "Spring Boot",
                                "Description 1",
                                true
                        ),
                        new Tutorial(
                                "Java JPA",
                                "Description 2",
                                false
                        )
                )
        );

        List<Tutorial> result =
                tutorialRepository
                        .findByTitleContaining("MongoDB");

        assertThat(result).isEmpty();
    }


    @Test
    void shouldDeleteTutorial() {

        Tutorial tutorial = tutorialRepository.save(
                new Tutorial(
                        "Delete Tutorial",
                        "Delete repository test",
                        false
                )
        );

        long id = tutorial.getId();

        tutorialRepository.deleteById(id);

        assertThat(
                tutorialRepository.findById(id)
        ).isEmpty();
    }


    @Test
    void shouldDeleteAllTutorials() {

        tutorialRepository.saveAll(
                List.of(
                        new Tutorial(
                                "Tutorial 1",
                                "Description 1",
                                true
                        ),
                        new Tutorial(
                                "Tutorial 2",
                                "Description 2",
                                false
                        )
                )
        );

        tutorialRepository.deleteAll();

        assertThat(
                tutorialRepository.findAll()
        ).isEmpty();
    }
}