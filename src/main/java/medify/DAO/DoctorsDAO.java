package medify.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import medify.Classes.Doctors;

public class DoctorsDAO {

    private Connection conn;

    public DoctorsDAO(Connection conn) {
        this.conn = conn;
    }

    // Load all doctors
    public List<Doctors> loadAll() throws SQLException {
        List<Doctors> doctorsList = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return doctorsList;
        }

        String sql = "SELECT DoctorID, DoctorName, PhoneNumber, Department FROM Doctors ORDER BY DoctorID ASC";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                doctorsList.add(new Doctors(
                        rs.getInt("DoctorID"),
                        rs.getString("DoctorName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Department")
                ));
            }
        }
        return doctorsList;
    }

    // Insert doctor
    public void insert(Doctors doctor) throws SQLException {
        String sql = "INSERT INTO DOCTORS (DoctorName, PhoneNumber, Department) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getDoctorName());
            pstmt.setString(2, doctor.getPhoneNumber());
            pstmt.setString(3, doctor.getDepartment());

            pstmt.executeUpdate();
        }
    }

    // Update existing Doctor entry in database
    public void update(Doctors doctor) throws SQLException {
        String sql = "UPDATE Doctors SET DoctorName=?, PhoneNumber=?, Department=? WHERE DoctorID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getDoctorName());
            pstmt.setString(2, doctor.getPhoneNumber());
            pstmt.setString(3, doctor.getDepartment());
            pstmt.setInt(4, doctor.getDoctorID());

            pstmt.executeUpdate();
        }
    }

    // Search by DoctorID
    public Doctors searchById(int doctorID) throws SQLException {
        String sql = "SELECT DoctorID, DoctorName, PhoneNumber, Department FROM Doctors WHERE DoctorID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctors(
                            rs.getInt("DoctorID"),
                            rs.getString("DoctorName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Department")
                    );
                }
            }
        }

        return null;
    }

    // Search by name (case-insensitive)
    public List<Doctors> searchByName(String name) throws SQLException {
        List<Doctors> list = new ArrayList<>();

        String sql = "SELECT DoctorID, DoctorName, PhoneNumber, Department " +
                     "FROM Doctors WHERE LOWER(DoctorName) LIKE LOWER(?) " +
                     "ORDER BY DoctorID ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Doctors(
                            rs.getInt("DoctorID"),
                            rs.getString("DoctorName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Department")
                    ));
                }
            }
        }
        return list;
    }
}
