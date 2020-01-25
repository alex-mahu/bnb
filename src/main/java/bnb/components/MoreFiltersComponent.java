package bnb.components;

import bnb.models.MoreFiltersExtras;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static bnb.helpers.DriverHelper.*;
import static java.lang.String.format;

public final class MoreFiltersComponent {
    private static final By EXTRA_SECTION_LOCATOR = By.cssSelector("[aria-label='Extras']");
    private static final By BEDROOMS_LOCATOR = By.id("filterItem-stepper-min_bedrooms-0");
    private static final By MINUS = By.cssSelector("button[aria-label='decrease value']");
    private static final By PLUS = By.cssSelector("button[aria-label='increase value']");
    private static final By HIDE_SHOW_ALL_OPTIONS = By.cssSelector("[style*='transform: rotate(']");
    private static final By SHOW_STAYS = By.cssSelector("footer>button");
    private final FirefoxDriver driver;

    public MoreFiltersComponent(FirefoxDriver driver) {
        this.driver = driver;
    }

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

    public MoreFiltersComponent setExtras(MoreFiltersExtras... extras) {
        final WebElement extraSection = driver.findElement(EXTRA_SECTION_LOCATOR);
        scrollToElement(driver, extraSection);
        for (MoreFiltersExtras extra : extras) {
            expandSection(extraSection);
            By extraOptionLocator = generateExtraOptionLocator(extra);
            WebElement extraOptionElement = extraSection.findElement(extraOptionLocator);
            if (!isOptionSelected(extraOptionElement)) {
                extraOptionElement.click();
            }
        }
        return this;
    }

    public void saveMoreFilters() {
        driver.findElement(SHOW_STAYS).click();
    }

    private boolean isOptionSelected(WebElement option) {
        final WebElement element = option.findElement(By.cssSelector("[data-checkbox]"));
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

    private By generateExtraOptionLocator(MoreFiltersExtras extra) {
        return By.cssSelector(format("label[for='filterItem-checkbox-amenities-%d']", extra.getLocatorPart()));
    }

    private int getNumberOfEntitiesFromElement(WebElement entityElement) {
        int entities = Integer.parseInt(entityElement.getText().split("\n")[0]);
        return entities;
    }
}
