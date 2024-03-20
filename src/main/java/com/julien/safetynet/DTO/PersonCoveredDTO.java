package com.julien.safetynet.DTO;

import com.julien.safetynet.entity.PersonEntity;

import java.util.Objects;

public class PersonCoveredDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonCoveredDTO() {}

    public PersonCoveredDTO(PersonEntity person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCoveredDTO personCoveredDTO = (PersonCoveredDTO) o;
        return Objects.equals(firstName, personCoveredDTO.firstName) && Objects.equals(lastName, personCoveredDTO.lastName) && Objects.equals(address, personCoveredDTO.address) && Objects.equals(phone, personCoveredDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, phone);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
