package ru.arkhipov.MySecondTestApp.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {
    public static SimpleDateFormat getCustomFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'I'HH:mm:ss.SSS'Z'");
    }
}
