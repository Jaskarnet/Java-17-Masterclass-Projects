package net.jaskar;

public record Course(String courseCode, String title, int lectureCount) {

    public Course {
        if (lectureCount <= 0) {
            lectureCount = 0;
        }
    }

    public Course(String courseCode, String title) {
        this(courseCode, title, 40);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(courseCode, title);
    }
}
