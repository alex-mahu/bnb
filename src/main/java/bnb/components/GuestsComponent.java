package bnb.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public final class GuestsComponent {
    private final ChromeDriver driver;

    private static final By ADULTS_COUNT_LOCATOR = By.cssSelector("[aria-labelledby*='-adults']");
    private static final By CHILDREN_COUNT_LOCATOR = By.cssSelector("[aria-labelledby*='-children']");
    private static final By CURRENT_PEOPLE_COUNT = By.cssSelector("[aria-hidden='true']");
    private static final By SAVE_BUTTON_LOCATOR = By.id("filter-panel-save-button");

    public GuestsComponent(ChromeDriver driver) {
        this.driver = driver;
    }

    public GuestsComponent havingAdults(int adults) {
        setCountTo(ADULTS_COUNT_LOCATOR, adults);
        return this;
    }

    public GuestsComponent havingChildren(int children) {
        setCountTo(CHILDREN_COUNT_LOCATOR, children);
        return this;
    }

    public void saveGuestsSelection() {
        driver.findElement(SAVE_BUTTON_LOCATOR).click();
    }

    private void setCountTo(By forPersonLocator, int number) {
        final WebElement personTypeElement = driver.findElement(forPersonLocator);

        int numberOfTimeToModifyPersonQuantity = number - takeCurrentQuantityForPersonType(personTypeElement);

        if (numberOfTimeToModifyPersonQuantity == 0) {
            return;
        }

        By quantityModificationElementLocator = numberOfTimeToModifyPersonQuantity > 0
                ? By.cssSelector("[aria-label='add']")
                : By.cssSelector("[aria-label='subtract']");

        WebElement quantityModificationElement = personTypeElement.findElement(quantityModificationElementLocator);

        for (int i = 0; i < Math.abs(numberOfTimeToModifyPersonQuantity); i++) {
            quantityModificationElement.click();
        }
    }

    private Integer takeCurrentQuantityForPersonType(WebElement forPersonElement) {
        String currentNumberOfPeople = forPersonElement.findElement(CURRENT_PEOPLE_COUNT).getText();
        Integer numberOfPeople = Integer.parseInt(currentNumberOfPeople.split("\\+")[0]);
        return numberOfPeople;
    }
}
