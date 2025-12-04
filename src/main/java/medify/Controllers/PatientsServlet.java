package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import medify.Classes.Patients;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medify.DAO.PatientsDAO;
import medify.DBConnection.DatabaseConnection;

@WebServlet("/PatientsServlet")
public class PatientsServlet extends HttpServlet {

    private PatientsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new PatientsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idSearch = request.getParameter("patientID");
            String nameSearch = request.getParameter("searchName");

            List<Patients> patients = dao.loadAll();

            // Search by ID
            if (idSearch != null && !idSearch.isEmpty()) {
                int id = Integer.parseInt(idSearch);
                Patients found = dao.searchById(id);
                request.setAttribute("patient", found);

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String mode = request.getParameter("mode");

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

            if ("update".equals(mode)) { // update mode
                int patientID = Integer.parseInt(request.getParameter("patientID"));
                Patients patient = new Patients(patientID, name, dob, gender, address);
                dao.update(patient);
            }
            else {
                // insert mode
                Patients patient = new Patients(0, name, dob, gender, address);
                dao.insert(patient);
            }

            response.sendRedirect("PatientsServlet");

        } catch (Exception e) {
            throw new ServletException("Error creating patient", e);
        }
    }
}
