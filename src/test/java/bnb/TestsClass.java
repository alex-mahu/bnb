package bnb;

import bnb.components.AccommodationsListComponent;
import bnb.components.FiltersComponent;
import bnb.components.MoreFiltersComponent;
import bnb.components.SearchComponent;
import bnb.factories.DriverFactory;
import bnb.helpers.DateGenerator;
import bnb.helpers.PageNavigation;
import bnb.models.Accommodation;
import bnb.models.SearchCriteria;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static bnb.helpers.AccommodationsHelper.takeAccommodationsThatHaveLessMaxGuests;
import static bnb.helpers.DateGenerator.getExpectedDurationFilterString;
import static bnb.models.MoreFiltersExtras.POOL;

public class TestsClass {

    private FirefoxDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @DataProvider(name = "searchDataProvider")
    public Object[] searchDataProvider() {
        DateGenerator dateGenerator = new DateGenerator();
        return new Object[]{new SearchCriteria("Rome, Italy", dateGenerator.getCheckInDate(), dateGenerator.getCheckOutDate(), 2, 1)};
    }

    @Test(dataProvider = "searchDataProvider")
    public void test1(SearchCriteria searchCriteria) {

        new PageNavigation(driver).navigateToAirBnB();

        new SearchComponent(driver)
                .withLocation(searchCriteria.getLocation())
                .setCheckIn(searchCriteria.getCheckInDate())
                .setCheckout(searchCriteria.getCheckOutDate())
                .withGuests(searchCriteria.getAdults(), searchCriteria.getChildren())
                .search();

        final FiltersComponent filtersComponent = new FiltersComponent(driver);

        SoftAssert checker = new SoftAssert();

        int guests = searchCriteria.computeGuests();

        checker.assertEquals(filtersComponent.getDatesFilterValue(), getExpectedDurationFilterString(searchCriteria.getCheckInDate(), searchCriteria.getCheckOutDate()), "Dates filter does not have the correct value");
        checker.assertEquals(filtersComponent.getGuestsFilterValue(), guests + " guests", "Guests filter does not have the correct value");

        final List<Accommodation> accommodationsInformationFromPage = new AccommodationsListComponent(driver)
                .getAccommodationsInformationFromPage();

        final List<Accommodation> filteredAccommodations = takeAccommodationsThatHaveLessMaxGuests(accommodationsInformationFromPage, guests);

        checker.assertTrue(filteredAccommodations.isEmpty(), String.format("Accommodations that have less than %d guests are: %n %s", guests, filteredAccommodations));
        checker.assertAll();
    }

    @DataProvider(name = "extraFiltersDataProvider")
    public Object[] extraFiltersDataProvider() {
        DateGenerator dateGenerator = new DateGenerator();
        return new Object[]{new SearchCriteria("Rome, Italy", dateGenerator.getCheckInDate(), dateGenerator.getCheckOutDate(), 2, 1)};
    }

    @Test(dataProvider = "extraFiltersDataProvider")
    public void test2(SearchCriteria searchCriteria) {
        new PageNavigation(driver).navigateToAirBnBSearchResultPage(searchCriteria);

        final FiltersComponent filtersComponent = new FiltersComponent(driver);
        filtersComponent.openMoreFilters();

        final MoreFiltersComponent moreFiltersComponent = new MoreFiltersComponent(driver);
        moreFiltersComponent.setNumberOfBedrooms(5)
                .setExtras(POOL)
                .saveMoreFilters();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
