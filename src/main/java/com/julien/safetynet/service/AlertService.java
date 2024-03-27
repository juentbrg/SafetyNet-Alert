package com.julien.safetynet.service;

import com.julien.safetynet.DTO.PersonCoveredDTO;
import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.pojo.Child;
import com.julien.safetynet.pojo.Hearth;
import com.julien.safetynet.pojo.Inhabitant;
import com.julien.safetynet.pojo.InhabitantWithFireStation;
import com.julien.safetynet.repository.FireStationRepository;
import com.julien.safetynet.repository.MedicalRecordRepository;
import com.julien.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AlertService {
    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public AlertService(PersonRepository personRepository, FireStationRepository fireStationRepository, MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<Child> findChildByAddress(String address){
        List<PersonEntity> personList = personRepository.findAllPersonByAddress(address);
        List<Child> childList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (PersonEntity person : personList){
            Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
            LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            int age = Period.between(birthdate, currentDate).getYears();

            if (age < 18) {
                Child child = new Child();
                child.setFirstName(person.getFirstName());
                child.setLastName(person.getLastName());
                child.setAge(age);
                childList.add(child);
            }
        }

        for (Child child : childList) {
            List<PersonCoveredDTO> otherPersonInHousehold = new ArrayList<>();
            for (PersonEntity person : personList){
                if (!Objects.equals(person.getFirstName(), child.getFirstName())){
                    otherPersonInHousehold.add(new PersonCoveredDTO(person));
                }
            }
            child.setOtherPersonInHousehold(otherPersonInHousehold);
        }

        if (!childList.isEmpty()) {
            return childList;
        } else {
            return Collections.emptyList();
        }
    }

    public List<String> getPhoneNumberResidentServed(int stationNumber) {
        List<String> phoneNumberResidentCoveredList = new ArrayList<>();
        List<FireStationEntity> fireStationList = fireStationRepository.findAllFireStationByStationNumber(stationNumber);
        Set<PersonEntity> personList = new HashSet<>();


        for (FireStationEntity fireStation : fireStationList) {
            String address = fireStation.getAddress();
            List<PersonEntity> listPersonsEntity = personRepository.findAllPersonByAddress(address);
            personList.addAll(listPersonsEntity);
        }

        for (PersonEntity person : personList) {
            phoneNumberResidentCoveredList.add(person.getPhone());
        }

        if (!phoneNumberResidentCoveredList.isEmpty()){
            return phoneNumberResidentCoveredList;
        } else {
            return Collections.emptyList();
        }
    }

    public InhabitantWithFireStation getInhabitantAndFireStationByAddress(String address){
        List<PersonEntity> personList = personRepository.findAllPersonByAddress(address);
        Optional<FireStationEntity> fireStationOpt = fireStationRepository.findFireStationByAddress(address);
        InhabitantWithFireStation inhabitantWithFireStation = new InhabitantWithFireStation();
        List<Inhabitant> inhabitantList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (PersonEntity person : personList) {
            Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
            if (medicalRecordOpt.isPresent()) {
                LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                int age = Period.between(birthdate, currentDate).getYears();
                Inhabitant inhabitant = new Inhabitant();
                inhabitant.setFirstName(person.getFirstName());
                inhabitant.setLastName(person.getLastName());
                inhabitant.setPhoneNumber(person.getPhone());
                inhabitant.setAge(age);
                inhabitant.setMedication(medicalRecordOpt.get().getMedications());
                inhabitant.setAllergies(medicalRecordOpt.get().getAllergies());
                inhabitantList.add(inhabitant);
            }
        }
        if (fireStationOpt.isPresent() && !inhabitantList.isEmpty()) {
            inhabitantWithFireStation.setInhabitantList(inhabitantList);
            inhabitantWithFireStation.setStationNumber(fireStationOpt.get().getStation());
            return inhabitantWithFireStation;
        } else {
            return null;
        }
    }

    public List<Hearth> getHearthByStationNumber(List<Integer> stations) {
        List<Hearth> hearthList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int stationNumber : stations) {
            List<FireStationEntity> allFireStationByStationNumber = fireStationRepository.findAllFireStationByStationNumber(stationNumber);
            for (FireStationEntity fireStation : allFireStationByStationNumber) {
                boolean addressProcessed = false;
                for (Hearth hearth : hearthList) {
                    if (hearth.getAddress().equals(fireStation.getAddress())) {
                        addressProcessed = true;
                        break;
                    }
                }
                if (!addressProcessed) {
                    List<PersonEntity> personList = personRepository.findAllPersonByAddress(fireStation.getAddress());
                    List<Inhabitant> inhabitantList = new ArrayList<>();
                    Hearth hearth = new Hearth();
                    hearth.setAddress(fireStation.getAddress());
                    for (PersonEntity person : personList) {
                        Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                        if (medicalRecordOpt.isPresent()) {
                            LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                            int age = Period.between(birthdate, currentDate).getYears();
                            Inhabitant inhabitant = new Inhabitant();
                            inhabitant.setFirstName(person.getFirstName());
                            inhabitant.setLastName(person.getLastName());
                            inhabitant.setPhoneNumber(person.getPhone());
                            inhabitant.setAge(age);
                            inhabitant.setMedication(medicalRecordOpt.get().getMedications());
                            inhabitant.setAllergies(medicalRecordOpt.get().getAllergies());
                            inhabitantList.add(inhabitant);
                        }
                    }
                    hearth.setInhabitantList(inhabitantList);
                    hearthList.add(hearth);
                }
            }
        }
        if (hearthList.isEmpty()){
            return Collections.emptyList();
        }
        return hearthList;
    }
}
