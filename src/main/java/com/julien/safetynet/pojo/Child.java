package com.julien.safetynet.pojo;

import com.julien.safetynet.DTO.PersonCoveredDTO;

import java.util.List;

public class Child {
    private String firstName;
    private String lastName;
    private int age;
    private List<PersonCoveredDTO> otherPersonInHousehold;

    public Child() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PersonCoveredDTO> getOtherPersonInHousehold() {
        return otherPersonInHousehold;
    }

    public void setOtherPersonInHousehold(List<PersonCoveredDTO> otherPersonInHousehold) {
        this.otherPersonInHousehold = otherPersonInHousehold;
    }
}
