package net.jaskar;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(List.of(
                new Employee(Employee.getRandomFirstName(), Employee.getRandomLastName(), Employee.getRandomHireDate()),
                new Employee(Employee.getRandomFirstName(), Employee.getRandomLastName(), Employee.getRandomHireDate()),
                new Employee(Employee.getRandomFirstName(), Employee.getRandomLastName(), Employee.getRandomHireDate()),
                new Employee(Employee.getRandomFirstName(), Employee.getRandomLastName(), Employee.getRandomHireDate())
        ));

        printEmployees(employees);
    }

    public static void printEmployees(List<Employee> employees) {
        // Local class inside a method
        class EmployeeWrapper {
            final private String fullName;
            final private String yearsWorked;
            public EmployeeWrapper(Employee employee) {
                fullName = employee.lastName() + " " + employee.firstName();
                yearsWorked = calculateYearsWorked(employee.hireDate());
            }

            private String calculateYearsWorked(Date hireDate) {
                LocalDate hireLocalDate = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                Period period = Period.between(hireLocalDate, currentDate);
                return String.valueOf(period.getYears());
            }
        }

        List<EmployeeWrapper> employeeWrapperList = new ArrayList<>(employees.size());
        for (Employee employee : employees)
            employeeWrapperList.add(new EmployeeWrapper(employee));
        // Anonymous class implementing Comparator
        employeeWrapperList.sort(new Comparator<EmployeeWrapper>() {
            @Override
            public int compare(EmployeeWrapper o1, EmployeeWrapper o2) {
                return o1.fullName.compareTo(o2.fullName);
            }
        });
        for (EmployeeWrapper employeeWrapper : employeeWrapperList)
            System.out.println(employeeWrapper.fullName + ", years worked: " + employeeWrapper.yearsWorked);
    }
}
