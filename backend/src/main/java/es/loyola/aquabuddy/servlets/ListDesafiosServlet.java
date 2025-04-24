package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import es.loyola.aquabuddy.manager.ManagerDesafios;
import es.loyola.aquabuddy.model.Desafio;

@WebServlet("/listDesafios")
public class ListDesafiosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        JSONArray arr = new JSONArray();
        try {
            String p = req.getParameter("usuarioId");
            List<Desafio> lista;
            if (p != null && !p.trim().isEmpty()) {
                int uid = Integer.parseInt(p.trim());
                lista = ManagerDesafios.getByUsuario(uid);
            } else {
                lista = ManagerDesafios.getAll();
            }
            for (Desafio d : lista) {
                JSONObject o = new JSONObject();
                o.put("id", d.getId());
                o.put("usuarioId", d.getUsuarioId());
                o.put("tipo", d.getTipo());
                o.put("fechaInicio", d.getFechaInicio().toString());
                o.put("fechaFin", d.getFechaFin().toString());
                o.put("completado", d.isCompletado());
                arr.put(o);
            }
            try (PrintWriter out = resp.getWriter()) {
                out.write(arr.toString());
            }
        } catch (Exception e) {
            resp.sendError(500, "Error listando desafíos: " + e.getMessage());
        }
    }
}
