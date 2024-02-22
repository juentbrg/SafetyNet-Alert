package com.julien.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julien.safetynet.entity.MedicalRecordEntity;
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
public class MedicalRecordRepository {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

    @Value("${json.filePath}")
    private String jsonFilePath;

    private List<MedicalRecordEntity> loadAllMedicalRecords() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(jsonFilePath), new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Error loading medical records from JSON file", e);
            return new ArrayList<>();
        }
    }

    private void saveAllMedicalRecords(List<MedicalRecordEntity> medicalRecords) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), medicalRecords);
        } catch (IOException e) {
            logger.error("Error saving medical records to JSON file", e);
        }
    }

    public Optional<MedicalRecordEntity> findMedicalRecordByName(String firstName, String lastName) {
        List<MedicalRecordEntity> medicalRecords = loadAllMedicalRecords();
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst();
    }

    public void addMedicalRecord(MedicalRecordEntity newMedicalRecord) {
        List<MedicalRecordEntity> medicalRecords = loadAllMedicalRecords();
        medicalRecords.add(newMedicalRecord);
        saveAllMedicalRecords(medicalRecords);
    }

    public void updateMedicalRecord(String firstName, String lastName, MedicalRecordEntity updatedMedicalRecord) {
        List<MedicalRecordEntity> medicalRecords = loadAllMedicalRecords();
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        medicalRecords.add(updatedMedicalRecord);
        saveAllMedicalRecords(medicalRecords);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecordEntity> medicalRecords = loadAllMedicalRecords();
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        saveAllMedicalRecords(medicalRecords);
    }
}

