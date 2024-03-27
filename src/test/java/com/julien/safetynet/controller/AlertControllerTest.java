package com.julien.safetynet.controller;

import com.julien.safetynet.DTO.PersonCoveredDTO;
import com.julien.safetynet.pojo.Child;
import com.julien.safetynet.pojo.Hearth;
import com.julien.safetynet.pojo.InhabitantWithEmail;
import com.julien.safetynet.pojo.InhabitantWithFireStation;
import com.julien.safetynet.service.AlertService;
import com.julien.safetynet.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Mockito.when(alertService.findChildByAddress(address)).thenReturn(mockChildList);

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved children.", response.getBody().getMessage());
        assertEquals(mockChildList, response.getBody().getBody());
    }

    @Test
    public void findChildByAddressReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";
        List<Child> mockChildList = new ArrayList<>();

        Mockito.when(alertService.findChildByAddress(address)).thenReturn(mockChildList);

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPhoneNumberResidentServedReturnsOkTest() {
        int firestation = 1;
        List<String> mockPhoneNumberResidentCoveredList = new ArrayList<>();
        mockPhoneNumberResidentCoveredList.add("0666678754");

        Mockito.when(alertService.getPhoneNumberResidentServed(firestation)).thenReturn(mockPhoneNumberResidentCoveredList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getPhoneNumberResidentServed(firestation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved resident served phone number.", response.getBody().getMessage());
        assertEquals(mockPhoneNumberResidentCoveredList, response.getBody().getBody());
    }

    @Test
    public void getPhoneNumberResidentServedReturnsNotFoundTest() {
        int firestation = 6;
        List<String> mockPhoneNumberResidentCoveredList = new ArrayList<>();

        Mockito.when(alertService.getPhoneNumberResidentServed(firestation)).thenReturn(mockPhoneNumberResidentCoveredList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getPhoneNumberResidentServed(firestation);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getInhabitantAndFireStationByAddressReturnsOkTest() {
        String address = "1 rue du test unitaire";
        InhabitantWithFireStation mockInhabitantWithFireStation = new InhabitantWithFireStation();

        Mockito.when(alertService.getInhabitantAndFireStationByAddress(address)).thenReturn(mockInhabitantWithFireStation);

        ResponseEntity<ApiResponse<InhabitantWithFireStation>> response = alertController.getInhabitantAndFireStationByAddress(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved inhabitant list.", response.getBody().getMessage());
        assertEquals(mockInhabitantWithFireStation, response.getBody().getBody());
    }

    @Test
    public void getInhabitantAndFireStationByAddressReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";

        Mockito.when(alertService.getInhabitantAndFireStationByAddress(address)).thenReturn(null);

        ResponseEntity<ApiResponse<InhabitantWithFireStation>> response = alertController.getInhabitantAndFireStationByAddress(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getHearthByStationNumberReturnsOkTest() {
        List<Integer> stationList = new ArrayList<>();
        stationList.add(1);
        stationList.add(2);

        List<Hearth> mockHearthList = new ArrayList<>();
        mockHearthList.add(new Hearth());

        Mockito.when(alertService.getHearthByStationNumber(stationList)).thenReturn(mockHearthList);

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

        Mockito.when(alertService.getHearthByStationNumber(stationList)).thenReturn(mockHearthList);

        ResponseEntity<ApiResponse<List<Hearth>>> response = alertController.getHearthByStationNumber(stationList);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getInhabitantByFullNameReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        List<InhabitantWithEmail> mockInhabitantList = new ArrayList<>();
        InhabitantWithEmail mockInhabitant = new InhabitantWithEmail();
        mockInhabitantList.add(mockInhabitant);

        Mockito.when(alertService.getInhabitantByFullName(firstName, lastName)).thenReturn(mockInhabitantList);

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> response = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved inhabitant.", response.getBody().getMessage());
        assertEquals(mockInhabitantList, response.getBody().getBody());
    }

    @Test
    public void getInhabitantByFullNameReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        List<InhabitantWithEmail> mockInhabitantList = new ArrayList<>();

        Mockito.when(alertService.getInhabitantByFullName(firstName, lastName)).thenReturn(mockInhabitantList);

        ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> response = alertController.getInhabitantByFullName(firstName, lastName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getCommunityEmailByCityReturnsOkTest() {
        String city = "culver";

        List<String> mockEmailList = new ArrayList<>();
        mockEmailList.add("john.doe@email.com");

        Mockito.when(alertService.getCommunityEmailByCity(city)).thenReturn(mockEmailList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getCommunityEmailByCity(city);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved community emails.", response.getBody().getMessage());
        assertEquals(mockEmailList, response.getBody().getBody());
    }

    @Test
    public void getCommunityEmailByCityReturnsNotFoundTest() {
        String city = "cluver";

        List<String> mockEmailList = new ArrayList<>();

        Mockito.when(alertService.getCommunityEmailByCity(city)).thenReturn(mockEmailList);

        ResponseEntity<ApiResponse<List<String>>> response = alertController.getCommunityEmailByCity(city);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
