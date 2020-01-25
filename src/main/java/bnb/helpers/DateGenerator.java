package bnb.helpers;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public final class DateGenerator {

    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public DateGenerator() {
        checkInDate = LocalDate.now().plus(1, ChronoUnit.WEEKS);
        checkOutDate = checkInDate.plus(1, ChronoUnit.WEEKS);
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public static String getExpectedDurationFilterString(LocalDate checkInDate, LocalDate checkOutDate) {
        final Month checkInMonth = checkInDate.getMonth();
        final Month checkOutMonth = checkOutDate.getMonth();
        String checkInMonthAsString = checkInMonth.getDisplayName(TextStyle.SHORT, Locale.US);
        String checkOutMonthAsString = checkInMonth == checkOutMonth
                ? ""
                : checkOutMonth.getDisplayName(TextStyle.SHORT, Locale.US) + " ";
        return String.format("%s %d - %s%d",
                checkInMonthAsString,
                checkInDate.getDayOfMonth(),
                checkOutMonthAsString,
                checkOutDate.getDayOfMonth());
    }


}
