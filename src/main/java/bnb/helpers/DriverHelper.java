package bnb.helpers;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static bnb.constants.ConfigurationConstants.IMPLICIT_WAIT;
import static java.time.Duration.of;
import static java.time.temporal.ChronoUnit.MILLIS;

public final class DriverHelper {

    private DriverHelper(){}

    public static void waitableClick(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, IMPLICIT_WAIT)
                .until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (ElementClickInterceptedException ex) {
            new Actions(driver)
                    .pause(of(500, MILLIS))
                    .click(element)
                    .perform();
        }
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        if (!element.isDisplayed()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        }
    }

    public static boolean elementHasChildren(WebDriver driver, WebElement element) {
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].hasChildNodes();", element);
    }

    public static String getWindowHandleOtherThan(WebDriver driver, String windowHandle) {
        Set<String> windowHandles = driver.getWindowHandles();
        while (windowHandles.size() == 1) {
            windowHandles = driver.getWindowHandles();
        }
        windowHandles.remove(windowHandle);
        return (String) windowHandles.toArray()[0];
    }
}
