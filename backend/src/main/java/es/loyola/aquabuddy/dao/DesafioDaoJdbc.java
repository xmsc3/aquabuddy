package es.loyola.aquabuddy.dao;

import java.sql.*;
// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import es.loyola.aquabuddy.model.Desafio;
import es.loyola.aquabuddy.util.ConnectionManager;

public class DesafioDaoJdbc implements DesafioDao {

    @Override
    public List<Desafio> findAll() throws SQLException {
        String sql = "SELECT id, usuario_id, tipo, fecha_inicio, fecha_fin, completado FROM desafios";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Desafio> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            return lista;
        }
    }

    @Override
    public List<Desafio> findByUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT id, usuario_id, tipo, fecha_inicio, fecha_fin, completado FROM desafios WHERE usuario_id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Desafio> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
                return lista;
            }
        }
    }

    @Override
    public boolean create(Desafio d) throws SQLException {
        String sql = "INSERT INTO desafios (usuario_id, tipo, fecha_inicio, fecha_fin, completado) VALUES (?,?,?,?,?)";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getUsuarioId());
            ps.setString(2, d.getTipo());
            ps.setDate(3, Date.valueOf(d.getFechaInicio()));
            ps.setDate(4, Date.valueOf(d.getFechaFin()));
            ps.setBoolean(5, d.isCompletado());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) d.setId(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean updateCompletado(int id, boolean completado) throws SQLException {
        String sql = "UPDATE desafios SET completado = ? WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, completado);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM desafios WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Desafio mapRow(ResultSet rs) throws SQLException {
        return new Desafio(
            rs.getInt("id"),
            rs.getInt("usuario_id"),
            rs.getString("tipo"),
            rs.getDate("fecha_inicio").toLocalDate(),
            rs.getDate("fecha_fin").toLocalDate(),
            rs.getBoolean("completado")
        );
    }
}
