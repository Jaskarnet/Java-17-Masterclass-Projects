package net.jaskar;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainChallenge {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass", 50);
        Course jmc = new Course("JMC", "Java Masterclass", 100);
        Course cgj = new Course("CGJ", "Creating Games in Java");

        var studentsList = Stream.generate(() -> Student.getRandomStudent(pymc, jmc))
                .limit(5000)
                .toList();

        // Calculate average percentage completed for all students for just the Java Masterclass using reduce
        var totalPercent = studentsList.stream()
                .mapToDouble(s -> s.getPercentComplete(jmc.courseCode()))
                .reduce(0.00, Double::sum); // could just use sum() but challenge specified reduce()
        var average = totalPercent / studentsList.size();
        System.out.println("Average percentage completed for all students (JMC): " + average + "%");

        // Use this result, multiplying it by 1.25, to collect a group of students. These would be all students
        // who've completed more than three quarters of that average percentage
        int topPercent = (int) (1.25 * average);
        System.out.printf("Best Percentage complete = %d%% %n", topPercent);

        var goodStudents = studentsList.stream()
                .filter(s -> s.getMonthsSinceActive(jmc.courseCode()) <= 0)
                .filter(s -> s.getPercentComplete(jmc.courseCode()) >= topPercent)
                .collect(Collectors.toList()); //creates mutable list in oppose to toList()

        System.out.println("Hardworkers = " + goodStudents.size());

        // Sort by the longest enrolled students who are still active, 10 of them are going to get my new course for trial run
        List<Student> hardworkers = goodStudents.stream()
                .filter(s -> s.getMonthsSinceActive() <= 0)
                .sorted(Comparator.comparing(Student::getYearEnrolled))
                .limit(10)
                .peek(System.out::println)
                .toList();

        hardworkers.forEach(s -> {
            s.addCourse(cgj);
            System.out.print(s + " ");
        });
    }
}
