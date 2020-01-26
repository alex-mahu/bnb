package bnb.components;

import bnb.models.AccommodationInformation;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public final class AccommodationComponent {
    private final WebElement accommodation;
    private static final By ACCOMMODATION_NAME_LOCATOR = By.cssSelector("._1ebt2xej");
    private static final By ACCOMMODATION_INFORMATION_LOCATOR = By.cssSelector("._1s7voim");
    private static final String SEPARATOR = " · ";
    private final List<String> splitAccommodationInformation;
    private static final int GUESTS = 0;
    private static final int BEDROOMS = 1;

    public AccommodationComponent(WebElement accommodation) {
        this.accommodation = accommodation;
        //the first element found will always be the accommodation information
        final String accommodationInformation = accommodation.findElement(ACCOMMODATION_INFORMATION_LOCATOR).getText();
        splitAccommodationInformation = Arrays.asList(accommodationInformation.split(SEPARATOR));
    }

    @Step
    public AccommodationInformation getAccommodationFromPage() {
        final String accommodationName = accommodation.findElement(ACCOMMODATION_NAME_LOCATOR).getText();
        final int maximumGuestsAllowed = getMaxAllowedGuests();
        final int bedrooms = getBedrooms();
        return new AccommodationInformation(accommodationName, maximumGuestsAllowed, bedrooms);
    }

    private int getMaxAllowedGuests() {
        final int maxGuestsAllowed = getOnlyFirstNumber(splitAccommodationInformation.get(GUESTS));
        return maxGuestsAllowed;
    }

    private int getBedrooms() {
        final int bedrooms = getOnlyFirstNumber(splitAccommodationInformation.get(BEDROOMS));
        return bedrooms;
    }

    private int getOnlyFirstNumber(String fromString) {
        return Integer.parseInt(fromString.split(" ")[0].trim().replace("+", ""));
    }
}
