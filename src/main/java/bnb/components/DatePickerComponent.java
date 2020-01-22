package bnb.components;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public final class DatePickerComponent {

    private final WebDriver driver;
    private final WebElement datePickerElement;

    private static final By DATE_PICKER_LOCATOR = By.cssSelector("[aria-roledescription='datepicker']");
    private static final By NEXT_MONTH_ARROW_LOCATOR = By.cssSelector("[aria-label='Move forward to switch to the next month.']");

    public DatePickerComponent(WebDriver driver) {
        this.driver = driver;
        datePickerElement = driver.findElement(DATE_PICKER_LOCATOR);
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

        datePickerElement.findElement(By.cssSelector(dateCellLocator)).click();
    }


}
