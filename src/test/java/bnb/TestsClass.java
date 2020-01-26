package bnb;

import bnb.components.*;
import bnb.factories.DriverFactory;
import bnb.helpers.DateGenerator;
import bnb.helpers.PageNavigation;
import bnb.models.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static bnb.helpers.AccommodationsHelper.takeAccommodationsThatHaveLessMaxGuests;
import static bnb.helpers.AccommodationsHelper.takeAccommodationsThatHaveLessThanBedrooms;
import static bnb.helpers.DateGenerator.getExpectedDurationFilterString;
import static bnb.models.MoreFilters.POOL;
import static java.lang.String.format;

public class TestsClass {

    private ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        driverHolder.set(DriverFactory.getDriver());
    }

    private WebDriver getDriver() {
        return driverHolder.get();
    }

    @DataProvider(name = "searchDataProvider")
    public Object[] searchDataProvider() {
        DateGenerator dateGenerator = new DateGenerator();
        return new Object[]{new SearchCriteria("Rome, Italy", dateGenerator.getCheckInDate(), dateGenerator.getCheckOutDate(), 2, 1, 0)};
    }

    @Test(dataProvider = "searchDataProvider")
    public void test1(SearchCriteria searchCriteria) {

        WebDriver driver = getDriver();

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

        final List<AccommodationInformation> accommodationsInformationFromPage = new AccommodationsListComponent(driver)
                .getAccommodationsInformationFromPage();

        final List<AccommodationInformation> filteredAccommodationsInformation = takeAccommodationsThatHaveLessMaxGuests(accommodationsInformationFromPage, guests);

        checker.assertTrue(filteredAccommodationsInformation.isEmpty(), format("Accommodations that have less than %d guests are: %n %s", guests, filteredAccommodationsInformation));
        checker.assertAll();
    }

    @DataProvider(name = "extraFiltersDataProvider")
    public Object[][] extraFiltersDataProvider() {
        DateGenerator dateGenerator = new DateGenerator();
        return new Object[][]{{
            new SearchCriteria("Rome, Italy", dateGenerator.getCheckInDate(), dateGenerator.getCheckOutDate(), 2, 1, 5),
            new MoreFilters[] {POOL}}};
    }

    @Test(dataProvider = "extraFiltersDataProvider")
    public void test2(SearchCriteria searchCriteria, MoreFilters... filters) {

        WebDriver driver = getDriver();

        new PageNavigation(driver).navigateToAirBnBSearchResultPage(searchCriteria);

        final FiltersComponent filtersComponent = new FiltersComponent(driver);
        filtersComponent.openMoreFilters();

        final MoreFiltersComponent moreFiltersComponent = new MoreFiltersComponent(driver);
        moreFiltersComponent.setNumberOfBedrooms(searchCriteria.getMinBedrooms())
                .setFilterOptions(filters)
                .saveMoreFilters();

        final AccommodationsListComponent accommodationsListComponent = new AccommodationsListComponent(driver);

        final List<AccommodationInformation> accommodationsFromPage = accommodationsListComponent
                .getAccommodationsInformationFromPage();

        final List<AccommodationInformation> filteredAccommodationsInformation = takeAccommodationsThatHaveLessThanBedrooms(accommodationsFromPage, searchCriteria.getMinBedrooms());

        SoftAssert checker = new SoftAssert();

        checker.assertTrue(filteredAccommodationsInformation.isEmpty(), format("Accommodations that have less than %d bedrooms are: %n %s", searchCriteria.getMinBedrooms(), filteredAccommodationsInformation));

        final AccommodationDetails accommodationDetails = accommodationsListComponent.getDetailsOf(0);

        checker.assertEquals(accommodationDetails.getFilters(), filters);
        checker.assertAll();
    }

    @DataProvider(name = "mapCheckDataProvider")
    public Object[][] mapCheckDataProvider() {
        DateGenerator dateGenerator = new DateGenerator();
        return new Object[][]{ {
                new SearchCriteria("Rome, Italy", dateGenerator.getCheckInDate(), dateGenerator.getCheckOutDate(), 2, 1, 0), 0}};
    }

    @Test(dataProvider = "mapCheckDataProvider")
    public void test3(SearchCriteria searchCriteria, int listIndexAccommodation) {

        WebDriver driver = getDriver();

        new PageNavigation(driver).navigateToAirBnBSearchResultPage(searchCriteria);

        final AccommodationsListComponent accommodationsListComponent = new AccommodationsListComponent(driver);

        AccommodationMapDetails mapDetailsFromList = accommodationsListComponent
                .getMapAccommodationDetails(listIndexAccommodation);

        accommodationsListComponent.hoverOverAccommodationFromList(listIndexAccommodation);

        MapComponent mapComponent = new MapComponent(driver);

        final AccommodationMapDetails mapDetailsFromOpenedPin = mapComponent.openHighlightedPin()
                .getMapDetailsFromOpenedPin();

        Assert.assertEquals(mapDetailsFromOpenedPin, mapDetailsFromList);
    }

    @AfterMethod
    public void tearDown() {
        getDriver().quit();
    }
}
