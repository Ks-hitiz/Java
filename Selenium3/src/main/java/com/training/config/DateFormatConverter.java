package com.training.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatConverter {
    public static String formatToYYYYMMDD(String firstActionDate) {
        // Input format e.g., "30 May 2022"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH);
        // Output format e.g., "20220530"
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate date = LocalDate.parse(firstActionDate, inputFormatter);
        return date.format(outputFormatter);
    }

}

