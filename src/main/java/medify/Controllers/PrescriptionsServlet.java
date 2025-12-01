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
import java.util.List;

@WebServlet("/PrescriptionsServlet")
public class PrescriptionsServlet extends HttpServlet {

    private PrescriptionsDAO dao;

    //Initialize a connection to the database
    @Override
    public void init() throws ServletException {
        try {
            dao = new PrescriptionsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("DB connection error", e);
        }
    }

    //Retrieve data from the server
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<Prescriptions> list = dao.loadAll();
            req.setAttribute("prescriptions", list);
            req.getRequestDispatcher("Prescriptions.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading prescriptions", e);
        }
    }

    //Send data to the server
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("prescriptionId");
        String newStatus = req.getParameter("newStatus");

        if (id == null || id.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Enter a valid prescriptionId. ");
            return;
        }

        if (newStatus == null || newStatus.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Enter a valid prescription status: Processing, Filled, or Completed. ");
            return;
        }

        int prescriptionId;

        try {
            prescriptionId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Enter a valid prescriptionId.");
            return;
        }

        try {
            boolean updated = dao.updatePrescriptionStatus(prescriptionId, newStatus);

            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/PrescriptionsServlet");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Prescription with ID " + prescriptionId + " not found.");
            }

        } catch (SQLException e) {
            throw new ServletException("Database error during status update.", e);
        }
    }


}
