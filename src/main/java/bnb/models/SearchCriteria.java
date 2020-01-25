package bnb.models;

import java.time.LocalDate;

public final class SearchCriteria {
    private String location;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adults;
    private int children;
    private int minBedrooms;

    public SearchCriteria(String location, LocalDate checkInDate, LocalDate checkOutDate, int adults, int children, int minBedrooms) {
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adults = adults;
        this.children = children;
        this.minBedrooms = minBedrooms;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int computeGuests() {
        return adults + children;
    }

    public int getMinBedrooms() {
        return minBedrooms;
    }
}
