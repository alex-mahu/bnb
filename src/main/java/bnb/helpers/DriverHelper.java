package bnb.helpers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static bnb.constants.ConfigurationConstants.IMPLICIT_WAIT;

public final class DriverHelper {

    private DriverHelper(){}

    public static void waitableClick(FirefoxDriver driver, WebElement element) {
        new WebDriverWait(driver, IMPLICIT_WAIT)
                .until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static void scrollToElement(FirefoxDriver driver, WebElement element) {
        driver.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static boolean elementHasChildren(FirefoxDriver driver, WebElement element) {
        return (boolean) driver.executeScript("return arguments[0].hasChildNodes();", element);
    }
}
