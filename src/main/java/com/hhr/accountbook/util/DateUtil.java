package com.hhr.accountbook.util;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: Harry
 * @Date: 2021/8/18 17:20
 * @Version 1.0
 */
@Component
public class DateUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");

    public String localDateToString(LocalDate localDate){
        return localDateToSqlDate(localDate).toString();
    }

    public Date localDateToSqlDate(LocalDate localDate){
        return Date.valueOf(localDate);
    }

    public String localDateToStringAddEndDayTime(LocalDate localDate){
        return localDateToSqlDate(localDate).toString() + " 23:59:59";
    }

    public Date localDateToSqlDateAddEndDayTime(LocalDate localDate){
        return Date.valueOf(localDateToStringAddEndDayTime(localDate));
    }

    public String now(){
        java.util.Date date = new java.util.Date();

        Timestamp now = new Timestamp(date.getTime());

        return now.toString();
    }

    public String nowForMat(){
        java.util.Date date = new java.util.Date();

        return sdf1.format(date);
    }

    public String fixedTime(String time){
        Timestamp ts = Timestamp.valueOf(time);
        java.util.Date date = new java.util.Date(ts.getTime());
        return sdf.format(date);
    }

    public String fixedTimeForExportExcel(String time){
        Timestamp ts = Timestamp.valueOf(time);
        java.util.Date date = new java.util.Date(ts.getTime());
        return sdf2.format(date);
    }

    public Timestamp localDateToStartTimestamp(LocalDate localDate){
        return localDateToTimestamp(localDate,0,0,0);
    }

    public Timestamp localDateToEndTimestamp(LocalDate localDate){
        return localDateToTimestamp(localDate,23,59,59);
    }

    public Timestamp localDateToTimestamp(LocalDate localDate,int hour,int minute,int second){
        return Timestamp.valueOf(LocalDateTime.of(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth(),hour,minute,second));
    }
}
