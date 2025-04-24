package es.loyola.aquabuddy.model;

public class Recordatorio {
    private int id;
    private int usuarioId;
    private int intervaloMin;
    private boolean activo;

    public Recordatorio() {}

    public Recordatorio(int id, int usuarioId, int intervaloMin, boolean activo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.intervaloMin = intervaloMin;
        this.activo = activo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getIntervaloMin() { return intervaloMin; }
    public void setIntervaloMin(int intervaloMin) { this.intervaloMin = intervaloMin; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
