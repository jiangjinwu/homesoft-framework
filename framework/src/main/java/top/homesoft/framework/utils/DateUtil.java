package top.homesoft.framework.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateUtil {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter DATE_FORMATTER_MIN = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static final DateTimeFormatter DATE_FORMATTER_HOURS = DateTimeFormatter.ofPattern("yyyyMMddHH");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter DATETIME_FORMATTER_STR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentTimeMinuteStr() {
        return LocalDateTime.now().format(DATE_FORMATTER_MIN);
    }

    public static String getTimeMinuteStr(LocalDateTime date) {
        return date.format(DATE_FORMATTER_MIN);
    }

    public static String getCurrentTimeHoursStr() {
        return LocalDateTime.now().format(DATE_FORMATTER_HOURS);
    }

    public static String getCurrentDatetimeStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER_STR);
    }

    public static String formatDatetimeStr(long timestamp) {
        return getDateTimeOfTimestamp(timestamp).format(DATETIME_FORMATTER_STR);
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static String getCurrentDateStr() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(SHORT_DATE_FORMATTER);
    }

    public static String getCurrentMonthStr() {
        return LocalDate.now().format(MONTH_FORMATTER);
    }

    public static String getCurrentShortDateTimeStr() {
        return LocalDateTime.now().format(SHORT_DATETIME_FORMATTER);
    }

    public static String getCurrentTimeStr() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentDatetimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    public static long periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return endDateExclusive.toEpochDay() - startDateInclusive.toEpochDay();
    }

    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    public static Long toEpochMilli(LocalDateTime dateTime) {
        return Long.valueOf(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    public static Long toSelectEpochMilli(LocalDateTime dateTime, ZoneId zoneId) {
        return Long.valueOf(dateTime.atZone(zoneId).toInstant().toEpochMilli());
    }
}
