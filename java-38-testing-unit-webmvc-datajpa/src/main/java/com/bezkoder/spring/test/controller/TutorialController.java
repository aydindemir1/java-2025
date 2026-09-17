package com.bezkoder.spring.test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.test.dto.TutorialRequest;
import com.bezkoder.spring.test.dto.TutorialResponse;
import com.bezkoder.spring.test.service.TutorialService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

    private final TutorialService tutorialService;

    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping
    public ResponseEntity<List<TutorialResponse>> getAllTutorials(
            @RequestParam(required = false) String title) {

        return ResponseEntity.ok(
                tutorialService.getAllTutorials(title)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorialResponse> getTutorialById(
            @PathVariable long id) {

        return ResponseEntity.ok(
                tutorialService.getTutorialById(id)
        );
    }

    @GetMapping("/published/{flag}")
    public ResponseEntity<List<TutorialResponse>> findByPublished(
            @PathVariable boolean flag) {

        return ResponseEntity.ok(
                tutorialService.findByPublished(flag)
        );
    }

    @PostMapping
    public ResponseEntity<TutorialResponse> createTutorial(
            @Valid @RequestBody TutorialRequest request) {

        TutorialResponse createdTutorial =
                tutorialService.createTutorial(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTutorial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorialResponse> updateTutorial(
            @PathVariable long id,
            @Valid @RequestBody TutorialRequest request) {

        return ResponseEntity.ok(
                tutorialService.updateTutorial(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorial(
            @PathVariable long id) {

        tutorialService.deleteTutorial(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTutorials() {

        tutorialService.deleteAllTutorials();

        return ResponseEntity.noContent().build();
    }
}