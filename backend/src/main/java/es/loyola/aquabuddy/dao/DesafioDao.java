package es.loyola.aquabuddy.dao;

import java.util.List;
import es.loyola.aquabuddy.model.Desafio;

public interface DesafioDao {
    List<Desafio> findAll() throws Exception;
    List<Desafio> findByUsuario(int usuarioId) throws Exception;
    boolean create(Desafio d) throws Exception;
    boolean updateCompletado(int id, boolean completado) throws Exception;
    boolean delete(int id) throws Exception;
}
