package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerConsumos;

@WebServlet("/progressPercent")
public class ProgressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            String idParam = req.getParameter("usuarioId");
            if (idParam == null || idParam.isEmpty()) {
                resp.sendError(400, "Falta parámetro usuarioId");
                return;
            }
            int uid = Integer.parseInt(idParam);
            int totalHoy    = ManagerConsumos.getTotalHoy(uid);
            int objetivo    = ManagerConsumos.getObjetivoDiario(uid);
            double porcentaje = (objetivo > 0)
                ? Math.min(100.0, totalHoy * 100.0 / objetivo)
                : 0.0;

            JSONObject r = new JSONObject();
            r.put("usuarioId", uid);
            r.put("totalHoy", totalHoy);
            r.put("objetivo", objetivo);
            r.put("porcentaje", String.format("%.2f", porcentaje));
            out.write(r.toString());
        } catch (NumberFormatException e) {
            resp.sendError(400, "usuarioId no es un número válido");
        } catch (Exception e) {
            resp.sendError(500, "Error calculando progreso: " + e.getMessage());
        }
    }
}
