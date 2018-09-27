package com.yugimaster.javhub.common;

import java.util.Calendar;

public class Common {

    public static Long getUTCTime() {
        Calendar calendar = Calendar.getInstance();
        Long mills = calendar.getTimeInMillis();
        System.out.println("UTC = " + mills);

        return mills;
    }

    public static boolean isNetUrl(String url) {
        boolean result = false;
        if (url != null) {
            if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("rtsp") || url.toLowerCase().startsWith("mms"))
                result = true;
        }
        return result;
    }
}
