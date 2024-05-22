package com.julien.safetynet.repository;

import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.utils.JsonDataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FireStationRepository {

    private static final Logger logger = LoggerFactory.getLogger(FireStationRepository.class);

    private final JsonDataUtils jsonDataUtils;

    public FireStationRepository(JsonDataUtils jsonDataUtils) {
        this.jsonDataUtils = jsonDataUtils;
    }

    public Optional<FireStationEntity> findFireStationByAddress(String address) {
        Data data = jsonDataUtils.loadData();
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
        Data data = jsonDataUtils.loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return null;
        } else {
            return data.getFirestations().stream()
                    .filter(fireStation -> fireStation.getStation().equals(stationNumber)).toList();
        }
    }

    public void addFireStation(FireStationEntity fireStation) {
        Data data = jsonDataUtils.loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            List<FireStationEntity> fireStationData = data.getFirestations();
            fireStationData.add(fireStation);
            jsonDataUtils.saveAllData(data);
        } catch (Exception e) {
            logger.error("Error saving fireStation to JSON file", e);
        }
    }

    public void updateFireStation(String address, FireStationEntity updatedFireStation) {
        Data data = jsonDataUtils.loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            List<FireStationEntity> fireStations = data.getFirestations();
            fireStations.removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
            fireStations.add(updatedFireStation);
            jsonDataUtils.saveAllData(data);
        } catch (Exception e) {
            logger.error("Error updating fireStation to JSON file", e);
        }
    }

    public void deleteFireStation(String address) {
        Data data = jsonDataUtils.loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getFirestations().removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
            jsonDataUtils.saveAllData(data);
        } catch (Exception e) {
            logger.error("Error deleting fireStation from JSON file", e);
        }
    }
}
