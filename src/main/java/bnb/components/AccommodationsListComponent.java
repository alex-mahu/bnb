package bnb.components;

import bnb.models.Accommodation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public final class AccommodationsListComponent {
    private static final By ACCOMMODATION_LIST_LOCATOR = By.cssSelector("[itemprop='itemList']");
    private static final By ACCOMMODATION_LIST_ITEM_LOCATOR = By.cssSelector("[itemprop='itemListElement']");

    private final List<WebElement> accommodations;

    public AccommodationsListComponent(ChromeDriver driver) {
        accommodations = driver.findElement(ACCOMMODATION_LIST_LOCATOR)
                .findElements(ACCOMMODATION_LIST_ITEM_LOCATOR);
    }

    public List<Accommodation> getAccommodationsInformationFromPage() {
        List<Accommodation> accommodationsFromPage = new ArrayList<>();

        accommodations.forEach(accommodation -> accommodationsFromPage.add(new AccommodationComponent(accommodation).getAccommodationFromPage()));

        return accommodationsFromPage;
    }
}
