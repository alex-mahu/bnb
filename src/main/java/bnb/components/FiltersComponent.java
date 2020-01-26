package bnb.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class FiltersComponent {
    private final WebDriver driver;

    private static final By GUESTS_FILTER_BUTTON = By.id("menuItemButton-guest_picker");
    private static final By DATES_FILTER_BUTTON = By.id("menuItemButton-date_picker");
    private static final By MORE_FILTERS_BUTTON = By.cssSelector("[aria-label*='More filters']");

    public FiltersComponent(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void openMoreFilters() {
        driver.findElement(MORE_FILTERS_BUTTON).click();
    }

    @Step
    public String getDatesFilterValue() {
        return driver.findElement(DATES_FILTER_BUTTON).getText();
    }

    @Step
    public String getGuestsFilterValue() {
        return driver.findElement(GUESTS_FILTER_BUTTON).getText();
    }
}
