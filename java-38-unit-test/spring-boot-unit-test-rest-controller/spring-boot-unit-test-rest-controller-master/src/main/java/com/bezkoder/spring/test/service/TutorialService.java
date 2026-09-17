package com.bezkoder.spring.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bezkoder.spring.test.dto.TutorialRequest;
import com.bezkoder.spring.test.dto.TutorialResponse;
import com.bezkoder.spring.test.exception.ResourceNotFoundException;
import com.bezkoder.spring.test.model.Tutorial;
import com.bezkoder.spring.test.repository.TutorialRepository;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public List<TutorialResponse> getAllTutorials(String title) {

        List<Tutorial> tutorials;

        if (title == null || title.isBlank()) {
            tutorials = tutorialRepository.findAll();
        } else {
            tutorials = tutorialRepository.findByTitleContaining(title);
        }

        return tutorials.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TutorialResponse getTutorialById(long id) {

        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutorial not found with id: " + id
                        )
                );

        return mapToResponse(tutorial);
    }

    public List<TutorialResponse> findByPublished(boolean published) {

        return tutorialRepository.findByPublished(published)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TutorialResponse createTutorial(TutorialRequest request) {

        Tutorial tutorial = new Tutorial(
                request.title(),
                request.description(),
                request.published()
        );

        Tutorial savedTutorial =
                tutorialRepository.save(tutorial);

        return mapToResponse(savedTutorial);
    }

    public TutorialResponse updateTutorial(
            long id,
            TutorialRequest request) {

        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutorial not found with id: " + id
                        )
                );

        tutorial.setTitle(request.title());
        tutorial.setDescription(request.description());
        tutorial.setPublished(request.published());

        Tutorial updatedTutorial =
                tutorialRepository.save(tutorial);

        return mapToResponse(updatedTutorial);
    }

    public void deleteTutorial(long id) {

        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutorial not found with id: " + id
                        )
                );

        tutorialRepository.delete(tutorial);
    }

    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    private TutorialResponse mapToResponse(Tutorial tutorial) {

        return new TutorialResponse(
                tutorial.getId(),
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.isPublished()
        );
    }
}