package com.aydindemir.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aydindemir.model.Person;
import com.aydindemir.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/persons")
@Tag(name = "Person API", description = "Person işlemleri")
public class PersonController {

    private static final Logger logger =
            LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "Tüm person kayıtlarını getirir")
    public List<Person> getPersons() {

        logger.trace("TRACE log from {}", PersonController.class.getSimpleName());
        logger.debug("DEBUG log from {}", PersonController.class.getSimpleName());
        logger.info("INFO log from {}", PersonController.class.getSimpleName());
        logger.warn("WARN log from {}", PersonController.class.getSimpleName());

        return personService.getPersons();
    }
    
    @GetMapping("/error-test")
    @Operation(summary = "Exception logging testi")
    public String errorTest() {

        try {
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            logger.error("Person işlemi sırasında hata oluştu", e);
        }

        return "Error logging test completed";
    }
}