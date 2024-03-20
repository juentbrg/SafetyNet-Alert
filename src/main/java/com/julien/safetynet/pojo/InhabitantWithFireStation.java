package com.julien.safetynet.pojo;

import java.util.List;
import java.util.Objects;

public class InhabitantWithFireStation {
    List<Inhabitant> inhabitantList;
    int stationNumber;

    public InhabitantWithFireStation() {}

    public InhabitantWithFireStation(List<Inhabitant> inhabitantList, int stationNumber) {
        this.inhabitantList = inhabitantList;
        this.stationNumber = stationNumber;
    }

    public List<Inhabitant> getInhabitantList() {
        return inhabitantList;
    }

    public void setInhabitantList(List<Inhabitant> inhabitantList) {
        this.inhabitantList = inhabitantList;
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
        return stationNumber == that.stationNumber && Objects.equals(inhabitantList, that.inhabitantList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inhabitantList, stationNumber);
    }

    @Override
    public String toString() {
        return "InhabitantWithFireStation{" +
                "inhabitantList=" + inhabitantList +
                ", stationNumber=" + stationNumber +
                '}';
    }
}
