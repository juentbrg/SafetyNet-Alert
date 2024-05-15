package com.julien.safetynet.service;

import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.pojo.*;
import com.julien.safetynet.repository.FireStationRepository;
import com.julien.safetynet.repository.MedicalRecordRepository;
import com.julien.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private static final String TEST_STREET_ADDRESS = "1 rue du test unitaire";
    private static final String TEST_CITY = "TestCity";
    private static final String TEST_ZIP_CODE = "12345";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        FireStationEntity fireStationEntity = new FireStationEntity();
        fireStationEntity.setAddress(TEST_STREET_ADDRESS);
        fireStationEntity.setStation(1);

        PersonEntity personEntity1 = new PersonEntity();
        personEntity1.setFirstName("John");
        personEntity1.setLastName("Doe");
        personEntity1.setAddress(TEST_STREET_ADDRESS);
        personEntity1.setCity(TEST_CITY);
        personEntity1.setZip(TEST_ZIP_CODE);
        personEntity1.setPhone("123-456-7890");
        personEntity1.setEmail("johndoe@example.com");

        PersonEntity personEntity2 = new PersonEntity();
        personEntity2.setFirstName("Jane");
        personEntity2.setLastName("Doe");
        personEntity2.setAddress(TEST_STREET_ADDRESS);
        personEntity2.setCity(TEST_CITY);
        personEntity2.setZip(TEST_ZIP_CODE);
        personEntity2.setPhone("987-654-3210");
        personEntity2.setEmail("janedoe@example.com");

        MedicalRecordEntity medicalRecordEntity1 = new MedicalRecordEntity();
        medicalRecordEntity1.setFirstName("John");
        medicalRecordEntity1.setLastName("Doe");
        medicalRecordEntity1.setBirthdate("01/01/2015");
        medicalRecordEntity1.setMedications(new ArrayList<>());
        medicalRecordEntity1.setAllergies(new ArrayList<>());

        MedicalRecordEntity medicalRecordEntity2 = new MedicalRecordEntity();
        medicalRecordEntity2.setFirstName("Jane");
        medicalRecordEntity2.setLastName("Doe");
        medicalRecordEntity2.setBirthdate("01/01/1990");
        medicalRecordEntity2.setMedications(new ArrayList<>());
        medicalRecordEntity2.setAllergies(new ArrayList<>());

        when(fireStationRepository.findAllFireStationByStationNumber(1)).thenReturn(Collections.singletonList(fireStationEntity));
        when(fireStationRepository.findFireStationByAddress(TEST_STREET_ADDRESS)).thenReturn(Optional.of(fireStationEntity));
        when(personRepository.findAllPersonByAddress(TEST_STREET_ADDRESS)).thenReturn(Arrays.asList(personEntity1, personEntity2));
        when(personRepository.findAllPersonByCity(TEST_CITY)).thenReturn(Arrays.asList(personEntity1, personEntity2));
        when(personRepository.findAllPersonByFullName(personEntity1.getFirstName(), personEntity1.getLastName())).thenReturn(List.of(personEntity1));
        when(medicalRecordRepository.findAllMedicalRecordByFullName(medicalRecordEntity1.getFirstName(), medicalRecordEntity1.getLastName())).thenReturn(List.of(medicalRecordEntity1));
        when(medicalRecordRepository.findMedicalRecordByFullName("John", "Doe")).thenReturn(Optional.of(medicalRecordEntity1));
        when(medicalRecordRepository.findMedicalRecordByFullName("Jane", "Doe")).thenReturn(Optional.of(medicalRecordEntity2));
    }

    @Test
    public void getPersonCoveredOkTest() {
        int stationNumber = 1;
        PersonCovered personCovered = alertService.getPersonCovered(stationNumber);

        assertEquals(2, personCovered.getPersonCovered().size());
        assertEquals(1, personCovered.getChildrenNumber());
        assertEquals(1, personCovered.getAdultNumber());
    }

    @Test
    public void getPersonCoveredWrongStationNumberTest() {
        int stationNumber = 19;
        PersonCovered personCovered = alertService.getPersonCovered(stationNumber);

        assertNull(personCovered);
    }

    @Test
    public void findChildByAddressOkTest() {
        String address = "1 rue du test unitaire";
        List<Child> childList = alertService.findChildByAddress(address);

        assertEquals(1, childList.size());
        Child child = childList.getFirst();
        assertEquals(9, child.getAge());
        assertEquals(1, child.getOtherPersonInHousehold().size());
    }

    @Test
    public void findChildByAddressWrongAddressTest() {
        String address = "15 rue du test unitaire";
        List<Child> childList = alertService.findChildByAddress(address);

        assertEquals(0, childList.size());
    }

    @Test
    public void getPhoneNumberResidentServedOkTest() {
        int stationNumber = 1;
        List<String> phoneNumberList = alertService.getPhoneNumberResidentServed(stationNumber);

        assertEquals(2, phoneNumberList.size());
        assertTrue(phoneNumberList.contains("123-456-7890"));
        assertTrue(phoneNumberList.contains("987-654-3210"));
    }

    @Test
    public void getPhoneNumberResidentServedWrongStationNumberTest() {
        int stationNumber = 19;
        List<String> phoneNumberList = alertService.getPhoneNumberResidentServed(stationNumber);

        assertEquals(0, phoneNumberList.size());
    }

    @Test
    public void getInhabitantAndFireStationByAddressOkTest() {
        String address = "1 rue du test unitaire";
        InhabitantWithFireStation inhabitantWithFireStation = alertService.getInhabitantAndFireStationByAddress(address);

        assertNotNull(inhabitantWithFireStation);
        assertEquals(1, inhabitantWithFireStation.getStationNumber());
        assertEquals(2, inhabitantWithFireStation.getInhabitantList().size());
    }

    @Test
    public void getInhabitantAndFireStationByAddressWrongAddressTest() {
        String address = "15 rue du test unitaire";
        InhabitantWithFireStation inhabitantWithFireStation = alertService.getInhabitantAndFireStationByAddress(address);

        assertNull(inhabitantWithFireStation);
    }

    @Test
    public void getHearthByStationNumberOkTest() {
        int stationNumber = 1;
        List<Hearth> hearthList = alertService.getHearthByStationNumber(List.of(stationNumber));

        assertEquals(1, hearthList.size());
        assertEquals("1 rue du test unitaire", hearthList.getFirst().getAddress());
        assertEquals(2, hearthList.getFirst().getInhabitantList().size());
    }

    @Test
    public void getHearthByStationNumberWrongStationNumberTest() {
        int stationNumber = 19;
        List<Hearth> hearthList = alertService.getHearthByStationNumber(List.of(stationNumber));

        assertEquals(0, hearthList.size());
    }

    @Test
    public void getInhabitantByFullNameOkTest() {
        String firstName = "John";
        String lastName = "Doe";
        List<InhabitantWithEmail> inhabitantList = alertService.getInhabitantByFullName(firstName, lastName);

        assertEquals(1, inhabitantList.size());
        assertEquals(firstName, inhabitantList.getFirst().getFirstName());
        assertEquals(lastName, inhabitantList.getFirst().getLastName());
    }

    @Test
    public void getInhabitantByFullNameWrongFirstNameTest() {
        String firstName = "Wrong";
        String lastName = "Doe";
        List<InhabitantWithEmail> inhabitantList = alertService.getInhabitantByFullName(firstName, lastName);

        assertEquals(0, inhabitantList.size());
    }

    @Test
    public void getInhabitantByFullNameWrongLastNameTest() {
        String firstName = "John";
        String lastName = "Wrong";
        List<InhabitantWithEmail> inhabitantList = alertService.getInhabitantByFullName(firstName, lastName);

        assertEquals(0, inhabitantList.size());
    }

    @Test
    public void findMedicalRecordByFullNameOkTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecordEntity medicalRecordEntity1 = new MedicalRecordEntity();
        medicalRecordEntity1.setFirstName(firstName);
        medicalRecordEntity1.setLastName(lastName);
        medicalRecordEntity1.setBirthdate("01/01/2015");
        medicalRecordEntity1.setMedications(new ArrayList<>());
        medicalRecordEntity1.setAllergies(new ArrayList<>());

        List<MedicalRecordEntity> medicalRecordEntityList = List.of(medicalRecordEntity1);

        AlertService instance = new AlertService(personRepository, fireStationRepository, medicalRecordRepository);

        Method findMedicalRecordByFullName = AlertService.class.getDeclaredMethod("findMedicalRecordByFullName", List.class, String.class, String.class);
        findMedicalRecordByFullName.setAccessible(true);

        MedicalRecordEntity medicalRecordEntity = (MedicalRecordEntity) findMedicalRecordByFullName.invoke(instance, medicalRecordEntityList, firstName, lastName);

        assertNotNull(medicalRecordEntity);
        assertEquals(firstName, medicalRecordEntity.getFirstName());
        assertEquals(lastName, medicalRecordEntity.getLastName());
    }

    @Test
    public void findMedicalRecordByFullNameNullTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String firstName = "";
        String lastName = "";
        List<MedicalRecordEntity> medicalRecordEntityList = new ArrayList<>();

        AlertService instance = new AlertService(personRepository, fireStationRepository, medicalRecordRepository);

        Method findMedicalRecordByFullName = AlertService.class.getDeclaredMethod("findMedicalRecordByFullName", List.class, String.class, String.class);
        findMedicalRecordByFullName.setAccessible(true);

        MedicalRecordEntity medicalRecordEntity = (MedicalRecordEntity) findMedicalRecordByFullName.invoke(instance, medicalRecordEntityList, firstName, lastName);

        assertNull(medicalRecordEntity);
    }

    @Test
    public void getCommunityEmailByCityOkTest() {
        String city = "TestCity";
        List<String> communityEmailList = alertService.getCommunityEmailByCity(city);

        assertFalse(communityEmailList.isEmpty());
        assertEquals("janedoe@example.com", communityEmailList.getFirst());
        assertEquals("johndoe@example.com", communityEmailList.getLast());
    }

    @Test
    public void getCommunityEmailByCityWrongCityTest() {
        String city = "CityTest";
        List<String> communityEmailList = alertService.getCommunityEmailByCity(city);

        assertTrue(communityEmailList.isEmpty());
    }
}
