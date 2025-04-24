package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;

import es.loyola.aquabuddy.manager.ManagerConsumos;
import es.loyola.aquabuddy.model.Consumo;

@WebServlet("/addConsumo")
public class AddConsumoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            int usuarioId = Integer.parseInt(req.getParameter("usuarioId"));
            int cantidad = Integer.parseInt(req.getParameter("cantidadMl"));

            Consumo c = new Consumo();
            c.setUsuarioId(usuarioId);
            c.setFecha(LocalDateTime.now());
            c.setCantidadMl(cantidad);

            boolean ok = ManagerConsumos.add(c);
            JSONObject result = new JSONObject();
            if (ok) {
                result.put("status", "ok");
                result.put("id", c.getId());
                result.put("fecha", c.getFecha().toString());
            } else {
                result.put("status", "error");
                result.put("message", "No se pudo guardar el consumo.");
            }
            out.write(result.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error guardando consumo: " + e.getMessage());
        }
    }
}
