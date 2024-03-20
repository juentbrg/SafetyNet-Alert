package com.julien.safetynet.pojo;

import com.julien.safetynet.DTO.PersonDTO;

import java.util.List;
import java.util.Objects;

public class PersonCovered {
    List<PersonDTO> personCovered;
    int childrenNumber;
    int adultNumber;

    public PersonCovered() {}

    public PersonCovered(List<PersonDTO> personCovered, int childrenNumber, int adultNumber) {
        this.personCovered = personCovered;
        this.childrenNumber = childrenNumber;
        this.adultNumber = adultNumber;
    }

    public List<PersonDTO> getPersonCovered() {
        return personCovered;
    }

    public void setPersonCovered(List<PersonDTO> personCovered) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCovered that = (PersonCovered) o;
        return childrenNumber == that.childrenNumber && adultNumber == that.adultNumber && Objects.equals(personCovered, that.personCovered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personCovered, childrenNumber, adultNumber);
    }

    @Override
    public String toString() {
        return "PersonCovered{" +
                "personCovered=" + personCovered +
                ", childrenNumber=" + childrenNumber +
                ", adultNumber=" + adultNumber +
                '}';
    }
}

