package bnb.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.Month;

import static java.util.Objects.isNull;

public final class SearchComponent {

    private final ChromeDriver driver;

    private static final By SEARCH_LOCATOR = By.name("query");
    private static final By CHECK_IN_LOCATOR = By.id("checkin_input");
    private static final By CHECK_OUT_LOCATOR = By.id("checkout_input");

    public SearchComponent(ChromeDriver driver) {
        this.driver = driver;
    }

    public SearchComponent searchForLocation(String location) {
        WebElement search = driver.findElement(SEARCH_LOCATOR);
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

    private void setDate(LocalDate date, By forDatePart) {
        driver.findElement(forDatePart).click();

        new DatePickerComponent(driver)
                .selectDate(date);
    }





}
