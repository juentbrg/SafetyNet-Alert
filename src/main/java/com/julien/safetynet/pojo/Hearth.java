package com.julien.safetynet.pojo;

import java.util.List;
import java.util.Objects;

public class Hearth {
    private String address;
    private List<InhabitantWithPhone> inhabitantWithPhoneList;

    public Hearth() {}

    public Hearth(String address, List<InhabitantWithPhone> inhabitantWithPhoneList) {
        this.address = address;
        this.inhabitantWithPhoneList = inhabitantWithPhoneList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<InhabitantWithPhone> getInhabitantList() {
        return inhabitantWithPhoneList;
    }

    public void setInhabitantList(List<InhabitantWithPhone> inhabitantWithPhoneList) {
        this.inhabitantWithPhoneList = inhabitantWithPhoneList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hearth hearth = (Hearth) o;
        return Objects.equals(address, hearth.address) && Objects.equals(inhabitantWithPhoneList, hearth.inhabitantWithPhoneList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, inhabitantWithPhoneList);
    }

    @Override
    public String toString() {
        return "Hearth{" +
                "address='" + address + '\'' +
                ", inhabitantList=" + inhabitantWithPhoneList +
                '}';
    }
}
