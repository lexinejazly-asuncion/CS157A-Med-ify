package medify.DBConnection;
import java.sql.*;

public class DatabaseConnection {

    private static String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=medify"; //jdbc:postgresql://localhost:5434/medify";
    private static String user = "postgres";
    private static String pass = "M@c@h1y@";

    private static Connection conn = null;

    static {
        try {
            // Explicitly load the driver class
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // If the driver JAR is missing from WEB-INF/lib, this error will be thrown.
            System.err.println("FATAL: PostgreSQL JDBC Driver not found!");
            // Propagate the error to stop the application startup
            throw new RuntimeException("PostgreSQL Driver Registration Failed", e);
        }
    }

    // Returns the connection, null if it does not exist yet
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }

    // Close connection
    public static void closeConnection() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException se) {
                System.err.println("Error closing connection: " + se.getMessage());
            }
            conn = null;
        }
    }
}
