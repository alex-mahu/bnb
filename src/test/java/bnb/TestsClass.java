package bnb;

import bnb.components.AccommodationsListComponent;
import bnb.components.FiltersComponent;
import bnb.components.SearchComponent;
import bnb.factories.DriverFactory;
import bnb.helpers.DateGenerator;
import bnb.models.Accommodation;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

import static bnb.helpers.AccommodationsHelper.takeAccommodationsThatHaveLessMaxGuests;

public class TestsClass {

    private ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Test
    public void testName() {
        driver.get("https://www.airbnb.com/");

        DateGenerator dateGenerator = new DateGenerator();
        LocalDate checkIn = dateGenerator.getCheckInDate();
        LocalDate checkOut = dateGenerator.getCheckOutDate();

        new SearchComponent(driver)
                .withLocation("Rome, Italy")
                .setCheckIn(checkIn)
                .setCheckout(checkOut)
                .withGuests(2, 1)
                .search();

        final FiltersComponent filtersComponent = new FiltersComponent(driver);
        final String datesFilterValue = filtersComponent.getDatesFilterValue();
        final String guestsFilterValue = filtersComponent.getGuestsFilterValue();

        SoftAssert checker = new SoftAssert();

        checker.assertEquals(datesFilterValue, dateGenerator.getExpectedDurationFilterString(), "Dates filter does not have the correct value");
        checker.assertEquals(guestsFilterValue, "3 guests", "Guests filter does not have the correct value");

        final List<Accommodation> accommodationsInformationFromPage = new AccommodationsListComponent(driver)
                .getAccommodationsInformationFromPage();

        final List<Accommodation> filteredAccommodations = takeAccommodationsThatHaveLessMaxGuests(accommodationsInformationFromPage, 3);

        checker.assertTrue(filteredAccommodations.isEmpty(), String.format("Accommodations that have less than %d guests are: %n %s", 3, filteredAccommodations));
        checker.assertAll();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
