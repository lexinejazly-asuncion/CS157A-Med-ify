package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.DAO.AppointmentsDAO;
import medify.Classes.Appointments;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/AppointmentsServlet")
public class AppointmentsServlet extends HttpServlet {

    private AppointmentsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new AppointmentsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("DB connection error", e);
        }
    }

    // ======================================================
    // CORS (Required for React)
    // ======================================================
    private void setCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setCors(resp);
    }

    // ======================================================
    // GET (JSP or JSON endpoint)
    // ======================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setCors(resp);

        String type = req.getParameter("type");

        // React endpoint
        if ("json".equals(type)) {
            sendJson(resp);
            return;
        }

        // JSP fallback
        try {
            List<Appointments> list = dao.loadAll();
            req.setAttribute("appointments", list);
            req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error fetching appointments", e);
        }
    }

    // ======================================================
    // POST (Insert new appointment)
    // ======================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setCors(resp);

        String mode = req.getParameter("mode");

        // ----- INSERT new appointment -----
        if ("insert".equals(mode)) {
            try {
                int patientID = Integer.parseInt(req.getParameter("patientID"));
                int doctorID = Integer.parseInt(req.getParameter("doctorID"));
                String apptTime = req.getParameter("apptTime");
                String status = req.getParameter("status");

                Timestamp ts = Timestamp.valueOf(apptTime); // "YYYY-MM-DD HH:MM:00"

                Appointments a = new Appointments(
                        0,
                        patientID,
                        doctorID,
                        ts,
                        status
                );

                dao.insert(a);
                resp.setStatus(HttpServletResponse.SC_OK);

            } catch (Exception e) {
                throw new ServletException("Error inserting appointment", e);
            }
        }
    }

    // ======================================================
    // JSON Output for React
    // ======================================================
    private void sendJson(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");

        try {
            List<Appointments> list = dao.loadAll();

            for (int i = 0; i < list.size(); i++) {
                Appointments a = list.get(i);

                json.append("{")
                        .append("\"appointmentID\":").append(a.getAppointmentID()).append(",")
                        .append("\"patientID\":").append(a.getPatientID()).append(",")
                        .append("\"doctorID\":").append(a.getDoctorID()).append(",")
                        .append("\"apptTime\":\"").append(a.getApptTime()).append("\",")
                        .append("\"status\":\"").append(a.getStatus()).append("\"")
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
