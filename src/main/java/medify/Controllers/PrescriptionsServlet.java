package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Prescriptions;
import medify.DAO.PrescriptionsDAO;
import medify.DBConnection.DatabaseConnection;
import medify.DAO.PatientsDAO;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/PrescriptionsServlet")
public class PrescriptionsServlet extends HttpServlet {

    private PrescriptionsDAO dao;
    private PatientsDAO patientsDAO;

    @Override
    public void init() throws ServletException {
        try {
            dao = new PrescriptionsDAO(DatabaseConnection.getConnection());
            patientsDAO = new PatientsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("DB connection error", e);
        }
    }

    // =================== CORS ===================
    private void setCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        setCors(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // =================== GET ===================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setCors(resp);
        String type = req.getParameter("type");

        // REACT JSON mode
        if ("json".equals(type)) {
            sendJson(resp);
            return;
        }

        // JSP fallback
        try {
            List<Prescriptions> list = dao.loadAll();
            req.setAttribute("prescriptions", list);
            req.getRequestDispatcher("Prescriptions.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading prescriptions", e);
        }
    }

    // =================== POST ===================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setCors(resp);

        String mode = req.getParameter("mode");

        try {
            if ("update".equals(mode)) {

                int prescriptionID = Integer.parseInt(req.getParameter("prescriptionID"));
                String newStatus = req.getParameter("newStatus");

                if (!dao.prescriptionExists(prescriptionID)) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                Prescriptions p = new Prescriptions(prescriptionID, newStatus);
                dao.updatePrescriptionStatus(p);

                resp.setStatus(HttpServletResponse.SC_OK);
                return;

            } else if ("insert".equals(mode)) {

                Date date = Date.valueOf(req.getParameter("prescriptionDate"));
                int patientID = Integer.parseInt(req.getParameter("patientID"));
                String prescriptionName = req.getParameter("prescriptionName");
                String dose = req.getParameter("dose");
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                int refills = Integer.parseInt(req.getParameter("refills"));

                if (!patientsDAO.patientExists(patientID)) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                Prescriptions newP = new Prescriptions(
                        date, patientID, prescriptionName, dose, quantity, refills, "Processing"
                );

                dao.insertPrescription(newP);

                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // =================== JSON ===================
    private void sendJson(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");

        try {
            List<Prescriptions> list = dao.loadAll();

            for (int i = 0; i < list.size(); i++) {
                Prescriptions p = list.get(i);

                json.append("{")
                        .append("\"prescriptionID\":").append(p.getPrescriptionID()).append(",")
                        .append("\"prescriptionDate\":\"").append(p.getPrescriptionDate()).append("\",")
                        .append("\"patientID\":").append(p.getPatientID()).append(",")
                        .append("\"patientName\":\"").append(p.getPatientName()).append("\",")
                        .append("\"prescriptionName\":\"").append(p.getPrescriptionName()).append("\",")
                        .append("\"dose\":\"").append(p.getDose()).append("\",")
                        .append("\"quantity\":").append(p.getQuantity()).append(",")
                        .append("\"refills\":").append(p.getRefills()).append(",")
                        .append("\"prescriptionStatus\":\"").append(p.getPrescriptionStatus()).append("\"")
                        .append("}");

                if (i < list.size() - 1) json.append(",");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }
}
