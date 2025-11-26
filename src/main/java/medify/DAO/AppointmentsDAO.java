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

    public List<Appointments> loadAll() throws SQLException {
        List<Appointments> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        String query = "SELECT AppointmentID, PatientID, DoctorID, ApptTime, Status FROM Appointments";

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
}
