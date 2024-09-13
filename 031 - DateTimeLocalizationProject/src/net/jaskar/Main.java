/**
 * This program schedules potential meeting times for two employees located in different time zones.
 * It uses several classes from the java.time package to handle date, time, and time zone calculations,
 * as well as formatting date-time information for display purposes.
 *
 * Classes used for Time and Date Management:
 *
 * 1. LocalDate:
 *    - Represents a date (year, month, day) without any time zone information.
 *    - Used for working with date-only operations like determining weekdays, weekends, and date ranges.
 *    - Example: "2023-09-13".
 *
 * 2. ZonedDateTime:
 *    - Represents a date-time with a specific time zone (e.g., "2023-09-13T14:30:00+02:00[Europe/Paris]").
 *    - Essential for handling dates and times in different time zones, converting between zones,
 *      and ensuring accurate scheduling across regions.
 *    - Used to find the potential meeting hours between employees located in different time zones.
 *
 * 3. ZoneId:
 *    - Represents a time zone identifier (e.g., "America/New_York").
 *    - Provides the rules for a specific geographical region, allowing us to create ZonedDateTime
 *      instances that account for time zone differences and daylight savings time.
 *    - Used to obtain the time zone for each employee.
 *
 * 4. ZoneRules:
 *    - Provides access to the rules of a time zone, including how it handles daylight saving time.
 *    - Used to determine whether a specific date-time falls into daylight saving time and to calculate
 *      the correct offset from UTC.
 *    - Helps manage special cases like daylight savings when scheduling meetings.
 *
 * 5. Duration:
 *    - Represents an amount of time, such as "34.5 seconds".
 *    - Used to calculate the difference in time (hours and minutes) between two ZonedDateTime objects.
 *    - Ensures the correct time gap is maintained between employees in different time zones.
 *
 * 6. DateTimeFormatter:
 *    - Formats and parses date-time objects in a specific pattern or localized style.
 *    - Supports different formatting styles, such as FULL for a complete textual representation or SHORT for numeric formats.
 *    - Used to display date and time information formatted according to each employee's locale.
 *
 * 7. DayOfWeek:
 *    - An enum that represents the seven days of the week (MONDAY to SUNDAY).
 *    - Used in predicates to determine if a given date falls on a weekday or weekend.
 *
 * Purpose:
 * The program determines all possible meeting times between two employees (one in New York, USA, and the other in Sydney, Australia)
 * over the next 10 days. It considers the working hours (7 AM to 8 PM) and weekdays (Monday to Friday) only, while properly managing
 * different time zones, daylight savings, and localized date-time formatting.
 */


package net.jaskar;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.format.DateTimeFormatter.*;

public class Main {

    // Define a record to represent an Employee with name, locale, and time zone information
    private record Employee(String name, Locale locale, ZoneId zone) {

        // Constructor to create an Employee using a language tag string for locale and a time zone string
        public Employee(String name, String locale, String zone) {
            this(name, Locale.forLanguageTag(locale), ZoneId.of(zone));
        }

        // Constructor to create an Employee using a Locale object and a time zone string
        public Employee(String name, Locale locale, String zone) {
            this(name, locale, ZoneId.of(zone));
        }

        // Method to format the date-time information for the employee in their locale's format
        String getDateInfo(ZonedDateTime zdt, DateTimeFormatter dtf) {
            return "%s [%s] : %s".formatted(name, zone, zdt.format(dtf.localizedBy(locale)));
        }
    }

    public static void main(String[] args) {
        // Create Employee instances for Jane and Joe with their respective locales and time zones
        Employee jane = new Employee("Jane", Locale.US, "America/New_York");
        Employee joe = new Employee("Joe", "en-AU", "Australia/Sydney");

        // Get the time zone rules for each employee's location (for handling daylight savings, etc.)
        ZoneRules janesRules = jane.zone.getRules();
        ZoneRules joesRules = joe.zone.getRules();
        System.out.println(jane + " " + janesRules);
        System.out.println(joe + " " + joesRules);

        // Get the current time for Jane and convert it to Joe's time zone
        ZonedDateTime janeNow = ZonedDateTime.now(jane.zone);
        ZonedDateTime joeNow = ZonedDateTime.of(janeNow.toLocalDateTime(), joe.zone);

        // Calculate the time difference between the two time zones in hours and minutes
        long hoursBetween = Duration.between(joeNow, janeNow).toHours();
        long minutesBetween = Duration.between(joeNow, janeNow).toMinutesPart();

        // Print the time difference between the two employees
        System.out.println("Joe is " + Math.abs(hoursBetween) + " hours " + Math.abs(minutesBetween) + " minutes " +
                ((hoursBetween < 0) ? "behind" : "ahead"));

        // Check if each employee is in daylight savings time and print the results
        System.out.println("Jane in daylight savings? " + janesRules.isDaylightSavings(janeNow.toInstant()) + " " +
                janesRules.getDaylightSavings(janeNow.toInstant()) + ": " +
                janeNow.format(ofPattern("zzzz z")));

        System.out.println("Joe in daylight savings? " + joesRules.isDaylightSavings(joeNow.toInstant()) + " " +
                joesRules.getDaylightSavings(joeNow.toInstant()) + ": " +
                joeNow.format(ofPattern("zzzz z")));

        // Number of days to check for possible meeting times
        int days = 10;

        // Schedule possible meeting times between Joe and Jane over the next 'days' days
        var map = schedule(joe, jane, days);

        // Define a date-time formatter for formatting the output in a full date and short time style
        DateTimeFormatter dtf = ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);

        // Print the possible meeting times for each day in the 10-day range
        for (LocalDate ldt : map.keySet()) {
            System.out.println(ldt.format(ofLocalizedDate(FormatStyle.FULL)));
            for (ZonedDateTime zdt : map.get(ldt)) {
                System.out.println("\t" +
                        jane.getDateInfo(zdt, dtf) + " <---> " +
                        joe.getDateInfo(zdt.withZoneSameInstant(joe.zone()), dtf)
                );
            }
        }
    }

    private static Map<LocalDate, List<ZonedDateTime>> schedule(Employee first, Employee second, int days) {
        // Predicate to check if a given ZonedDateTime falls within work hours (7 AM to 8 PM) and on a weekday
        Predicate<ZonedDateTime> rules = zdt ->
                zdt.getDayOfWeek() != DayOfWeek.SATURDAY
                        && zdt.getDayOfWeek() != DayOfWeek.SUNDAY
                        && zdt.getHour() >= 7 && zdt.getHour() < 21;

        // Define the starting date for scheduling (the day after tomorrow)
        LocalDate startingDate = LocalDate.now().plusDays(2);

        // Generate a stream of dates for the range specified (next 'days' days) and calculate possible meeting times
        return startingDate.datesUntil(startingDate.plusDays(days + 1))
                .map(dt -> dt.atStartOfDay(first.zone()))  // Convert each date to the start of the day in the first employee's time zone
                .flatMap(dt -> IntStream.range(0, 24).mapToObj(dt::withHour))  // Generate each hour of the day
                .filter(rules)  // Filter out hours that don't match work hours or fall on weekends for the first employee
                .map(dtz -> dtz.withZoneSameInstant(second.zone()))  // Convert the filtered hours to the second employee's time zone
                .filter(rules)  // Filter out hours that don't match work hours or fall on weekends for the second employee
                .collect(
                        Collectors.groupingBy(ZonedDateTime::toLocalDate, TreeMap::new, Collectors.toList())  // Group by date
                );
    }
}
