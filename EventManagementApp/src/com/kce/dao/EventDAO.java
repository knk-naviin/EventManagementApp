package com.kce.dao;
import java.sql.*;

import com.kce.bean.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class EventDAO {
    private Connection connection;

    public EventDAO(Connection connection) {
        this.connection = connection;
    }

    public void createEvent(Event event) throws SQLException {
        String query = "INSERT INTO events (name, venue, date, time, capacity) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getVenue());
            stmt.setString(3, event.getDate());
            stmt.setString(4, event.getTime());
            stmt.setInt(5, event.getCapacity());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                event.setId(rs.getInt(1));
            }
        }
    }

    public Event getEventById(int eventId) throws SQLException {
        String query = "SELECT * FROM events WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setVenue(rs.getString("venue"));
                event.setDate(rs.getString("date"));
                event.setTime(rs.getString("time"));
                event.setCapacity(rs.getInt("capacity"));
                return event;
            }
        }
        return null;
    }

    // Other methods for event management
}

