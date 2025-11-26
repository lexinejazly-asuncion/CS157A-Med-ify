package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.DAO.AppointmentsDAO;
import medify.Classes.Appointments;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
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
            List<Appointments> list = dao.loadAll();
            req.setAttribute("appointments", list);
            req.getRequestDispatcher("Appointments.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error fetching appointments", e);
        }
    }
}
