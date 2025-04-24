package es.loyola.aquabuddy.dao;

import java.util.List;
import es.loyola.aquabuddy.model.Consumo;

public interface ConsumoDao {
    List<Consumo> findAll() throws Exception;
    List<Consumo> findByUsuario(int usuarioId) throws Exception;
    boolean create(Consumo consumo) throws Exception;
    boolean delete(int id) throws Exception;
}
