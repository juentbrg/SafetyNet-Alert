package com.julien.safetynet.entity;

import java.util.List;

public class Data {

    private List<PersonEntity> persons;
    private List<FireStationEntity> firestations;
    private List<MedicalRecordEntity> medicalrecords;

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public List<FireStationEntity> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStationEntity> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecordEntity> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecordEntity> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}

