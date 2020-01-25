package bnb.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

import static bnb.constants.ConfigurationConstants.IMPLICIT_WAIT;
import static org.openqa.selenium.Platform.ANY;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static FirefoxDriver getDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver(getOptions());
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private static FirefoxOptions getOptions() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(ANY);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("disable-infobars");
        return firefoxOptions.merge(capabilities);
    }

//    private static void maximizeBrowser(ChromeDriver driver) {
//        Point position = new Point(0, 0);
//        driver.manage().window().setPosition(position);
//        final int availWidth = toIntExact((long) (driver.executeScript("return window.screen.availWidth")));
//        final int availHeight = toIntExact((long) (driver.executeScript("return window.screen.availHeight")));
//        Dimension maximizedScreenSize = new Dimension(availWidth, availHeight);
//        driver.manage().window().setSize(maximizedScreenSize);
//    }

}
