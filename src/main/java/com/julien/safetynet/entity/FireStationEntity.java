package com.julien.safetynet.entity;

import java.util.Objects;

public class FireStationEntity {
    private String address;
    private Integer station;

    public FireStationEntity() { }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
