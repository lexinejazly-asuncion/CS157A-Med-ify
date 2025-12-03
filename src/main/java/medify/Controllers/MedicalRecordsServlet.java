/*
package medify.Controllers;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.MedicalRecords;
import medify.DAO.MedicalRecordsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
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

    // --------------------  CORS HEADERS FOR REACT  --------------------
    private void enableCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    // --------------------  GET (JSON for React, JSP for Tomcat)  --------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        enableCORS(response);

        String type = request.getParameter("type");

        // If React requests JSON → return JSON
        if ("json".equals(type)) {
            sendJson(response);
            return;
        }

        // Default JSP behavior
        try {
            List<MedicalRecords> list = dao.loadAll();
            request.setAttribute("medicalrecords", list);
            request.getRequestDispatcher("MedicalRecords.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading medical records", e);
        }
    }

    // --------------------  POST (Insert or Update)  --------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        enableCORS(response);

        try {
            int patientID = Integer.parseInt(request.getParameter("patientID"));
            int doctorID = Integer.parseInt(request.getParameter("doctorID"));
            int prescriptionID = Integer.parseInt(request.getParameter("prescriptionID"));
            Date visitDate = Date.valueOf(request.getParameter("visitDate"));

            MedicalRecords record = new MedicalRecords(
                    patientID,
                    doctorID,
                    prescriptionID,
                    visitDate
            );

            String mode = request.getParameter("mode");

            if ("update".equals(mode)) {
                dao.update(record);
            } else {
                dao.insert(record);
            }

            response.sendRedirect("MedicalRecordsServlet");

        } catch (Exception e) {
            throw new ServletException("Error saving medical record", e);
        }
    }

    // --------------------  JSON Response for React  --------------------
    private void sendJson(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");

        try {
            List<MedicalRecords> list = dao.loadAll();

            for (int i = 0; i < list.size(); i++) {
                MedicalRecords m = list.get(i);

                json.append("{")
                        .append("\"patientID\":").append(m.getPatientID()).append(",")
                        .append("\"doctorID\":").append(m.getDoctorID()).append(",")
                        .append("\"prescriptionID\":").append(m.getPrescriptionID()).append(",")
                        .append("\"visitDate\":\"").append(m.getVisitDate()).append("\"")
                        .append("}");

                if (i < list.size() - 1) json.append(",");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        json.append("]");
        response.getWriter().write(json.toString());
    }
}

 */

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

        // CORS
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String type = req.getParameter("type");

        // JSON for React
        if ("json".equals(type)) {
            sendJson(resp);
            return;
        }

        // JSP fallback
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

        // CORS
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

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

            String mode = req.getParameter("mode");

            if ("update".equals(mode)) {
                dao.update(record);
            } else {
                dao.insert(record);
            }

            resp.setStatus(200);

        } catch (Exception e) {
            resp.setStatus(500);
            throw new ServletException("Error saving medical record", e);
        }
    }

    private void sendJson(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<MedicalRecords> list = dao.loadAll();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                MedicalRecords m = list.get(i);
                json.append("{")
                        .append("\"patientID\":").append(m.getPatientID()).append(",")
                        .append("\"doctorID\":").append(m.getDoctorID()).append(",")
                        .append("\"prescriptionID\":").append(m.getPrescriptionID()).append(",")
                        .append("\"visitDate\":\"").append(m.getVisitDate()).append("\"")
                        .append("}");
                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");
            resp.getWriter().write(json.toString());
        } catch (SQLException e) {
            resp.getWriter().write("[]");
        }
    }
}

