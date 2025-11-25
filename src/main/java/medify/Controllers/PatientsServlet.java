package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import medify.Classes.Patients;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import medify.DAO.PatientsDAO;
import medify.DBConnection.DatabaseConnection;

@WebServlet("/PatientsServlet")
public class PatientsServlet extends HttpServlet {

    private PatientsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new PatientsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Patients> patients = dao.loadAll(); // updated method
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("Patients.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving patients", e);
        }
    }

}
