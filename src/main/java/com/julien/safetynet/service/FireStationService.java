package com.julien.safetynet.service;

import com.julien.safetynet.DTO.PersonCoveredDTO;
import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.pojo.PersonCovered;
import com.julien.safetynet.repository.FireStationRepository;
import com.julien.safetynet.repository.MedicalRecordRepository;
import com.julien.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    public FireStationEntity getFireStationByAddress(String address) {
        Optional<FireStationEntity> fireStationOpt = fireStationRepository.findFireStationByAddress(address);
        try {
            if (fireStationOpt.isPresent()) {
                return fireStationOpt.get();
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }

    public boolean addFireStation(FireStationEntity fireStationEntity) {
        try {
            fireStationRepository.addFireStation(fireStationEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateFireStation(String address, FireStationEntity updatedFireStation) {
        Optional<FireStationEntity> fireStationOpt = fireStationRepository.findFireStationByAddress(address);

        try {
            if (fireStationOpt.isPresent()) {
                FireStationEntity existingFireStation = fireStationOpt.get();

                if (null != updatedFireStation.getStation()) {
                    existingFireStation.setStation(updatedFireStation.getStation());
                }

                fireStationRepository.updateFireStation(address, existingFireStation);
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    public boolean deleteFireStation(String address) {
        try {
            fireStationRepository.deleteFireStation(address);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
