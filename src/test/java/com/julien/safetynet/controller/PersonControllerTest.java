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
        mockPerson.setFirstName(firstName);
        mockPerson.setLastName(lastName);
        mockPerson.setAddress("1 rue Pierre et Marie Curie");
        mockPerson.setCity("Paris");
        mockPerson.setZip("75016");
        mockPerson.setPhone("0677543369");
        mockPerson.setEmail("john.doe@mail.com");

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        Mockito.when(personService.getPersonByFullName(firstName, lastName)).thenReturn(mockPerson);

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(requestMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved person.", response.getBody().getMessage());
        assertEquals(mockPerson, response.getBody().getBody());
    }

    @Test
    public void getPersonByFullNameReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: Parameters firstName and lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void getPersonByFullNameReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        Mockito.when(personService.getPersonByFullName(firstName, lastName)).thenReturn(null);

        ResponseEntity<ApiResponse<PersonEntity>> response = personController.getPersonByFullName(requestMap);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
    public void updatePersonReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        PersonEntity mockPerson = new PersonEntity();

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(requestMap, mockPerson);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName or lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void updatePersonReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        Mockito.when(personService.updatePerson(firstName, lastName, mockPerson)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(requestMap, mockPerson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person successfully updated.", response.getBody().getMessage());
    }

    @Test
    public void updatePersonReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        PersonEntity mockPerson = new PersonEntity();

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        Mockito.when(personService.updatePerson(firstName, lastName, mockPerson)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = personController.updatePerson(requestMap, mockPerson);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deletePersonReturnsBadRequestTest() {
        String firstName = "John";
        String lastName = "";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request: firstName and lastName cannot be empty.", response.getBody().getMessage());
    }

    @Test
    public void deletePersonReturnsOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        Mockito.when(personService.deletePerson(firstName, lastName)).thenReturn(true);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(requestMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Person successfully deleted.", response.getBody().getMessage());
    }

    @Test
    public void deletePersonReturnsNotFoundTest() {
        String firstName = "John";
        String lastName = "Doe";

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("firstName", firstName);
        requestMap.put("lastName", lastName);

        Mockito.when(personService.deletePerson(firstName, lastName)).thenReturn(false);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(requestMap);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}


