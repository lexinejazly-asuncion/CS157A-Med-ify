package medify.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import medify.Classes.Patients;

public class PatientsDAO {

    private Connection conn = null;

    public PatientsDAO(Connection conn){
        this.conn = conn;
    }

    // load data from the Patients table
    public List<Patients> loadAll() throws SQLException {
        List<Patients> patientsList = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to database");
            return patientsList;
        }

        String sql = "SELECT PatientID, PatientName, DOB, Gender, Address " +
                "FROM Patients ORDER BY PatientID ASC";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                patientsList.add(new Patients(
                        rs.getInt("PatientID"),
                        rs.getString("PatientName"),
                        rs.getDate("DOB"),
                        rs.getString("Gender"),
                        rs.getString("Address")
                ));
            }

            rs.close();
        }

        return patientsList;
    }

    // insert into the Patients table
    public void insert(Patients patient) throws SQLException {
        if (conn == null){
            System.out.println("Could not connect to database");
            return;
        }

        String sql = "INSERT INTO Patients (PatientName, DOB, Gender, Address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, patient.getPatientName());
            pstmt.setDate(2, patient.getDOB());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getAddress());

            pstmt.executeUpdate();
        }
    }

    // Update patient information
    public void update(Patients patient) throws SQLException {
        if (conn == null) {
            System.out.println("Could not connect to database");
            return;
        }

        String sql = "UPDATE Patients SET PatientName=?, DOB=?, Gender=?, Address=? WHERE PatientID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, patient.getPatientName());
            pstmt.setDate(2, patient.getDOB());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getAddress());
            pstmt.setInt(5, patient.getPatientID());

            pstmt.executeUpdate();

        }
    }

    // Get Patient by ID
    public Patients searchById(int patientID) throws SQLException {
        if (conn == null) {
            System.out.println("Could not connect to database");
            return null;
        }

        String sql = "SELECT PatientID, PatientName, DOB, Gender, Address FROM Patients WHERE PatientID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Patients(
                            rs.getInt("PatientID"),
                            rs.getString("PatientName"),
                            rs.getDate("DOB"),
                            rs.getString("Gender"),
                            rs.getString("Address")
                    );
                }
            }
        }
        return null;
    }

    // Search by Patient Name
    public List<Patients> searchByName(String name) throws SQLException {
        List<Patients> list = new ArrayList<>();

        String sql =  "SELECT PatientID, PatientName, DOB, Gender, Address " +
                      "FROM Patients WHERE LOWER(PatientName) LIKE LOWER(?) " +
                      "ORDER BY PatientID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Patients(
                            rs.getInt("PatientID"),
                            rs.getString("PatientName"),
                            rs.getDate("DOB"),
                            rs.getString("Gender"),
                            rs.getString("Address")
                    ));
                }
            }
        }

        return list;
    }
}

