package com.julien.safetynet.pojo;

import com.julien.safetynet.DTO.PersonCoveredDTO;

import java.util.List;
import java.util.Objects;

public class PersonCovered {
    private List<PersonCoveredDTO> personCovered;
    private int childrenNumber;
    private int adultNumber;

    public PersonCovered() {}

    public List<PersonCoveredDTO> getPersonCovered() {
        return personCovered;
    }

    public void setPersonCovered(List<PersonCoveredDTO> personCovered) {
        this.personCovered = personCovered;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public int getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(int adultNumber) {
        this.adultNumber = adultNumber;
    }
}

