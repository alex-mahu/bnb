package bnb.models;

public final class Accommodation {
    private String name;
    private int guests;

    public Accommodation(String name, int guests) {
        this.name = name;
        this.guests = guests;
    }

    public String getName() {
        return name;
    }

    public int getGuests() {
        return guests;
    }

    @Override
    public String toString() {
        return String.format("{%n\"name\":\"%s\",%n\"guests\":%d%n}%n", name, guests);
    }
}
