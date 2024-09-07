package net.jaskar;

import java.time.LocalDate;
import java.time.Period;

public class CourseEngagement {
    private final Course course;
    private final LocalDate enrollmentDate;
    private String engagementType;
    private int lastLecture;
    private LocalDate lastActivityDate;

    public CourseEngagement(Course course, LocalDate enrollmentDate, String engagementType) {
        this.course = course;
        this.enrollmentDate = this.lastActivityDate = enrollmentDate;
        this.engagementType = engagementType;
    }

    public String getCourseCode() {
        return course.courseCode();
    }

    public int getEnrollmentYear() {
        return enrollmentDate.getYear();
    }

    public int getLastActivityYear() {
        return lastActivityDate.getYear();
    }

    public String getLastActivityMonth() {
        return "%tb".formatted(lastActivityDate);
    }

    public int getMonthsSinceActive() {
        LocalDate now = LocalDate.now();
        var months = Period.between(lastActivityDate, now).toTotalMonths();
        return (int) months;
    }

    public double getPercentComplete() {
        return lastLecture * 100.0 / course.lectureCount();
    }

    public void watchLecture(int lectureNumber, LocalDate date) {
        lastLecture = Math.max(lectureNumber, lastLecture);
        lastActivityDate = date;
        engagementType = "Lecture " + lastLecture;
    }

    @Override
    public String toString() {
        return "%s: %s %d %s [%d]".formatted(course.courseCode(), getLastActivityMonth(), getLastActivityYear(), engagementType, getMonthsSinceActive());
    }
}
