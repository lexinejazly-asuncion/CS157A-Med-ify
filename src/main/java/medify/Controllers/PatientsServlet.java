package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import medify.Classes.Patients;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medify.Classes.Prescriptions;
import medify.DAO.PatientsDAO;
import medify.DBConnection.DatabaseConnection;

@WebServlet("/PatientsServlet")
public class PatientsServlet extends HttpServlet {

    private PatientsDAO dao;

    // Initializes the servlet and establishes the database connection and instantiates the DAOs
    @Override
    public void init() throws ServletException {
        try {
            dao = new PatientsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Handles HTTP GET requests for loading all patients to display in a table (default behavior)
    // or searching for a specific patient by their ID or name
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idSearch = request.getParameter("patientID");
            String nameSearch = request.getParameter("searchName");

            //Load the full table
            List<Patients> patients = dao.loadAll();

            // Search by ID
            if (idSearch != null && !idSearch.isEmpty()) {
                int id = Integer.parseInt(idSearch);
                Patients found = dao.searchById(id);
                request.setAttribute("patient", found);

                // If a match is found
                if (found != null) {
                    patients = List.of(found);
                } else {
                    patients = new ArrayList<>();
                }
            }

            // Search by name (case-insensitive)
            if (nameSearch != null && !nameSearch.trim().isEmpty()) {
                List<Patients> results = dao.searchByName(nameSearch.trim());
                request.setAttribute("patients", results);
                request.setAttribute("nameSearch", true);

                if (results.isEmpty()) {
                    request.setAttribute("nameNotFound", true);
                }

                // Return early so name search does NOT get overridden
                request.setAttribute("patient", null);
                request.getRequestDispatcher("Patients.jsp").forward(request, response);
                return;
            }

            request.setAttribute("patients", patients);
            request.getRequestDispatcher("Patients.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving patients", e);
        }
    }

    // Handles HTTP POST requests for processing form submissions
    // to perform data manipulation operations (Insert, Update, or Delete) on patients table
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String mode = request.getParameter("mode");
            //Delete an existing patient record
            if ("delete".equals(mode)) {
                int patientID = Integer.parseInt(request.getParameter("patientID"));
                dao.delete(patientID);
                response.sendRedirect("PatientsServlet");
                return;
            }

            String name = request.getParameter("patientName");
            java.sql.Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");

            // Update a patient record
            if ("update".equals(mode)) {
                int patientID = Integer.parseInt(request.getParameter("patientID"));
                Patients found = dao.searchById(patientID);
                request.setAttribute("updatePatientID", patientID);

                //Checks if patient exists
                if (found != null){
                    Patients patient = new Patients(patientID, name, dob, gender, address);
                    dao.update(patient);
                    request.setAttribute("updateIDNotFound", false);
                }
                else {
                    request.setAttribute("updateIDNotFound", true);

                    //Keep the table loaded, even when update fails because patientID does not exist
                    List<Patients> patients = dao.loadAll();
                    request.setAttribute("patients", patients);

                    request.getRequestDispatcher("Patients.jsp").forward(request, response);
                    return;
                }
            }
            else {
                // Insert a new patient record
                Patients patient = new Patients(0, name, dob, gender, address);
                dao.insert(patient);
            }

            //Redirect back to the Patients Servlet after update/insert/delete is complete
            response.sendRedirect("PatientsServlet");

        } catch (Exception e) {
            throw new ServletException("Error creating patient", e);
        }
    }
}
