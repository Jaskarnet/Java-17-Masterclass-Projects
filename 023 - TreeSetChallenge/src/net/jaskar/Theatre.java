package net.jaskar;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Theatre {
    private String name;
    private int seatsPerRow;
    private NavigableSet<Seat> seats;

    public Theatre(String theatreName, int rows, int totalSeats) {
        this.name = theatreName;
        this.seatsPerRow = totalSeats / rows;
        this.seats = new TreeSet<>();
        for (int i = 0; i < totalSeats; i++) {
            char rowChar = (char) (i / seatsPerRow + (int) 'A');
            int seatInRow = i % seatsPerRow + 1;
            seats.add(new Seat(rowChar, seatInRow));
        }
    }

    public String reserveSeat(char row, int seat) {

        Seat requestedSeat = new Seat(row, seat);
        Seat requested = seats.ceiling(requestedSeat);

        if (requested == null || !requested.toString().equals(requestedSeat.toString())) {
            System.out.print("--> No such seat: " + requestedSeat);
            System.out.printf(": Seat must be between %s and %s%n",
                    seats.first().number, seats.last().number);
        } else {
            if (!requested.reserved) {
                requested.reserved = true;
                return requested.toString();
            } else {
                System.out.println("Seat's already reserved.");
            }
        }
        return null;
    }

    private boolean validate(int count, char first, char last, int min, int max) {
        boolean result = (min > 0 || seatsPerRow >= count || (max - min + 1) >= count);
        result = result && seats.contains(new Seat(first, min));
        if (!result) {
            System.out.printf("Invalid! %1$d seats between " + "%2$c[%3$d-%4$d]-%5$c[%3$d-%4$d] Try again", count, first, min, max, last);
            System.out.printf(": Seat must be between %s and %s%n", seats.first().row, seats.last().row);
        }
        return result;
    }

    public Set<Seat> reserveSeats(int count,  char minRow, char maxRow, int minSeat, int maxSeat) {
        char lastValid = seats.last().row;
        maxRow = (maxRow < lastValid) ? maxRow : lastValid;
        if (!validate(count, minRow, maxRow, minSeat, maxSeat)) {
            return null;
        }
        NavigableSet<Seat> selected = null;
        for (char letter = minRow; letter <= maxRow; letter++) {
            NavigableSet<Seat> contiguous = seats.subSet(
                    new Seat(letter, minSeat), true,
                    new Seat(letter, maxSeat), true);
            int index = 0;
            Seat first = null;
            for (Seat current : contiguous) {
                if (current.reserved) {
                    index = 0;
                    continue;
                }
                first = (index == 0) ? current : first;
                if (++index == count) {
                    selected = contiguous.subSet(first, true, current, true);
                    break;
                }
            }
            if (selected != null) {
                break;
            }
        }
        Set<Seat> reservedSeats = null;
        if (selected != null) {
            selected.forEach(Seat::reserve);
            reservedSeats = new TreeSet<>(selected);
        }
        return reservedSeats;
    }

    public void printSeatMap() {
        String separatorLine = "-".repeat(90);
        System.out.printf("%1$s%n%2$s Seat Map%n%1$s%n", separatorLine, name);
        int index = 0;
        for (Seat s : seats) {
            System.out.printf("%-8s%s", s + ((s.reserved) ? "(●)" : ""), ((index++ + 1) % seatsPerRow == 0) ? "\n" : "");
        }
        System.out.println(separatorLine);
    }

    public class Seat implements Comparable<Seat>{
        private char row;
        private int number;
        private boolean reserved;
        public Seat(char row, int number) {
            this(Character.toUpperCase(row), number, false);
        }
        private Seat(char row, int number, boolean reserved) {
            this.row = row;
            this.number = number;
            this.reserved = reserved;
        }

        private void reserve() {
            this.reserved = true;
        }

        @Override
        public String toString() {
            return "%c%03d".formatted(row, number);
        }

        @Override
        public int compareTo(Seat o) {
            int compareRow = Character.compare(row, o.row);
            if (compareRow == 0) return Integer.compare(number, o.number);
            return compareRow;
        }
    }
}
