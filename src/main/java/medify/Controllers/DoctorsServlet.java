package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medify.Classes.Doctors;
import medify.Classes.Patients;
import medify.DAO.DoctorsDAO;
import medify.DBConnection.DatabaseConnection;

@WebServlet("/DoctorsServlet")
public class DoctorsServlet extends HttpServlet {

    private DoctorsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new DoctorsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idParam = req.getParameter("doctorID");
            String nameParam = req.getParameter("searchName");

            List<Doctors> doctors = dao.loadAll();

            // Search by ID
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Doctors found = dao.searchById(id);

                req.setAttribute("doctor", found);

                if (found != null) {
                    doctors = List.of(found);
                } else {
                    doctors = new ArrayList<>();
                    req.setAttribute("idNotFound", true);
                }
            }

            // Search by name
            if (nameParam != null && !nameParam.trim().isEmpty()) {

                List<Doctors> results = dao.searchByName(nameParam.trim());

                req.setAttribute("doctors", results);
                req.setAttribute("nameSearch", true);

                if (results.isEmpty())
                    req.setAttribute("nameNotFound", true);

                // Prevent overriding name search
                req.setAttribute("doctor", null);
                req.getRequestDispatcher("Doctors.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("doctors", doctors);
            req.getRequestDispatcher("Doctors.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving doctors", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String mode = req.getParameter("mode");

            // DELETE FIRST (just like PatientsServlet)
            if ("delete".equals(mode)) {
                int doctorID = Integer.parseInt(req.getParameter("doctorID"));
                dao.delete(doctorID);
                resp.sendRedirect("DoctorsServlet");
                return;
            }

            String name = req.getParameter("doctorName");
            String phone = req.getParameter("phoneNumber");
            String dept = req.getParameter("department");

            if ("update".equals(mode)) {
                int doctorID = Integer.parseInt(req.getParameter("doctorID"));
                Doctors found = dao.searchById(doctorID);
                req.setAttribute("updateDoctorID", doctorID);

                //Checks if patient exists
                if (found != null){
                    Doctors doctor = new Doctors(doctorID, name, phone, dept);
                    dao.update(doctor);
                    req.setAttribute("updateIDNotFound", false);
                }
                else {
                    req.setAttribute("updateIDNotFound", true);

                    //Keep the table loaded, even when update fails because patientID does not exist
                    List<Doctors> doctors = dao.loadAll();
                    req.setAttribute("doctors", doctors);

                    req.getRequestDispatcher("Doctors.jsp").forward(req, resp);
                    return;
                }
            } else {
                Doctors doctor = new Doctors(0, name, phone, dept);
                dao.insert(doctor);
            }

            resp.sendRedirect("DoctorsServlet");

        } catch (Exception e) {
            throw new ServletException("Error saving doctor", e);
        }
    }
}
