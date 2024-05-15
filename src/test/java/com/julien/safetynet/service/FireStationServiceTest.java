package com.julien.safetynet.service;

import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FireStationServiceTest {

    @InjectMocks
    private FireStationService fireStationService;

    @Mock
    private FireStationRepository fireStationRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getFireStationByAddressOkTest() {
        String address = "1 rue du test unitaire";
        FireStationEntity fireStationEntity = new FireStationEntity();
        fireStationEntity.setAddress(address);
        fireStationEntity.setStation(1);

        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(Optional.of(fireStationEntity));

        FireStationEntity resFireStationEntity = fireStationService.getFireStationByAddress(address);

        assertNotNull(resFireStationEntity);
        assertEquals(address, resFireStationEntity.getAddress());
        assertEquals(fireStationEntity.getStation(), resFireStationEntity.getStation());
    }

    @Test
    public void getFireStationByAddressFailureTest() {
        String address = "15 rue du test unitaire";

        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(null);

        FireStationEntity resFireStationEntity = fireStationService.getFireStationByAddress(address);

        assertNull(resFireStationEntity);
    }

    @Test
    public void addFireStationOkTest() {
        FireStationEntity fireStationEntity = new FireStationEntity();
        doNothing().when(fireStationRepository).addFireStation(fireStationEntity);

        boolean result = fireStationService.addFireStation(fireStationEntity);

        assertTrue(result);
        verify(fireStationRepository, times(1)).addFireStation(fireStationEntity);
    }

    @Test
    public void addFireStationFailureTest() {
        FireStationEntity fireStationEntity = new FireStationEntity();
        doThrow(new RuntimeException()).when(fireStationRepository).addFireStation(fireStationEntity);

        boolean result = fireStationService.addFireStation(fireStationEntity);

        assertFalse(result);
        verify(fireStationRepository, times(1)).addFireStation(fireStationEntity);
    }

    @Test
    public void updateFireStationOkTest() {
        String address = "1 rue du test unitaire";

        FireStationEntity fireStationEntity = new FireStationEntity();

        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(Optional.of(fireStationEntity));
        doNothing().when(fireStationRepository).updateFireStation(address, fireStationEntity);

        boolean result = fireStationService.updateFireStation(address, fireStationEntity);

        assertTrue(result);
    }

    @Test
    public void updateFireStationFailureTest() {
        String address = "15 rue du test unitaire";

        FireStationEntity fireStationEntity = new FireStationEntity();

        when(fireStationRepository.findFireStationByAddress(address)).thenReturn(null);
        doThrow(new RuntimeException()).when(fireStationRepository).updateFireStation(address, fireStationEntity);

        boolean result = fireStationService.updateFireStation(address, fireStationEntity);

        assertFalse(result);
    }

    @Test
    public void deleteFireStationOkTest() {
        String address = "1 rue du test unitaire";

        doNothing().when(fireStationRepository).deleteFireStation(address);

        boolean result = fireStationService.deleteFireStation(address);

        assertTrue(result);
    }

    @Test
    public void deleteFireStationFailureTest() {
        String address = "15 rue du test unitaire";

        doThrow(new RuntimeException()).when(fireStationRepository).deleteFireStation(address);

        boolean result = fireStationService.deleteFireStation(address);

        assertFalse(result);
    }
}
