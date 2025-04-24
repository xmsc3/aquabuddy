package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerRecordatorios;
import es.loyola.aquabuddy.model.Recordatorio;

@WebServlet("/listRecordatorios")
public class ListRecordatoriosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        JSONArray arr = new JSONArray();
        try {
            String p = req.getParameter("usuarioId");
            List<Recordatorio> list = (p!=null && !p.trim().isEmpty())
                ? ManagerRecordatorios.getByUsuario(Integer.parseInt(p.trim()))
                : ManagerRecordatorios.getAll();
            for (Recordatorio r : list) {
                JSONObject o = new JSONObject();
                o.put("id", r.getId());
                o.put("usuarioId", r.getUsuarioId());
                o.put("intervaloMin", r.getIntervaloMin());
                o.put("activo", r.isActivo());
                arr.put(o);
            }
            try (PrintWriter out = resp.getWriter()) { out.write(arr.toString()); }
        } catch (Exception e) {
            resp.sendError(500, "Error listando recordatorios: " + e.getMessage());
        }
    }
}
