package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerUsers;
import es.loyola.aquabuddy.model.User;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            int id          = Integer.parseInt(req.getParameter("id"));
            String password = req.getParameter("password");
            Double peso     = parseNullableDouble(req.getParameter("peso"));
            Double altura   = parseNullableDouble(req.getParameter("altura"));
            String nivel    = req.getParameter("nivel_actividad");
            Integer objMl   = parseNullableInt(req.getParameter("objetivo_ml"));

            User u = new User();
            u.setId(id);
            if (password!=null && !password.trim().isEmpty()) u.setPwdHash(password);
            if (peso!=null)    u.setPeso(peso);
            if (altura!=null)  u.setAltura(altura);
            if (nivel!=null && !nivel.isEmpty()) u.setNivelActividad(nivel);
            if (objMl!=null)   u.setObjetivoMl(objMl);

            boolean ok = ManagerUsers.update(u);

            JSONObject r = new JSONObject();
            r.put("status", ok ? "ok":"error");
            if (!ok) r.put("message", "No se pudo actualizar usuario");
            out.write(r.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error actualizando usuario: " + e.getMessage());
        }
    }

    private Double parseNullableDouble(String s) {
        try { return (s!=null && !s.isEmpty())? Double.valueOf(s): null; }
        catch (NumberFormatException e) { return null; }
    }
    private Integer parseNullableInt(String s) {
        try { return (s!=null && !s.isEmpty())? Integer.valueOf(s): null; }
        catch (NumberFormatException e) { return null; }
    }
}
