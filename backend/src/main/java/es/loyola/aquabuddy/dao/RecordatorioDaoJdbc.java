package es.loyola.aquabuddy.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import es.loyola.aquabuddy.model.Recordatorio;
import es.loyola.aquabuddy.util.ConnectionManager;

public class RecordatorioDaoJdbc implements RecordatorioDao {

    @Override
    public List<Recordatorio> findAll() throws SQLException {
        String sql = "SELECT id, usuario_id, intervalo_min, activo FROM recordatorios";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Recordatorio> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    @Override
    public List<Recordatorio> findByUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT id, usuario_id, intervalo_min, activo FROM recordatorios WHERE usuario_id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Recordatorio> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    @Override
    public boolean create(Recordatorio r) throws SQLException {
        String sql = "INSERT INTO recordatorios(usuario_id, intervalo_min, activo) VALUES (?,?,?)";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getUsuarioId());
            ps.setInt(2, r.getIntervaloMin());
            ps.setBoolean(3, r.isActivo());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) r.setId(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean updateActivo(int id, boolean activo) throws SQLException {
        String sql = "UPDATE recordatorios SET activo = ? WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM recordatorios WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Recordatorio mapRow(ResultSet rs) throws SQLException {
        return new Recordatorio(
            rs.getInt("id"),
            rs.getInt("usuario_id"),
            rs.getInt("intervalo_min"),
            rs.getBoolean("activo")
        );
    }
}
