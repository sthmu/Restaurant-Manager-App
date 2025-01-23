package team.group3.restaurantmanagementsystem.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static DatabaseConnector instance; // Singleton instance
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/rms";
    private final String username = "root";
    private final String password = "root";

    // Private constructor to prevent instantiation
    private DatabaseConnector() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database Driver not found.", e);
        }
    }

    // Public method to get the Singleton instance
    public static DatabaseConnector getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseConnector.class) {
                if (instance == null) {
                    instance = new DatabaseConnector();
                }
            }
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
}

