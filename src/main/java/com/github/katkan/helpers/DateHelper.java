package com.github.katkan.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static String getCurrentDateInSpecifiedFormat() {
        SimpleDateFormat orderSummaryDateFormat = new SimpleDateFormat("d MMMM, yyyy", Locale.forLanguageTag("pl"));
        return orderSummaryDateFormat.format(new Date());
    }
}
