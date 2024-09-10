package net.jaskar;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");

        var studentsList = Stream.generate(() -> Student.getRandomStudent(pymc, jmc))
                .limit(1000)
                .toList();

        // How many male and female students are in the group?
        long femaleStudents = studentsList.stream()
                .filter(s -> s.getGender().equals("F"))
                .count();
        long maleStudents = studentsList.stream()
                .filter(s -> s.getGender().equals("M"))
                .count();
        System.out.printf("-GENDER-%nFemale students: %d%nMale students: %d%n", femaleStudents, maleStudents);

        // How many students fall into three age ranges?
        long lessThan30 = studentsList.stream()
                .filter(s -> s.getAge() < 30)
                .count();
        long between30and60 = studentsList.stream()
                .filter(s -> s.getAge() >= 30 && s.getAge() <= 60)
                .count();
        long over60 = studentsList.stream()
                .filter(s -> s.getAge() > 60)
                .count();
        System.out.printf("%n-AGE RANGES-%nLess than 30: %d%nBetween 30 and 60: %d%nOver 60: %d%n", lessThan30, between30and60, over60);

        // Summary statistics on student's age
        var summaryStatistics = studentsList.stream()
                .mapToInt(Student::getAge)
                .summaryStatistics();
        System.out.printf(
                "%n-STATISTICS-%nMin age: %d%nMax age: %d%nAverage age: %.2f%n",
                summaryStatistics.getMin(),
                summaryStatistics.getMax(),
                summaryStatistics.getAverage()
        );

        // What countries are students from?
        var countries = studentsList.stream()
                .map(Student::getCountryCode)
                .distinct()
                .toList();
        System.out.println("\n-STUDENTS' ORIGINS-");
        countries.forEach(System.out::println);

        // Are there students that are still active, that have been enrolled for more than 7 years?
        var answer = studentsList.stream()
                .anyMatch(s -> s.getYearsSinceEnrolled() > 7 && s.getMonthsSinceActive() <= 0);
        if (answer) {
            System.out.println("\nThere are students that enrolled over 7 years ago and are still active!");
            var longTermStudents = studentsList.stream()
                    .filter(s -> s.getYearsSinceEnrolled() > 7 && s.getMonthsSinceActive() <= 0)
                    .toList();
            longTermStudents.forEach(System.out::println);
        }
    }
}
