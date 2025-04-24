package es.loyola.aquabuddy.dao;

import java.sql.*;
// import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.loyola.aquabuddy.model.Consumo;
import es.loyola.aquabuddy.util.ConnectionManager;

public class ConsumoDaoJdbc implements ConsumoDao {

    @Override
    public List<Consumo> findAll() throws SQLException {
        String sql = "SELECT id, usuario_id, fecha, cantidad_ml FROM consumos";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Consumo> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            return lista;
        }
    }

    @Override
    public List<Consumo> findByUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT id, usuario_id, fecha, cantidad_ml FROM consumos WHERE usuario_id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Consumo> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
                return lista;
            }
        }
    }

    @Override
    public boolean create(Consumo consumo) throws SQLException {
        String sql = "INSERT INTO consumos(usuario_id, fecha, cantidad_ml) VALUES (?,?,?)";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, consumo.getUsuarioId());
            ps.setTimestamp(2, Timestamp.valueOf(consumo.getFecha()));
            ps.setInt(3, consumo.getCantidadMl());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) consumo.setId(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM consumos WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Consumo mapRow(ResultSet rs) throws SQLException {
        return new Consumo(
            rs.getInt("id"),
            rs.getInt("usuario_id"),
            rs.getTimestamp("fecha").toLocalDateTime(),
            rs.getInt("cantidad_ml")
        );
    }
}
