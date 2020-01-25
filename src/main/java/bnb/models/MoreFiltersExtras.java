package bnb.models;

public enum MoreFiltersExtras {
    POOL(7);

    private int locatorPart;

    MoreFiltersExtras(int locatorPart) {
        this.locatorPart = locatorPart;
    }

    public int getLocatorPart() {
        return locatorPart;
    }
}
