package com.julien.safetynet.pojo;

import java.util.List;
import java.util.Objects;

public class Hearth {
    String address;
    List<Inhabitant> inhabitantList;

    public Hearth() {}

    public Hearth(String address, List<Inhabitant> inhabitantList) {
        this.address = address;
        this.inhabitantList = inhabitantList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Inhabitant> getInhabitantList() {
        return inhabitantList;
    }

    public void setInhabitantList(List<Inhabitant> inhabitantList) {
        this.inhabitantList = inhabitantList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hearth hearth = (Hearth) o;
        return Objects.equals(address, hearth.address) && Objects.equals(inhabitantList, hearth.inhabitantList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, inhabitantList);
    }

    @Override
    public String toString() {
        return "Hearth{" +
                "address='" + address + '\'' +
                ", inhabitantList=" + inhabitantList +
                '}';
    }
}
