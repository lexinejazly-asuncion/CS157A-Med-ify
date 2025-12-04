package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Appointments;
import medify.Classes.Doctors;
import medify.Classes.Patients;
import medify.Classes.Prescriptions;
import medify.DAO.AppointmentsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

//import for checking if a patient exists
import medify.DAO.PatientsDAO;
//import for checking if a patient exists
import medify.DAO.DoctorsDAO;

@WebServlet("/AppointmentsServlet")
public class AppointmentsServlet extends HttpServlet {

    private AppointmentsDAO dao;
    private PatientsDAO patientsDAO;
    private DoctorsDAO doctorsDAO;

    @Override
    public void init() throws ServletException {
        try {
            dao = new AppointmentsDAO(DatabaseConnection.getConnection());
            patientsDAO = new PatientsDAO(DatabaseConnection.getConnection());
            doctorsDAO = new DoctorsDAO(DatabaseConnection.getConnection());
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
                req.setAttribute("appointmentID", idSearch);

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

            // DELETE FIRST
            if ("delete".equals(mode)) {
                int appointmentID = Integer.parseInt(req.getParameter("appointmentID"));
                dao.delete(appointmentID);
                resp.sendRedirect("AppointmentsServlet");
                return;
            }

            // UPDATE MODE ----------------------------------------------------
            if ("update".equals(mode)) {

                int appointmentID = Integer.parseInt(req.getParameter("appointmentID"));

                // Convert datetime-local to SQL Timestamp
                String raw = req.getParameter("apptTime");
                raw = raw.replace("T", " ") + ":00";
                Timestamp apptTime = Timestamp.valueOf(raw);

                String status = req.getParameter("status");

                Appointments found = dao.searchById(appointmentID);
                req.setAttribute("updateAppointmentID", appointmentID);

                //Checks if patient exists
                if (found != null){
                    // Update ONLY apptTime and status
                    Appointments updated = new Appointments(
                            appointmentID,
                            0,   // PatientID NOT used
                            0,   // DoctorID NOT used
                            apptTime,
                            status
                    );

                    dao.update(updated);
                    req.setAttribute("updateIDNotFound", false);
                }
                else {
                    req.setAttribute("updateIDNotFound", true);

                    //Keep the table loaded, even when update fails because patientID does not exist
                    List<Appointments> appointments = dao.loadAll();
                    req.setAttribute("appointments", appointments);

                    req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
                    return;
                }

            }

            // INSERT MODE ---------------------------------------------------
            else {

                int patientID = Integer.parseInt(req.getParameter("patientID"));
                int doctorID = Integer.parseInt(req.getParameter("doctorID"));

                String raw = req.getParameter("apptTime");
                raw = raw.replace("T", " ") + ":00";
                Timestamp apptTime = Timestamp.valueOf(raw);
                String status = req.getParameter("status");

                Patients patientFound = patientsDAO.searchById(patientID);
                Doctors doctorFound = doctorsDAO.searchById(doctorID);
                req.setAttribute("insertRecordPatientID", patientID);
                req.setAttribute("insertRecordDoctorID", doctorID);

                //Checks if patient exists
                //If patient and doctor exist
                if (patientFound != null && doctorFound != null) {
                    Appointments a = new Appointments(0, patientID, doctorID, apptTime, status);
                    dao.insert(a);
                    req.setAttribute("insertRecordPatientIDNotFound", false);
                    req.setAttribute("insertRecordDoctorIDNotFound", false);

                //If patient AND doctor don't exist
                } else if (patientFound == null && doctorFound == null) {
                    req.setAttribute("insertRecordPatientIDNotFound", true);
                    req.setAttribute("insertRecordDoctorIDNotFound", true);
                    //Keep the table loaded, even when update fails because patientID and doctorID does not exist
                    List<Appointments> appointments = dao.loadAll();
                    req.setAttribute("appointments", appointments);
                    req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
                    return;

                //If doctor doesn't exist
                } else if (patientFound != null) {
                    req.setAttribute("insertRecordPatientIDNotFound", false);
                    req.setAttribute("insertRecordDoctorIDNotFound", true);
                    //Keep the table loaded, even when update fails because doctorID does not exist
                    List<Appointments> appointments = dao.loadAll();
                    req.setAttribute("appointments", appointments);
                    req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
                    return;

                //If patient doesn't exist
                } else {
                    req.setAttribute("insertRecordPatientIDNotFound", true);
                    req.setAttribute("insertRecordDoctorIDNotFound", false);
                    //Keep the table loaded, even when update fails because patientID does not exist
                    List<Appointments> appointments = dao.loadAll();
                    req.setAttribute("appointments", appointments);
                    req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
                    return;
                }
            }
            //Redirect back to the Prescriptions Servlet after update/insert is complete
            resp.sendRedirect(req.getContextPath() + "/AppointmentsServlet");

        } catch (Exception e) {
            throw new ServletException("Error saving appointment", e);
        }
    }
}

