package com.julien.safetynet.controller;

import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.service.FireStationService;
import com.julien.safetynet.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireStationControllerTest {
    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getFireStationByAddressReturnsOkTest() {
        String address = "1 rue du test unitaire";
        FireStationEntity mockFireStationEntity = new FireStationEntity();
        mockFireStationEntity.setStation(1);
        mockFireStationEntity.setAddress(address);

        Mockito.when(fireStationService.getFireStationByAddress(address)).thenReturn(mockFireStationEntity);

        ResponseEntity<ApiResponse<FireStationEntity>> response = fireStationController.getFireStationByAddress(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved fire station.", response.getBody().getMessage());
        assertEquals(mockFireStationEntity, response.getBody().getBody());
    }

    @Test
    public void getFireStationByAddressReturnsBadRequestTest() {
        String address = "";

        ResponseEntity<ApiResponse<FireStationEntity>> response = fireStationController.getFireStationByAddress(address);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: address cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void getFireStationByAddressReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";

        Mockito.when(fireStationService.getFireStationByAddress(address)).thenReturn(null);

        ResponseEntity<ApiResponse<FireStationEntity>> response = fireStationController.getFireStationByAddress(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addFireStationReturnsOkTest() {
        FireStationEntity mockFireStationEntity = new FireStationEntity();

        Mockito.when(fireStationService.addFireStation(mockFireStationEntity)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = fireStationController.addFireStation(mockFireStationEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fire station successfully created.", response.getBody().getMessage());
    }

    @Test
    public void addFireStationReturnsBadRequestTest() {
        FireStationEntity mockFireStationEntity = new FireStationEntity();

        Mockito.when(fireStationService.addFireStation(mockFireStationEntity)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = fireStationController.addFireStation(mockFireStationEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unable to add new fire station.", response.getBody().getMessage());
    }

    @Test
    public void updateFireStationReturnsBadRequestTest() {
        String address = "";

        FireStationEntity mockFireStationEntity = new FireStationEntity();

        ResponseEntity<ApiResponse<Void>> response = fireStationController.updateFireStation(address, mockFireStationEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: address cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void updateFireStationReturnsOkTest() {
        String address = "1 rue du test unitaire";

        FireStationEntity mockFireStationEntity = new FireStationEntity();



        Mockito.when(fireStationService.updateFireStation(address, mockFireStationEntity)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = fireStationController.updateFireStation(address, mockFireStationEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fire station successfully updated.", response.getBody().getMessage());
    }

    @Test
    public void updateFireStationReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";

        FireStationEntity mockFireStationEntity = new FireStationEntity();

        Mockito.when(fireStationService.updateFireStation(address, mockFireStationEntity)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = fireStationController.updateFireStation(address, mockFireStationEntity);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteFireStationReturnsBadRequestTest() {
        String address = "";

        ResponseEntity<ApiResponse<Void>> response = fireStationController.deleteFireStation(address);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: address cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void deleteFireStationReturnsOkTest() {
        String address = "1 rue du test unitaire";

        Mockito.when(fireStationService.deleteFireStation(address)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = fireStationController.deleteFireStation(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fire station successfully deleted.", response.getBody().getMessage());
    }

    @Test
    public void deleteFireStationReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";

        Mockito.when(fireStationService.deleteFireStation(address)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = fireStationController.deleteFireStation(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
