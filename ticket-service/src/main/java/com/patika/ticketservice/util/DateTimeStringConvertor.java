package com.patika.ticketservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeStringConvertor {

    public static LocalDateTime convertStringToLocalDateTime(String localDateTime) {

        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart()
                .appendPattern(" HH:mm")
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .toFormatter();

        return LocalDateTime.parse(localDateTime, format);
    }

    public static LocalDateTime formatLocalDateTime(LocalDateTime localDateTime) {

        String str = localDateTime.toString().substring(0, 16).replace("T", " ");

        return convertStringToLocalDateTime(str);
    }

    private DateTimeStringConvertor() {

    }

}
