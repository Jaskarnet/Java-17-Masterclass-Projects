package net.jaskar;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainFinalChallenge {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass", 50);
        Course jmc = new Course("JMC", "Java Masterclass", 100);
        Course cgj = new Course("JGAME", "Creating Games in Java", 40);

        var studentsList = Stream.generate(() -> Student.getRandomStudent(pymc, jmc, cgj))
                .filter(s -> s.getYearEnrolled() >= (LocalDate.now().getYear() - 4))
                .limit(10_000)
                .toList();

        // Count the number of students enrolled in each course
        System.out.println("Number of students enrolled in each course:");
        studentsList.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(CourseEngagement::getCourseCode, Collectors.counting()))
                .forEach((courseCode, count) -> System.out.printf("  %s: %d students%n", courseCode, count));

        // Count how many students are taking 1, 2, or 3 courses
        System.out.println("\nNumber of courses taken by students:");
        studentsList.stream()
                .collect(Collectors.groupingBy(s -> s.getEngagementMap().size(), Collectors.counting()))
                .forEach((numberOfCourses, count) -> System.out.printf("  %d course(s): %d students%n", numberOfCourses, count));

        // Calculate the average percentage completed for each course
        System.out.println("\nAverage percentage completion for each course:");
        studentsList.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(CourseEngagement::getCourseCode, Collectors.averagingDouble(CourseEngagement::getPercentComplete)))
                .forEach((courseCode, avgPercentage) -> System.out.printf("  %s: %.2f%%%n", courseCode, avgPercentage));

        // Count activity by year for each course
        System.out.println("\nActivity counts by year for each course:");
        studentsList.stream()
                .flatMap(s -> s.getEngagementMap().values().stream())
                .collect(Collectors.groupingBy(CourseEngagement::getCourseCode,
                      Collectors.groupingBy(CourseEngagement::getLastActivityYear, Collectors.counting())))
                .forEach((courseCode, yearCounts) -> {
                    System.out.println("  " + courseCode + ":");
                    yearCounts.forEach((year, count) -> System.out.printf("    %d: %d activities%n", year, count));
                });
    }
}
