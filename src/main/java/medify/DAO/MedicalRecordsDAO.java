package medify.DAO;

import medify.Classes.MedicalRecords;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordsDAO {

    private Connection conn;

    public MedicalRecordsDAO(Connection conn) {
        this.conn = conn;
    }

    public List<MedicalRecords> loadAll() throws SQLException {
        List<MedicalRecords> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        String query = "SELECT PatientID, DoctorID, PrescriptionID, VisitDate FROM MedicalRecords";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MedicalRecords m = new MedicalRecords(
                        rs.getInt("PatientID"),
                        rs.getInt("DoctorID"),
                        rs.getInt("PrescriptionID"),
                        rs.getDate("VisitDate")
                );
                list.add(m);
            }
        }
        return list;
    }

    // Insert new medical record
    public void insert(MedicalRecords record) throws SQLException {

        String sql = "INSERT INTO MedicalRecords (PatientID, DoctorID, PrescriptionID, VisitDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, record.getPatientID());
            pstmt.setInt(2, record.getDoctorID());
            pstmt.setInt(3, record.getPrescriptionID());
            pstmt.setDate(4, record.getVisitDate());

            pstmt.executeUpdate();
        }
    }

    public void update(MedicalRecords record) throws SQLException {

        String sql = "UPDATE MedicalRecords SET DoctorID=?, PrescriptionID=?, VisitDate=? WHERE PatientID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, record.getDoctorID());
            pstmt.setInt(2, record.getPrescriptionID());
            pstmt.setDate(3, record.getVisitDate());
            pstmt.setInt(4, record.getPatientID());

            pstmt.executeUpdate();
        }
    }


}
