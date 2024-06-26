package com.julien.safetynet.pojo;

import java.util.List;

public class InhabitantWithFireStation {
    private List<InhabitantWithPhone> inhabitantWithPhoneList;
    private int stationNumber;

    public InhabitantWithFireStation() {}

    public List<InhabitantWithPhone> getInhabitantList() {
        return inhabitantWithPhoneList;
    }

    public void setInhabitantList(List<InhabitantWithPhone> inhabitantWithPhoneList) {
        this.inhabitantWithPhoneList = inhabitantWithPhoneList;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }
}
