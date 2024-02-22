package com.julien.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julien.safetynet.entity.FirestationEntity;
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
public class FirestationRepository {

    private static final Logger logger = LoggerFactory.getLogger(FirestationRepository.class);

    @Value("${json.filePath}")
    private String jsonFilePath;

    private List<FirestationEntity> loadAllFirestations() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(jsonFilePath), new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Error loading firestations from JSON file", e);
            return new ArrayList<>();
        }
    }

    private void saveAllFirestations(List<FirestationEntity> firestations) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), firestations);
        } catch (IOException e) {
            logger.error("Error saving firestations to JSON file", e);
        }
    }

    public List<FirestationEntity> findAllFirestations() {
        return loadAllFirestations();
    }

    public Optional<FirestationEntity> findFirestationByAddress(String address) {
        List<FirestationEntity> firestations = loadAllFirestations();
        return firestations.stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();
    }

    public void addFirestation(FirestationEntity newFirestation) {
        List<FirestationEntity> firestations = loadAllFirestations();
        firestations.add(newFirestation);
        saveAllFirestations(firestations);
    }

    public void updateFirestation(String address, FirestationEntity updatedFirestation) {
        List<FirestationEntity> firestations = loadAllFirestations();
        firestations.removeIf(firestation -> firestation.getAddress().equals(address));
        firestations.add(updatedFirestation);
        saveAllFirestations(firestations);
    }

    public void deleteFirestation(String address) {
        List<FirestationEntity> firestations = loadAllFirestations();
        firestations.removeIf(firestation -> firestation.getAddress().equals(address));
        saveAllFirestations(firestations);
    }
}
