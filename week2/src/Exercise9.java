import java.io.*;
import java.sql.*;

public class Exercise9 {
    public static void main(String[] args) {
        Logger consoleLogger = new ConsoleLogger();
        Logger fileLogger = new FileLogger();
        Logger dbLogger = new DatabaseLogger();

        consoleLogger.info("Application started");
        consoleLogger.warning("Low memory");
        consoleLogger.error("Null pointer exception");

        fileLogger.info("User login successful");
        fileLogger.warning("Disk space running low");
        fileLogger.error("Unable to save file");

        dbLogger.info("Connected to database");
        dbLogger.warning("Query took too long");
        dbLogger.error("Transaction failed");
    }
}

interface Logger {
    void info(String message);
    void warning(String message);
    void error(String message);
}

class ConsoleLogger implements Logger {

    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void warning(String message) {
        System.out.println("[WARNING] " + message);
    }

    @Override
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}


class FileLogger implements Logger {
    private void writeToFile(String level, String message) {
        String filename = "week2/src/log.txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("[" + level + "] " + message +"\n");
        }
        catch (IOException e) {
            System.err.println("[ERROR] Failed to write to log file: " + e.getMessage());
        }
    }

    @Override
    public void info(String message) {
        writeToFile("INFO", message);
    }

    @Override
    public void warning(String message) {
        writeToFile("WARNING", message);
    }

    @Override
    public void error(String message) {
        writeToFile("ERROR", message);
    }
}

class DatabaseLogger implements Logger {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "logger_db";
    private static final String USER = "root";
    private static final String PASS = "Kshitiz@07";

    public DatabaseLogger() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                System.out.println("Database checked/created.");
            }

            try (Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
                 Statement stmt = conn.createStatement()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS logs (id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "level VARCHAR(10), message TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                stmt.executeUpdate(createTableSQL);
                System.out.println("Table checked/created.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL driver not found.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    private void logToDatabase(String level, String message) {
        String sql = "INSERT INTO logs (level, message) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, level);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to log to database: " + e.getMessage());
        }
    }

    @Override
    public void info(String message) {
        logToDatabase("INFO", message);
    }

    @Override
    public void warning(String message) {
        logToDatabase("WARNING", message);
    }

    @Override
    public void error(String message) {
        logToDatabase("ERROR", message);
    }
}
