package bnb.components;

import bnb.models.AccommodationDetails;
import bnb.models.AccommodationInformation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static bnb.helpers.DriverHelper.getWindowHandleOtherThan;
import static bnb.helpers.DriverHelper.scrollToElement;

public final class AccommodationsListComponent {
    private static final By ACCOMMODATION_LIST_LOCATOR = By.cssSelector("[itemprop='itemList']");
    private static final By ACCOMMODATION_LIST_ITEM_LOCATOR = By.cssSelector("[itemprop='itemListElement']");

    private final List<WebElement> accommodations;
    private final FirefoxDriver driver;
    private final String currentTabHandle;

    public AccommodationsListComponent(FirefoxDriver driver) {
        accommodations = driver.findElement(ACCOMMODATION_LIST_LOCATOR)
                .findElements(ACCOMMODATION_LIST_ITEM_LOCATOR);
        this.driver = driver;
        currentTabHandle = driver.getWindowHandle();
    }

    public List<AccommodationInformation> getAccommodationsInformationFromPage() {
        List<AccommodationInformation> accommodationsFromPage = new ArrayList<>();

        accommodations.forEach(accommodation -> accommodationsFromPage.add(new AccommodationComponent(accommodation).getAccommodationFromPage()));

        return accommodationsFromPage;
    }

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

}
