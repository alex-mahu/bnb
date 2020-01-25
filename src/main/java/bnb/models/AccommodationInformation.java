package bnb.models;

public final class AccommodationInformation {
    private String name;
    private int guests;
    private final int bedrooms;

    public AccommodationInformation(String name, int guests, int bedrooms) {
        this.name = name;
        this.guests = guests;
        this.bedrooms = bedrooms;
    }

    public String getName() {
        return name;
    }

    public int getGuests() {
        return guests;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    @Override
    public String toString() {
        return String.format("{%n\"name\":\"%s\",%n\"guests\":%d,%n\"bedrooms\":%d%n}%n", name, guests, bedrooms);
    }
}
