package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;

import es.loyola.aquabuddy.manager.ManagerDesafios;

@WebServlet("/updateDesafio")
public class UpdateDesafioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            int id         = Integer.parseInt(req.getParameter("id"));
            boolean comp   = Boolean.parseBoolean(req.getParameter("completado"));
            boolean ok     = ManagerDesafios.setCompletado(id, comp);
            JSONObject r   = new JSONObject();
            r.put("status", ok ? "ok" : "error");
            out.write(r.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error actualizando desafío: " + e.getMessage());
        }
    }
}
