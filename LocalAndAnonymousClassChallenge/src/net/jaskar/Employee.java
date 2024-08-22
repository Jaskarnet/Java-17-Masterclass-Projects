package net.jaskar;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public record Employee(String firstName, String lastName, Date hireDate) {
    private enum FirstNames {IAN, JESSE, MIKE, WALTER, JAMES, SKYLER}
    private enum LastNames {PINKMAN, WHITE, EHRMANTRAUT, MCGILL}

    public static String getRandomFirstName() {
        Random random = new Random();
        FirstNames randomFirstName = FirstNames.values()[random.nextInt(FirstNames.values().length)];
        return randomFirstName.toString();
    }

    public static String getRandomLastName() {
        Random random = new Random();
        LastNames randomLastName = LastNames.values()[random.nextInt(LastNames.values().length)];
        return randomLastName.toString();
    }

    public static Date getRandomHireDate() {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int randomYear = currentYear - random.nextInt(10);
        calendar.set(Calendar.YEAR, randomYear);
        int dayOfYear = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_YEAR)) + 1;
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTime();
    }
}
