package com.julien.safetynet.service;

import com.julien.safetynet.DTO.PersonCoveredDTO;
import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.pojo.*;
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

    public PersonCovered getPersonCovered(int stationNumber) {
        PersonCovered personCovered = new PersonCovered();
        List<FireStationEntity> fireStationList = fireStationRepository.findAllFireStationByStationNumber(stationNumber);
        Set<PersonCoveredDTO> personCoveredDTOSet = new HashSet<>();
        int childNumber = 0;
        int adultNumber = 0;
        LocalDate currentDate = LocalDate.now();


        for (FireStationEntity fireStation : fireStationList) {
            String address = fireStation.getAddress();
            List<PersonEntity> listPersonsEntity = personRepository.findAllPersonByAddress(address);
            for (PersonEntity person : listPersonsEntity) {
                PersonCoveredDTO personCoveredDTO = new PersonCoveredDTO(person);
                personCoveredDTOSet.add(personCoveredDTO);
            }
        }

        for (PersonCoveredDTO personCoveredDTO : personCoveredDTOSet) {
            Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(personCoveredDTO.getFirstName(), personCoveredDTO.getLastName());
            if (medicalRecordOpt.isPresent()){
                LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                if (Period.between(birthdate, currentDate).getYears() < 18) {
                    childNumber++;
                } else {
                    adultNumber++;
                }
            }
        }

        if (!personCoveredDTOSet.isEmpty()) {
            personCovered.setPersonCovered(personCoveredDTOSet.stream().toList());
            personCovered.setAdultNumber(adultNumber);
            personCovered.setChildrenNumber(childNumber);

            return personCovered;
        } else {
            return null;
        }
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
        List<InhabitantWithPhone> inhabitantWithPhoneList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (PersonEntity person : personList) {
            Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
            if (medicalRecordOpt.isPresent()) {
                LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                int age = Period.between(birthdate, currentDate).getYears();
                InhabitantWithPhone inhabitantWithPhone = new InhabitantWithPhone();
                inhabitantWithPhone.setFirstName(person.getFirstName());
                inhabitantWithPhone.setLastName(person.getLastName());
                inhabitantWithPhone.setPhoneNumber(person.getPhone());
                inhabitantWithPhone.setAge(age);
                inhabitantWithPhone.setMedication(medicalRecordOpt.get().getMedications());
                inhabitantWithPhone.setAllergies(medicalRecordOpt.get().getAllergies());
                inhabitantWithPhoneList.add(inhabitantWithPhone);
            }
        }
        if (fireStationOpt.isPresent() && !inhabitantWithPhoneList.isEmpty()) {
            inhabitantWithFireStation.setInhabitantList(inhabitantWithPhoneList);
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
                    List<InhabitantWithPhone> inhabitantWithPhoneList = new ArrayList<>();
                    Hearth hearth = new Hearth();
                    hearth.setAddress(fireStation.getAddress());
                    for (PersonEntity person : personList) {
                        Optional<MedicalRecordEntity> medicalRecordOpt = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                        if (medicalRecordOpt.isPresent()) {
                            LocalDate birthdate = LocalDate.parse(medicalRecordOpt.get().getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                            int age = Period.between(birthdate, currentDate).getYears();
                            InhabitantWithPhone inhabitantWithPhone = new InhabitantWithPhone();
                            inhabitantWithPhone.setFirstName(person.getFirstName());
                            inhabitantWithPhone.setLastName(person.getLastName());
                            inhabitantWithPhone.setPhoneNumber(person.getPhone());
                            inhabitantWithPhone.setAge(age);
                            inhabitantWithPhone.setMedication(medicalRecordOpt.get().getMedications());
                            inhabitantWithPhone.setAllergies(medicalRecordOpt.get().getAllergies());
                            inhabitantWithPhoneList.add(inhabitantWithPhone);
                        }
                    }
                    hearth.setInhabitantList(inhabitantWithPhoneList);
                    hearthList.add(hearth);
                }
            }
        }
        if (hearthList.isEmpty()){
            return Collections.emptyList();
        }
        return hearthList;
    }

    public List<InhabitantWithEmail> getInhabitantByFullName(String firstName, String lastName) {
        List<InhabitantWithEmail> inhabitantList = new ArrayList<>();
        List<PersonEntity> personList = personRepository.findAllPersonByFullName(firstName, lastName);
        List<MedicalRecordEntity> medicalRecordList = medicalRecordRepository.findAllMedicalRecordByFullName(firstName, lastName);
        LocalDate currentDate = LocalDate.now();

        for (PersonEntity person : personList) {
            MedicalRecordEntity medicalRecord = findMedicalRecordByFullName(medicalRecordList, person.getFirstName(), person.getLastName());
            if (medicalRecord != null) {
                LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                int age = Period.between(birthdate, currentDate).getYears();

                InhabitantWithEmail inhabitant = new InhabitantWithEmail();
                inhabitant.setFirstName(person.getFirstName());
                inhabitant.setLastName(person.getLastName());
                inhabitant.setEmail(person.getEmail());
                inhabitant.setAge(age);
                inhabitant.setMedication(medicalRecord.getMedications());
                inhabitant.setAllergies(medicalRecord.getAllergies());

                inhabitantList.add(inhabitant);
            }
        }

        if (inhabitantList.isEmpty()){
            return Collections.emptyList();
        }
        return inhabitantList;
    }

    private MedicalRecordEntity findMedicalRecordByFullName(List<MedicalRecordEntity> medicalRecordList, String firstName, String lastName) {
        for (MedicalRecordEntity medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                return medicalRecord;
            }
        }
        return null;
    }

    public List<String> getCommunityEmailByCity(String city) {
        Set<String> emailList = new HashSet<>();
        List<PersonEntity> personList = personRepository.findAllPersonByCity(city);

        for (PersonEntity person : personList) {
            emailList.add(person.getEmail());
        }

        if (emailList.isEmpty()) {
            return Collections.emptyList();
        }

        return emailList.stream().toList();
    }
}
