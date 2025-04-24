package es.loyola.aquabuddy.model;

import java.time.LocalDate;

public class Desafio {
    private int id;
    private int usuarioId;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean completado;

    public Desafio() {}

    public Desafio(int id, int usuarioId, String tipo,
                   LocalDate fechaInicio, LocalDate fechaFin,
                   boolean completado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.completado = completado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }
}
