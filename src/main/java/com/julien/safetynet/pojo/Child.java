package com.julien.safetynet.pojo;

import com.julien.safetynet.DTO.PersonDTO;

import java.util.List;
import java.util.Objects;

public class Child {
    String firstName;
    String lastName;
    int age;
    List<PersonDTO> otherPersonInHousehold;

    public Child() {}

    public Child(String firstName, String lastName, int age, List<PersonDTO> otherPersonInHousehold) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.otherPersonInHousehold = otherPersonInHousehold;
    }

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

    public List<PersonDTO> getOtherPersonInHousehold() {
        return otherPersonInHousehold;
    }

    public void setOtherPersonInHousehold(List<PersonDTO> otherPersonInHousehold) {
        this.otherPersonInHousehold = otherPersonInHousehold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child that = (Child) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(otherPersonInHousehold, that.otherPersonInHousehold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, otherPersonInHousehold);
    }

    @Override
    public String toString() {
        return "ChildByAddress{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", otherPersonInHousehold=" + otherPersonInHousehold +
                '}';
    }
}
