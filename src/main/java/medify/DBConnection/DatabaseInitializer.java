package medify.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    // File paths for the SQL scripts, in the resources folder
    private static final String SCHEMA_PATH = "/db/create_schema.sql";
    private static final String DATA_PATH = "/db/initialize_data.sql";

    // Executes DDL(CREATE TABLE) and DML(INSERT) statements
    public static void initializeDatabase() {
        // Get Connection
        try (Connection conn = DatabaseConnection.getConnection()) {

            System.out.println("Initializing database");

            // Read and execute schema creation (DDL)
            String schemaSql = readSqlFileAsString(SCHEMA_PATH);
            executeSqlBlock(conn, schemaSql, SCHEMA_PATH);

            // Read and execute data insertion (DML)
            String dataSql = readSqlFileAsString(DATA_PATH);
            executeSqlBlock(conn, dataSql, DATA_PATH);

            System.out.println("Initialized Database: Tables created and data inserted.");

        } catch (SQLException se) {
            System.out.println("Error initializing tables " + se.getMessage());
            se.printStackTrace(System.out);
        } catch (IOException e) {
            System.err.println("Could not read SQL resource files.");
            e.printStackTrace(System.out);
        } finally {
            // Close connection
            DatabaseConnection.closeConnection();
        }
    }

    // Read SQL script from db folder paths
    private static String readSqlFileAsString(String path) throws IOException {
        System.out.println("Loading SQL script " + path);

        try (InputStream input = DatabaseInitializer.class.getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("File not found from path: " + path);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }

        }
    }

    // Execute DDL and DML statements
    private static void executeSqlBlock(Connection conn, String sqlBlock, String blockName) throws SQLException {
        String[] commands = sqlBlock.split(";");

        System.out.println("Executing SQL statements");

        try (Statement stmt = conn.createStatement()) {
            for (String sql : commands) {
                String trimmedSql = sql.trim();

                if (!trimmedSql.isEmpty()) {
                    // Execute CREATE or INSERT statement
                    stmt.executeUpdate(trimmedSql);
                }
            }
        }
    }

    private static void dropExistingTables() {
        System.out.println("Dropping existing tables");
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String[] dropCommands = {
                    "DROP TABLE IF EXISTS Prescriptions CASCADE",
                    "DROP TABLE IF EXISTS Appointments CASCADE",
                    "DROP TABLE IF EXISTS Patients CASCADE",
                    "DROP TABLE IF EXISTS Doctors CASCADE"
            };

            for (String sql : dropCommands) {
                stmt.executeUpdate(sql);
            }
            System.out.println("Existing tables dropped successfully.");

        } catch (SQLException e) {
            System.err.println("Error dropping tables " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    // Initialize database
    public static void main(String[] args) {
        dropExistingTables();
        initializeDatabase();
    }

}
