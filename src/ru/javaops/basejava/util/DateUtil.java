package ru.javaops.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static YearMonth parsing(String text) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("M/uuuu");
        return YearMonth.parse(text, f);
    }
}
