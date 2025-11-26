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
}
