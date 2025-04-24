package es.loyola.aquabuddy.model;

import java.time.LocalDateTime;

public class Consumo {
    private int id;
    private int usuarioId;
    private LocalDateTime fecha;
    private int cantidadMl;

    public Consumo() {}

    public Consumo(int id, int usuarioId, LocalDateTime fecha, int cantidadMl) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.cantidadMl = cantidadMl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public int getCantidadMl() { return cantidadMl; }
    public void setCantidadMl(int cantidadMl) { this.cantidadMl = cantidadMl; }
}
