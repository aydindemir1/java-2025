package com.bezkoder.spring.data.mongodb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.data.mongodb.dto.TutorialRequest;
import com.bezkoder.spring.data.mongodb.dto.TutorialResponse;
import com.bezkoder.spring.data.mongodb.service.TutorialService;

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
            @PathVariable String id) {

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
            @PathVariable String id,
            @Valid @RequestBody TutorialRequest request) {

        return ResponseEntity.ok(
                tutorialService.updateTutorial(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorial(
            @PathVariable String id) {

        tutorialService.deleteTutorial(id);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteAllTutorials() {

        tutorialService.deleteAllTutorials();

        return ResponseEntity.noContent().build();
    }
}