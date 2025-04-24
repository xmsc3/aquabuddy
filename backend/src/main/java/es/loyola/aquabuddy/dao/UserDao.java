package es.loyola.aquabuddy.dao;

import java.util.List;
import es.loyola.aquabuddy.model.User;

public interface UserDao {
    List<User> findAll() throws Exception;
    User findByEmail(String email) throws Exception;
    User findById(int id) throws Exception;
    boolean create(User u) throws Exception;
    boolean update(User u) throws Exception;
    boolean delete(int id) throws Exception;
}
