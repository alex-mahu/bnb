package bnb.components;

import bnb.models.Accommodation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class AccommodationComponent {
    private final WebElement accommodation;
    private static final By ACCOMMODATION_NAME_LOCATOR = By.cssSelector("._1ebt2xej");
    private static final By ACCOMMODATION_INFORMATION_LOCATOR = By.cssSelector("._1s7voim");
    private static final String SEPARATOR = " · ";

    public AccommodationComponent(WebElement accommodation) {
        this.accommodation = accommodation;
    }

    public Accommodation getAccommodationFromPage() {
        String accommodationName = accommodation.findElement(ACCOMMODATION_NAME_LOCATOR).getText();
        int maximumGuestsAllowed = getMaxAllowedGuests();
        return new Accommodation(accommodationName, maximumGuestsAllowed);
    }

    private int getMaxAllowedGuests() {
        //the first element found will always be the accommodation information
        final String accommodationInformation = accommodation.findElement(ACCOMMODATION_INFORMATION_LOCATOR).getText();
        int maxGuestsAllowed = Integer.parseInt(accommodationInformation.split(SEPARATOR)[0].split(" ")[0].trim());
        return maxGuestsAllowed;
    }
}
