package com.kce.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kce.bean.Event;
import com.kce.util.OverbookingException;

public class AttendeeDAO {
    private Connection connection;

    public AttendeeDAO(Connection connection) {
        this.connection = connection;
    }

    public void addAttendee(int eventId, String attendeeName) throws OverbookingException, SQLException {
        String checkCapacityQuery = "SELECT capacity, COUNT(*) AS attendee_count FROM attendees WHERE event_id = ? GROUP BY event_id";
        try (PreparedStatement checkCapacityStmt = connection.prepareStatement(checkCapacityQuery)) {
            checkCapacityStmt.setInt(1, eventId);
            ResultSet rs = checkCapacityStmt.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int attendeeCount = rs.getInt("attendee_count");
                if (attendeeCount >= capacity) {
                    throw new OverbookingException("Event is already fully booked.");
                }
            }
        }

        String addAttendeeQuery = "INSERT INTO attendees (event_id, name) VALUES (?, ?)";
        try (PreparedStatement addAttendeeStmt = connection.prepareStatement(addAttendeeQuery)) {
            addAttendeeStmt.setInt(1, eventId);
            addAttendeeStmt.setString(2, attendeeName);
            addAttendeeStmt.executeUpdate();
        }
    }

    public String getAttendeesByEventId(int eventId) throws SQLException {
        String query = "SELECT name FROM attendees WHERE event_id = ?";
        StringBuilder attendees = new StringBuilder();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String attendeeName = rs.getString("name");
                attendees.append(attendeeName).append(", ");
            }
        }
        if (attendees.length() > 0) {
            attendees.delete(attendees.length() - 2, attendees.length()); // Remove the trailing comma and space
        }
        return attendees.toString();
    }

    // Other methods for attendee management
}
