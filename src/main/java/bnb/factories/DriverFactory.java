package bnb.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;
import static org.openqa.selenium.Platform.ANY;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static ChromeDriver getDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(getOptions());
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        maximizeBrowser(driver);
        return driver;
    }

    private static ChromeOptions getOptions() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(ANY);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        return chromeOptions.merge(capabilities);
    }

    private static void maximizeBrowser(ChromeDriver driver) {
        Point position = new Point(0, 0);
        driver.manage().window().setPosition(position);
        final int availWidth = toIntExact((long) (driver.executeScript("return window.screen.availWidth")));
        final int availHeight = toIntExact((long) (driver.executeScript("return window.screen.availHeight")));
        Dimension maximizedScreenSize = new Dimension(availWidth, availHeight);
        driver.manage().window().setSize(maximizedScreenSize);
    }

}
