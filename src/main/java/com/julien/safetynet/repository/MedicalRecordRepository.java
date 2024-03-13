package com.julien.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.MedicalRecordEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MedicalRecordRepository {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

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

    private void saveAllMedicalRecords(Data data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), data);
        } catch (IOException e) {
            logger.error("Error saving medical records to JSON file", e);
        }
    }

    public Optional<MedicalRecordEntity> findMedicalRecordByFullName(String firstName, String lastName) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return Optional.empty();
        } else {
            return data.getMedicalrecords().stream()
                    .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                    .findFirst();
        }
    }

    public void addMedicalRecord(MedicalRecordEntity newMedicalRecord) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return;
        }
        data.getMedicalrecords().add(newMedicalRecord);
        saveAllMedicalRecords(data);
    }

    public void updateMedicalRecord(String firstName, String lastName, MedicalRecordEntity updatedMedicalRecord) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return;
        }
        List<MedicalRecordEntity> medicalRecords = data.getMedicalrecords();
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        medicalRecords.add(updatedMedicalRecord);
        saveAllMedicalRecords(data);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        Data data = loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return;
        }
        data.getMedicalrecords().removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        saveAllMedicalRecords(data);
    }
}

