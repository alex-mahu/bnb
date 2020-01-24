package bnb.helpers;

import bnb.models.Accommodation;

import java.util.List;
import java.util.stream.Collectors;

public final class AccommodationsHelper {

    private AccommodationsHelper() {
    }

    public static List<Accommodation> takeAccommodationsThatHaveLessMaxGuests(List<Accommodation> accommodations, int maxAllowedGuests) {
        return accommodations.stream()
                .filter(accommodation -> accommodation.getGuests() < maxAllowedGuests)
                .collect(Collectors.toList());
    }
}
