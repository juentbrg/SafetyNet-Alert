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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Value("${json.filePath}")
    private String jsonFilePath;

    private Data loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);

        if (!file.exists()) {
            logger.error("JSON file not found");
        }

        try {
            return objectMapper.readValue(file, Data.class);
        } catch (Exception e) {
            logger.error("Error loading data from JSON file", e);
            return null;
        }
    }

    private void saveAllPersons(Data data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), data);
        } catch (IOException e) {
            logger.error("Error saving persons to JSON file", e);
        }
    }

    public List<PersonEntity> findAllPersonByCity(String city) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return Collections.emptyList();
        } else {
            return data.getPersons().stream()
                    .filter(person -> person.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }
    }

    public Optional<PersonEntity> findPersonByFullName(String firstName, String lastName) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return Optional.empty();
        } else {
            return data.getPersons().stream()
                    .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                    .findFirst();
        }
    }

    public List<PersonEntity> findAllPersonByFullName(String firstName, String lastName) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return Collections.emptyList();
        } else {
            return data.getPersons().stream()
                    .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                    .collect(Collectors.toList());
        }
    }

    public List<PersonEntity> findAllPersonByAddress(String address) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return null;
        } else {
            return data.getPersons().stream()
                    .filter(person -> person.getAddress().equalsIgnoreCase(address))
                    .toList();
        }
    }

    public void addPerson(PersonEntity newPerson) {
        Data data = loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getPersons().add(newPerson);
            saveAllPersons(data);
        } catch (Exception e) {
            logger.error("Error saving person to JSON file", e);
        }
    }

    public void updatePerson(String firstName, String lastName, PersonEntity updatedPerson) {
        Data data = loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            List<PersonEntity> persons = data.getPersons();
            persons.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName));
            persons.add(updatedPerson);
            saveAllPersons(data);
        } catch (Exception e) {
            logger.error("Error updating person to JSON file", e);
        }
    }

    public void deletePerson(String firstName, String lastName) {
        Data data = loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getPersons().removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName));
            saveAllPersons(data);
        } catch (Exception e) {
            logger.error("Error deleting person to JSON file", e);
        }
    }
}

