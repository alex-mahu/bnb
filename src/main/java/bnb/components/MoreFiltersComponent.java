package bnb.components;

import bnb.models.MoreFilters;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static bnb.helpers.DriverHelper.*;
import static java.lang.String.format;

public final class MoreFiltersComponent {
    private static final By EXTRA_SECTION_LOCATOR = By.cssSelector("[aria-label='Extras']");
    private static final By FACILITIES_SECTION_LOCATOR = By.cssSelector("[aria-label='Facilities']");
    private static final By BEDROOMS_LOCATOR = By.id("filterItem-stepper-min_bedrooms-0");
    private static final By MINUS = By.cssSelector("button[aria-label='decrease value']");
    private static final By PLUS = By.cssSelector("button[aria-label='increase value']");
    private static final By HIDE_SHOW_ALL_OPTIONS = By.cssSelector("[style*='transform: rotate(']");
    private static final By SHOW_STAYS = By.cssSelector("footer>button");
    private static final By GENERIC_HEADER_LOCATOR = By.cssSelector("[aria-label='More filters'] h2");
    private static final By SELECTED_OPTIONS_LOCATOR = By.cssSelector("[data-checkbox]");
    private final FirefoxDriver driver;

    public MoreFiltersComponent(FirefoxDriver driver) {
        this.driver = driver;
    }

    @Step
    public MoreFiltersComponent setNumberOfBedrooms(int bedrooms) {
        final WebElement bedroomsElement = driver.findElement(BEDROOMS_LOCATOR);
        final int numberOfBedroomsFromPage = getNumberOfEntitiesFromElement(bedroomsElement);
        final int interactWithNumberModificationElement = numberOfBedroomsFromPage - bedrooms;
        final By modificationLocator = interactWithNumberModificationElement > 0
                ? MINUS
                : PLUS;
        final WebElement modificationElement = bedroomsElement.findElement(modificationLocator);

        for (int i = 0; i < Math.abs(interactWithNumberModificationElement); i++) {
            waitableClick(driver, modificationElement);
        }

        return this;
    }

    @Step
    public MoreFiltersComponent setFilterOptions(MoreFilters... extras) {

        if (hasFacilities()) {
            setFacilities(extras);
        } else {
            setExtras(extras);
        }

        return this;
    }

    private void setFacilities(MoreFilters... extras) {
        final WebElement facilitiesSection = driver.findElement(FACILITIES_SECTION_LOCATOR);
        setOptions(facilitiesSection, false, extras);
    }

    private void setExtras(MoreFilters... extras) {
        final WebElement extraSection = driver.findElement(EXTRA_SECTION_LOCATOR);
        setOptions(extraSection, true, extras);
    }

    private boolean hasFacilities() {
        final List<String> headersText = new ArrayList<>();
        driver.findElements(GENERIC_HEADER_LOCATOR).forEach(header -> headersText.add(header.getText()));

        for (String headerText : headersText) {
            if (headerText.contains("Facilities")) {
                return true;
            }
        }
        return false;
    }

    private void setOptions(WebElement section, boolean expandSection, MoreFilters... extras) {
        scrollToElement(driver, section);

        if (expandSection) {
            expandSection(section);
        }

        for (MoreFilters extra : extras) {
            By extraOptionLocator = generateExtraOptionLocator(extra);
            WebElement extraOptionElement = section.findElement(extraOptionLocator);
            if (!isOptionSelected(extraOptionElement)) {
                extraOptionElement.click();
            }
        }
    }

    @Step
    public void saveMoreFilters() {
        driver.findElement(SHOW_STAYS).click();
        new Actions(driver)
                .pause(Duration.of(2, ChronoUnit.SECONDS))
                .perform();
    }

    private boolean isOptionSelected(WebElement option) {
        final WebElement element = option.findElement(SELECTED_OPTIONS_LOCATOR);
        return elementHasChildren(driver, element);
    }

    private void expandSection(WebElement section) {
        WebElement hideShowMoreOptions = section.findElement(HIDE_SHOW_ALL_OPTIONS);
        if (isCollapsed(hideShowMoreOptions)) {
            hideShowMoreOptions.click();
        }
    }

    private boolean isCollapsed(WebElement hideShowMoreOptions) {
        String styleValue = hideShowMoreOptions.getAttribute("style");
        return styleValue.contains("(0deg)");
    }

    private By generateExtraOptionLocator(MoreFilters extra) {
        return By.cssSelector(format("label[for='filterItem-checkbox-amenities-%d']", extra.getLocatorPart()));
    }

    private int getNumberOfEntitiesFromElement(WebElement entityElement) {
        int entities = Integer.parseInt(entityElement.getText().split("\n")[0]);
        return entities;
    }
}
