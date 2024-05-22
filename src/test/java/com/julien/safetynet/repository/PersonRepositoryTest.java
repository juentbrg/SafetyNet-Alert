package com.julien.safetynet.repository;

import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.utils.JsonDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository personRepository;

    @Mock
    private JsonDataUtils jsonDataUtils;

    private Data data;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName("John");
        personEntity.setLastName("Doe");
        personEntity.setAddress("1 rue du test unitaire");
        personEntity.setCity("Test City");
        personEntity.setZip("90000");
        personEntity.setPhone("123456789");
        personEntity.setEmail("john@doe.com");
        List<PersonEntity> personEntityList = new ArrayList<>();
        personEntityList.add(personEntity);

        data = new Data();
        data.setPersons(personEntityList);
    }

    @Test
    public void findAllPersonByCityOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<PersonEntity> result = personRepository.findAllPersonByCity("Test City");

        assertFalse(result.isEmpty());
        assertEquals("Test City", result.getFirst().getCity());
    }

    @Test
    public void findAllPersonByCityNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<PersonEntity> result = personRepository.findAllPersonByCity("City Test");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPersonByCityNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        List<PersonEntity> result = personRepository.findAllPersonByCity("Test City");

        assertNull(result);
    }

    @Test
    public void findPersonByFullNameOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        Optional<PersonEntity> result = personRepository.findPersonByFullName("John", "Doe");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    public void findPersonByFullNameNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        Optional<PersonEntity> result = personRepository.findPersonByFullName("John", "Snow");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findPersonByFullNameNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        Optional<PersonEntity> result = personRepository.findPersonByFullName("John", "Doe");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPersonByFullNameOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<PersonEntity> result = personRepository.findAllPersonByFullName("John", "Doe");

        assertFalse(result.isEmpty());
        assertEquals("John", result.getFirst().getFirstName());
        assertEquals("Doe", result.getFirst().getLastName());
    }

    @Test
    public void findAllPersonByFullNameNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<PersonEntity> result = personRepository.findAllPersonByFullName("John", "Snow");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPersonByFullNameNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        List<PersonEntity> result = personRepository.findAllPersonByFullName("John", "Doe");

        assertNull(result);
    }

    @Test
    public void findAllPersonByAddressOkTest() {
        String address = "1 rue du test unitaire";
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<PersonEntity> result = personRepository.findAllPersonByAddress(address);

        assertFalse(result.isEmpty());
        assertEquals("Test City", result.getFirst().getCity());
    }

    @Test
    public void findAllPersonByAddressNotFoundTest() {
        String address = "15 rue du test unitaire";
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<PersonEntity> result = personRepository.findAllPersonByAddress(address);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPersonByAddressNullDataTest() {
        String address = "1 rue du test unitaire";
        when(jsonDataUtils.loadData()).thenReturn(null);

        List<PersonEntity> result = personRepository.findAllPersonByAddress(address);

        assertNull(result);
    }

    @Test
    public void addPersonOkTest() {
        PersonEntity newPerson = new PersonEntity();
        newPerson.setFirstName("Jane");
        newPerson.setLastName("Doe");
        newPerson.setAddress("1 rue du test unitaire");
        newPerson.setCity("Test City");
        newPerson.setZip("90000");
        newPerson.setPhone("987654321");
        newPerson.setEmail("jane@doe.com");

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        personRepository.addPerson(newPerson);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getPersons().contains(newPerson));
    }

    @Test
    public void addPersonLoadDataReturnsNullTest() {
        PersonEntity newPerson = new PersonEntity();
        newPerson.setFirstName("Jane");
        newPerson.setLastName("Doe");
        newPerson.setAddress("1 rue du test unitaire");
        newPerson.setCity("Test City");
        newPerson.setZip("90000");
        newPerson.setPhone("987654321");
        newPerson.setEmail("jane@doe.com");

        when(jsonDataUtils.loadData()).thenReturn(null);

        personRepository.addPerson(newPerson);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getPersons().contains(newPerson));
    }

    @Test
    public void updatePersonOkTest() {
        PersonEntity newPerson = new PersonEntity();
        newPerson.setFirstName("Jane");
        newPerson.setLastName("Doe");
        newPerson.setAddress("1 rue du test unitaire");
        newPerson.setCity("Test City");
        newPerson.setZip("90000");
        newPerson.setPhone("987654321");
        newPerson.setEmail("jane@doe.com");

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        personRepository.updatePerson("John", "Doe", newPerson);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getPersons().contains(newPerson));
    }

    @Test
    public void updatePersonLoadDataReturnsNullTest() {
        PersonEntity newPerson = new PersonEntity();
        newPerson.setFirstName("Jane");
        newPerson.setLastName("Doe");
        newPerson.setAddress("1 rue du test unitaire");
        newPerson.setCity("Test City");
        newPerson.setZip("90000");
        newPerson.setPhone("987654321");
        newPerson.setEmail("jane@doe.com");

        when(jsonDataUtils.loadData()).thenReturn(null);

        personRepository.updatePerson("John", "Doe", newPerson);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getPersons().contains(newPerson));
    }

    @Test
    public void deletePersonOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        personRepository.deletePerson("John", "Doe");

        verify(jsonDataUtils).loadData();
        assertTrue(data.getPersons().isEmpty());
    }

    @Test
    public void deletePersonLoadDataReturnsNullTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        personRepository.deletePerson("John", "Doe");

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getPersons().isEmpty());
    }
}
