package bnb.components;

import bnb.models.AccommodationDetails;
import bnb.models.MoreFilters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

import static bnb.helpers.DriverHelper.scrollToElement;
import static bnb.models.MoreFilters.POOL;

public class AccommodationDetailsComponent {
    private final FirefoxDriver driver;

    private static final By AMENITIES = By.id("amenities");
    private static final By EXPAND_SECTION = By.tagName("button");
    private static final By ALL_AMENITIES = By.cssSelector("[aria-label='Amenities']");
    private static final By FACILITIES_SECTION = By.xpath("//*[text()='Facilities']//ancestor::section[position()=1]");
    private final WebElement amenitiesElement;


    public AccommodationDetailsComponent(FirefoxDriver driver) {
        this.driver = driver;
        amenitiesElement = driver.findElement(AMENITIES);
    }

    public AccommodationDetails getAccommodationDetails() {
        scrollToElement(driver, amenitiesElement);
        openAllAmenities();
        AccommodationDetails accommodationDetails = gatherAmenitiesInformation();
        return accommodationDetails;
    }

    private AccommodationDetails gatherAmenitiesInformation() {
        final WebElement facilitiesElement = driver.findElement(ALL_AMENITIES).findElement(FACILITIES_SECTION);
        final List<MoreFilters> filters = new ArrayList();
        boolean hasPool = facilitiesElement.getText().contains("Pool");
        if (hasPool) {
            filters.add(POOL);
        }
        return new AccommodationDetails(filters.toArray(new MoreFilters[]{}));
    }

    private void openAllAmenities() {
        final WebElement allAmenitiesElement = amenitiesElement.findElement(EXPAND_SECTION);
        scrollToElement(driver, allAmenitiesElement);
        allAmenitiesElement.click();
    }
}
