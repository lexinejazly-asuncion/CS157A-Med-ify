package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import medify.Classes.Patients;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
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
            List<Patients> patients = dao.loadAll(); // updated method
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
            String name = request.getParameter("patientName");
            java.sql.Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");

            Patients patient = new Patients(
                    0,
                    name,
                    dob,
                    gender,
                    address
            );

            dao.insert(patient);

            response.sendRedirect("PatientsServlet");

        } catch (Exception e) {
            throw new ServletException("Error creating patient", e);
        }
    }



}
