package net.jaskar;

import net.jaskar.model.LPAStudent;
import net.jaskar.model.Student;
import net.jaskar.util.LPAStudentComparator;
import net.jaskar.util.QueryItem;
import net.jaskar.util.QueryList;

import java.util.List;

record Employee(String name) implements QueryItem {

    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        QueryList<LPAStudent> queryList = new QueryList<>();
        for (int i = 0; i < 25; i++)
            queryList.add(new LPAStudent());

        System.out.println("Ordered");
        queryList.sort(null);
        printList(queryList);

        System.out.println("Matches sorted by percentages");
        var matches = queryList
                .getMatches("PercentComplete", "50")
                .getMatches("Course", "Python");
        matches.sort(new LPAStudentComparator());
        printList(matches);;

        System.out.println("Ordered");
        matches.sort(null);
        printList(matches);
    }


    public static void printMoreLists(List<? extends Student> students) {

        for (var student : students) {
            System.out.println(student);
        }
        System.out.println();
    }

    public static void printList(List<?> students) {
        for (var student : students)
            System.out.println(student);
    }
}
