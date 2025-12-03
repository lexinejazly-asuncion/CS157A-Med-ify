package medify.DAO;

import medify.Classes.Appointments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsDAO {

    private Connection conn;

    public AppointmentsDAO(Connection conn) {
        this.conn = conn;
    }

    // =====================================================
    // LOAD ALL APPOINTMENTS
    // =====================================================
    public List<Appointments> loadAll() throws SQLException {
        List<Appointments> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        String query = "SELECT AppointmentID, PatientID, DoctorID, ApptTime, Status " +
                "FROM Appointments ORDER BY AppointmentID ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Appointments a = new Appointments(
                        rs.getInt("AppointmentID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getTimestamp("ApptTime"),
                        rs.getString("Status")
                );
                list.add(a);
            }
        }

        return list;
    }

    // =====================================================
    // INSERT APPOINTMENT — needed for AddAppointment React page
    // =====================================================
    public void insert(Appointments a) throws SQLException {

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return;
        }

        String query = "INSERT INTO Appointments " +
                "(PatientID, DoctorID, ApptTime, Status) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, a.getPatientID());
            pstmt.setInt(2, a.getDoctorID());
            pstmt.setTimestamp(3, a.getApptTime());
            pstmt.setString(4, a.getStatus());

            pstmt.executeUpdate();
        }
    }

}
