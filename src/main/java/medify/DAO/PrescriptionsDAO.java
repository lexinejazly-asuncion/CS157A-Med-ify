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

    // Load/select rows from Prescriptions table
    public List<Prescriptions> loadAll() throws SQLException {
        List<Prescriptions> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        String query = "SELECT " +
                "p.prescriptionID, " +
                "p.PrescriptionDate, " +
                "p.patientID, " +
                "patients.PatientName, " +
                "p.PrescriptionName, " +
                "p.Dose, " +
                "p.Quantity, " +
                "p.Refills, " +
                "p.PrescriptionStatus " +
                "FROM Prescriptions p " +
                "INNER JOIN Patients patients ON p.PatientID = patients.PatientID " +
                "ORDER BY p.prescriptionID ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Prescriptions p = new Prescriptions(
                        rs.getInt("PrescriptionID"),
                        rs.getDate("PrescriptionDate"),
                        rs.getInt("PatientID"),
                        rs.getString("PatientName"),
                        rs.getString("PrescriptionName"),
                        rs.getString("Dose"),
                        rs.getInt("Quantity"),
                        rs.getInt("Refills"),
                        rs.getString("PrescriptionStatus")
                );
                list.add(p);
            }
        }
        return list;
    }

    //Update prescriptionStatus in Prescriptions table
    public void updatePrescriptionStatus(Prescriptions prescription) throws SQLException {
        String query = "UPDATE Prescriptions " +
                "SET PrescriptionStatus = ? " +
                "WHERE PrescriptionID =  ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, prescription.getPrescriptionStatus());
            pstmt.setInt(2, prescription.getPrescriptionID());
            pstmt.executeUpdate();

        }
    }

    //Insert a new prescription record
    public void insertPrescription(Prescriptions prescription) throws SQLException {
        String query = "INSERT INTO Prescriptions (" +
                "PrescriptionDate, PrescriptionName, Dose, Quantity, Refills, PrescriptionStatus, PatientID" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, prescription.getPrescriptionDate());
            pstmt.setString(2, prescription.getPrescriptionName());
            pstmt.setString(3, prescription.getDose());
            pstmt.setInt(4, prescription.getQuantity());
            pstmt.setInt(5, prescription.getRefills());
            pstmt.setString(6, prescription.getPrescriptionStatus());
            pstmt.setInt(7, prescription.getPatientID());
            pstmt.executeUpdate();

        }
    }
}
