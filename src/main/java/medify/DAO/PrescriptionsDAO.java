package medify.DAO;

import medify.Classes.Prescriptions;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionsDAO {

    private Connection conn;

    public PrescriptionsDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Prescriptions> loadAll() throws SQLException {
        List<Prescriptions> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        String query = "SELECT PrescriptionID, AppointmentID, PrescriptionDate, IssuedByDoctorID FROM Prescriptions";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Prescriptions p = new Prescriptions(
                        rs.getInt("PrescriptionID"),
                        rs.getInt("AppointmentID"),
                        rs.getDate("PrescriptionDate"),
                        rs.getInt("IssuedByDoctorID")
                );
                list.add(p);
            }
        }
        return list;
    }
}
