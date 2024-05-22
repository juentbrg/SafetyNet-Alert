package com.julien.safetynet.repository;

import com.julien.safetynet.entity.Data;
import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.utils.JsonDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FireStationRepositoryTest {

    @InjectMocks
    private FireStationRepository fireStationRepository;

    @Mock
    private JsonDataUtils jsonDataUtils;

    private Data data;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FireStationEntity fireStation = new FireStationEntity();
        fireStation.setAddress("1 rue du test unitaire");
        fireStation.setStation(1);
        List<FireStationEntity> fireStationEntityList = new ArrayList<>();
        fireStationEntityList.add(fireStation);

        data = new Data();
        data.setFirestations(fireStationEntityList);
    }

    @Test
    public void findFireStationByAddressOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        Optional<FireStationEntity> result = fireStationRepository.findFireStationByAddress("1 rue du test unitaire");

        assertTrue(result.isPresent());
        assertEquals("1 rue du test unitaire", result.get().getAddress());
    }

    @Test
    public void findFireStationByAddressNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        Optional<FireStationEntity> result = fireStationRepository.findFireStationByAddress("15 rue du test unitaire");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findFireStationByAddressNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        Optional<FireStationEntity> result = fireStationRepository.findFireStationByAddress("1 rue du test unitaire");

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllFireStationByStationNumberOkTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<FireStationEntity> result = fireStationRepository.findAllFireStationByStationNumber(1);

        assertFalse(result.isEmpty());
        assertEquals("1 rue du test unitaire", result.getFirst().getAddress());
    }

    @Test
    public void findAllFireStationByStationNumberNotFoundTest() {
        when(jsonDataUtils.loadData()).thenReturn(data);

        List<FireStationEntity> result = fireStationRepository.findAllFireStationByStationNumber(2);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllFireStationByStationNumberNullDataTest() {
        when(jsonDataUtils.loadData()).thenReturn(null);

        List<FireStationEntity> result = fireStationRepository.findAllFireStationByStationNumber(1);

        assertNull(result);
    }

    @Test
    public void addFireStationOkTest() {
        FireStationEntity newFireStation = new FireStationEntity();
        newFireStation.setAddress("1 rue du test unitaire");
        newFireStation.setStation(2);

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        fireStationRepository.addFireStation(newFireStation);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getFirestations().contains(newFireStation));
    }

    @Test
    public void addFireStationLoadDataReturnsNullTest() {
        FireStationEntity newFireStation = new FireStationEntity();
        newFireStation.setAddress("15 rue du test unitaire");
        newFireStation.setStation(2);

        when(jsonDataUtils.loadData()).thenReturn(null);

        fireStationRepository.addFireStation(newFireStation);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getFirestations().contains(newFireStation));
    }

    @Test
    public void updateFireStationOkTest() {
        String address = "1 rue du test unitaire";

        FireStationEntity newFireStation = new FireStationEntity();
        newFireStation.setAddress("1 rue du test unitaire");
        newFireStation.setStation(1);

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        fireStationRepository.updateFireStation(address, newFireStation);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getFirestations().contains(newFireStation));
    }

    @Test
    public void updateFireStationLoadDataReturnsNullTest() {
        String address = "1 rue du test unitaire";

        FireStationEntity newFireStation = new FireStationEntity();
        newFireStation.setAddress("15 rue du test unitaire");
        newFireStation.setStation(2);

        when(jsonDataUtils.loadData()).thenReturn(null);

        fireStationRepository.updateFireStation(address, newFireStation);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getFirestations().contains(newFireStation));
    }

    @Test
    public void deleteFireStationOkTest() {
        String address = "1 rue du test unitaire";

        when(jsonDataUtils.loadData()).thenReturn(data);
        doNothing().when(jsonDataUtils).saveAllData(data);

        fireStationRepository.deleteFireStation(address);

        verify(jsonDataUtils).loadData();
        assertTrue(data.getFirestations().isEmpty());
    }

    @Test
    public void deleteFireStationLoadDataReturnsNullTest() {
        String address = "1 rue du test unitaire";

        when(jsonDataUtils.loadData()).thenReturn(null);

        fireStationRepository.deleteFireStation(address);

        verify(jsonDataUtils).loadData();
        verify(jsonDataUtils, never()).saveAllData(any());
        assertFalse(data.getFirestations().isEmpty());
    }
}
