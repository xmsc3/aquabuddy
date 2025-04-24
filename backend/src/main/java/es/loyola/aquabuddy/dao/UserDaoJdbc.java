package es.loyola.aquabuddy.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import es.loyola.aquabuddy.model.User;
import es.loyola.aquabuddy.util.ConnectionManager;

public class UserDaoJdbc implements UserDao {

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, nombre, apellidos, email, pwd_hash, peso, altura, nivel_actividad, objetivo_ml FROM usuarios";
        try (Connection c = ConnectionManager.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            List<User> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("pwd_hash"),
                    rs.getDouble("peso"),
                    rs.getDouble("altura"),
                    rs.getString("nivel_actividad"),
                    rs.getInt("objetivo_ml")
                ));
            }
            return list;
        }
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id, nombre, apellidos, email, pwd_hash, peso, altura, nivel_actividad, objetivo_ml "
                   + "FROM usuarios WHERE email = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("pwd_hash"),
                        rs.getDouble("peso"),
                        rs.getDouble("altura"),
                        rs.getString("nivel_actividad"),
                        rs.getInt("objetivo_ml")
                    );
                }
                return null;
            }
        }
    }

    @Override
    public User findById(int id) throws SQLException {
        String sql = "SELECT id, nombre, apellidos, email, pwd_hash, peso, altura, nivel_actividad, objetivo_ml "
                   + "FROM usuarios WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("pwd_hash"),
                        rs.getDouble("peso"),
                        rs.getDouble("altura"),
                        rs.getString("nivel_actividad"),
                        rs.getInt("objetivo_ml")
                    );
                }
                return null;
            }
        }
    }

    @Override
    public boolean create(User u) throws SQLException {
        String sql = "INSERT INTO usuarios "
                   + "(nombre, apellidos, email, pwd_hash, peso, altura, nivel_actividad, objetivo_ml) "
                   + "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPwdHash());
            ps.setDouble(5, u.getPeso());
            ps.setDouble(6, u.getAltura());
            ps.setString(7, u.getNivelActividad());
            ps.setInt(8, u.getObjetivoMl());

            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setId(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean update(User u) throws SQLException {
        boolean hasPwd = u.getPwdHash() != null && !u.getPwdHash().isEmpty();
        String sql = "UPDATE usuarios SET "
                   + "nombre=?, apellidos=?, email=?, peso=?, altura=?, nivel_actividad=?, objetivo_ml=?"
                   + (hasPwd ? ", pwd_hash=?" : "")
                   + " WHERE id=?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int i = 1;
            ps.setString(i++, u.getNombre());
            ps.setString(i++, u.getApellidos());
            ps.setString(i++, u.getEmail());
            ps.setDouble(i++, u.getPeso());
            ps.setDouble(i++, u.getAltura());
            ps.setString(i++, u.getNivelActividad());
            ps.setInt(i++, u.getObjetivoMl());

            if (hasPwd) {
                ps.setString(i++, u.getPwdHash());
            }
            ps.setInt(i, u.getId());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }
}
