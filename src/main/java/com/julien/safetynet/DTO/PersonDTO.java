package com.julien.safetynet.DTO;

import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.entity.PersonEntity;

import java.util.Objects;

public class PersonDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonDTO() {}

    public PersonDTO(PersonEntity person) {
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
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && Objects.equals(phone, personDTO.phone);
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
