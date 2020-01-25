package bnb.helpers;

import bnb.models.AccommodationInformation;

import java.util.List;
import java.util.stream.Collectors;

public final class AccommodationsHelper {

    private AccommodationsHelper() {
    }

    public static List<AccommodationInformation> takeAccommodationsThatHaveLessMaxGuests(List<AccommodationInformation> accommodationInformations, int maxAllowedGuests) {
        return accommodationInformations.stream()
                .filter(accommodationInformation -> accommodationInformation.getGuests() < maxAllowedGuests)
                .collect(Collectors.toList());
    }

    public static List<AccommodationInformation> takeAccommodationsThatHaveLessThanBedrooms(List<AccommodationInformation> accommodationInformations, int minBedrooms) {
        return accommodationInformations.stream()
                .filter(accommodationInformation -> accommodationInformation.getBedrooms() < minBedrooms)
                .collect(Collectors.toList());
    }
}
