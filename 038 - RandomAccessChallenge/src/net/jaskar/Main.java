package net.jaskar;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Main {
    private record Employee(int employeeId, String firstName, String lastName, double salary) {
    }

    private static final Map<Integer, Long> indexedIds = new HashMap<>();

    public static void main(String[] args) {
        try (RandomAccessFile ra = new RandomAccessFile("resources/employees.dat", "rw")) {
            loadIndex(ra);
            System.out.println(readRecord(ra, 21));
            Scanner scanner = new Scanner(System.in);
            List<Integer> ids = new ArrayList<>(indexedIds.keySet());
            Collections.sort(ids);

            while (true) {
                System.out.println(ids);
                System.out.println("Enter an Employee Id or 0 to quit");
                if (!scanner.hasNext()) break;
                int employeeId = Integer.parseInt(scanner.nextLine());
                if (employeeId < 1) {
                    break;
                }
                if (!ids.contains(employeeId)) {
                    continue;
                }
                Employee e = readRecord(ra, employeeId);
                System.out.println("Enter new salary, nothing if no change:");
                try {
                    double salary = Double.parseDouble(scanner.nextLine());
                    ra.seek(indexedIds.get(employeeId) + 4);
                    ra.writeDouble(salary);
                    readRecord(ra, employeeId);
                } catch (NumberFormatException ignore) {
                    // If the entered input is not a valid number, I'll ignore it.
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadIndex(RandomAccessFile ra) {
        try {
            ra.seek(0);
            int recordCount = ra.readInt();
            for (int i = 0; i < recordCount; i++) {
                indexedIds.put(ra.readInt(), ra.readLong());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Employee readRecord(RandomAccessFile ra, int employeeId) throws IOException {
        ra.seek(indexedIds.get(employeeId));
        int id = ra.readInt();
        double salary = ra.readDouble();
        String first = ra.readUTF();
        String last = ra.readUTF();
        Employee e = new Employee(id, first, last, salary);
        System.out.println(e);
        return e;
    }
}
