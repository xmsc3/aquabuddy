package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import es.loyola.aquabuddy.manager.ManagerConsumos;
import es.loyola.aquabuddy.model.Consumo;

@WebServlet("/listConsumos")
public class ListConsumosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        JSONArray arr = new JSONArray();
        try {
            String param = req.getParameter("usuarioId");
            List<Consumo> lista;
            if (param != null && !param.trim().isEmpty()) {
                int uid = Integer.parseInt(param.trim());
                lista = ManagerConsumos.getByUsuario(uid);
            } else {
                lista = ManagerConsumos.getAll();
            }
            for (Consumo c : lista) {
                JSONObject obj = new JSONObject();
                obj.put("id", c.getId());
                obj.put("usuarioId", c.getUsuarioId());
                obj.put("fecha", c.getFecha().toString());
                obj.put("cantidadMl", c.getCantidadMl());
                arr.put(obj);
            }
            try (PrintWriter out = resp.getWriter()) {
                out.write(arr.toString());
            }
        } catch (Exception e) {
            resp.sendError(500, "Error listando consumos: " + e.getMessage());
        }
    }
    
    
}
