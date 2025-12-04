package medify.DBConnection;
import java.sql.*;

public class DatabaseConnection {
    private static String url = "jdbc:postgresql://localhost:5433/postgres";
    private static String user = "postgres";
    private static String pass = "Iloveeating1!";

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
}
