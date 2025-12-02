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

        //Get mode: UPDATE or INSERT
        String mode = req.getParameter("mode");

        if ("update".equals(mode)) {
            try {
                int prescriptionID = Integer.parseInt(req.getParameter("prescriptionID"));
                String newStatus = req.getParameter("newStatus");

                Prescriptions prescription = new Prescriptions(prescriptionID, newStatus);
                dao.updatePrescriptionStatus(prescription); //Call to update prescriptionStatus in Prescriptions table
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
                String status = req.getParameter("prescriptionStatus");


                Prescriptions prescription = new Prescriptions(date, patientID, prescriptionName, dose, quantity, refills, status);
                dao.insertPrescription(prescription); //Call to insert a new prescription record
            } catch (Exception e) {
                throw new ServletException("Error saving updating prescription record", e);
            }
        }

        //Redirect back to the Prescriptions Servlet after update/insert is complete
        resp.sendRedirect(req.getContextPath() + "/PrescriptionsServlet");
    }


}
