/*
package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Appointments;
import medify.DAO.AppointmentsDAO;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idSearch = req.getParameter("appointmentID");

            // SEARCH BY Appointment ID
            if (idSearch != null && !idSearch.isEmpty()) {
                int id = Integer.parseInt(idSearch);
                Appointments found = dao.searchById(id);

                req.setAttribute("appointment", found);

                if (found != null) {
                    req.setAttribute("appointments", List.of(found));
                } else {
                    req.setAttribute("appointments", List.of());
                    req.setAttribute("idNotFound", true);
                }

                req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
                return;
            }

            // LOAD ALL
            List<Appointments> list = dao.loadAll();
            req.setAttribute("appointments", list);

            req.getRequestDispatcher("Appointments.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving appointments", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String mode = req.getParameter("mode");

            int patientID = Integer.parseInt(req.getParameter("patientID"));
            int doctorID = Integer.parseInt(req.getParameter("doctorID"));

            // Convert datetime-local to SQL Timestamp
            String raw = req.getParameter("apptTime");     // e.g. "2025-12-04T23:03"
            raw = raw.replace("T", " ") + ":00";           // -> "2025-12-04 23:03:00"

            Timestamp apptTime = Timestamp.valueOf(raw);

            String status = req.getParameter("status");

            if ("update".equals(mode)) {
                int appointmentID = Integer.parseInt(req.getParameter("appointmentID"));
                Appointments a = new Appointments(appointmentID, patientID, doctorID, apptTime, status);
                dao.update(a);
            } else {
                Appointments a = new Appointments(0, patientID, doctorID, apptTime, status);
                dao.insert(a);
            }

            resp.sendRedirect("AppointmentsServlet");

        } catch (Exception e) {
            throw new ServletException("Error saving appointment", e);
        }
    }
}
 */
package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Appointments;
import medify.DAO.AppointmentsDAO;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idSearch = req.getParameter("appointmentID");

            // SEARCH BY Appointment ID
            if (idSearch != null && !idSearch.isEmpty()) {
                int id = Integer.parseInt(idSearch);
                Appointments found = dao.searchById(id);

                req.setAttribute("appointment", found);

                if (found != null) {
                    req.setAttribute("appointments", List.of(found));
                } else {
                    req.setAttribute("appointments", List.of());
                    req.setAttribute("idNotFound", true);
                }

                req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
                return;
            }

            // LOAD ALL
            List<Appointments> list = dao.loadAll();
            req.setAttribute("appointments", list);

            req.getRequestDispatcher("Appointments.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving appointments", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String mode = req.getParameter("mode");

            // UPDATE MODE ----------------------------------------------------
            if ("update".equals(mode)) {

                int appointmentID = Integer.parseInt(req.getParameter("appointmentID"));

                // Convert datetime-local to SQL Timestamp
                String raw = req.getParameter("apptTime");
                raw = raw.replace("T", " ") + ":00";
                Timestamp apptTime = Timestamp.valueOf(raw);

                String status = req.getParameter("status");

                // Update ONLY apptTime and status
                Appointments updated = new Appointments(
                        appointmentID,
                        0,   // PatientID NOT used
                        0,   // DoctorID NOT used
                        apptTime,
                        status
                );

                dao.update(updated);
            }

            // INSERT MODE ---------------------------------------------------
            else {

                int patientID = Integer.parseInt(req.getParameter("patientID"));
                int doctorID = Integer.parseInt(req.getParameter("doctorID"));

                String raw = req.getParameter("apptTime");
                raw = raw.replace("T", " ") + ":00";
                Timestamp apptTime = Timestamp.valueOf(raw);
                String status = req.getParameter("status");

                Appointments a = new Appointments(0, patientID, doctorID, apptTime, status);
                dao.insert(a);
            }

            resp.sendRedirect("AppointmentsServlet");

        } catch (Exception e) {
            throw new ServletException("Error saving appointment", e);
        }
    }
}

