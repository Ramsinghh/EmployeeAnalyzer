package com.ram;


import com.ram.model.Employee;
import io.github.rushuat.ocell.document.Document;
import io.github.rushuat.ocell.document.DocumentOOXML;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {
    public static void isConsecutiveDays(List<Employee> employees) {
        // Initialize SimpleDateFormat to parse dates
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        // Initialize a list to store employees with consecutive days
        List<Employee> consecutiveDaysEmployees = new ArrayList<>();
        int cnt=1;
        for (int i = 1; i < employees.size(); i++) {
            try {
                Employee curr_emp = employees.get(i);
                Employee prev_emp = employees.get(i-1);
                Date currDate = sdf.parse(curr_emp.timeOut);
                Date prevDate = sdf.parse(prev_emp.time);

                long dayDifference = (currDate.getTime() - prevDate.getTime()) / (24 * 60 * 60 * 1000);
                if (dayDifference <=1) {
                    cnt++;
                }
                else{
                    if(cnt>=7) {
                        consecutiveDaysEmployees.add(curr_emp);
                    }
                    cnt=1;
                }
            } catch (ParseException e) {
                System.err.println("Error parsing dates: " + e.getMessage());
            }
        }
        consecutiveDaysEmployees.stream()
                .limit(5)
                .forEach(System.out::println);

    }
    public static void isShortBreak(List<Employee> employees) throws ParseException {
        long minShiftIntervalMillis = 60 * 60 * 1000; // 1 hour
        long maxShiftIntervalMillis = 10 * 60 * 60 * 1000; // 10 hours

        List<Employee> eligibleEmployees = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < employees.size(); i++) {
            Employee currentEmployee = employees.get(i);

            Date date = sdf.parse(currentEmployee.timeHour);
            long timeBetweenShiftsMillis = date.getTime();
            System.out.println(timeBetweenShiftsMillis+ " hi");
            if (timeBetweenShiftsMillis > minShiftIntervalMillis && timeBetweenShiftsMillis < maxShiftIntervalMillis) {
                eligibleEmployees.add(currentEmployee);
            }
        }
        eligibleEmployees.stream()
                .limit(5)
                .forEach(System.out::println);
    }
    public static void isLongShift(List<Employee> employees) {
        // Initialize SimpleDateFormat to parse timestamps
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        // Initialize a list to store employees with long shifts
        List<Employee> longShiftEmployees = new ArrayList<>();

        for (Employee emp : employees) {
            try {
                Date shift = sdf.parse(emp.timeHour);

                long shiftDurationMillis = shift.getTime();
                double shiftDurationHours = (double) shiftDurationMillis / (60 * 60 * 1000); // Convert to hours

                if (shiftDurationHours > 14.0) {
                    longShiftEmployees.add(emp);
                }
            } catch (ParseException e) {
                System.err.println("Error parsing date and time: " + e.getMessage());
            }
        }
        longShiftEmployees.stream()
                .forEach(System.out::println);

    }
    public static void main(String[] args) {

        try (Document document = new DocumentOOXML()) {
            document.fromFile("AssignmentTimecard.xlsx");
            List<Employee> employees = document.getSheet("Table 1", Employee.class);

            // Print first 5 employee
           employees.stream()
                    .limit(5)
                    .forEach(System.out::println);

            isShortBreak(employees);
            isConsecutiveDays(employees);
            isLongShift(employees);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
