package com.julien.safetynet.controller;

import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.service.MedicalRecordService;
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
import static org.mockito.Mockito.when;

public class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getMedicalRecordByFullNameReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecordEntity mockMedicalRecord = new MedicalRecordEntity();
        mockMedicalRecord.setFirstName(firstName);
        mockMedicalRecord.setLastName(lastName);
        mockMedicalRecord.setBirthdate("22/01/2000");

        Mockito.when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenReturn(mockMedicalRecord);

        ResponseEntity<ApiResponse<MedicalRecordEntity>> response = medicalRecordController.getMedicalRecordByFullName(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved medical record.", response.getBody().getMessage());
        assertEquals(mockMedicalRecord, response.getBody().getBody());
    }

    @Test
    public void getMedicalMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        ResponseEntity<ApiResponse<MedicalRecordEntity>> response = medicalRecordController.getMedicalRecordByFullName(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: Parameters firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void getMedicalMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        ResponseEntity<ApiResponse<MedicalRecordEntity>> response = medicalRecordController.getMedicalRecordByFullName(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: Parameters firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void getMedicalRecordByFullNameReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenReturn(null);

        ResponseEntity<ApiResponse<MedicalRecordEntity>> response = medicalRecordController.getMedicalRecordByFullName(firstName, lastName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getMedicalRecordByFullNameReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(medicalRecordService.getMedicalRecordByFullName(firstName, lastName)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<MedicalRecordEntity>> responseEntity = medicalRecordController.getMedicalRecordByFullName(firstName, lastName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void addMedicalRecordReturnsOkTest() {
        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        Mockito.when(medicalRecordService.addMedicalRecord(mockMedicalRecordEntity)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.addMedicalRecord(mockMedicalRecordEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Medical record successfully added.", response.getBody().getMessage());
    }

    @Test
    public void addMedicalRecordReturnsBadRequestTest() {
        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        Mockito.when(medicalRecordService.addMedicalRecord(mockMedicalRecordEntity)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.addMedicalRecord(mockMedicalRecordEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unable to add new medical record.", response.getBody().getMessage());
    }

    @Test
    public void addMedicalRecordReturnsInternalServerErrorTest() {
        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        when(medicalRecordService.addMedicalRecord(mockMedicalRecordEntity)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<Void>> responseEntity = medicalRecordController.addMedicalRecord(mockMedicalRecordEntity);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void updateMedicalRecordMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void updateMedicalRecordMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void updateMedicalRecordReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        Mockito.when(medicalRecordService.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Medical record successfully updated.", response.getBody().getMessage());
    }

    @Test
    public void updateMedicalRecordReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        Mockito.when(medicalRecordService.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateMedicalRecordReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecordEntity mockMedicalRecordEntity = new MedicalRecordEntity();

        when(medicalRecordService.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<Void>> responseEntity = medicalRecordController.updateMedicalRecord(firstName, lastName, mockMedicalRecordEntity);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void deleteMedicalRecordMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void deleteMedicalRecordMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void deleteMedicalRecordReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Medical record successfully deleted.", response.getBody().getMessage());
    }

    @Test
    public void deleteMedicalRecordReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteMedicalRecordReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<Void>> responseEntity = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
