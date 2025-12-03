package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Patients;
import medify.DAO.PatientsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

@WebServlet("/PatientsServlet")
public class PatientsServlet extends HttpServlet {

    private PatientsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new PatientsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Database connection failed", e);
        }
    }

    // =================== CORS for React ===================
    private void setCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        setCors(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // =================== GET ============================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCors(response);

        String type = request.getParameter("type");

        if ("json".equals(type)) {
            sendJson(response);
            return;
        }

        try {
            List<Patients> patients = dao.loadAll();
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("Patients.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving patients", e);
        }
    }

    // =================== POST ============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCors(response);

        try {
            String name = request.getParameter("patientName");
            Date dob = Date.valueOf(request.getParameter("dob"));
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

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            throw new ServletException("Error creating patient", e);
        }
    }

    // =================== JSON ============================
    private void sendJson(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");

        try {
            List<Patients> patients = dao.loadAll();

            for (int i = 0; i < patients.size(); i++) {
                Patients p = patients.get(i);

                json.append("{")
                        .append("\"id\":").append(p.getPatientID()).append(",")
                        .append("\"name\":\"").append(p.getPatientName()).append("\",")
                        .append("\"dob\":\"").append(p.getDOB()).append("\",")
                        .append("\"gender\":\"").append(p.getGender()).append("\",")
                        .append("\"address\":\"").append(p.getAddress()).append("\"")
                        .append("}");

                if (i < patients.size() - 1) json.append(",");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        json.append("]");
        response.getWriter().write(json.toString());
    }
}
