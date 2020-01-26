package bnb.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

import static bnb.constants.ConfigurationConstants.IMPLICIT_WAIT;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        synchronized (DriverFactory.class) {
            final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
            WebDriverManager.firefoxdriver().setup();
            FirefoxDriver driver = new FirefoxDriver(getOptions());
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driverThread.set(driver);
            return driverThread.get();
        }
    }

    private static FirefoxOptions getOptions() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        return firefoxOptions.merge(capabilities);
    }

}
