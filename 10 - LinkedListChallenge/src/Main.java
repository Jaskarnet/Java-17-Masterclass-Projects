import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var itineraryOfPlacesToVisit = new LinkedList<Town>();
        addPlace(itineraryOfPlacesToVisit, new Town("Sydney", 0));
        addPlace(itineraryOfPlacesToVisit, new Town("Adelaide", 1374));
        addPlace(itineraryOfPlacesToVisit, new Town("Alice Springs", 2771));
        addPlace(itineraryOfPlacesToVisit, new Town("Brisbane", 917));
        addPlace(itineraryOfPlacesToVisit, new Town("Darwin", 3972));
        addPlace(itineraryOfPlacesToVisit, new Town("Melbourne", 877));
        addPlace(itineraryOfPlacesToVisit, new Town("Perth", 3923));
        System.out.println(itineraryOfPlacesToVisit);

        boolean stopCondition = false, forward = false;
        Scanner scanner = new Scanner(System.in);
        var listIterator = itineraryOfPlacesToVisit.listIterator();
        printOptions();
        while (!stopCondition) {
            if (!listIterator.hasPrevious()) {
                System.out.println("Originating: " + listIterator.next());
                forward = true;
            }
            if (!listIterator.hasNext()) {
                System.out.println("Final: " + listIterator.previous());
                forward = false;
            }
            System.out.print("Enter value: ");
            char input = scanner.nextLine().toUpperCase().charAt(0);
            switch (input) {
                case 'F' -> {
                    if (!forward) {
                        forward = true;
                        if (listIterator.hasNext())
                            listIterator.next();
                    }
                    if (listIterator.hasNext())
                        System.out.println(listIterator.next());
                }
                case 'B' -> {
                    if (forward) {
                        forward = false;
                        if (listIterator.hasPrevious())
                            listIterator.previous();
                    }
                    if (listIterator.hasPrevious())
                        System.out.println(listIterator.previous());
                }
                case 'L' -> System.out.println(itineraryOfPlacesToVisit);
                case 'M' -> printOptions();
                case 'Q' -> stopCondition = true;
            }
        }
    }

    private static void printOptions() {
        System.out.println("""
                Available actions (select word or letter):
                (F)orward
                (B)ackward
                (L)ist Places
                (M)enu
                (Q)uit""");
    }

    private static void addPlace(LinkedList<Town> list, Town town) {
        if (list.contains(town)) {
            System.out.println("Found duplicate " + town);
            return;
        }

        for (Town t : list) {
            if (t.name().equalsIgnoreCase(town.name())) {
                System.out.println("Found duplicate: " + town);
                return;
            }
        }

        int matchedIndex = 0;
        for (var listTown : list) {
            if (town.distanceFromSydneyInKm() < listTown.distanceFromSydneyInKm()) {
                list.add(matchedIndex, town);
                return;
            }
            matchedIndex++;
        }

        list.add(town);
    }
}

record Town(String name, int distanceFromSydneyInKm) {
    @Override
    public String toString() {
        return String.format("%s (%d)", name, distanceFromSydneyInKm);
    }
}


