package es.loyola.aquabuddy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import es.loyola.aquabuddy.manager.ManagerUsers;
import es.loyola.aquabuddy.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            String email = req.getParameter("email");
            String pass  = req.getParameter("password");
            User u = ManagerUsers.authenticate(email, pass);
            JSONObject r = new JSONObject();
            if (u != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("userId", u.getId());
                r.put("status","ok");
                r.put("id", u.getId());
                r.put("nombre", u.getNombre());
            } else {
                r.put("status","error");
                r.put("message","Credenciales inválidas");
            }
            out.write(r.toString());
        } catch (Exception e) {
            resp.sendError(500, "Error en login: " + e.getMessage());
        }
    }
}
