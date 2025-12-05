package medify.DBConnection;
import java.sql.*;

public class DatabaseConnection {

    private static String url = "";
    private static String user = "";
    private static String pass = "";

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
            conn = DriverManager.getConnection(url, user, pass); // Connect to the database specifying user, password, and DB url
        }
        return conn;
    }

    // Close connection, needed to release DB resources after executing statements
    public static void closeConnection() {
        // If there is a connection
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
