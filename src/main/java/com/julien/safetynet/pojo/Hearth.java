package com.julien.safetynet.pojo;

import java.util.List;
import java.util.Objects;

public class Hearth {
    private String address;
    private List<InhabitantWithPhone> inhabitantWithPhoneList;

    public Hearth() {}

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
}
