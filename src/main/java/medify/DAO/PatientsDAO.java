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

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT PatientID, PatientName, DOB, Gender, Address FROM Patients"
            );

            while (rs.next()) {
                Patients patient = new Patients(
                        rs.getInt("PatientID"),
                        rs.getString("PatientName"),
                        rs.getDate("DOB"),
                        rs.getString("Gender"),
                        rs.getString("Address")
                );
                patientsList.add(patient);
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException se)
        {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }

        return patientsList;
    }


    // insert into the Patients table
    public void insert(Patients patient) throws SQLException {
        if (conn == null){
            System.out.println("Could not connect to database");
            return;
        }

        try{
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Patients(PatientName, DOB, Gender, Address) values(?,?,?,?)");

            pstmt.setString(1, patient.getPatientName());
            pstmt.setDate(2, patient.getDOB());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getAddress());

            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException se)
        {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }
    }
}
