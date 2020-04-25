package com.springboot.demo.common;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

/**
 * Provides date time API using newly introduced DateTime api in Java
 */
@UtilityClass
public class DateTimeUtils {

    static DateTimeFormatter TIMESTAMP_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    static List<Integer> validDaysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);


    /**
     * This is a restricted method, should not be used in general. Always use getCurrentTimeInUTC
     *
     * @return DateTime
     */
    public static LocalDateTime getNowInUTC() {
        ZonedDateTime nowUTC = ZonedDateTime.now(ZoneOffset.UTC);
        return nowUTC.toLocalDateTime();
    }

    /**
     * Takes date and time at NOW and then adds/subtracts days and adds/subtracts minutes from that
     *
     * @param days
     * @param minutes
     * @return ISO Representation in form 2017-01-15T09:08:45.503Z
     */
    public static String getDateTimeRelativeNowInIso(int days, int minutes) {
        return DateTimeUtils.getDateTimeInUTCIso(getNowInUTC().plusDays(days).plusMinutes(minutes));
    }

    /**
     * Takes date and time at NOW and then adds/subtracts days and adds/subtracts minutes from that
     *
     * @return ISO Representation in form 2017-01-15T09:08:45.503Z
     */
    public static String getCurrentDateTimeInIso() {
        return DateTimeUtils.getDateTimeInUTCIso(getNowInUTC());
    }

    /**
     * Takes date and time at start of day i.e TODAY and then adds/subtracts days and adds/subtracts minutes from that
     *
     * @param days
     * @param minutes
     * @return ISO Representation in form 2017-01-15T09:08:45.503Z
     */
    public static String getDateTimeRelativeTodayInIso(int days, int minutes) {
        LocalDateTime today = getNowInUTC().withHour(0).withMinute(0).withSecond(0);
        return DateTimeUtils.getDateTimeInUTCIso(today.plusDays(days).plusMinutes(minutes));
    }

    /**
     * DateTime as string
     *
     * @param dateTime DateTime
     * @return DateTime in string
     */
    public static String getDateTimeInUTCIso(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneOffset.UTC).toString();
    }

    /**
     * Converts local date time to epoch form (in milliseconds)
     *
     * @param localDateTime
     * @return epoch val
     */
    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

    /**
     * Use this instead of System.currentTimeMillis()
     * System.currentTimeMillis() will work only when the local host is also in UTC.
     *
     * @return currentTimeInMillis
     */
    public static long getCurrentTimeInMillis() {
        return DateTimeUtils.toEpochMilli(DateTimeUtils.getNowInUTC());
    }

    /**
     * Doesn't take care of leap year. Considers only 28 days in Feb month
     *
     * @param month
     * @param day
     * @return true if month day combination is valid else false;
     */
    public static boolean isMonthDayCombinationValid(int month, int day) {
        return month > 0 &&
                month <= 12 &&
                day <= validDaysInMonth.get(month - 1);
    }

    /**
     * Converts iso format dateTime string to {@link LocalDateTime} .
     *
     * @param dateTime in iso format
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getLocalDateTimeFromIso(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            throw new IllegalArgumentException("dateTime can't be empty");
        }
        return ZonedDateTime.parse(dateTime).toLocalDateTime();
    }

    /**
     * Get date time for ISO string
     *
     * @param dateTime
     * @return
     */
    public static String getISODateTime(String dateTime) {
        return getIsoDateTime(dateTime).toString();
    }

    public static LocalDateTime getIsoDateTime(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            throw new IllegalArgumentException("dateTime can't be empty");
        }
        LocalDateTime ldt = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ldt.atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public String getDateTimeAfterDuration(LocalDateTime baseDateTime, String isoDuration) {
        Period period = Period.parse(isoDuration);
        LocalDateTime dateTimeAfterYears = baseDateTime.plus(period.getYears(), ChronoUnit.YEARS);
        LocalDateTime dateTimeAfterMonths = dateTimeAfterYears.plus(period.getMonths(), ChronoUnit.MONTHS);
        LocalDateTime dateTimeAfterDays = dateTimeAfterMonths.plus(period.getDays(), ChronoUnit.DAYS);
        return getDateTimeInUTCIso(dateTimeAfterDays);
    }

    public long getDateTimeDifferenceInMonths(String dateTimeOne, String dateTimeTwo) {
        return ChronoUnit.MONTHS.between(getLocalDateTimeFromIso(dateTimeOne).toLocalDate().withDayOfMonth(1),
                getLocalDateTimeFromIso(dateTimeTwo).toLocalDate().withDayOfMonth(1));
    }

    public String setTimeToBeginningOfDay(String dateTime) {
        return dateTime.replaceAll("T(.*)Z", "T00:00:00.000Z");
    }

    public String setTimeToEndOfDay(String dateTime) {
        return dateTime.replaceAll("T(.*)Z", "T23:59:59.999Z");
    }

    /**
     * returns duration between currentDateTime and passed parameter DateTime
     *
     * @param dateTime
     * @return Duration
     */
    public static Duration getDuration(String dateTime){
        LocalDateTime startDateTime = DateTimeUtils.getLocalDateTimeFromIso(DateTimeUtils.getCurrentDateTimeInIso());
        LocalDateTime endDateTime = DateTimeUtils.getLocalDateTimeFromIso(dateTime);
        return Duration.between(startDateTime, endDateTime);
    }

    /**
     * returns nDays after today with DateTimeFormatter as input
     * @param nDays
     * @param formatter
     * @return
     */
    public static String getNDaysAfterCurrentTimeInUTC(long nDays, DateTimeFormatter formatter)    {
        LocalDateTime localDateTime = getNowInUTC();
        LocalDateTime tenDaysAfterToday = localDateTime.plusDays(nDays);
        return tenDaysAfterToday.format(formatter);
    }
}