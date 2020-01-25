package bnb.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.LocalDate;

public final class SearchComponent {

    private static final By SEARCH_LOCATION_LOCATOR = By.name("query");
    private static final By CHECK_IN_LOCATOR = By.id("checkin_input");
    private static final By CHECK_OUT_LOCATOR = By.id("checkout_input");
    private static final By GUESTS_DROPDOWN_LOCATOR = By.id("lp-guestpicker");
    private static final By SEARCH_BUTTON_LOCATOR = By.cssSelector("[type='submit']");
    private final FirefoxDriver driver;

    public SearchComponent(FirefoxDriver driver) {
        this.driver = driver;
    }

    public SearchComponent withLocation(String location) {
        WebElement search = driver.findElement(SEARCH_LOCATION_LOCATOR);
        search.sendKeys(location);
        search.sendKeys(Keys.ENTER);
        return this;
    }

    public SearchComponent setCheckIn(LocalDate date) {
        setDate(date, CHECK_IN_LOCATOR);
        return this;
    }

    public SearchComponent setCheckout(LocalDate date) {
        setDate(date, CHECK_OUT_LOCATOR);
        return this;
    }

    public SearchComponent withGuests(int adults, int children) {
        driver.findElement(GUESTS_DROPDOWN_LOCATOR).click();
        new GuestsComponent(driver)
                .havingAdults(adults)
                .havingChildren(children)
                .saveGuestsSelection();
        return this;
    }

    public void search() {
        driver.findElement(SEARCH_BUTTON_LOCATOR).click();
    }

    private void setDate(LocalDate date, By forDatePart) {
        driver.findElement(forDatePart).click();

        new DatePickerComponent(driver)
                .selectDate(date);
    }
}
