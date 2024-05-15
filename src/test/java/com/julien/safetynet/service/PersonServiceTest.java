package com.julien.safetynet.service;

import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPersonByFullNameOkTest() {
        String firstName = "John";
        String lastName = "Doe";
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(firstName);
        personEntity.setLastName(lastName);
        personEntity.setAddress("1 rue du test unitaire");
        personEntity.setCity("TestCity");
        personEntity.setZip("12345");
        personEntity.setPhone("0612345678");
        personEntity.setEmail("john@doe.com");

        when(personRepository.findPersonByFullName(firstName, lastName)).thenReturn(Optional.of(personEntity));

        PersonEntity resPersonEntity = personService.getPersonByFullName(firstName, lastName);

        assertNotNull(resPersonEntity);
        assertEquals(firstName, resPersonEntity.getFirstName());
        assertEquals(lastName, resPersonEntity.getLastName());
    }

     @Test
    public void getPersonByFullNameFailureTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(personRepository.findAllPersonByFullName(firstName, lastName)).thenReturn(null);

         PersonEntity resPersonEntity = personService.getPersonByFullName(firstName, lastName);

         assertNull(resPersonEntity);
    }

    @Test
    public void addPersonOkTest() {
        PersonEntity PersonEntity = new PersonEntity();
        doNothing().when(personRepository).addPerson(PersonEntity);

        boolean result = personService.addPerson(PersonEntity);

        assertTrue(result);
        verify(personRepository, times(1)).addPerson(PersonEntity);
    }

    @Test
    public void addPersonFailureTest() {
        PersonEntity PersonEntity = new PersonEntity();
        doThrow(new RuntimeException()).when(personRepository).addPerson(PersonEntity);

        boolean result = personService.addPerson(PersonEntity);

        assertFalse(result);
        verify(personRepository, times(1)).addPerson(PersonEntity);
    }

    @Test
    public void updatePersonOkTest() {
        String firstName = "John";
        String lastname = "Doe";

        PersonEntity personEntity = new PersonEntity();
        personEntity.setAddress("15 rue du test unitaire");
        personEntity.setCity("testCity");
        personEntity.setZip("1000");
        personEntity.setEmail("john@doe.com");
        personEntity.setPhone("123-456-7891");

        when(personRepository.findPersonByFullName(firstName, lastname)).thenReturn(Optional.of(personEntity));
        doNothing().when(personRepository).updatePerson(firstName, lastname, personEntity);

        boolean result = personService.updatePerson(firstName, lastname, personEntity);

        assertTrue(result);
    }

    @Test
    public void updatePersonFailureTest() {
        String firstName = "John";
        String lastname = "Doe";

        PersonEntity PersonEntity = new PersonEntity();

        when(personRepository.findPersonByFullName(firstName, lastname)).thenReturn(null);
        doThrow(new RuntimeException()).when(personRepository).updatePerson(firstName, lastname, PersonEntity);

        boolean result = personService.updatePerson(firstName, lastname, PersonEntity);

        assertFalse(result);
    }

    @Test
    public void deletePersonOkTest() {
        String firstName = "John";
        String lastname = "Doe";

        doNothing().when(personRepository).deletePerson(firstName, lastname);

        boolean result = personService.deletePerson(firstName, lastname);

        assertTrue(result);
    }

    @Test
    public void deletePersonFailureTest() {
        String firstName = "John";
        String lastname = "Doe";

        doThrow(new RuntimeException()).when(personRepository).deletePerson(firstName, lastname);

        boolean result = personService.deletePerson(firstName, lastname);

        assertFalse(result);
    }
}
