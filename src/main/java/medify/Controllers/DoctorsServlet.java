package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Doctors;
import medify.DAO.DoctorsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Doctors> doctors = dao.loadAll();
            request.setAttribute("doctors", doctors);
            request.getRequestDispatcher("Doctors.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving doctors", e);
        }
    }
}
