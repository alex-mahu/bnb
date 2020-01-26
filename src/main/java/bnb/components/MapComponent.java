package bnb.components;

import bnb.models.AccommodationMapDetails;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.util.Objects.isNull;

public final class MapComponent {
    private static final By MAP_PIN_LOCATOR = By.cssSelector("aside [style*='z-index: 9999']>button");
    private static final By MAP_DETAILS_LOCATOR = By.cssSelector("[style*='touch-action: pan-x pan-y;'] [style*='z-index: 10000']");
    private static final By MAP_ACCOMMODATION_TITLE = By.cssSelector("._4ntfzh");
    private static final By MAP_ACCOMMODATION_NAME = By.cssSelector("._86s60oj");
    private static final By MAP_ACCOMMODATION_PRICE = By.cssSelector("._o60r27k");
    private static final By MAP_ACCOMMODATION_RATING = By.cssSelector("._60hvkx2");
    private final FirefoxDriver driver;
    private WebElement openedMapDetails;

    public MapComponent(FirefoxDriver driver) {
        this.driver = driver;
    }

    private WebElement getOpenedMapDetails() {
        if (isNull(openedMapDetails)) {
            openedMapDetails = driver.findElement(MAP_DETAILS_LOCATOR);
        }

        return openedMapDetails;
    }

    @Step
    public MapComponent openHighlightedPin() {
        driver.findElement(MAP_PIN_LOCATOR).click();
        return this;
    }

    @Step
    public AccommodationMapDetails getMapDetailsFromOpenedPin() {
        WebElement mapDetails = getOpenedMapDetails();
        String accommodationTitle = mapDetails.findElement(MAP_ACCOMMODATION_TITLE).getText().trim();
        String accommodationName = mapDetails.findElement(MAP_ACCOMMODATION_NAME).getText().trim();
        String accommodationPrice = mapDetails.findElement(MAP_ACCOMMODATION_PRICE).getText().trim();
        String accommodationRating = mapDetails.findElement(MAP_ACCOMMODATION_RATING).getText().trim();
        return new AccommodationMapDetails(accommodationTitle, accommodationName, accommodationPrice, accommodationRating);
    }
}
