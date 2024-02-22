package com.julien.safetynet.service;

import com.julien.safetynet.entity.FirestationEntity;
import com.julien.safetynet.repository.FirestationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public FirestationEntity getFirestationByAddress(String address) {
        Optional<FirestationEntity> firestationOpt = firestationRepository.findFirestationByAddress(address);
        return firestationOpt.orElse(null);
    }

    public boolean addFirestation(FirestationEntity firestationEntity) {
        try {
            firestationRepository.addFirestation(firestationEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateFirestation(String address, FirestationEntity updatedFirestation) {
        Optional<FirestationEntity> firestationOpt = firestationRepository.findFirestationByAddress(address);

        if (firestationOpt.isPresent()) {
            FirestationEntity existingFirestation = firestationOpt.get();

            if (null != updatedFirestation.getStation()) {
                existingFirestation.setStation(updatedFirestation.getStation());
            }

            firestationRepository.updateFirestation(address, existingFirestation);
            return true;
        }

        return false;
    }

    public boolean deleteFirestation(String address) {
        try {
            firestationRepository.deleteFirestation(address);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
