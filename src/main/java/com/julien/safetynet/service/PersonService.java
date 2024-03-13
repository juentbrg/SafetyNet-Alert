package com.julien.safetynet.service;

import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonEntity getPersonByFullName(String firstName, String lastName) {
        Optional<PersonEntity> personOpt = personRepository.findPersonByFullName(firstName, lastName);
        return personOpt.orElse(null);
    }

    public boolean addPerson(PersonEntity personEntity) {
        try {
            personRepository.addPerson(personEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updatePerson(String firstName, String lastName, PersonEntity updatedPerson) {
        Optional<PersonEntity> personOpt = personRepository.findPersonByFullName(firstName, lastName);
        if (personOpt.isPresent()) {
            PersonEntity existingPerson = personOpt.get();

            if (null != updatedPerson.getAddress()) {
                existingPerson.setAddress(updatedPerson.getAddress());
            }
            if (null != updatedPerson.getCity()){
                existingPerson.setCity(updatedPerson.getCity());
            }
            if (null != updatedPerson.getZip()) {
                existingPerson.setZip(updatedPerson.getZip());
            }
            if (null != updatedPerson.getPhone()) {
                existingPerson.setPhone(updatedPerson.getPhone());
            }
            if (null != updatedPerson.getEmail()) {
                existingPerson.setEmail(updatedPerson.getEmail());
            }
            personRepository.updatePerson(firstName, lastName, existingPerson);
            return true;
        }
        return false;
    }

    public boolean deletePerson(String firstName, String lastName) {
        try {
            personRepository.deletePerson(firstName, lastName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
