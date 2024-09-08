package net.jaskar;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Student {
    private final static Random random = new Random();
    private static long lastStudentId = 1;
    private final long studentId;
    private final String countryCode;
    private final int yearEnrolled;
    private final int ageEnrolled;
    private final String gender;
    private final boolean programmingExperience;
    private final Map<String, CourseEngagement> engagementMap;

    private static String getRandomVal(String... data) {
        return data[random.nextInt(data.length)];
    }

    public static Student getRandomStudent(Course... courses) {
        int maxYear = LocalDate.now().getYear() + 1;
        Student student = new Student(
                getRandomVal("AU", "CA", "CN", "GB", "IN", "UA", "US"),
                random.nextInt(2015, maxYear),
                random.nextInt(18, 90),
                getRandomVal("M", "F", "U"),
                random.nextBoolean(),
                courses);
        for (Course c : courses) {
            int lecture = random.nextInt(30, c.lectureCount());
            int year = random.nextInt(student.getYearEnrolled(), maxYear);
            int month = random.nextInt(1, 13);
            if (year == (maxYear - 1)) {
                if (month > LocalDate.now().getMonthValue()) {
                    month = LocalDate.now().getMonthValue();
                }
            }
            student.watchLecture(c.courseCode(), lecture, month, year);
        }
        return student;
    }

    public Student(String countryCode, int yearEnrolled, int ageEnrolled, String gender, boolean programmingExperience, Course... courses) {
        this.studentId = lastStudentId++;
        this.countryCode = countryCode;
        this.yearEnrolled = yearEnrolled;
        this.ageEnrolled = ageEnrolled;
        this.gender = gender;
        this.programmingExperience = programmingExperience;
        engagementMap = new TreeMap<>();
        for (Course c : courses)
            addCourse(c, LocalDate.of(yearEnrolled, 1, 1));
    }

    public void watchLecture(String courseCode, int lectureNumber, int month, int year) {
        var activity = engagementMap.get(courseCode);
        if (activity != null)
            activity.watchLecture(lectureNumber, LocalDate.of(year, month, 1));
    }

    public void addCourse(Course course) {
        addCourse(course, LocalDate.now());
    }

    public void addCourse(Course course, LocalDate enrollDate) {
        engagementMap.put(course.courseCode(), new CourseEngagement(course, enrollDate, "Enrollment"));
    }

    public int getAge() {
        return ageEnrolled + getYearsSinceEnrolled();
    }

    public int getYearsSinceEnrolled() {
        return LocalDate.now().getYear() - yearEnrolled;
    }

    public int getMonthsSinceActive() {
        int leastMonths = -1;
        for (CourseEngagement ce : engagementMap.values()) {
            int ceMonthsSinceActive = ce.getMonthsSinceActive();
            if (leastMonths == -1 || ceMonthsSinceActive < leastMonths) {
                leastMonths = ceMonthsSinceActive;
            }
        }
        return leastMonths;
    }

    public int getMonthsSinceActive(String courseCode) {
        CourseEngagement info = engagementMap.get(courseCode);
        return info == null ? 0 : info.getMonthsSinceActive();
    }

    public double getPercentComplete(String courseCode) {
        var info = engagementMap.get(courseCode);
        return info == null ? 0 : info.getPercentComplete();
    }

    public long getStudentId() {
        return studentId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getYearEnrolled() {
        return yearEnrolled;
    }

    public int getAgeEnrolled() {
        return ageEnrolled;
    }

    public String getGender() {
        return gender;
    }

    public boolean hasProgrammingExperience() {
        return programmingExperience;
    }

    public Map<String, CourseEngagement> getEngagementMap() {
        return Map.copyOf(engagementMap);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", countryCode='" + countryCode + '\'' +
                ", yearEnrolled=" + yearEnrolled +
                ", ageEnrolled=" + ageEnrolled +
                ", gender='" + gender + '\'' +
                ", programmingExperience=" + programmingExperience +
                ", engagementMap=" + engagementMap +
                '}';
    }
}
