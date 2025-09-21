package com.project.currencyconverter.database;

import com.project.currencyconverter.model.Conversion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:currency_converter.db";

    public DatabaseManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS conversions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "from_currency TEXT NOT NULL," +
                    "to_currency TEXT NOT NULL," +
                    "amount REAL NOT NULL," +
                    "converted_amount REAL NOT NULL," +
                    "rate REAL NOT NULL," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
            
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    public void saveConversion(Conversion conversion) {
        String sql = "INSERT INTO conversions(from_currency, to_currency, amount, converted_amount, rate) VALUES(?,?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, conversion.getFromCurrency());
            pstmt.setString(2, conversion.getToCurrency());
            pstmt.setDouble(3, conversion.getAmount());
            pstmt.setDouble(4, conversion.getConvertedAmount());
            pstmt.setDouble(5, conversion.getRate());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving conversion: " + e.getMessage());
        }
    }

    public List<Conversion> getConversionHistory() {
        List<Conversion> history = new ArrayList<>();
        String sql = "SELECT * FROM conversions ORDER BY timestamp DESC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Conversion conversion = new Conversion();
                conversion.setId(rs.getInt("id"));
                conversion.setFromCurrency(rs.getString("from_currency"));
                conversion.setToCurrency(rs.getString("to_currency"));
                conversion.setAmount(rs.getDouble("amount"));
                conversion.setConvertedAmount(rs.getDouble("converted_amount"));
                conversion.setRate(rs.getDouble("rate"));
                conversion.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                
                history.add(conversion);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving history: " + e.getMessage());
        }
        return history;
    }
}