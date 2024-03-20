package com.julien.safetynet.service;

import com.julien.safetynet.DTO.PersonDTO;
import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.pojo.PersonCovered;
import com.julien.safetynet.repository.FireStationRepository;
import com.julien.safetynet.repository.MedicalRecordRepository;
import com.julien.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public FireStationEntity getFireStationByAddress(String address) {
        Optional<FireStationEntity> fireStationOpt = fireStationRepository.findFireStationByAddress(address);
        return fireStationOpt.orElse(null);
    }

    public PersonCovered getPersonCovered(int stationNumber) {
        PersonCovered personCovered = new PersonCovered();
        List<FireStationEntity> fireStationList = fireStationRepository.findAllFireStationByStationNumber(stationNumber);
        Set<PersonDTO> personDTOSet = new HashSet<>();
        int childNumber = 0;
        int adultNumber = 0;
        LocalDate currentDate = LocalDate.now();


        for (FireStationEntity fireStation : fireStationList) {
            String address = fireStation.getAddress();
            List<PersonEntity> listPersonsEntity = personRepository.findAllPersonByAddress(address);
            for (PersonEntity person : listPersonsEntity) {
                PersonDTO personDTO = new PersonDTO(person);
                personDTOSet.add(personDTO);
            }
        }

        for (PersonDTO personDTO : personDTOSet) {
            Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(personDTO.getFirstName(), personDTO.getLastName());
            if (medicalRecordOpt.isPresent()){
                LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                if (Period.between(birthdate, currentDate).getYears() < 18) {
                    childNumber++;
                } else {
                    adultNumber++;
                }
            }
        }

        if (!personDTOSet.isEmpty()) {
            personCovered.setPersonCovered(personDTOSet.stream().toList());
            personCovered.setAdultNumber(adultNumber);
            personCovered.setChildrenNumber(childNumber);

            return personCovered;
        } else {
            return null;
        }
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

        if (fireStationOpt.isPresent()) {
            FireStationEntity existingFireStation = fireStationOpt.get();

            if (null != updatedFireStation.getStation()) {
                existingFireStation.setStation(updatedFireStation.getStation());
            }

            fireStationRepository.updateFireStation(address, existingFireStation);
            return true;
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
