package com.julien.safetynet.pojo;

import java.util.List;
import java.util.Objects;

public class InhabitantWithFireStation {
    List<InhabitantWithPhone> inhabitantWithPhoneList;
    int stationNumber;

    public InhabitantWithFireStation() {}

    public InhabitantWithFireStation(List<InhabitantWithPhone> inhabitantWithPhoneList, int stationNumber) {
        this.inhabitantWithPhoneList = inhabitantWithPhoneList;
        this.stationNumber = stationNumber;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InhabitantWithFireStation that = (InhabitantWithFireStation) o;
        return stationNumber == that.stationNumber && Objects.equals(inhabitantWithPhoneList, that.inhabitantWithPhoneList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inhabitantWithPhoneList, stationNumber);
    }

    @Override
    public String toString() {
        return "InhabitantWithFireStation{" +
                "inhabitantList=" + inhabitantWithPhoneList +
                ", stationNumber=" + stationNumber +
                '}';
    }
}
