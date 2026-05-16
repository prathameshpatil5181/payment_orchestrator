package com.orbyte.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {

    private static final ZoneId IST_ZONE =
            ZoneId.of("Asia/Kolkata");

    public static LocalDateTime epochMillisToIst(long epochMillis) {

        return Instant.ofEpochMilli(epochMillis)
                .atZone(IST_ZONE)
                .toLocalDateTime();
    }

    public static LocalDateTime epochSecondsToIst(long epochSeconds) {

        return Instant.ofEpochSecond(epochSeconds)
                .atZone(IST_ZONE)
                .toLocalDateTime();
    }
}