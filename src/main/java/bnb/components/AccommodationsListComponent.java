package bnb.components;

import bnb.models.AccommodationDetails;
import bnb.models.AccommodationInformation;
import bnb.models.AccommodationMapDetails;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static bnb.helpers.DriverHelper.getWindowHandleOtherThan;
import static bnb.helpers.DriverHelper.scrollToElement;
import static java.time.temporal.ChronoUnit.MILLIS;

public final class AccommodationsListComponent {
    private static final By ACCOMMODATION_LIST_LOCATOR = By.cssSelector("[itemprop='itemList']");
    private static final By ACCOMMODATION_LIST_ITEM_LOCATOR = By.cssSelector("[itemprop='itemListElement']");
    private static final By ACCOMMODATION_LIST_TITLE_AND_RATING = By.cssSelector("._4ntfzh");
    private static final By ACCOMMODATION_LIST_RATING_ONLY = By.cssSelector("._60dc7z");
    private static final By ACCOMMODATION_LIST_NAME = By.cssSelector("._1ebt2xej");
    private static final By ACCOMMODATION_LIST_PRICE_PER_NIGHT = By.cssSelector("._1l2ab9yk");

    private List<WebElement> accommodations;
    private final FirefoxDriver driver;
    private final String currentTabHandle;

    public AccommodationsListComponent(FirefoxDriver driver) {
        this.driver = driver;
        currentTabHandle = driver.getWindowHandle();
        initializeAccommodationsList();
    }

    private void initializeAccommodationsList() {
        accommodations = driver.findElement(ACCOMMODATION_LIST_LOCATOR)
                .findElements(ACCOMMODATION_LIST_ITEM_LOCATOR);
    }

    @Step
    public List<AccommodationInformation> getAccommodationsInformationFromPage() {
        List<AccommodationInformation> accommodationsFromPage = new ArrayList<>();

        accommodations.forEach(accommodation -> accommodationsFromPage.add(new AccommodationComponent(accommodation).getAccommodationFromPage()));

        return accommodationsFromPage;
    }

    @Step
    public AccommodationDetails getDetailsOf(int accommodationIndex) {
        WebElement accommodation = accommodations.get(accommodationIndex);
        scrollToElement(driver, accommodation);
        new Actions(driver)
                .click(accommodation)
                .perform();
        driver.switchTo().window(getWindowHandleOtherThan(driver, currentTabHandle));
        AccommodationDetails accommodationDetails = new AccommodationDetailsComponent(driver)
                .getAccommodationDetails();
        driver.close();
        driver.switchTo().window(currentTabHandle);
        return accommodationDetails;
    }

    @Step
    public AccommodationMapDetails getMapAccommodationDetails(int accommodationIndex) {
        final WebElement accommodation = accommodations.get(accommodationIndex);
        final String accommodationTitleAndRating = accommodation.findElement(ACCOMMODATION_LIST_TITLE_AND_RATING).getText().trim();
        final String accommodationRating = accommodation.findElement(ACCOMMODATION_LIST_RATING_ONLY).getText().trim();
        final String accommodationTitle = accommodationTitleAndRating.replace(accommodationRating, "").trim();
        final String accommodationName = accommodation.findElement(ACCOMMODATION_LIST_NAME).getText().trim();
        final String accommodationPricePerNight = accommodation.findElement(ACCOMMODATION_LIST_PRICE_PER_NIGHT).getText().trim();
        return new AccommodationMapDetails(accommodationTitle, accommodationName, accommodationPricePerNight, accommodationRating);
    }

    @Step
    public void hoverOverAccommodationFromList(int accommodationIndex) {
        new Actions(driver)
                .moveToElement(accommodations.get(accommodationIndex))
                .pause(Duration.of(500, MILLIS))
                .perform();
    }
}
