package es.loyola.aquabuddy.manager;

import java.util.List;
import es.loyola.aquabuddy.dao.RecordatorioDao;
import es.loyola.aquabuddy.dao.RecordatorioDaoJdbc;
import es.loyola.aquabuddy.model.Recordatorio;

public class ManagerRecordatorios {
    private static final RecordatorioDao dao = new RecordatorioDaoJdbc();

    public static List<Recordatorio> getAll() throws Exception { return dao.findAll(); }
    public static List<Recordatorio> getByUsuario(int uid) throws Exception { return dao.findByUsuario(uid); }
    public static boolean add(Recordatorio r) throws Exception { return dao.create(r); }
    public static boolean setActivo(int id, boolean activo) throws Exception { return dao.updateActivo(id, activo); }
    public static boolean remove(int id) throws Exception { return dao.delete(id); }
}
