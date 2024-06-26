package com.julien.safetynet.repository;

import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.utils.JsonDataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MedicalRecordRepository {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

    private final JsonDataUtils jsonDataUtils;

    public MedicalRecordRepository(JsonDataUtils jsonDataUtils) {
        this.jsonDataUtils = jsonDataUtils;
    }

    public Optional<MedicalRecordEntity> findMedicalRecordByFullName(String firstName, String lastName) {
        Data data = jsonDataUtils.loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return Optional.empty();
        } else {
            return data.getMedicalrecords().stream()
                    .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName))
                    .findFirst();
        }
    }

    public List<MedicalRecordEntity> findAllMedicalRecordByFullName(String firstName, String lastName) {
        Data data = jsonDataUtils.loadData();
        if (data == null) {
            logger.error("Error loading data from JSON file");
            return null;
        } else {
            return data.getMedicalrecords().stream()
                    .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName))
                    .collect(Collectors.toList());
        }
    }

    public void addMedicalRecord(MedicalRecordEntity newMedicalRecord) {
        Data data = jsonDataUtils.loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getMedicalrecords().add(newMedicalRecord);
            jsonDataUtils.saveAllData(data);
        } catch (Exception e) {
            logger.error("Error saving medical record", e);
        }
    }

    public void updateMedicalRecord(String firstName, String lastName, MedicalRecordEntity updatedMedicalRecord) {
        Data data = jsonDataUtils.loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            List<MedicalRecordEntity> medicalRecords = data.getMedicalrecords();
            medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName));
            medicalRecords.add(updatedMedicalRecord);
            jsonDataUtils.saveAllData(data);
        } catch (Exception e) {
            logger.error("Error updating medical record", e);
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        Data data = jsonDataUtils.loadData();
        try {
            if (data == null) {
                throw new Exception("Error loading data from JSON file");
            }
            data.getMedicalrecords().removeIf(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName));
            jsonDataUtils.saveAllData(data);
        } catch (Exception e) {
            logger.error("Error deleting medical record", e);
        }
    }
}

