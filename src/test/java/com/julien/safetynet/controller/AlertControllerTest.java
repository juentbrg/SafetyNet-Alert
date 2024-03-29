package com.julien.safetynet.controller;

import com.julien.safetynet.DTO.PersonCoveredDTO;
import com.julien.safetynet.pojo.*;
import com.julien.safetynet.service.AlertService;
import com.julien.safetynet.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AlertControllerTest {
    @Mock
    private AlertService alertService;

    @InjectMocks
    private AlertController alertController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPeopleCoveredReturnsOkTest() {
        int stationNumber = 1;
        PersonCovered mockPersonCovered = new PersonCovered();
        List<PersonCoveredDTO> mockPersonCoveredDTOList = new ArrayList<>();
        PersonCoveredDTO mockPersonCoveredDTO = new PersonCoveredDTO();

        mockPersonCoveredDTO.setFirstName("John");
        mockPersonCoveredDTO.setLastName("Doe");
        mockPersonCoveredDTO.setAddress("1 rue du test unitaire");
        mockPersonCoveredDTO.setPhone("0676543421");

        mockPersonCoveredDTOList.add(mockPersonCoveredDTO);

        mockPersonCovered.setPersonCovered(mockPersonCoveredDTOList);
        mockPersonCovered.setAdultNumber(3);
        mockPersonCovered.setChildrenNumber(3);

        when(alertService.getPersonCovered(stationNumber)).thenReturn(mockPersonCovered);

        ResponseEntity<ApiResponse<PersonCovered>> response = alertController.getPeopleCovered(stationNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved covered persons.", response.getBody().getMessage());
        assertEquals(mockPersonCoveredDTOList, response.getBody().getBody().getPersonCovered());
    }

    @Test
    public void getPeopleCoveredReturnsNotFoundTest() {
        int stationNumber = 6;

        when(alertService.getPersonCovered(stationNumber)).thenReturn(null);

        ResponseEntity<ApiResponse<PersonCovered>> response = alertController.getPeopleCovered(stationNumber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPeopleCoveredReturnsInternalServerErrorTest() {
        int stationNumber = 1;

        when(alertService.getPersonCovered(stationNumber)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<PersonCovered>> responseEntity = alertController.getPeopleCovered(stationNumber);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void findChildByAddressReturnsOkTest() {
        String address = "1 rue du test unitaire";
        List<Child> mockChildList = new ArrayList<>();
        Child mockChild = new Child();
        List<PersonCoveredDTO> mockPersonCoveredDTOList = new ArrayList<>();
        PersonCoveredDTO mockPersonCoveredDTO = new PersonCoveredDTO();

        mockPersonCoveredDTOList.add(mockPersonCoveredDTO);
        mockChild.setFirstName("John");
        mockChild.setLastName("Doe");
        mockChild.setAge(16);
        mockChild.setOtherPersonInHousehold(mockPersonCoveredDTOList);

        mockChildList.add(mockChild);

        when(alertService.findChildByAddress(address)).thenReturn(mockChildList);

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved children.", response.getBody().getMessage());
        assertEquals(mockChildList, response.getBody().getBody());
    }

    @Test
    public void findChildByAddressReturnsBadRequestTest() {
        String address = "";

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Address field is missing.", response.getBody().getMessage());
    }

    @Test
    public void findChildByAddressReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";
        List<Child> mockChildList = new ArrayList<>();

        when(alertService.findChildByAddress(address)).thenReturn(mockChildList);

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findChildByAddressReturnsInternalServerErrorTest() {
        String address = "15 rue du test unitaire";

        when(alertService.findChildByAddress(address)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<List<Child>>> responseEntity = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getPhoneNumberResidentServedReturnsOkTest() {
        int firestation = 1;
        List<String> mockPhoneNumberResidentCoveredList = new ArrayList<>();
        mockPhoneNumberResidentCoveredList.add("0666678754");

        when(alertService.getPhoneNumberResidentServed(firestation)).thenReturn(mockPhoneNumberResidentCoveredList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getPhoneNumberResidentServed(firestation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved resident served phone number.", response.getBody().getMessage());
        assertEquals(mockPhoneNumberResidentCoveredList, response.getBody().getBody());
    }

    @Test
    public void getPhoneNumberResidentServedReturnsNotFoundTest() {
        int firestation = 6;
        List<String> mockPhoneNumberResidentCoveredList = new ArrayList<>();

        when(alertService.getPhoneNumberResidentServed(firestation)).thenReturn(mockPhoneNumberResidentCoveredList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getPhoneNumberResidentServed(firestation);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPhoneNumberResidentServedReturnsInternalServerErrorTest() {
        int fireStation = 1;

        when(alertService.getPhoneNumberResidentServed(fireStation)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<List<String>>> responseEntity = alertController.getPhoneNumberResidentServed(fireStation);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getInhabitantAndFireStationByAddressReturnsOkTest() {
        String address = "1 rue du test unitaire";
        InhabitantWithFireStation mockInhabitantWithFireStation = new InhabitantWithFireStation();

        when(alertService.getInhabitantAndFireStationByAddress(address)).thenReturn(mockInhabitantWithFireStation);

        ResponseEntity<ApiResponse<InhabitantWithFireStation>> response = alertController.getInhabitantAndFireStationByAddress(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved inhabitant list.", response.getBody().getMessage());
        assertEquals(mockInhabitantWithFireStation, response.getBody().getBody());
    }

    @Test
    public void getInhabitantAndFireStationByAddressReturnsBadRequestTest() {
        String address = "";

        ResponseEntity<ApiResponse<InhabitantWithFireStation>> response = alertController.getInhabitantAndFireStationByAddress(address);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Address field is missing.", response.getBody().getMessage());
    }

    @Test
    public void getInhabitantAndFireStationByAddressReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";

        when(alertService.getInhabitantAndFireStationByAddress(address)).thenReturn(null);

        ResponseEntity<ApiResponse<InhabitantWithFireStation>> response = alertController.getInhabitantAndFireStationByAddress(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getInhabitantAndFireStationByAddressReturnsInternalServerErrorTest() {
        String address = "15 rue du test unitaire";

        when(alertService.getInhabitantAndFireStationByAddress(address)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<InhabitantWithFireStation>> responseEntity = alertController.getInhabitantAndFireStationByAddress(address);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getHearthByStationNumberReturnsOkTest() {
        List<Integer> stationList = new ArrayList<>();
        stationList.add(1);
        stationList.add(2);

        List<Hearth> mockHearthList = new ArrayList<>();
        mockHearthList.add(new Hearth());

        when(alertService.getHearthByStationNumber(stationList)).thenReturn(mockHearthList);

        ResponseEntity<ApiResponse<List<Hearth>>> response = alertController.getHearthByStationNumber(stationList);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved hearth list.", response.getBody().getMessage());
        assertEquals(mockHearthList, response.getBody().getBody());
    }

    @Test
    public void getHearthByStationNumberReturnsNotFoundTest() {
        List<Integer> stationList = new ArrayList<>();
        stationList.add(8);
        stationList.add(10);

        List<Hearth> mockHearthList = new ArrayList<>();

        when(alertService.getHearthByStationNumber(stationList)).thenReturn(mockHearthList);

        ResponseEntity<ApiResponse<List<Hearth>>> response = alertController.getHearthByStationNumber(stationList);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getHearthByStationNumberReturnsInternalServerErrorTest() {
        List<Integer> stationList = new ArrayList<>();
        stationList.add(1);
        stationList.add(2);

        when(alertService.getHearthByStationNumber(stationList)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<List<Hearth>>> responseEntity = alertController.getHearthByStationNumber(stationList);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getInhabitantByFullNameReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        List<InhabitantWithEmail> mockInhabitantList = new ArrayList<>();
        InhabitantWithEmail mockInhabitant = new InhabitantWithEmail();
        mockInhabitantList.add(mockInhabitant);

        when(alertService.getInhabitantByFullName(firstName, lastName)).thenReturn(mockInhabitantList);

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> response = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved inhabitant.", response.getBody().getMessage());
        assertEquals(mockInhabitantList, response.getBody().getBody());
    }

    @Test
    public void getInhabitantMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> response = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Firstname or lastname field is missing.", response.getBody().getMessage());
    }

    @Test
    public void getInhabitantMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> response = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Firstname or lastname field is missing.", response.getBody().getMessage());
    }

    @Test
    public void getInhabitantByFullNameReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        List<InhabitantWithEmail> mockInhabitantList = new ArrayList<>();

        when(alertService.getInhabitantByFullName(firstName, lastName)).thenReturn(mockInhabitantList);

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> response = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getInhabitantByFullNameReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(alertService.getInhabitantByFullName(firstName, lastName)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> responseEntity = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getCommunityEmailByCityReturnsOkTest() {
        String city = "culver";

        List<String> mockEmailList = new ArrayList<>();
        mockEmailList.add("john.doe@email.com");

        when(alertService.getCommunityEmailByCity(city)).thenReturn(mockEmailList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getCommunityEmailByCity(city);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved community emails.", response.getBody().getMessage());
        assertEquals(mockEmailList, response.getBody().getBody());
    }

    @Test
    public void getCommunityEmailByCityReturnsBadRequestTest() {
        String city = "";

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getCommunityEmailByCity(city);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("City field is missing.", response.getBody().getMessage());
    }

    @Test
    public void getCommunityEmailByCityReturnsNotFoundTest() {
        String city = "cluver";

        List<String> mockEmailList = new ArrayList<>();

        when(alertService.getCommunityEmailByCity(city)).thenReturn(mockEmailList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getCommunityEmailByCity(city);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getCommunityEmailByCityReturnsInternalServerErrorTest() {
        String city = "culver";

        when(alertService.getCommunityEmailByCity(city)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<List<String>>> responseEntity = alertController.getCommunityEmailByCity(city);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
