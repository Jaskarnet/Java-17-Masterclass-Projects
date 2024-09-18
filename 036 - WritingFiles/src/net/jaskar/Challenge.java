package net.jaskar;

import net.jaskar.student.Course;
import net.jaskar.student.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Challenge {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");
        String delimiter = "," + System.lineSeparator();
        String students = Stream.generate(() -> Student.getRandomStudent(pymc, jmc))
                .limit(1000)
                .map(Student::toJSON)
                .collect(Collectors.joining(delimiter, "[", "]"));
        System.out.println(students);

        try {
            Files.writeString(Path.of("students.json"), students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
