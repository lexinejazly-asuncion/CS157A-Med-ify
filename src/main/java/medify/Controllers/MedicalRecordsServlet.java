package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.MedicalRecords;
import medify.DAO.MedicalRecordsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;


@WebServlet("/MedicalRecordsServlet")
public class MedicalRecordsServlet extends HttpServlet {

    private MedicalRecordsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new MedicalRecordsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("DB connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<MedicalRecords> list = dao.loadAll();
            req.setAttribute("medicalrecords", list);
            req.getRequestDispatcher("MedicalRecords.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading medical records", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int patientID = Integer.parseInt(req.getParameter("patientID"));
            int doctorID = Integer.parseInt(req.getParameter("doctorID"));
            int prescriptionID = Integer.parseInt(req.getParameter("prescriptionID"));
            Date visitDate = Date.valueOf(req.getParameter("visitDate"));

            MedicalRecords record = new MedicalRecords(
                    patientID,
                    doctorID,
                    prescriptionID,
                    visitDate
            );

            // Decide whether this is UPDATE or INSERT based on mode
            String mode = req.getParameter("mode");

            if ("update".equals(mode)) {
                dao.update(record);
            } else {
                dao.insert(record);
            }

            // Redirect back
            resp.sendRedirect("MedicalRecordsServlet");

        } catch (Exception e) {
            throw new ServletException("Error saving medical record", e);
        }
    }

}
