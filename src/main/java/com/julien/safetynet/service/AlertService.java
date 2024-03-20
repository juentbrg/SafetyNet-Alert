package com.julien.safetynet.service;

import com.julien.safetynet.DTO.PersonDTO;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.pojo.Child;
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
    private final MedicalRecordRepository medicalRecordRepository;

    public AlertService(PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
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
            List<PersonDTO> otherPersonInHousehold = new ArrayList<>();
            for (PersonEntity person : personList){
                if (!Objects.equals(person.getFirstName(), child.getFirstName())){
                    otherPersonInHousehold.add(new PersonDTO(person));
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
}
