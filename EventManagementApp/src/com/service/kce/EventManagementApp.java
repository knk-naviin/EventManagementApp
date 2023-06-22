package com.service.kce;

import java.sql.*;
import java.util.*;

import com.kce.bean.Event;
import com.kce.util.OverbookingException;

import com.kce.dao.*;


public class EventManagementApp {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "knknaveen28@gmail.com";
    private static final String PASSWORD = "Naveen1.2";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            EventDAO eventDAO = new EventDAO(connection);
            AttendeeDAO attendeeDAO = new AttendeeDAO(connection);

            Scanner scanner = new Scanner(System.in);

            int choice;
            do {
                System.out.println("Event Management System");
                System.out.println("1. Create Event");
                System.out.println("2. Add Attendee");
                System.out.println("3. View Event Details");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter event name: ");
                        String eventName = scanner.nextLine();
                        System.out.print("Enter venue: ");
                        String venue = scanner.nextLine();
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter time: ");
                        String time = scanner.nextLine();
                        System.out.print("Enter capacity: ");
                        int capacity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        Event event = new Event(eventName, venue, date, time, capacity);
                        eventDAO.createEvent(event);
                        System.out.println("Event created successfully.");
                        break;

                    case 2:
                        System.out.print("Enter event ID: ");
                        int eventId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.print("Enter attendee name: ");
                        String attendeeName = scanner.nextLine();

                        try {
                            attendeeDAO.addAttendee(eventId, attendeeName);
                            System.out.println("Attendee added successfully.");
                        } catch (OverbookingException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("Enter event ID: ");
                        int eventId1 = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        Event retrievedEvent = eventDAO.getEventById(eventId1);
                        if (retrievedEvent != null) {
                            System.out.println("Event: " + retrievedEvent.getName());
                            System.out.println("Date: " + retrievedEvent.getDate());
                            System.out.println("Time: " + retrievedEvent.getTime());
                            System.out.println("Venue: " + retrievedEvent.getVenue());
                            System.out.println("Capacity: " + retrievedEvent.getCapacity());
                            System.out.println("Attendees: " + attendeeDAO.getAttendeesByEventId(eventId1));
                        } else {
                            System.out.println("Event not found.");
                        }
                        break;

                    case 4:
                        System.out.println("Exiting the program.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                System.out.println();
            } while (choice != 4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

