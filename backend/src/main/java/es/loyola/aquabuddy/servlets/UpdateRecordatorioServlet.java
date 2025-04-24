package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;

import es.loyola.aquabuddy.manager.ManagerRecordatorios;

@WebServlet("/updateRecordatorio")
public class UpdateRecordatorioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            int id     = Integer.parseInt(req.getParameter("id"));
            boolean activo = Boolean.parseBoolean(req.getParameter("activo"));
            boolean ok = ManagerRecordatorios.setActivo(id, activo);
            JSONObject r = new JSONObject();
            r.put("status", ok ? "ok" : "error");
            out.write(r.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error actualizando recordatorio: " + e.getMessage());
        }
    }
}
