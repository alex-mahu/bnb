package bnb.models;

public enum MoreFilters {
    POOL(7);

    private int locatorPart;

    MoreFilters(int locatorPart) {
        this.locatorPart = locatorPart;
    }

    public int getLocatorPart() {
        return locatorPart;
    }
}
