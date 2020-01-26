package bnb.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import static bnb.helpers.DriverHelper.waitableClick;

public final class DatePickerComponent {

    private static final By DATE_PICKER_LOCATOR = By.cssSelector("[aria-roledescription='datepicker']");
    private static final By NEXT_MONTH_ARROW_LOCATOR = By.cssSelector("[aria-label='Move forward to switch to the next month.']");
    private final WebElement datePickerElement;
    private final FirefoxDriver driver;

    public DatePickerComponent(FirefoxDriver driver) {
        datePickerElement = driver.findElement(DATE_PICKER_LOCATOR);
        this.driver = driver;
    }

    private boolean shouldMoveToNextMonth(Month expectedMonth) {
        String yearAndMonthFromPicker = datePickerElement.findElement(By.cssSelector("[data-visible='true'] strong")).getText();
        String month = yearAndMonthFromPicker.split(" ")[0].toUpperCase();

        return Month.valueOf(month) != expectedMonth;
    }

    public void selectDate(LocalDate date) {
        if (shouldMoveToNextMonth(date.getMonth())) {
            datePickerElement.findElement(NEXT_MONTH_ARROW_LOCATOR).click();
        }

        String dateCellLocator = String.format("[aria-label*='%s %d,']",
                date.getMonth().getDisplayName(TextStyle.FULL, Locale.US),
                date.getDayOfMonth());

        final WebElement dateElement = datePickerElement.findElement(By.cssSelector(dateCellLocator));

        waitableClick(driver, dateElement);
    }


}
