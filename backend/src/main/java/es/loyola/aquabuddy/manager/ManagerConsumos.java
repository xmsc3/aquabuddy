package es.loyola.aquabuddy.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import es.loyola.aquabuddy.dao.ConsumoDao;
import es.loyola.aquabuddy.dao.ConsumoDaoJdbc;
import es.loyola.aquabuddy.model.Consumo;
import es.loyola.aquabuddy.util.ConnectionManager;

public class ManagerConsumos {
    private static final ConsumoDao dao = new ConsumoDaoJdbc();

    public static List<Consumo> getAll() throws Exception {
        return dao.findAll();
    }

    public static List<Consumo> getByUsuario(int usuarioId) throws Exception {
        return dao.findByUsuario(usuarioId);
    }

    public static boolean add(Consumo c) throws Exception {
        return dao.create(c);
    }

    public static boolean remove(int id) throws Exception {
        return dao.delete(id);
    }

    public static int getTotalHoy(int usuarioId) throws Exception {
        String sql = 
            "SELECT COALESCE(SUM(cantidad_ml),0) AS total " +
            "FROM consumos " +
            "WHERE usuario_id = ? AND DATE(fecha) = CURRENT_DATE()";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("total") : 0;
            }
        }
    }

    public static int getObjetivoDiario(int usuarioId) throws Exception {
        String sql = "SELECT objetivo_ml FROM usuarios WHERE id = ?";
        try (Connection c = ConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("objetivo_ml") : 0;
            }
        }
    }
}
