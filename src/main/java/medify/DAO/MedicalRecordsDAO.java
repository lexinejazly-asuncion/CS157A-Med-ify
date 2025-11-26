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
}
