package bnb;

import bnb.components.SearchComponent;
import bnb.factories.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TestsClass {

    private ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Test
    public void testName() {
        driver.get("https://www.airbnb.com/");

        LocalDate checkIn = LocalDate.now().plus(1, ChronoUnit.WEEKS);
        LocalDate checkOut = checkIn.plus(1, ChronoUnit.WEEKS);

        new SearchComponent(driver)
                .searchForLocation("Milano")
                .setCheckIn(checkIn)
                .setCheckout(checkOut);

        System.out.println("Done");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
