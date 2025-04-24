package es.loyola.aquabuddy.dao;

import java.util.List;
import es.loyola.aquabuddy.model.Recordatorio;

public interface RecordatorioDao {
    List<Recordatorio> findAll() throws Exception;
    List<Recordatorio> findByUsuario(int usuarioId) throws Exception;
    boolean create(Recordatorio r) throws Exception;
    boolean updateActivo(int id, boolean activo) throws Exception;
    boolean delete(int id) throws Exception;
}
