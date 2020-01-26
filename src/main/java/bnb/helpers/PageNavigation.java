package bnb.helpers;

import bnb.models.SearchCriteria;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public final class PageNavigation {
    private final WebDriver driver;
    private static final Map<String, String> LOCATION_TO_URL_STRING;
    private static final String PAGE_LINK = "https://www.airbnb.com";

    static {
        LOCATION_TO_URL_STRING = new HashMap<>();
        LOCATION_TO_URL_STRING.put("Rome, Italy", "Rome--Italy");
    }

    public PageNavigation(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void navigateToAirBnB() {
        driver.get(PAGE_LINK);
    }

    @Step
    public void navigateToAirBnBSearchResultPage(SearchCriteria searchCriteria) {
        String link = String.format("%s/s/%s/homes?query=%s&adults=%d&children=%d&checkin=%s&checkout=%s",
                PAGE_LINK,
                LOCATION_TO_URL_STRING.get(searchCriteria.getLocation()),
                searchCriteria.getLocation(),
                searchCriteria.getAdults(),
                searchCriteria.getChildren(),
                searchCriteria.getCheckInDate(),
                searchCriteria.getCheckOutDate());
        driver.get(link);
    }

}
