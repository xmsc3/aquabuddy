package es.loyola.aquabuddy.manager;

import java.util.List;
import es.loyola.aquabuddy.dao.DesafioDao;
import es.loyola.aquabuddy.dao.DesafioDaoJdbc;
import es.loyola.aquabuddy.model.Desafio;

public class ManagerDesafios {
    private static final DesafioDao dao = new DesafioDaoJdbc();

    public static List<Desafio> getAll() throws Exception {
        return dao.findAll();
    }
    public static List<Desafio> getByUsuario(int uid) throws Exception {
        return dao.findByUsuario(uid);
    }
    public static boolean add(Desafio d) throws Exception {
        return dao.create(d);
    }
    public static boolean setCompletado(int id, boolean comp) throws Exception {
        return dao.updateCompletado(id, comp);
    }
    public static boolean remove(int id) throws Exception { return dao.delete(id); }
}
