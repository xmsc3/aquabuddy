package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerRecordatorios;
import es.loyola.aquabuddy.model.Recordatorio;

@WebServlet("/addRecordatorio")
public class AddRecordatorioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            int uid = Integer.parseInt(req.getParameter("usuarioId"));
            int intervalo = Integer.parseInt(req.getParameter("intervaloMin"));
            Recordatorio r = new Recordatorio();
            r.setUsuarioId(uid);
            r.setIntervaloMin(intervalo);
            r.setActivo(true);
            boolean ok = ManagerRecordatorios.add(r);
            JSONObject res = new JSONObject();
            if (ok) {
                res.put("status","ok");
                res.put("id", r.getId());
            } else {
                res.put("status","error");
            }
            out.write(res.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error guardando recordatorio: " + e.getMessage());
        }
    }
}
