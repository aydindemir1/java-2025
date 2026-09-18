package com.aydindemir.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aydindemir.model.Person;



@Service
public class PersonService {

    private static final Logger logger =
            LoggerFactory.getLogger(PersonService.class);

    public List<Person> getPersons() {

        logger.debug("Person listesi hazırlanıyor");

        List<Person> persons = List.of(
                new Person(1L, "Aydın"),
                new Person(2L, "Mehmet")
        );

        logger.info("{} adet person bulundu", persons.size());

        return persons;
    }
}