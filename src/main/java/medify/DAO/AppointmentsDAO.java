/*
package medify.DAO;


import medify.Classes.Appointments;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsDAO {

    private Connection conn;

    public AppointmentsDAO(Connection conn){
        this.conn = conn;
    }

    // Load all appointments
    public List<Appointments> loadAll() throws SQLException {
        List<Appointments> list = new ArrayList<>();

        String sql = "SELECT AppointmentID, PatientID, DoctorID, ApptTime, Status " +
                "FROM Appointments ORDER BY AppointmentID ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Appointments(
                        rs.getInt("AppointmentID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getTimestamp("ApptTime"),
                        rs.getString("Status")
                ));
            }
        }
        return list;
    }

    // Insert appointment
    public void insert(Appointments a) throws SQLException {
        String sql = "INSERT INTO Appointments (PatientID, DoctorID, ApptTime, Status) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, a.getPatientID());
            pstmt.setInt(2, a.getDoctorID());
            pstmt.setTimestamp(3, a.getApptTime());
            pstmt.setString(4, a.getStatus());
            pstmt.executeUpdate();
        }
    }

    // Update appointment
    public void update(Appointments a) throws SQLException {
        String sql = "UPDATE Appointments SET PatientID=?, DoctorID=?, ApptTime=?, Status=? " +
                "WHERE AppointmentID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, a.getPatientID());
            pstmt.setInt(2, a.getDoctorID());
            pstmt.setTimestamp(3, a.getApptTime());
            pstmt.setString(4, a.getStatus());
            pstmt.setInt(5, a.getAppointmentID());
            pstmt.executeUpdate();
        }
    }

    // Search by AppointmentID
    public Appointments searchById(int id) throws SQLException {
        String sql = "SELECT AppointmentID, PatientID, DoctorID, ApptTime, Status " +
                "FROM Appointments WHERE AppointmentID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Appointments(
                            rs.getInt("AppointmentID"),
                            rs.getInt("PatientID"),
                            rs.getInt("DoctorID"),
                            rs.getTimestamp("ApptTime"),
                            rs.getString("Status")
                    );
                }
            }
        }
        return null;
    }
}

 */

package medify.DAO;

import medify.Classes.Appointments;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsDAO {

    private Connection conn;

    public AppointmentsDAO(Connection conn){
        this.conn = conn;
    }

    // Load all appointments
    public List<Appointments> loadAll() throws SQLException {
        List<Appointments> list = new ArrayList<>();

        String sql = "SELECT AppointmentID, PatientID, DoctorID, ApptTime, Status " +
                "FROM Appointments ORDER BY AppointmentID ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Appointments(
                        rs.getInt("AppointmentID"),
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getTimestamp("ApptTime"),
                        rs.getString("Status")
                ));
            }
        }
        return list;
    }

    // Insert appointment
    public void insert(Appointments a) throws SQLException {
        String sql = "INSERT INTO Appointments (PatientID, DoctorID, ApptTime, Status) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, a.getPatientID());
            pstmt.setInt(2, a.getDoctorID());
            pstmt.setTimestamp(3, a.getApptTime());
            pstmt.setString(4, a.getStatus());
            pstmt.executeUpdate();
        }
    }

    // Update ONLY appt time and status
    public void update(Appointments a) throws SQLException {
        String sql = "UPDATE Appointments SET ApptTime=?, Status=? WHERE AppointmentID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, a.getApptTime());
            pstmt.setString(2, a.getStatus());
            pstmt.setInt(3, a.getAppointmentID());
            pstmt.executeUpdate();
        }
    }

    // DELETE appointment
    public void delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE AppointmentID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentID);
            pstmt.executeUpdate();
        }
    }


    // Search by Appointment ID
    public Appointments searchById(int id) throws SQLException {
        String sql = "SELECT AppointmentID, PatientID, DoctorID, ApptTime, Status " +
                "FROM Appointments WHERE AppointmentID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Appointments(
                            rs.getInt("AppointmentID"),
                            rs.getInt("PatientID"),
                            rs.getInt("DoctorID"),
                            rs.getTimestamp("ApptTime"),
                            rs.getString("Status")
                    );
                }
            }
        }
        return null;
    }
}
