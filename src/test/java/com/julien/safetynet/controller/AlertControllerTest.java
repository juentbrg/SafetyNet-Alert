package com.julien.safetynet.controller;

import com.julien.safetynet.DTO.PersonDTO;
import com.julien.safetynet.pojo.Child;
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
    public void getPeopleCoveredReturnsOkTest() {
        String address = "1 rue du test unitaire";
        List<Child> mockChildList = new ArrayList<>();
        Child mockChild = new Child();
        List<PersonDTO> mockPersonDTOList = new ArrayList<>();
        PersonDTO mockPersonDTO = new PersonDTO();

        mockPersonDTOList.add(mockPersonDTO);
        mockChild.setFirstName("John");
        mockChild.setLastName("Doe");
        mockChild.setAge(16);
        mockChild.setOtherPersonInHousehold(mockPersonDTOList);

        mockChildList.add(mockChild);

        Mockito.when(alertService.findChildByAddress(address)).thenReturn(mockChildList);

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully retrieved children.", response.getBody().getMessage());
        assertEquals(mockChildList, response.getBody().getBody());
    }

    @Test
    public void getPeopleCoveredReturnsNotFoundTest() {
        String address = "15 rue du test unitaire";
        List<Child> mockChildList = new ArrayList<>();

        Mockito.when(alertService.findChildByAddress(address)).thenReturn(mockChildList);

        ResponseEntity<ApiResponse<List<Child>>> response = alertController.findChildByAdress(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
