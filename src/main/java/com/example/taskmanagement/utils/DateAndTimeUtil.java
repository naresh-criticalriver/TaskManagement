package com.example.taskmanagement.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Component
public class DateAndTimeUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateAndTimeUtil.class);

    public  static LocalDate getLocalDate(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //07-08-2023
        try {
            return LocalDate.parse(date, dateTimeFormatter);
            //  System.out.println(datetime);
        } catch (DateTimeParseException e) {
            // DateTimeParseException - Text '2019-nov-12' could not be parsed at index 5
            // Exception handling message/mechanism/logging as per company standard
            logger.error(e.getMessage());
        }
        return null;
    }

}
