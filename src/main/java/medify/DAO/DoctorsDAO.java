package medify.DAO;

import medify.Classes.Doctors;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorsDAO {
    private Connection conn;

    public DoctorsDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Doctors> loadAll() throws SQLException {
        List<Doctors> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT DoctorID, DoctorName, PhoneNumber, Department FROM Doctors"
        );

        while (rs.next()) {
            Doctors d = new Doctors(
                    rs.getInt("DoctorID"),
                    rs.getString("DoctorName"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Department")
            );
            list.add(d);
        }
        rs.close();
        stmt.close();
        return list;
    }
}
