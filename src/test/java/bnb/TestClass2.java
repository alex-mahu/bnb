package bnb;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TestClass2 {

    @Test
    public void testName() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("https://www.airbnb.com");

        final WebElement element = driver.findElement(By.xpath("//*[text()='Facilities']//ancestor::section[position()=1]"));

        System.out.println(element.getText());
    }
}
