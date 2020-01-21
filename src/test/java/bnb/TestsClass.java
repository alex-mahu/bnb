package bnb;

import bnb.factories.WebDriverFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestsClass {

    private ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        driver = WebDriverFactory.getDriver();
    }

    @Test
    public void testName() {
        driver.get("https://google.com");

        System.out.println("Done");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
