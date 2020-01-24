package bnb.components;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public final class FiltersComponent {
    private final ChromeDriver driver;

    private static final By GUESTS_FILTER_BUTTON = By.id("menuItemButton-guest_picker");
    private static final By DATES_FILTER_BUTTON = By.id("menuItemButton-date_picker");

    public FiltersComponent(ChromeDriver driver) {
        this.driver = driver;
    }

    public String getDatesFilterValue() {
        return driver.findElement(DATES_FILTER_BUTTON).getText();
    }

    public String getGuestsFilterValue() {
        return driver.findElement(GUESTS_FILTER_BUTTON).getText();
    }
}
