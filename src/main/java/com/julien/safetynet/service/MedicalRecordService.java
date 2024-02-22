package com.julien.safetynet.service;

import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecordEntity getMedicalRecord(String firstName, String lastName) {
        Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);
        return medicalRecordOpt.orElse(null);
    }

    public boolean addMedicalRecord(MedicalRecordEntity medicalRecordEntity) {
        try {
            medicalRecordRepository.addMedicalRecord(medicalRecordEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateMedicalRecord(String firstName, String lastName, MedicalRecordEntity updatedMedicalRecord) {
        Optional<MedicalRecordEntity> medicalRecordopt = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);
        if (medicalRecordopt.isPresent()) {
            MedicalRecordEntity existingMedicalRecord = medicalRecordopt.get();

            if (!updatedMedicalRecord.getMedications().isEmpty()) {
                existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
            }
            if (!updatedMedicalRecord.getAllergies().isEmpty()) {
                existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
            }

            medicalRecordRepository.updateMedicalRecord(firstName, lastName, existingMedicalRecord);
            return true;
        }

        return false;
    }

    public boolean deleteMedicalRecord(String firstName, String lastName){
        try {
            medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
