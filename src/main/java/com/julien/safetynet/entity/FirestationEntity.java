package com.julien.safetynet.entity;

import java.util.Objects;

public class FirestationEntity {
    private String address;
    private int station;

    public FirestationEntity() { }

    public FirestationEntity(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirestationEntity that = (FirestationEntity) o;
        return station == that.station && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }

    @Override
    public String toString() {
        return "FirestationEntity{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
