package com.julien.safetynet.repository;

import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.MedicalRecordEntity;
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

public class MedicalRecordRepositoryTest {

    @InjectMocks
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private JsonDataUtils jsonDataUtils;

    private Data data;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        medicalRecordEntity.setFirstName("John");
        medicalRecordEntity.setLastName("Doe");
        medicalRecordEntity.setBirthdate("03/06/2000");
        medicalRecordEntity.setMedications(new ArrayList<>());
        medicalRecordEntity.setAllergies(new ArrayList<>());
        List<MedicalRecordEntity> medicalRecordEntityList = new ArrayList<>();
        medicalRecordEntityList.add(medicalRecordEntity);

        data = new Data();
        data.setMedicalrecords(medicalRecordEntityList);
    }

    @Test
    public void findMedicalRecordByFullNameOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        Optional<MedicalRecordEntity> result = medicalRecordRepository.findMedicalRecordByFullName("John", "Doe");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    public void findMedicalRecordByFullNameNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        Optional<MedicalRecordEntity> result = medicalRecordRepository.findMedicalRecordByFullName("John", "Snow");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findMedicalRecordByFullNameNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        Optional<MedicalRecordEntity> result = medicalRecordRepository.findMedicalRecordByFullName("John", "Doe");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllMedicalRecordByFullNameOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<MedicalRecordEntity> result = medicalRecordRepository.findAllMedicalRecordByFullName("John", "Doe");

        assertFalse(result.isEmpty());
        assertEquals("John", result.getFirst().getFirstName());
        assertEquals("Doe", result.getFirst().getLastName());
    }

    @Test
    public void findAllMedicalRecordByFullNameNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<MedicalRecordEntity> result = medicalRecordRepository.findAllMedicalRecordByFullName("John", "Snow");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllMedicalRecordByFullNameNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        List<MedicalRecordEntity> result = medicalRecordRepository.findAllMedicalRecordByFullName("John", "Doe");

        assertNull(result);
    }

    @Test
    public void addMedicalRecordOkTest() {
        MedicalRecordEntity newMedicalRecord = new MedicalRecordEntity();
        newMedicalRecord.setFirstName("Jane");
        newMedicalRecord.setLastName("Doe");
        newMedicalRecord.setBirthdate("07/11/2000");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        medicalRecordRepository.addMedicalRecord(newMedicalRecord);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getMedicalrecords().contains(newMedicalRecord));
    }

    @Test
    public void addMedicalRecordLoadDataReturnsNullTest() {
        MedicalRecordEntity newMedicalRecord = new MedicalRecordEntity();
        newMedicalRecord.setFirstName("Jane");
        newMedicalRecord.setLastName("Doe");
        newMedicalRecord.setBirthdate("07/11/2000");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        when(jsonDataUtils.loadData()).thenReturn(null);

        medicalRecordRepository.addMedicalRecord(newMedicalRecord);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getMedicalrecords().contains(newMedicalRecord));
    }

    @Test
    public void updateMedicalRecordOkTest() {
        MedicalRecordEntity newMedicalRecord = new MedicalRecordEntity();
        newMedicalRecord.setFirstName("Jane");
        newMedicalRecord.setLastName("Doe");
        newMedicalRecord.setBirthdate("07/11/2000");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        medicalRecordRepository.updateMedicalRecord("John", "Doe", newMedicalRecord);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getMedicalrecords().contains(newMedicalRecord));
    }

    @Test
    public void updateMedicalRecordLoadDataReturnsNullTest() {
        MedicalRecordEntity newMedicalRecord = new MedicalRecordEntity();
        newMedicalRecord.setFirstName("Jane");
        newMedicalRecord.setLastName("Doe");
        newMedicalRecord.setBirthdate("07/11/2000");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        when(jsonDataUtils.loadData()).thenReturn(null);

        medicalRecordRepository.updateMedicalRecord("John", "Doe", newMedicalRecord);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getMedicalrecords().contains(newMedicalRecord));
    }

    @Test
    public void deleteMedicalRecordOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        medicalRecordRepository.deleteMedicalRecord("John", "Doe");

        verify(jsonDataUtils).loadData();
        assertTrue(data.getMedicalrecords().isEmpty());
    }

    @Test
    public void deleteMedicalRecordLoadDataReturnsNullTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        medicalRecordRepository.deleteMedicalRecord("John", "Doe");

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getMedicalrecords().isEmpty());
    }
}
