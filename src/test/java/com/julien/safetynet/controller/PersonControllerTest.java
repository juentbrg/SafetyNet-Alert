package com.julien.safetynet.controller;

import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.service.PersonService;
import com.julien.safetynet.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPersonByFullNameReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        Mockito.when(personService.getPersonByFullName(firstName, lastName)).thenReturn(mockPerson);

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved person.", response.getBody().getMessage());
        assertEquals(mockPerson, response.getBody().getBody());
    }

    @Test
    public void getPersonMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: Parameters firstName and lastName cannot be empty.", response.getBody().getMessage());
    }
    @Test
    public void getPersonMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: Parameters firstName and lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void getPersonByFullNameReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(personService.getPersonByFullName(firstName, lastName)).thenReturn(null);

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(firstName, lastName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPersonByFullNameReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(personService.getPersonByFullName(firstName, lastName)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<PersonEntity>> responseEntity = personController.getPersonByFullName(firstName, lastName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void addPersonReturnsOkTest() {
        PersonEntity mockPerson = new PersonEntity();

        Mockito.when(personService.addPerson(mockPerson)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = personController.addPerson(mockPerson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person successfully created.", response.getBody().getMessage());
    }

    @Test
    public void addPersonReturnsBadRequestTest() {
        PersonEntity mockPerson = new PersonEntity();

        Mockito.when(personService.addPerson(mockPerson)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = personController.addPerson(mockPerson);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unable to add new person.", response.getBody().getMessage());
    }

    @Test
    public void addPersonReturnsInternalServerErrorTest() {
        PersonEntity mockPerson = new PersonEntity();

        when(personService.addPerson(mockPerson)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<Void>> responseEntity = personController.addPerson(mockPerson);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void updatePersonMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        PersonEntity mockPerson = new PersonEntity();

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(firstName, lastName, mockPerson);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void updatePersonMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(firstName, lastName, mockPerson);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void updatePersonReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        Mockito.when(personService.updatePerson(firstName, lastName, mockPerson)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(firstName, lastName, mockPerson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person successfully updated.", response.getBody().getMessage());
    }

    @Test
    public void updatePersonReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        Mockito.when(personService.updatePerson(firstName, lastName, mockPerson)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(firstName, lastName, mockPerson);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updatePersonReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        when(personService.updatePerson(firstName, lastName, mockPerson)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<Void>> responseEntity = personController.updatePerson(firstName, lastName, mockPerson);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void deletePersonMissingLastNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName and lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void deletePersonMissingFirstNameReturnsBadRequestTest() {
        String firstName = "";
        String lastName = "Doe";

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(firstName, lastName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName and lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void deletePersonReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(personService.deletePerson(firstName, lastName)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(firstName, lastName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person successfully deleted.", response.getBody().getMessage());
    }

    @Test
    public void deletePersonReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.when(personService.deletePerson(firstName, lastName)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(firstName, lastName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deletePersonReturnsInternalServerErrorTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(personService.deletePerson(firstName, lastName)).thenThrow(new RuntimeException("Exception test"));

        ResponseEntity<ApiResponse<Void>> responseEntity = personController.deletePerson(firstName, lastName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}


