package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerUsers;
import es.loyola.aquabuddy.model.User;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            String nombre    = req.getParameter("nombre");
            String apellidos = req.getParameter("apellidos");
            String email     = req.getParameter("email");
            String password  = req.getParameter("password");
            double peso      = parseDouble(req.getParameter("peso"));
            double altura    = parseDouble(req.getParameter("altura"));
            String nivel     = req.getParameter("nivel_actividad");
            int objetivo     = parseInt(req.getParameter("objetivo_ml"));

            User u = new User();
            u.setNombre(nombre);
            u.setApellidos(apellidos);
            u.setEmail(email);
            u.setPwdHash(password);
            u.setPeso(peso);
            u.setAltura(altura);
            u.setNivelActividad(nivel);
            u.setObjetivoMl(objetivo);

            boolean ok = ManagerUsers.add(u);

            JSONObject r = new JSONObject();
            if (ok) {
                r.put("status", "ok");
                r.put("id", u.getId());
            } else {
                r.put("status", "error");
                r.put("message", "No se pudo crear usuario");
            }
            out.write(r.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error agregando usuario: " + e.getMessage());
        }
    }

    private double parseDouble(String s) {
        try { return (s!=null && !s.isEmpty())? Double.parseDouble(s): 0; }
        catch (NumberFormatException e) { return 0; }
    }
    private int parseInt(String s) {
        try { return (s!=null && !s.isEmpty())? Integer.parseInt(s): 0; }
        catch (NumberFormatException e) { return 0; }
    }
}
