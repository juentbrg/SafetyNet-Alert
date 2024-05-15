package com.julien.safetynet.service;

import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedicalRecordServiceTest {
    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getMedicalRecordByFullNameOkTest() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        medicalRecordEntity.setFirstName(firstName);
        medicalRecordEntity.setLastName(lastName);
        medicalRecordEntity.setBirthdate("01/01/2000");

        when(medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName)).thenReturn(Optional.of(medicalRecordEntity));

        MedicalRecordEntity resMedicalRecordEntity = medicalRecordService.getMedicalRecordByFullName(firstName, lastName);

        assertNotNull(resMedicalRecordEntity);
        assertEquals(firstName, resMedicalRecordEntity.getFirstName());
        assertEquals(lastName, resMedicalRecordEntity.getLastName());
    }

    @Test
    public void getMedicalRecordByFullNameFailureTest() {
        String firstName = "John";
        String lastName = "Doe";

        when(medicalRecordRepository.findAllMedicalRecordByFullName(firstName, lastName)).thenReturn(null);

        MedicalRecordEntity resMedicalRecordEntity = medicalRecordService.getMedicalRecordByFullName(firstName, lastName);

        assertNull(resMedicalRecordEntity);
    }

    @Test
    public void addMedicalRecordOkTest() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        doNothing().when(medicalRecordRepository).addMedicalRecord(medicalRecordEntity);

        boolean result = medicalRecordService.addMedicalRecord(medicalRecordEntity);

        assertTrue(result);
        verify(medicalRecordRepository, times(1)).addMedicalRecord(medicalRecordEntity);
    }

    @Test
    public void addMedicalRecordFailureTest() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        doThrow(new RuntimeException()).when(medicalRecordRepository).addMedicalRecord(medicalRecordEntity);

        boolean result = medicalRecordService.addMedicalRecord(medicalRecordEntity);

        assertFalse(result);
        verify(medicalRecordRepository, times(1)).addMedicalRecord(medicalRecordEntity);
    }

    @Test
    public void updateMedicalRecordOkTest() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        medicalRecordEntity.setFirstName(firstName);
        medicalRecordEntity.setLastName(lastName);
        medicalRecordEntity.setBirthdate("01/01/2000");
        medicalRecordEntity.setMedications(new ArrayList<>());
        medicalRecordEntity.setAllergies(new ArrayList<>());

        when(medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName)).thenReturn(Optional.of(medicalRecordEntity));
        doNothing().when(medicalRecordRepository).updateMedicalRecord(firstName, lastName, medicalRecordEntity);

        boolean result = medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecordEntity);

        assertTrue(result);
    }

    @Test
    public void updateMedicalRecordFailureTest() {
        String firstName = "John";
        String lastname = "Doe";

        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();

        when(medicalRecordRepository.findMedicalRecordByFullName(firstName, lastname)).thenReturn(null);
        doThrow(new RuntimeException()).when(medicalRecordRepository).updateMedicalRecord(firstName, lastname, medicalRecordEntity);

        boolean result = medicalRecordService.updateMedicalRecord(firstName, lastname, medicalRecordEntity);

        assertFalse(result);
    }

    @Test
    public void deleteMedicalRecordOkTest() {
        String firstName = "John";
        String lastname = "Doe";

        doNothing().when(medicalRecordRepository).deleteMedicalRecord(firstName, lastname);

        boolean result = medicalRecordService.deleteMedicalRecord(firstName, lastname);

        assertTrue(result);
    }

    @Test
    public void deleteMedicalRecordFailureTest() {
        String firstName = "John";
        String lastname = "Doe";

        doThrow(new RuntimeException()).when(medicalRecordRepository).deleteMedicalRecord(firstName, lastname);

        boolean result = medicalRecordService.deleteMedicalRecord(firstName, lastname);

        assertFalse(result);
    }
}
