package com.github.katkan.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static String getCurrentDateInSpecifiedFormat(SimpleDateFormat format) {
        return format.format(new Date());
    }
}
