package com.julien.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.FireStationEntity;
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
public class FireStationRepository {

    private static final Logger logger = LoggerFactory.getLogger(FireStationRepository.class);

    @Value("${json.filePath}")
    private String jsonFilePath;

    public List<FireStationEntity> loadAllFireStations() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);

        if (!file.exists()) {
            logger.error("JSON file not found");
        }

        try {
            Data data = objectMapper.readValue(file, Data.class);
            return data.getFirestations();
        } catch (Exception e) {
            logger.error("Error loading fire stations from JSON file", e);
            return new ArrayList<>();
        }
    }

    private void saveAllFireStations(List<FireStationEntity> fireStations) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), fireStations);
        } catch (IOException e) {
            logger.error("Error saving fireStations to JSON file", e);
        }
    }

    public Optional<FireStationEntity> findFireStationByAddress(String address) {
        List<FireStationEntity> fireStations = loadAllFireStations();
        return fireStations.stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst();
    }

    public void addFireStation(FireStationEntity fireStation) {
        List<FireStationEntity> fireStations = loadAllFireStations();
        fireStations.add(fireStation);
        saveAllFireStations(fireStations);
    }

    public void updateFireStation(String address, FireStationEntity updatedFireStation) {
        List<FireStationEntity> fireStations = loadAllFireStations();
        fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
        fireStations.add(updatedFireStation);
        saveAllFireStations(fireStations);
    }

    public void deleteFireStation(String address) {
        List<FireStationEntity> fireStations = loadAllFireStations();
        fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
        saveAllFireStations(fireStations);
    }
}
