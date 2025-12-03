/*
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
        List<Prescriptions> prescriptions = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return prescriptions;
        }

        try {
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

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Prescriptions prescription = new Prescriptions(
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
                prescriptions.add(prescription);

            }
            rs.close();
            stmt.close();
        }
        catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }

        return prescriptions;
    }

    //Update prescriptionStatus in Prescriptions table
    public void updatePrescriptionStatus(Prescriptions prescription) throws SQLException {
        if (conn == null) {
            System.out.println("Could not connect to DB");
            return;
        }

        try {
            String query = "UPDATE Prescriptions " +
                    "SET PrescriptionStatus = ? " +
                    "WHERE PrescriptionID =  ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, prescription.getPrescriptionStatus());
            pstmt.setInt(2, prescription.getPrescriptionID());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }
    }

    //Insert a new prescription record
    public void insertPrescription(Prescriptions prescription) throws SQLException {
        if (conn == null) {
            System.out.println("Could not connect to DB");
            return;
        }

        try {
            String query = "INSERT INTO Prescriptions (" +
                    "PrescriptionDate, PrescriptionName, Dose, Quantity, Refills, PatientID, PrescriptionStatus" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, prescription.getPrescriptionDate());
            pstmt.setString(2, prescription.getPrescriptionName());
            pstmt.setString(3, prescription.getDose());
            pstmt.setInt(4, prescription.getQuantity());
            pstmt.setInt(5, prescription.getRefills());
            pstmt.setInt(6, prescription.getPatientID());
            pstmt.setString(7, prescription.getPrescriptionStatus());
            pstmt.executeUpdate();
            pstmt.close();

        }
        catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }
    }

    //Handle case where user inputs non-existent prescriptionID: check if a prescription exists in the database before updating its status
    public boolean prescriptionExists(int prescriptionID) throws SQLException {
        if (conn == null){
            System.out.println("Could not connect to database");
            return false;
        }

        try {
            String query = "Select PrescriptionName FROM Prescriptions WHERE PrescriptionID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, prescriptionID);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); //rs.next() returns true if there is a row with the patientID
        }
        catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }
        return false;
    }
}
 */

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

    // ===================== SELECT ALL =====================
    public List<Prescriptions> loadAll() throws SQLException {
        List<Prescriptions> list = new ArrayList<>();

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return list;
        }

        try {
            String query =
                    "SELECT p.prescriptionID, " +
                            "       p.prescriptionDate, " +
                            "       a.patientID, " +
                            "       pat.PatientName AS patientName, " +
                            "       doc.DoctorName AS doctorName, " +
                            "       p.appointmentID, " +
                            "       p.issuedByDoctorID " +
                            "FROM Prescriptions p " +
                            "JOIN Appointments a ON p.appointmentID = a.appointmentID " +
                            "JOIN Patients pat ON a.patientID = pat.patientID " +
                            "JOIN Doctors doc ON p.issuedByDoctorID = doc.doctorID " +
                            "ORDER BY p.prescriptionID ASC";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Prescriptions p = new Prescriptions(
                        rs.getInt("prescriptionID"),
                        rs.getDate("prescriptionDate"),
                        rs.getInt("patientID"),
                        rs.getString("patientName"),
                        "N/A",           // prescriptionName not in DB
                        "N/A",           // dose not in DB
                        0,               // quantity not in DB
                        0,               // refills not in DB
                        "N/A"            // status not in DB
                );

                list.add(p);
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }

        return list;
    }

    // ===================== INSERT =====================
    // Simple DB only supports date + appointmentID + doctor
    public void insertPrescription(Prescriptions p) throws SQLException {

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return;
        }

        try {
            String query =
                    "INSERT INTO Prescriptions (prescriptionDate, appointmentID, issuedByDoctorID) " +
                            "VALUES (?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, p.getPrescriptionDate());
            pstmt.setInt(2, p.getPatientID()); // FIX: use appointmentID instead
            pstmt.setInt(3, 1); // or p.getDoctorID if you add it
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }
    }

    // ===================== CHECK EXISTS =====================
    public boolean prescriptionExists(int id) throws SQLException {

        if (conn == null) {
            System.out.println("Could not connect to DB");
            return false;
        }

        try {
            String query = "SELECT prescriptionID FROM Prescriptions WHERE prescriptionID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(System.out);
        }

        return false;
    }

    // ===================== UPDATE =====================
    // Simple schema does NOT support status column. Do nothing.
    public void updatePrescriptionStatus(Prescriptions p) throws SQLException {
        System.out.println("STATUS COLUMN NOT IN SIMPLE SCHEMA — NO UPDATE PERFORMED.");
    }
}

