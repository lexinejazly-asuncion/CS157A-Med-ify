package medify.DAO;

import medify.Classes.MedicalRecords;
import medify.Classes.Prescriptions;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionsDAO {

    private Connection conn;

    public PrescriptionsDAO(Connection conn) {
        this.conn = conn;
    }

    // Load/select all rows from Prescriptions table
    public List<Prescriptions> loadAll() throws SQLException {
        List<Prescriptions> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        String query = "SELECT " +
                "p.PrescriptionID, " +
                "p.PrescriptionDate, " +
                "p.PrescriptionName, " +
                "p.Dose, " +
                "p.Quantity, " +
                "p.Refills, " +
                "p.PrescriptionStatus, " +
                "patients.PatientName, " +
                "doctors.DoctorName " +
                "FROM Prescriptions p " +
                "INNER JOIN Patients patients ON p.PatientID = patients.PatientID " +
                "INNER JOIN Doctors doctors ON p.IssuedByDoctorID = doctors.DoctorID";


        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Prescriptions p = new Prescriptions(
                        rs.getInt("PrescriptionID"),
                        rs.getDate("PrescriptionDate"),
                        rs.getString("PrescriptionName"),
                        rs.getString("Dose"),
                        rs.getInt("Quantity"),
                        rs.getInt("Refills"),
                        rs.getString("PatientName"),
                        rs.getString("DoctorName"),
                        rs.getString("PrescriptionStatus")
                );
                list.add(p);
            }
        }
        return list;
    }

    // Update prescriptionStatus in Prescriptions table
    public boolean updatePrescriptionStatus(int prescriptionId, String newStatus) throws SQLException {
        String query = "UPDATE Prescriptions SET PrescriptionStatus = ? WHERE PrescriptionID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, prescriptionId);

            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0;
        }
    }


}
