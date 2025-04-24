package es.loyola.aquabuddy.manager;

import java.util.List;
import es.loyola.aquabuddy.dao.UserDao;
import es.loyola.aquabuddy.dao.UserDaoJdbc;
import es.loyola.aquabuddy.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class ManagerUsers {
    private static final UserDao dao = new UserDaoJdbc();

    public static List<User> getAll() throws Exception {
        return dao.findAll();
    }

    public static User getById(int id) throws Exception {
        return dao.findById(id);
    }

    public static boolean add(User u) throws Exception {
        String hash = BCrypt.hashpw(u.getPwdHash(), BCrypt.gensalt());
        u.setPwdHash(hash);
        return dao.create(u);
    }

    public static boolean update(User u) throws Exception {
        if (u.getPwdHash() != null && !u.getPwdHash().isEmpty()) {
            String hash = BCrypt.hashpw(u.getPwdHash(), BCrypt.gensalt());
            u.setPwdHash(hash);
        }
        return dao.update(u);
    }

    public static boolean remove(int id) throws Exception {
        return dao.delete(id);
    }

    public static User authenticate(String email, String plainPassword) throws Exception {
        User u = dao.findByEmail(email);
        if (u != null && BCrypt.checkpw(plainPassword, u.getPwdHash())) {
            u.setPwdHash(null);
            return u;
        }
        return null;
    }
}
