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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FireStationRepository {

    private static final Logger logger = LoggerFactory.getLogger(FireStationRepository.class);

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

    private void saveAllFireStations(Data data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), data);
        } catch (IOException e) {
            logger.error("Error saving fireStations to JSON file", e);
        }
    }

    public Optional<FireStationEntity> findFireStationByAddress(String address) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return Optional.empty();
        } else {
            return data.getFirestations().stream()
                    .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                    .findFirst();
        }
    }

    public List<FireStationEntity> findAllFireStationByStationNumber(int stationNumber) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return null;
        } else {
            return data.getFirestations().stream()
                    .filter(fireStation -> fireStation.getStation().equals(stationNumber)).toList();
        }
    }

    public void addFireStation(FireStationEntity fireStation) {
        Data data = loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getFirestations().add(fireStation);
            saveAllFireStations(data);
        } catch (Exception e) {
            logger.error("Error saving fireStation to JSON file", e);
        }
    }

    public void updateFireStation(String address, FireStationEntity updatedFireStation) {
        Data data = loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            List<FireStationEntity> fireStations = data.getFirestations();
            fireStations.removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
            fireStations.add(updatedFireStation);
            saveAllFireStations(data);
        } catch (Exception e) {
            logger.error("Error updating fireStation to JSON file", e);
        }
    }

    public void deleteFireStation(String address) {
        Data data = loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getFirestations().removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
            saveAllFireStations(data);
        } catch (Exception e) {
            logger.error("Error deleting fireStation from JSON file", e);
        }
    }
}
