package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import es.loyola.aquabuddy.manager.ManagerDesafios;
import es.loyola.aquabuddy.model.Desafio;

@WebServlet("/addDesafio")
public class AddDesafioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            int usuarioId = Integer.parseInt(req.getParameter("usuarioId"));
            String tipo = req.getParameter("tipo");

            LocalDate inicio = LocalDate.now();
            LocalDate fin    = inicio.plusDays(7);

            Desafio d = new Desafio();
            d.setUsuarioId(usuarioId);
            d.setTipo(tipo);
            d.setFechaInicio(inicio);
            d.setFechaFin(fin);
            d.setCompletado(false);

            boolean ok = ManagerDesafios.add(d);

            JSONObject res = new JSONObject();
            if (ok) {
                res.put("status", "ok");
                res.put("id", d.getId());
                res.put("fechaInicio", inicio.toString());
                res.put("fechaFin", fin.toString());
            } else {
                res.put("status", "error");
                res.put("message", "No se pudo crear el desafío.");
            }
            out.write(res.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error guardando desafío: " + e.getMessage());
        }
    }
}
