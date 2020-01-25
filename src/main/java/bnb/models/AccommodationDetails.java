package bnb.models;

public final class AccommodationDetails {
    private MoreFilters[] filters;

    public AccommodationDetails(MoreFilters[] filters) {
        this.filters = filters;
    }

    public MoreFilters[] getFilters() {
        return filters;
    }
}
