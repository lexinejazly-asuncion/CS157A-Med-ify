package medify.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import medify.Classes.Prescriptions;
import medify.DAO.PrescriptionsDAO;
import medify.DBConnection.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/PrescriptionsServlet")
public class PrescriptionsServlet extends HttpServlet {

    private PrescriptionsDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new PrescriptionsDAO(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("DB connection error", e);
        }
    }

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
}
