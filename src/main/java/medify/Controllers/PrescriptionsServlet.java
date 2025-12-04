package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.MedicalRecords;
import medify.Classes.Patients;
import medify.Classes.Prescriptions;
import medify.DAO.PrescriptionsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import for checking if a patient exists
import medify.DAO.PatientsDAO;

@WebServlet("/PrescriptionsServlet")
public class PrescriptionsServlet extends HttpServlet {

    private PrescriptionsDAO dao;
    private PatientsDAO patientsDAO;

    //Initialize a connection to the database
    @Override
    public void init() throws ServletException {
        try {
            dao = new PrescriptionsDAO(DatabaseConnection.getConnection());
            patientsDAO = new PatientsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("DB connection error", e);
        }
    }

    //Retrieve data from the server
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idSearch = req.getParameter("prescriptionID");
            String nameSearch = req.getParameter("patientName");
            String statusFilter = req.getParameter("status");

            List<Prescriptions> prescriptions;

            //Filter by prescription status
            if (statusFilter != null && !statusFilter.isEmpty()) {
                prescriptions = dao.loadByStatus(statusFilter);
                req.setAttribute("statusFilter", statusFilter);
            } else {
                prescriptions = dao.loadAll();
            }

            // Search by ID
            if (idSearch != null && !idSearch.isEmpty()) {
                int id = Integer.parseInt(idSearch);
                Prescriptions found = dao.searchById(id);
                req.setAttribute("prescriptionID", id);

                if (found != null) {
                    prescriptions = List.of(found);
                    req.setAttribute("idNotFound", false);
                }
                else {
                    prescriptions = new ArrayList<>();
                    req.setAttribute("idNotFound", true);
                }
            }

            // Search by name
            if (nameSearch != null && !nameSearch.trim().isEmpty()) {
                List<Prescriptions> results = dao.searchByName(nameSearch.trim());
                req.setAttribute("prescriptions", results);
                req.setAttribute("nameSearch", true);
                req.setAttribute("searchName", nameSearch.trim());

                if (results.isEmpty()) {
                    req.setAttribute("nameNotFound", true);
                }
                else {
                    req.setAttribute("nameNotFound", false);
                }

                req.getRequestDispatcher("Prescriptions.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("prescriptions", prescriptions);
            req.getRequestDispatcher("Prescriptions.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading prescriptions", e);
        }
    }

    //Send data to the server
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //Get mode: UPDATE or INSERT
        String mode = req.getParameter("mode");

        if ("update".equals(mode)) {
            try {
                int prescriptionID = Integer.parseInt(req.getParameter("prescriptionID"));
                String newStatus = req.getParameter("newStatus");

                Prescriptions found = dao.searchById(prescriptionID);
                req.setAttribute("updatePrescriptionID", prescriptionID);

                //Checks if prescription exists
                if (found != null) {
                    Prescriptions prescription = new Prescriptions(prescriptionID, newStatus);
                    dao.updatePrescriptionStatus(prescription); //Call to update prescriptionStatus in Prescriptions table
                    req.setAttribute("updateIDNotFound", false);
                } else {
                    req.setAttribute("updateIDNotFound", true);

                    //Keep the table loaded, even when update fails because prescriptionID does not exist
                    List<Prescriptions> prescriptions = dao.loadAll();
                    req.setAttribute("prescriptions", prescriptions);

                    req.getRequestDispatcher("Prescriptions.jsp").forward(req, resp);
                    return;
                }

            } catch (Exception e) {
                throw new ServletException("Error saving updating prescription record", e);
            }
        }
        else if ("insert".equals(mode)) {
            try {
                Date date = Date.valueOf(req.getParameter("prescriptionDate"));
                int patientID = Integer.parseInt(req.getParameter("patientID"));
                String prescriptionName = req.getParameter("prescriptionName");
                String dose = req.getParameter("dose");
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                int refills = Integer.parseInt(req.getParameter("refills"));
                String status = "Processing"; //When a new prescription record is made, it will always start as 'processing'

                Patients found = patientsDAO.searchById(patientID);
                req.setAttribute("insertRecordPatientID", patientID);

                //Checks if patient exists
                if (found != null) {
                    Prescriptions prescription = new Prescriptions(date, patientID, prescriptionName, dose, quantity, refills, status);
                    dao.insertPrescription(prescription); //Call to insert a new prescription record
                    req.setAttribute("insertRecordPatientIDNotFound", false);
                } else {
                    req.setAttribute("insertRecordPatientIDNotFound", true);
                    //Keep the table loaded, even when update fails because patientID does not exist
                    List<Prescriptions> prescriptions = dao.loadAll();
                    req.setAttribute("prescriptions", prescriptions);
                    req.getRequestDispatcher("Prescriptions.jsp").forward(req, resp);
                    return;
                }

            } catch (Exception e) {
                throw new ServletException("Error saving inserting new prescription record", e);
            }
        }

        //Redirect back to the Prescriptions Servlet after update/insert is complete
        resp.sendRedirect(req.getContextPath() + "/PrescriptionsServlet");
    }


}
