package com.julien.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.PersonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Value("${json.filePath}")
    private String jsonFilePath;

    public List<PersonEntity> loadAllPersons() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);

        if (!file.exists()) {
            logger.error("JSON file not found");
        }

        try {
            Data data = objectMapper.readValue(file, Data.class);
            return data.getPersons();
        } catch (Exception e) {
            logger.error("Error loading persons from JSON file", e);
            return new ArrayList<>();
        }
    }

    private void saveAllPersons(List<PersonEntity> persons) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), persons);
        } catch (IOException e) {
            logger.error("Error saving persons to JSON file", e);
        }
    }

    public Optional<PersonEntity> findPersonByFullName(String firstName, String lastName) {
        List<PersonEntity> persons = loadAllPersons();
        return persons.stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }

    public void addPerson(PersonEntity newPerson) {
        List<PersonEntity> persons = loadAllPersons();
        persons.add(newPerson);
        saveAllPersons(persons);
    }

    public void updatePerson(String firstName, String lastName, PersonEntity updatedPerson) {
        List<PersonEntity> persons = loadAllPersons();
        persons.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
        persons.add(updatedPerson);
        saveAllPersons(persons);
    }

    public void deletePerson(String firstName, String lastName) {
        List<PersonEntity> persons = loadAllPersons();
        persons.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
        saveAllPersons(persons);
    }
}

