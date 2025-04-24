package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import es.loyola.aquabuddy.util.ConnectionManager;

@WebServlet("/testdb")
public class TestDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        try (Connection conn = ConnectionManager.getConnection();
             PrintWriter out = resp.getWriter()) {

            out.println("Conexión OK. Tablas y Estructura:");

            ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                out.println("\nTabla: " + tableName);
                
                describeTable(conn, tableName, out);
            }

        } catch (Exception e) {
            throw new ServletException("Fallo BD: " + e.getMessage(), e);
        }
    }

    private void describeTable(Connection conn, String tableName, PrintWriter out) throws Exception {
        String describeSQL = "DESCRIBE " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rsDescribe = stmt.executeQuery(describeSQL)) {

            while (rsDescribe.next()) {
                out.println("  - " + rsDescribe.getString("Field") 
                          + " (" + rsDescribe.getString("Type") + ")");
            }
        }
    }
}
