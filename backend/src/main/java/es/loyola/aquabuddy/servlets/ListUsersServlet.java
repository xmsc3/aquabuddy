package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerUsers;
import es.loyola.aquabuddy.model.User;

@WebServlet("/listUsers")
public class ListUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            String idParam = req.getParameter("id");
            JSONArray arr = new JSONArray();

            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                User u = ManagerUsers.getById(id);
                if (u != null) arr.put(toJson(u));
            } else {
                List<User> users = ManagerUsers.getAll();
                for (User u : users) arr.put(toJson(u));
            }
            out.write(arr.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error listando usuarios: " + e.getMessage());
        }
    }

    private JSONObject toJson(User u) {
        JSONObject o = new JSONObject();
        o.put("id", u.getId());
        o.put("nombre", u.getNombre());
        o.put("apellidos", u.getApellidos());
        o.put("email", u.getEmail());
        o.put("peso", u.getPeso());
        o.put("altura", u.getAltura());
        o.put("nivelActividad", u.getNivelActividad());
        o.put("objetivoMl", u.getObjetivoMl());
        return o;
    }
}
