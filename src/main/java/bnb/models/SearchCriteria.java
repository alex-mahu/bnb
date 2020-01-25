package bnb.models;

import java.time.LocalDate;

public final class SearchCriteria {
    private String location;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adults;
    private int children;

    public SearchCriteria(String location, LocalDate checkInDate, LocalDate checkOutDate, int adults, int children) {
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adults = adults;
        this.children = children;
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
}
